import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-loan-decisions',

  standalone: true, 

  imports: [CommonModule], 

  templateUrl: './loan-decisions.component.html',
  styleUrls: ['./loan-decisions.component.css']
})
export class LoanDecisionsComponent implements OnInit {

  applications: any[] = [];
  loading = true;  // <-- IMPORTANT change

  constructor(
  private http: HttpClient,
  private cd: ChangeDetectorRef 
) {}

  ngOnInit(): void {
    this.loadApplications();
  }

  loadApplications() {
  this.http.get<any[]>('http://localhost:8080/api/loans/all')
    .subscribe({
      next: (res) => {
        console.log("✅ API:", res);

        this.applications = res;
        this.loading = false;

        this.cd.detectChanges(); 
      },
      error: () => {
        this.loading = false;

        this.cd.detectChanges(); 
      }
    });
  }

  approve(id: number) {
    this.http.put(`http://localhost:8080/api/loans/${id}/decision`, {
      decision: 'APPROVE'
    }).subscribe(() => this.loadApplications());
  }

  reject(id: number) {
    this.http.put(`http://localhost:8080/api/loans/${id}/decision`, {
      decision: 'REJECT'
    }).subscribe(() => this.loadApplications());
  }
}
