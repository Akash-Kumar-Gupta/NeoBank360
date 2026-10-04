import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import {
  Observable,
  forkJoin,
  of,
  switchMap,
  map,
  shareReplay,
  take
} from 'rxjs';

import { ChartConfiguration, ChartType } from 'chart.js';

import { AccountsService, Account } from '../accounts/accounts.service';
import {
  TransactionsService,
  Transaction
} from '../transactions/transactions.service';
import { Page } from '../shared/models/page.model';
import { BaseChartDirective } from 'ng2-charts';

interface DashboardCard {
  title: string;
  description: string;
  icon: string;
  route: string;
}

@Component({
  standalone: true,
  selector: 'app-dashboard',
  imports: [CommonModule, RouterModule, BaseChartDirective],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  accounts$!: Observable<Account[]>;

  selectedAccount: Account | null = null;
  transactionHistory: Transaction[] = [];
  transactionHistoryLoading = false;
  transactionHistoryError = '';
  readonly transactionHistoryPage = 0;

  dashboardCards: DashboardCard[] = [
    {
      title: 'KYC Details',
      description:
        'View and manage identity verification records, submitted documents, and profile compliance details.',
      icon: 'bi bi-person-vcard',
      route: '/kyc-details'
    },

    {
      title: 'Transaction History',
      description:
        'View all transactions for your accounts with detailed history and filters.',
      icon: 'bi bi-clock-history',
      route: '/transactions'
    },
    
    {
      title: 'Budget',
      description:
        'Create and manage monthly budgets, track and monitor utilization in real time.',
      icon: 'bi bi-graph-up',
      route: '/budget'
    },

    { 
      title: 'Bills',
      description: 
        'Manage your bills and payments.',
      icon: 'bi bi-receipt',
      route: '/bills'
    },

    {
      title: 'Rewards',
      description: 
        'View your reward points and history.',
      icon: 'bi bi-gift',
      route: '/rewards'
    },

    {
      title: 'Loans',
      description:
        'Check loan accounts, repayment schedules, balances, EMI information, and related application status.',
      icon: 'bi bi-bank',
      route: '/loan/my-loans'
    },

    {
      title: 'Insights',
      description:
        'Track portfolio summaries, returns, product holdings, and investment performance insights.',
      icon: 'bi bi-bar-chart',
      route: '/insights'
    },

    {
      title: 'Cards',
      description:
        'Open your debit and credit card records, card settings, limits, PIN options, and usage details.',
      icon: 'bi bi-credit-card',
      route: '/cards'
    },

    {
      title: 'Alerts and Notifications',
      description:
        'Stay updated with account alerts, service messages, reminders, security updates, and information.',
      icon: 'bi bi-bell',
      route: '/alerts-notifications'
    },
    {
      title: 'Support',
      description:
        'Reach help resources, FAQs, service contacts, and customer assistance options.',
      icon: 'bi bi-question-circle',
      route: '/support'
    },
    {
      title: 'Legal Information',
      description:
        'Read terms disclosures, privacy notices, compliance documents, and policy-related information.',
      icon: 'bi bi-shield-check',
      route: '/legal-information'
    }
  ];

  balanceChartType: ChartType = 'bar';
  balanceChartData: ChartConfiguration<'bar'>['data'] = {
    labels: [],
    datasets: [
      {
        label: 'Account Balance',
        data: [],
        backgroundColor: '#0d6efd'
      }
    ]
  };

  txnChartType: ChartType = 'line';
  txnChartData: ChartConfiguration<'line'>['data'] = {
    labels: [],
    datasets: [
      {
        label: 'Transaction Amount',
        data: [],
        fill: false,
        borderColor: '#198754',
        tension: 0.2
      }
    ]
  };

  stackedBarChartType: 'bar' = 'bar';
  stackedBarChartData: ChartConfiguration<'bar'>['data'] = {
    labels: [],
    datasets: [
      {
        label: 'Total Credit',
        data: [],
        backgroundColor: '#198754'
      },
      {
        label: 'Total Debit',
        data: [],
        backgroundColor: '#dc3545'
      }
    ]
  };

  stackedBarChartOptions: ChartConfiguration<'bar'>['options'] = {
    responsive: true,
    scales: {
      x: { stacked: true },
      y: { stacked: true, beginAtZero: true }
    }
  };

  constructor(
    private readonly accountsService: AccountsService,
    private readonly transactionsService: TransactionsService,
    private readonly router: Router
  ) {}

  ngOnInit(): void {
    this.accounts$ = this.accountsService.getAccounts().pipe(shareReplay(1));
    this.loadCharts();
    this.loadInitialTransactionHistory();
  }

  private loadInitialTransactionHistory(): void {
    this.accounts$
      .pipe(
        take(1),
        map(accounts => accounts[0] ?? null)
      )
      .subscribe(account => {
        if (account) {
          this.loadTransactionHistory(account, false);
        }
      });
  }

  loadTransactionHistory(account: Account, scroll = true): void {
    this.selectedAccount = account;
    this.transactionHistoryLoading = true;
    this.transactionHistoryError = '';

    this.transactionsService
      .getHistory(account.id, this.transactionHistoryPage)
      .subscribe({
        next: (page: Page<Transaction>) => {
          this.transactionHistory = [...page.content].sort(
            (a, b) =>
              new Date(b.createdAt).getTime() -
              new Date(a.createdAt).getTime()
          );

          this.transactionHistoryLoading = false;

          if (scroll) {
            document
              .getElementById('transaction-history-card')
              ?.scrollIntoView({ behavior: 'smooth', block: 'start' });
          }
        },
        error: () => {
          this.transactionHistory = [];
          this.transactionHistoryLoading = false;
          this.transactionHistoryError =
            'Unable to load transaction history.';
        }
      });
  }

  private loadCharts(): void {
    this.accounts$
      .pipe(
        switchMap(accounts => {
          this.prepareBalanceChart(accounts);

          if (accounts.length === 0) {
            return of({
              accounts,
              transactions: [] as Transaction[]
            });
          }

          const accountIds = accounts.slice(0, 3).map(a => a.id);

          return forkJoin(
            accountIds.map(id =>
              this.transactionsService
                .getHistory(id, 0)
                .pipe(map((page: Page<Transaction>) => page.content))
            )
          ).pipe(
            map(pages => ({
              accounts,
              transactions: pages.flat()
            }))
          );
        })
      )
      .subscribe(({ accounts, transactions }) => {
        this.prepareTransactionChart(transactions);
        this.prepareStackedBarChart(accounts, transactions);
      });
  }

  private prepareBalanceChart(accounts: Account[]): void {
    this.balanceChartData.labels = accounts.map(a => a.accountNumber);
    this.balanceChartData.datasets[0].data = accounts.map(a => a.balance);
  }

  private prepareTransactionChart(transactions: Transaction[]): void {
    const recent = [...transactions]
      .sort(
        (a, b) =>
          new Date(a.createdAt).getTime() -
          new Date(b.createdAt).getTime()
      )
      .slice(-7);

    this.txnChartData.labels = recent.map(t =>
      new Date(t.createdAt).toLocaleDateString()
    );

    this.txnChartData.datasets[0].data = recent.map(t => t.amount);
  }

  private prepareStackedBarChart(
    accounts: Account[],
    transactions: Transaction[]
  ): void {
    const creditMap = new Map<number, number>();
    const debitMap = new Map<number, number>();

    accounts.forEach(account => {
      creditMap.set(account.id, 0);
      debitMap.set(account.id, 0);
    });

    transactions.forEach(txn => {
      if (txn.type === 'CREDIT') {
        creditMap.set(
          txn.accountId,
          (creditMap.get(txn.accountId) ?? 0) + txn.amount
        );
      }

      if (txn.type === 'DEBIT') {
        debitMap.set(
          txn.accountId,
          (debitMap.get(txn.accountId) ?? 0) + txn.amount
        );
      }
    });

    this.stackedBarChartData.labels = accounts.map(a => a.accountNumber);

    this.stackedBarChartData.datasets[0].data = accounts.map(
      a => creditMap.get(a.id) ?? 0
    );

    this.stackedBarChartData.datasets[1].data = accounts.map(
      a => debitMap.get(a.id) ?? 0
    );
  }

  goToAccounts(): void {
    this.router.navigate(['/accounts']);
  }

  goToTransactions(account: Account): void {
    this.loadTransactionHistory(account);
  }

  viewAllTransactions(): void {
    if (this.selectedAccount) {
      this.router.navigate(['/transactions', this.selectedAccount.id]);
    }
  }

  navigateTo(route: string): void 
  {

    if (route === '/transactions') 
    {
      this.accounts$.pipe(take(1)).subscribe(accounts => 
      {
        if (accounts.length > 0) {
          this.router.navigate(['/transactions', accounts[0].id]);
        } else {
          alert('No account found');
        }
      });

      return;
    }

    this.router.navigate([route]);
  }

  scrollToTop(): void {
    window.scrollTo({
      top: 0,
      behavior: 'smooth'
    });
  }
}
