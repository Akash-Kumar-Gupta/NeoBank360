import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SystemHealthService {

  private api =
    'http://localhost:8080/api/admin/system-health';

  constructor(private http: HttpClient) {}

  getSystemHealth() {
    return this.http.get<any>(this.api);
  }
}