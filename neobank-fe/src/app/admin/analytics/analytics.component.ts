import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { AnalyticsService } from './analytics.service';
import { BaseChartDirective } from 'ng2-charts';

@Component({
  selector: 'app-analytics',
  standalone: true,
  imports: [CommonModule, MatCardModule, BaseChartDirective],
  templateUrl: './analytics.component.html',
  styleUrls: ['./analytics.component.css']
})
export class AnalyticsComponent implements OnInit {

  data: any;

  loading = true;
  error = false;

  currentPage = 1;
  pageSize = 10;

  timeframe: string = '7d';

  constructor(
    private service: AnalyticsService,
    private cdr: ChangeDetectorRef
  ) {}

  // ✅ TRANSACTION CHART
  transactionChartData: any = {
    labels: [],
    datasets: [
      {
        data: [],
        label: 'Inflow',
        borderColor: '#22C55E',
        backgroundColor: 'rgba(34,197,94,0.2)',
        fill: true,
        tension: 0.4
      },
      {
        data: [],
        label: 'Outflow',
        borderColor: '#EF4444',
        backgroundColor: 'rgba(239,68,68,0.2)',
        fill: true,
        tension: 0.4
      }
    ]
  };

  // ✅ LOAN CHART
  loanChartData: any = {
    labels: [],
    datasets: [
      {
        data: [],
        label: 'Loan Distribution',
        backgroundColor: ['#3B82F6', '#22C55E', '#EF4444']
      }
    ]
  };

  ngOnInit(): void {
    this.loadAnalytics();
    this.loadTransactionAnalytics();
    this.loadLoanAnalytics();
  }

  // ✅ MAIN ANALYTICS
  loadAnalytics() {
    this.loading = true;

    this.service.getAnalytics().subscribe({
      next: (res) => {
        this.data = res;
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: () => {
        this.error = true;
        this.loading = false;
      }
    });
  }

  // ✅ ✅ UPDATED TRANSACTION CHART (REAL DATES)
  loadTransactionAnalytics() {
    this.service.getTransactionAnalytics(this.timeframe)
      .subscribe(res => {

        console.log("Transaction API Response:", res);

        // ✅ safety check
        if (!res || !res.dailyInflow || res.dailyInflow.length === 0) {
          this.transactionChartData = {
            labels: [],
            datasets: [
              { data: [], label: 'Inflow' },
              { data: [], label: 'Outflow' }
            ]
          };
          return;
        }

        const labels = Array.from({ length: res.dailyInflow.length }, (_, i) => {
          const date = new Date();
          date.setDate(date.getDate() - (res.dailyInflow.length - 1 - i));
          return date.toLocaleDateString('en-IN', {
            day: '2-digit',
            month: 'short'
          });
        });

        this.transactionChartData = {
          labels: labels,
          datasets: [
            {
              data: res.dailyInflow,
              label: 'Inflow',
              borderColor: '#22C55E',
              backgroundColor: 'rgba(34,197,94,0.2)',
              fill: true,
              tension: 0.4
            },
            {
              data: res.dailyOutflow,
              label: 'Outflow',
              borderColor: '#EF4444',
              backgroundColor: 'rgba(239,68,68,0.2)',
              fill: true,
              tension: 0.4
            }
          ]
        };
      });
  }

  // ✅ LOAN CHART
  loadLoanAnalytics() {
    this.service.getLoanAnalytics(this.timeframe)
      .subscribe(res => {

        this.loanChartData = {
          labels: Object.keys(res.loanDistribution),
          datasets: [
            {
              data: Object.values(res.loanDistribution),
              label: 'Loan Distribution',
              backgroundColor: ['#3B82F6', '#22C55E', '#EF4444']
            }
          ]
        };
      });
  }

  // ✅ TIMEFRAME SWITCH
  changeTimeframe(tf: string) {
    this.timeframe = tf;
    this.loadTransactionAnalytics();
    this.loadLoanAnalytics();
  }

  // ✅ PAGINATION
  get paginatedTransactions() {
    if (!this.data?.transactions) return [];
    const start = (this.currentPage - 1) * this.pageSize;
    return this.data.transactions.slice(start, start + this.pageSize);
  }

  get totalPages() {
    if (!this.data?.transactions) return 0;
    return Math.ceil(this.data.transactions.length / this.pageSize);
  }

  nextPage() {
    if (this.currentPage < this.totalPages) this.currentPage++;
  }

  previousPage() {
    if (this.currentPage > 1) this.currentPage--;
  }
}
