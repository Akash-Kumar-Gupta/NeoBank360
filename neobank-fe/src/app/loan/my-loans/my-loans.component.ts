import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ChangeDetectorRef } from '@angular/core';
import { Router } from '@angular/router';


@Component({
  selector: 'app-my-loans',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './my-loans.component.html',
  styleUrls: ['./my-loans.component.css']
})
export class MyLoansComponent implements OnInit {

  loans: any[] = [];

  total = 0;
  pending = 0;
  approved = 0;
  rejected = 0;

  constructor(
  private http: HttpClient,
  private cd: ChangeDetectorRef,
  private router: Router 
  ) {}



  ngOnInit(): void {
    this.loadLoans();
  }

  loadLoans() {
    this.http.get<any[]>('http://localhost:8080/api/loans/my')
      .subscribe({
        next: (res) => {
          this.loans = res;

          // ✅ Calculate counts
          this.total = res.length;
          this.pending = res.filter(l => l.status === 'PENDING').length;
          this.approved = res.filter(l => l.status === 'APPROVED').length;
          this.rejected = res.filter(l => l.status === 'REJECTED').length;
          this.cd.detectChanges();
        },
        error: () => {
          alert('Failed to load loans');
        }
      });
  }

  goToRepayment(id: number, status: string)
  {

    if (status !== 'APPROVED') 
    {
      return;
    }

    const headers = {
      headers: {
        Authorization: 'Bearer ' + localStorage.getItem('token')
      }
    };

    this.http.get<any>(`http://localhost:8080/api/loan-accounts/by-application/${id}`, headers).subscribe({ next: (res) => 
    {

      const loanAccountId = res.id;

      console.log('Mapped Loan Account ID:', loanAccountId);

      // ✅ STEP 2: navigate with correct ID
      this.router.navigate(['/loan/repayment', loanAccountId]);
    },
    
    error: () => {
      alert('Loan account not found');
    }
  });
  
  }


}