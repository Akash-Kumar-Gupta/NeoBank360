import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoanProductsService {

  // ✅ FIXED: Updated API URL to match backend
  private apiUrl = 'http://localhost:8080/api/loans/products';

  constructor(private http: HttpClient) {}

  // ✅ Get all loan products
  getAll(): Observable<any> {
    return this.http.get(this.apiUrl);
  }

  // ✅ Create new loan product
  createLoan(data: any): Observable<any> {
    return this.http.post(this.apiUrl, data);
  }

  // ✅ Toggle active/inactive status
  toggleStatus(id: number): Observable<any> {
    return this.http.patch(`${this.apiUrl}/${id}/toggle`, {});
  }
}
