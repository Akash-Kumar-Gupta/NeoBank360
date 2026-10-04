import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class LoanService {

  private baseUrl = '/api/loans'; // uses proxy

  constructor(private http: HttpClient) {}

  // CUSTOMER

  getProducts() {
    return this.http.get<any[]>(`${this.baseUrl}/products`);
  }

  applyLoan(data: any) {
    return this.http.post(`${this.baseUrl}/apply`, data);
  }

  getMyAccounts() {
    return this.http.get<any[]>(`${this.baseUrl}/my-accounts`);
  }

  getRepayments(accountId: number) {
    return this.http.get<any[]>(`${this.baseUrl}/${accountId}/repayments`);
  }

  // ADMIN

  getApplications() {
    return this.http.get<any[]>(`${this.baseUrl}/admin/applications`);
  }

  decideApplication(id: number, data: any) {
    return this.http.put(`${this.baseUrl}/${id}/decision`, data);
  }

  createProduct(data: any) {
    return this.http.post(`${this.baseUrl}/products`, data);
  }
}
