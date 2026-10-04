import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms'; // ✅ REQUIRED
import { CommonModule } from '@angular/common'; // ✅ REQUIRED
import { LoanProductsService } from '../loan-products/loan-products.service';


@Component({
  selector: 'app-create-loan-product',
  standalone: true, // ✅ IMPORTANT
  imports: [CommonModule, FormsModule], // ✅ REQUIRED
  templateUrl: './create-loan-product.component.html',
  styleUrls: ['./create-loan-product.component.css']
})
export class CreateLoanProductComponent {

  // ✅ MATCH BACKEND DTO STRUCTURE
  loan = {
    productName: '',
    minAmount: 0,
    maxAmount: 0,
    annualInterestRate: 0,
    allowedTenures: '',
    active: true
  };

  constructor(
    private service: LoanProductsService,
    private router: Router
  ) {}

  submit() {
    this.service.createLoan(this.loan).subscribe({
      next: () => {
        this.router.navigate(['/admin/loan-products']);
      },
      error: (err) => {
        console.error('Error creating loan product:', err);
      }
    });
  }
}
