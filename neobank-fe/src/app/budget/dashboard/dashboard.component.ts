import { Component, OnInit } from '@angular/core';
import { BudgetService } from '../budget.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ChangeDetectorRef } from '@angular/core';


@Component({
  selector: 'app-budget-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class BudgetDashboardComponent implements OnInit {

  summary: any[] = [];
  selectedMonth = '2026-05';

  totalLimit = 0;
  totalSpent = 0;
  totalRemaining = 0;

  constructor(
    private service: BudgetService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.load();
  }

  load() {
    this.service.getSummary(this.selectedMonth)
      .subscribe((res: any[]) => {
        this.summary = res;

        this.totalLimit = res.reduce((sum, b) => sum + b.limit, 0);
        this.totalSpent = res.reduce((sum, b) => sum + b.spent, 0);
        this.totalRemaining = res.reduce((sum, b) => sum + b.remaining, 0);

        this.cdr.detectChanges();
      });
  }


  goToCreate() {
    this.router.navigate(['/budget/create']);
  }
}
