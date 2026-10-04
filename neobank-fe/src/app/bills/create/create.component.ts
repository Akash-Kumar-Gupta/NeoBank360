import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BillsService } from '../bills.service';
import { Router } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-bill-create',
  imports: [CommonModule, FormsModule],
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.css']
})
export class BillCreateComponent {

  bill = {
    name: '',
    category: '',
    amount: 0,
    dueDate: ''
  };

  constructor(
    private service: BillsService,
    private router: Router
  ) {}

  submit() {

    if (!this.bill.name || !this.bill.dueDate) {
      alert('Fill all fields');
      return;
    }

    this.service.createBill(this.bill)
      .subscribe(() => {
        alert('Bill Created ✅');
        this.router.navigate(['/bills']);
      });
  }
}