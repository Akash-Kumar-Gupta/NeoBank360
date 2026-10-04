import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AnalyticsService 
{
  private api = 'http://localhost:8080/api/admin/analytics';

  constructor(private http: HttpClient) {}

  getAnalytics() {
    return this.http.get<any>(this.api);
  }

  // ✅ Transaction Analytics
  getTransactionAnalytics(timeframe: string) {
    return this.http.get<any>(`${this.api}/transactions?timeframe=${timeframe}`);
  }

  // ✅ Loan Analytics
  getLoanAnalytics(timeframe: string) {
    return this.http.get<any>(`${this.api}/loans?timeframe=${timeframe}`);
  }
}