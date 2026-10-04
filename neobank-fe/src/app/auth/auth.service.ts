import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {

  private readonly api = '/api/auth';

  // ✅ REAL-TIME USERNAME STATE
  private userNameSubject = new BehaviorSubject<string>('User');
  userName$ = this.userNameSubject.asObservable();

  constructor(private readonly http: HttpClient) {}

  /* =========================
     AUTH APIs
  ========================= */

  login(data: { email: string; password: string }) {
    return this.http.post<{ token: string }>(
      `${this.api}/login`,
      data
    );
  }

  register(data: {
    fullName: string;
    email: string;
    password: string;
  }) {
    return this.http.post<void>(
      `${this.api}/register`,
      data
    );
  }

  /* =========================
     ROLE
  ========================= */

  get role(): 'ADMIN' | 'CUSTOMER' | null {
    const payload = this.getPayload();
    return payload?.role ?? null;
  }

  isAdmin(): boolean {
    return this.role === 'ADMIN';
  }

  /* =========================
     TOKEN HANDLING
  ========================= */

  saveToken(token: string): void {
    sessionStorage.setItem('token', token);

    const payload = this.getPayloadFromToken(token);
    console.log("✅ TOKEN PAYLOAD:", payload);

    const name =
      payload?.fullName ||
      payload?.name ||
      this.extractNameFromEmail(payload?.sub) ||
      'User';

    // ✅ SAVE TO LOCAL STORAGE
    localStorage.setItem('username', name);

    // ✅ 👇 MOST IMPORTANT FIX (REAL-TIME UPDATE)
    this.userNameSubject.next(name);
  }

  get token(): string | null {
    return sessionStorage.getItem('token');
  }

  logout(): void {
    sessionStorage.removeItem('token');
    localStorage.removeItem('username');

    // ✅ RESET UI INSTANTLY
    this.userNameSubject.next('User');
  }

  /* =========================
     USER NAME (NAVBAR)
  ========================= */

  getUserName(): string {
    const name = localStorage.getItem('username') || 'User';

    // ✅ sync on refresh
    this.userNameSubject.next(name);

    return name;
  }

  /* =========================
     TOKEN DECODING
  ========================= */

  private getPayload(): any {
    const token = this.token;
    if (!token) return null;

    return this.getPayloadFromToken(token);
  }

  private getPayloadFromToken(token: string): any {
    try {
      return JSON.parse(atob(token.split('.')[1]));
    } catch (e) {
      return null;
    }
  }

  /* =========================
     EXTRACT NAME FROM EMAIL
  ========================= */

  private extractNameFromEmail(email: string): string {
    if (!email) return 'User';

    const raw = email.split('@')[0];

    // ✅ Capitalize first letter
    return raw.charAt(0).toUpperCase() + raw.slice(1);
  }
}
