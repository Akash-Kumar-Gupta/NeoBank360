import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

import {
  AdminService,
  AdminSummary,
  AdminTransaction
} from './admin.service';

@Component({
  standalone: true,
  selector: 'app-admin',
  imports: [CommonModule],
  templateUrl: './admin.component.html'
})
export class AdminComponent implements OnInit {

  summary?: AdminSummary;
  transactions: AdminTransaction[] = [];

  loading = true;

  constructor(private readonly adminService: AdminService) {}

  ngOnInit(): void {
    this.loadDashboard();
  }

  private loadDashboard(): void {
    this.loading = true;

    this.adminService.getSummary().subscribe(summary => {
      this.summary = summary;
    });

    this.adminService.getRecentTransactions().subscribe(txns => {
      this.transactions = txns;
      this.loading = false;
    });
  }
}
