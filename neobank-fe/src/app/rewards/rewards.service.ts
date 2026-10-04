import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Reward {
  id?: number;
  userId?: number;
  points: number;
  description: string;
  createdAt: string;
}

@Injectable({
  providedIn: 'root'
})
export class RewardsService {

  private baseUrl = '/api/rewards';

  constructor(private http: HttpClient) {}

  getRewards(): Observable<Reward[]> {
    return this.http.get<Reward[]>(`${this.baseUrl}/2`);
  }
}