import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, ReactiveFormsModule, FormGroup } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { BudgetService } from '../budget.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-budget-create',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.css'] // ✅ FIX ADDED
})
export class BudgetCreateComponent implements OnInit {

  categories = [
    'GROCERIES',
    'UTILITIES',
    'RENT',
    'ENTERTAINMENT',
    'TRANSFER',
    'OTHER'
  ];

  form!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private service: BudgetService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      category: ['', Validators.required],
      budgetMonth: ['', Validators.required],
      limitAmount: ['', Validators.required]
    });
  }

  submit() {

    if (this.form.invalid) {
      alert('Please fill all fields');
      return;
    }

    const data = {
      ...this.form.value,
      budgetMonth: this.form.value.budgetMonth + '-01'
    };

    this.service.createBudget(data)
      .subscribe({
        next: () => {
          alert('Budget Created ✅');
          this.router.navigate(['/budget']);
        },
        error: (err) => {
          console.error("ERROR:", err);
          alert('Error creating budget');
        }
      });
  }
}
