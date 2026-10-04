import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChangeDetectorRef } from '@angular/core';
import { AdminService, AdminUser } from '../admin.service';

@Component({
    standalone: true,
    selector: 'app-admin-users',
    imports: [CommonModule],
    templateUrl: './users.component.html'
})
export class AdminUsersComponent implements OnInit {

    users: AdminUser[] = [];
    loading = true;

    constructor(
        private readonly adminService: AdminService,
        private readonly cdr: ChangeDetectorRef   // ✅ add this
    ) { }

    ngOnInit(): void {
        this.loadUsers();
    }


    loadUsers(): void {
        this.loading = true;

        this.adminService.getAllUsers().subscribe({
            next: (data) => {
                console.log('✅ Users received:', data);
                this.users = data;
                this.loading = false;

                this.cdr.detectChanges(); // ✅ FORCE UI UPDATE
            },
            error: (err) => {
                console.error(err);
                this.loading = false;

                this.cdr.detectChanges(); // ✅ ALSO HERE
            }
        });
    }


    activate(userId: number): void {
        if (!confirm('Activate this user?')) return;

        this.adminService.activateUser(userId)
            .subscribe(() => this.loadUsers());
    }

    deactivate(userId: number): void {
        if (!confirm('Deactivate this user?')) return;

        this.adminService.deactivateUser(userId)
            .subscribe(() => this.loadUsers());
    }
}