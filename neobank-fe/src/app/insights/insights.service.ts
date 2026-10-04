import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class InsightsService {

  private baseUrl = 'http://localhost:8080/api/insights';

  constructor(private http: HttpClient) {}

  getInsights() {
    return this.http.get<any>(`${this.baseUrl}`);
  }
}
