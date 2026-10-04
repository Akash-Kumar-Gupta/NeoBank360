import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { AdminService, AccountOpeningRequest } from '../admin.service';

@Component({
  standalone: true,
  selector: 'app-account-requests',
  imports: [CommonModule],
  templateUrl: './account-requests.component.html'
})
export class AccountRequestsComponent implements OnInit {

  requests: AccountOpeningRequest[] = [];
  loading = true;

  constructor(
    private readonly adminService: AdminService,
    private readonly cdr: ChangeDetectorRef   // ✅ ADD THIS
  ) {}

  ngOnInit(): void {
    this.loadRequests();
  }

  loadRequests(): void {
    this.loading = true;

    this.adminService.getPendingAccountRequests().subscribe({
      next: (data: AccountOpeningRequest[]) => {
        this.requests = data;
        this.loading = false;

        this.cdr.detectChanges();  // ✅ FORCE UI UPDATE
      },
      error: (err: HttpErrorResponse) => {
        console.error(err);
        this.loading = false;

        this.cdr.detectChanges();  // ✅ ALSO HERE
      }
    });
  }

  approve(id: number): void {
    if (!confirm('Approve this request?')) return;
    this.adminService.approveAccountRequest(id)
      .subscribe(() => this.loadRequests());
  }

  reject(id: number): void {
    if (!confirm('Reject this request?')) return;
    this.adminService.rejectAccountRequest(id)
      .subscribe(() => this.loadRequests());
  }
}