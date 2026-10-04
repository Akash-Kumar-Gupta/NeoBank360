import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface BudgetSummary {
  category: string;
  limit: number;
  spent: number;
  remaining: number;
  percentUsed: number;
}

@Injectable({ providedIn: 'root' })
export class BudgetService {

  private baseUrl = '/api/budgets';

  constructor(private http: HttpClient) {}

  createBudget(data: any): Observable<any> {
    return this.http.post<any>(this.baseUrl, data);
  }

  getSummary(month: string): Observable<BudgetSummary[]> {
    return this.http.get<BudgetSummary[]>(`${this.baseUrl}/${month}`);
  }
}