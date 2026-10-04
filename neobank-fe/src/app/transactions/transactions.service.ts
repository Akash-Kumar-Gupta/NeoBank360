import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Page } from '../shared/models/page.model';

export interface Transaction {
  id: number;
  accountId: number;
  type: 'CREDIT' | 'DEBIT' | 'TRANSFER';
  amount: number;
  balanceAfter: number;
  createdAt: string;
  description?: string;

  category: string;
}

@Injectable({ providedIn: 'root' })
export class TransactionsService {

  private readonly api = '/api/transactions';

  constructor(private readonly http: HttpClient) {}

  /** ✅ Get transaction history */
  getHistory(
    accountId: number,
    page: number,
    type?: string
  ): Observable<Page<Transaction>> {

    const params: any = { page };

    if (type) {
      params.type = type;
    }

    return this.http.get<Page<Transaction>>(
      `${this.api}/${accountId}`,
      { params }
    );
  }

  /** ✅ Credit money */
  credit(accountId: number, amount: number, category: string): Observable<Transaction> {
    return this.http.post<Transaction>(
      `${this.api}/credit`,
      null,
      {
        params: {
          accountId: accountId,
          amount: amount,
          category: category
        }
      }
    );
  }

  /** ✅ Debit money */
  debit(accountId: number, amount: number, category: string): Observable<Transaction> {
    return this.http.post<Transaction>(
      `${this.api}/debit`,
      null,
      {
        params: {
          accountId: accountId,
          amount: amount,
          category: category
        }
      }
    );
  }

  /** ✅ Transfer money */
  transfer(
    fromAccountId: number,
    toAccountNumber: string,
    amount: number,
    category: string
  ): Observable<void> {
    return this.http.post<void>(
      `${this.api}/transfer`,
      null,
      {
        params: {
          fromAccountId: fromAccountId,
          toAccountNumber: toAccountNumber,
          amount: amount,
          category: category
        }
      }
    );
  }
}
