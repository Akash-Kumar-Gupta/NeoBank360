import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-repayment',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './repayment.component.html',
  styleUrls: ['./repayment.component.css']
})
export class RepaymentComponent implements OnInit {

  loanAccountId!: number;

  loanDetails: any = {};
  repayments: any[] = [];
  accounts: any[] = [];

  selectedAccountId: number | null = null;

  successMessage: string = '';
  errorMessage: string = '';

  constructor(
    private route: ActivatedRoute,
    private http: HttpClient,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loanAccountId = Number(this.route.snapshot.paramMap.get('id'));

    this.loadLoanDetails();
    this.loadRepayments();
    this.loadAccounts();
  }

  // ✅ LOAD LOAN
  loadLoanDetails() {
    this.http.get(`http://localhost:8080/api/loan-accounts/${this.loanAccountId}`)
      .subscribe({
        next: (res) => {
          this.loanDetails = res;
          this.cdr.detectChanges();
        }
      });
  }

  // ✅ LOAD EMI LIST
  loadRepayments() {
    this.http.get<any[]>(`http://localhost:8080/api/loan-repayments/${this.loanAccountId}`)
      .subscribe({
        next: (res) => {
          this.repayments = res || [];
          this.cdr.detectChanges();
        },
        error: (err) => {
          console.error('Error loading repayments:', err);
        }
      });
  }

  // ✅ LOAD ACCOUNTS
  loadAccounts() {
    this.http.get<any[]>('http://localhost:8080/api/accounts')
      .subscribe(res => {
        this.accounts = res;
        this.cdr.detectChanges();
      });
  }

  // ✅ ✅ ✅ PAY EMI
  payEmi(repaymentId: number) {

  if (!this.selectedAccountId) {
    this.errorMessage = 'Please select account ❌';
    return;
  }

  this.http.post(
    `http://localhost:8080/api/loan-repayments/pay`,
    {
      repaymentId: repaymentId,
      accountId: this.selectedAccountId
    },
    {
      headers: {
        Authorization: 'Bearer ' + localStorage.getItem('token')
      },
      responseType: 'text'
    }
  ).subscribe({
    next: () => {

      // ✅ SUCCESS
      this.successMessage = 'Loan paid successfully ✅';
      this.errorMessage = '';

      setTimeout(() => {
      this.successMessage = '';
      this.errorMessage = '';

      this.cdr.detectChanges();
      }, 2500);

      const item = this.repayments.find(r => r.id === repaymentId);

      if (item) {
        item.paymentStatus = 'PAID';
        this.repayments = [...this.repayments];
      }

      this.cdr.detectChanges();
    },

    error: (err) => {
      console.error(err);

      this.errorMessage = 'Payment failed ❌';

      setTimeout(() => {
      this.errorMessage = '';
      this.cdr.detectChanges();
      }, 2500);
    }
  });
  }
}