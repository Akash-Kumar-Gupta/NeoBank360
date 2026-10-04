import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface AdminSummary {
    totalUsers: number;
    totalAccounts: number;
    totalTransactions: number;
    totalTransactionAmount: number;
}

export interface AdminTransaction {
    id: number;
    accountId: number;
    type: 'CREDIT' | 'DEBIT' | 'TRANSFER';
    amount: number;
    createdAt: string;
}


export interface AccountOpeningRequest {
    id: number;
    fullName: string;
    email: string;
    aadhaarNumber: string;
    panNumber: string;
    dob: string;
    phone: string;
    address: string;
    accountType: 'SAVINGS' | 'CURRENT';
    status: 'PENDING' | 'APPROVED' | 'REJECTED';
    createdAt: string;
}


export interface AdminUser {
    id: number;
    fullName: string;
    email: string;
    role: 'ADMIN' | 'CUSTOMER';
    active: boolean;
    createdAt: string;
}


@Injectable({ providedIn: 'root' })
export class AdminService {

    private readonly api = '/api/admin';
    private readonly accountOpeningApi = '/api/account-opening/admin';

    constructor(private readonly http: HttpClient) { }

    /** ✅ System KPIs */
    getSummary(): Observable<AdminSummary> {
        return this.http.get<AdminSummary>(`${this.api}/summary`);
    }

    /** ✅ Recent system-wide transactions */
    getRecentTransactions(): Observable<AdminTransaction[]> {
        return this.http.get<AdminTransaction[]>(
            `${this.api}/transactions/recent`
        );
    }


    getPendingAccountRequests(): Observable<AccountOpeningRequest[]> {
        return this.http.get<AccountOpeningRequest[]>(
            `${this.accountOpeningApi}/requests`
        );
    }

    approveAccountRequest(id: number): Observable<void> {
        return this.http.post<void>(
            `${this.accountOpeningApi}/approve/${id}`,
            {}
        );
    }

    rejectAccountRequest(id: number): Observable<void> {
        return this.http.post<void>(
            `${this.accountOpeningApi}/reject/${id}`,
            {}
        );
    }


    getAllUsers(): Observable<AdminUser[]> {
        return this.http.get<AdminUser[]>(`${this.api}/users`);
    }


    activateUser(id: number): Observable<void> {
        return this.http.put<void>(
            `${this.api}/users/${id}/activate`,
            {}
        );
    }


    deactivateUser(id: number): Observable<void> {
        return this.http.put<void>(
            `${this.api}/users/${id}/deactivate`,
            {}
        );
    }


}
