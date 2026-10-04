import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { BaseChartDirective } from 'ng2-charts';
import { InsightsService } from '../insights/insights.service';
import { ChartType } from 'chart.js';

@Component({
  selector: 'app-insights',
  standalone: true,
  imports: [CommonModule, MatCardModule, BaseChartDirective],
  templateUrl: './insights.component.html',
  styleUrls: ['./insights.component.css']
})
export class InsightsComponent implements OnInit {

  data: any;
  error = false;

  // ✅ COMMON CHART OPTIONS
  chartOptions: any = {
    responsive: true,
    maintainAspectRatio: false,
    scales: {
      y: {
        beginAtZero: true,
        ticks: {
          callback: (value: any) => '₹ ' + value
        }
      }
    },
    plugins: {
      legend: { position: 'top' }
    }
  };

  // ✅ LINE CHART
  lineChartType: ChartType = 'line';
  lineChartData: any = {
    labels: [],
    datasets: [
      {
        label: 'Income',
        data: [],
        borderColor: '#22C55E',
        backgroundColor: 'rgba(34,197,94,0.2)',
        fill: true,
        tension: 0.4
      },
      {
        label: 'Expense',
        data: [],
        borderColor: '#EF4444',
        backgroundColor: 'rgba(239,68,68,0.2)',
        fill: true,
        tension: 0.4
      }
    ]
  };

  // ✅ BAR CHART
  barChartType: ChartType = 'bar';
  barChartData: any = {
    labels: [],
    datasets: [
      {
        label: 'Income',
        data: [],
        backgroundColor: '#22C55E'
      },
      {
        label: 'Expense',
        data: [],
        backgroundColor: '#EF4444'
      }
    ]
  };

  // ✅ SAVINGS CHART
  savingsChartType: ChartType = 'bar';
  savingsChartData: any = {
    labels: [],
    datasets: [
      {
        label: 'Savings',
        data: [],
        backgroundColor: '#1E40AF'
      }
    ]
  };

  // ✅ ✅ UPDATED PIE CHART (DYNAMIC FROM BACKEND)
  pieChartType: ChartType = 'doughnut';
  pieChartData: any = {
    labels: [],
    datasets: [
      {
        data: [],
        backgroundColor: [
          '#EF4444',
          '#3B82F6',
          '#22C55E',
          '#F59E0B',
          '#8B5CF6'
        ]
      }
    ]
  };

  constructor(
    private insightsService: InsightsService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadInsights();
  }

  // ✅ LOAD DATA (CLEAN METHOD)
  loadInsights() {
    this.insightsService.getInsights().subscribe({
      next: (res: any) => {
        this.data = res;

        this.prepareCharts(res.trendSummary);
        this.preparePieChart(res);   // ✅ NEW

        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error(err);
        this.error = true;
        this.cdr.detectChanges();
      }
    });
  }

  // ✅ REFRESH BUTTON
  refresh() {
    this.error = false;
    this.data = null;
    this.loadInsights();
  }

  // ✅ PREPARE LINE + BAR + SAVINGS
  prepareCharts(trend: any[]) {
    if (!trend || trend.length === 0) return;

    const labels = trend.map(t => t.month);
    const income = trend.map(t => t.income);
    const expense = trend.map(t => t.expense);
    const savings = trend.map(t => t.income - t.expense);

    // Line
    this.lineChartData.labels = labels;
    this.lineChartData.datasets[0].data = income;
    this.lineChartData.datasets[1].data = expense;

    // Bar
    this.barChartData.labels = labels;
    this.barChartData.datasets[0].data = income;
    this.barChartData.datasets[1].data = expense;

    // Savings
    this.savingsChartData.labels = labels;
    this.savingsChartData.datasets[0].data = savings;
  }

  // ✅ ✅ NEW: REAL CATEGORY PIE CHART
  preparePieChart(res: any) {
    if (!res.categories || !res.categoryAmounts) return;

    this.pieChartData.labels = res.categories;
    this.pieChartData.datasets[0].data = res.categoryAmounts;
  }
}