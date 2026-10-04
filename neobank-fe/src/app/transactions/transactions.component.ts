import { FormsModule } from '@angular/forms';
import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import {
  ReactiveFormsModule,
  FormBuilder,
  Validators,
  FormControl,
  FormGroup
} from '@angular/forms';

import { TransactionsService, Transaction } from './transactions.service';
import { ConfirmService } from '../dialog/confirm.service';

@Component({
  standalone: true,
  selector: 'app-transactions',
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
  templateUrl: './transactions.component.html'
})
export class TransactionsComponent implements OnInit {

  // ---- ROUTE ----
  accountId!: number;

  // ---- PAGINATION ----
  page = 0;
  totalPages = 0;

  // ---- FILTER ----
  filterType = '';
  loading = true;

  // ---- DATA ----
  transactions: Transaction[] = [];

  // ✅ CATEGORY LIST
  categories = [
    'GROCERIES',
    'UTILITIES',
    'RENT',
    'ENTERTAINMENT',
    'TRANSFER',
    'OTHER'
  ];

  // ---- FORM (UPDATED ✅) ----
  form!: FormGroup<{
    amount: FormControl<number>;
    toAccount: FormControl<string>;
    category: FormControl<string>;
  }>;

  constructor(
    private readonly route: ActivatedRoute,
    private readonly fb: FormBuilder,
    private readonly service: TransactionsService,
    private readonly confirmService: ConfirmService,
    private readonly cdr: ChangeDetectorRef
  ) {
    // ✅ CATEGORY ADDED TO FORM
    this.form = this.fb.nonNullable.group({
      amount: [0, [Validators.required, Validators.min(1)]],
      toAccount: [''],
      category: ['', Validators.required] // ✅ IMPORTANT
    });
  }

  ngOnInit(): void {
    this.accountId = Number(
      this.route.snapshot.paramMap.get('accountId')
    );
    this.load();
  }

  // ---- LOAD HISTORY ----
  load(): void {
    this.loading = true;

    this.service
      .getHistory(this.accountId, this.page, this.filterType)
      .subscribe({
        next: (pageData) => {
          this.transactions = pageData.content;
          this.totalPages = pageData.totalPages;
          this.loading = false;
          this.cdr.detectChanges();
        },
        error: (err) => {
          console.error(err);
          this.loading = false;
          this.cdr.detectChanges();
        }
      });
  }

  // ---- PAGINATION ----
  next(): void {
    if (this.page < this.totalPages - 1) {
      this.page++;
      this.load();
    }
  }

  prev(): void {
    if (this.page > 0) {
      this.page--;
      this.load();
    }
  }

  // ---- ACTIONS ----

  credit(): void {
    const { amount, category } = this.form.getRawValue();

    if (!category) {
      alert('Please select a category');
      return;
    }

    this.service.credit(this.accountId, amount, category).subscribe(() => {
      this.page = 0;
      this.load();
    });
  }

  debit(): void {
    const { amount, category } = this.form.getRawValue();

    if (!category) {
      alert('Please select a category');
      return;
    }

    this.service.debit(this.accountId, amount, category).subscribe(() => {
      this.page = 0;
      this.load();
    });
  }

  confirmDebit(): void {
    this.confirmService.open(
      'Are you sure you want to debit this amount?',
      () => this.debit()
    );
  }

  transfer(): void {
    const { amount, toAccount, category } = this.form.getRawValue();

    if (!toAccount || !toAccount.trim()) return;

    if (!category) {
      alert('Please select a category');
      return;
    }

    this.service
      .transfer(this.accountId, toAccount, amount, category)
      .subscribe(() => {
        this.page = 0;
        this.filterType = '';
        this.load();
      });
  }
}
