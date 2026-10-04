import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { LoanProductsService } from '../../admin/loans/loan-products/loan-products.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-apply',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './apply.component.html',
  styleUrls: ['./apply.component.css']
})
export class ApplyComponent implements OnInit {

  products: any[] = [];
  selectedProduct: any;

  amount: number = 0;
  tenure: number = 0;
  tenures: number[] = [];

  // ✅ EMI VARIABLES
  emi: number = 0;
  interestRate: number = 0;
  showEmi: boolean = false;

  constructor(
    private service: LoanProductsService,
    private http: HttpClient
  ) {}

  ngOnInit() {
    this.service.getAll().subscribe(data => {
      this.products = data;
    });
  }

  onProductChange() {
    if (this.selectedProduct) {
      this.tenures = this.selectedProduct.allowedTenures
        .split(',')
        .map((t: string) => Number(t));
    }
  }

  // ✅ FORM VALIDATION
  isFormValid(): boolean {
    return (
      this.selectedProduct &&
      this.amount > 0 &&
      this.tenure > 0
    );
  }

  // ✅ EMI PREVIEW FUNCTION
  previewEmi() {

    if (!this.selectedProduct) return;

    // ✅ get interest rate
    this.interestRate = this.selectedProduct.annualInterestRate;

    const P = this.amount;
    const N = this.tenure;
    const R = this.interestRate / 12 / 100;

    // ✅ EMI FORMULA
    this.emi =
      (P * R * Math.pow(1 + R, N)) /
      (Math.pow(1 + R, N) - 1);

    this.showEmi = true;
  }

  // ✅ CLOSE POPUP
  closeEmi() {
    this.showEmi = false;
  }

  // ✅ APPLY LOAN
  applyLoan() {

    const payload = {
      loanProductId: this.selectedProduct.id,
      amount: this.amount,
      tenure: this.tenure
    };

    this.http.post('http://localhost:8080/api/loans/apply', payload)
      .subscribe({
        next: () => {
          alert('Loan applied successfully ✅');

          // ✅ reset form (optional but clean)
          this.amount = 0;
          this.tenure = 0;
          this.selectedProduct = null;
          this.tenures = [];
        },
        error: (err) => {
          console.error(err);
          alert('Application failed ❌');
        }
      });
  }
}
