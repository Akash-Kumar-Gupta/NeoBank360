import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Bill {
  id?: number;
  userId?: number;
  name: string;
  category: string;
  amount: number;
  dueDate: string;
  status?: string;
  paidDate?: string;
}

@Injectable({
  providedIn: 'root'
})
export class BillsService {

  private baseUrl = '/api/bills';

  constructor(private http: HttpClient) {}

  // ✅ CREATE BILL (NO userId)
  createBill(data: Bill): Observable<any> {
    return this.http.post(this.baseUrl, data);
  }

  // ✅ GET BILLS (NO userId in URL)
  getBills(): Observable<Bill[]> {
    return this.http.get<Bill[]>(this.baseUrl);
  }

  // ✅ MARK AS PAID
  markPaid(id: number): Observable<any> {
    return this.http.put(`${this.baseUrl}/${id}/pay`, {});
  }
}
