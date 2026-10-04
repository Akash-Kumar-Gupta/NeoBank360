import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Account {
  id: number;
  accountNumber: string;
  accountType: 'SAVINGS' | 'CURRENT';
  balance: number;
  createdAt: string;
}

@Injectable({ providedIn: 'root' })
export class AccountsService {

  private readonly api = '/api/accounts';

  constructor(private readonly http: HttpClient) {}

  /** ✅ Fetch all user accounts */
  getAccounts(): Observable<Account[]> {
    return this.http.get<Account[]>(this.api);
  }

  /** ✅ Create new account */
  createAccount(type: 'SAVINGS' | 'CURRENT'): Observable<Account> {
    return this.http.post<Account>(`${this.api}?type=${type}`, {});
  }
}