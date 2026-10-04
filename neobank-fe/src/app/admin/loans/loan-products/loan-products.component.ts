import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { LoanProductsService } from './loan-products.service';

@Component({
  selector: 'app-loan-products',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './loan-products.component.html',
  styleUrls: ['./loan-products.component.css']
})
export class LoanProductsComponent implements OnInit {

  loanProducts: any[] = [];

  constructor(
    private router: Router,
    private loanService: LoanProductsService,
    private cdr: ChangeDetectorRef // ✅ added
  ) {}

  // ✅ Load on init + check for new data
  ngOnInit(): void {
    this.loadLoanProducts();

    // ✅ Instant update when coming back from create page
    const newLoan = localStorage.getItem('newLoan');

    if (newLoan) {
      this.loanProducts.unshift(JSON.parse(newLoan)); // add at top
      localStorage.removeItem('newLoan');
      this.cdr.detectChanges(); // ✅ force UI refresh
    }
  }

  // ✅ Fetch all loan products
  loadLoanProducts() {
    this.loanService.getAll().subscribe({
      next: (data: any) => {
        this.loanProducts = data;
        this.cdr.detectChanges(); // ✅ ensure UI updates
      },
      error: (err) => {
        console.error('Error fetching loan products:', err);
      }
    });
  }

  // ✅ Navigate to Create Page
  addLoanProduct() {
    this.router.navigate(['/admin/loan-products/create']);
  }

  // ✅ Edit (next step)
  editProduct(product: any) {
    console.log('Edit:', product);
  }

  // ✅ Toggle Active/Inactive
  toggleStatus(product: any) {
    this.loanService.toggleStatus(product.id).subscribe({
      next: () => {
        // ✅ instant UI update instead of reload
        product.active = !product.active;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error updating status:', err);
      }
    });
  }
}