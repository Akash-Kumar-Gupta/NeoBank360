import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BillsService, Bill } from '../bills.service';
import { Router } from '@angular/router';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  standalone: true,
  selector: 'app-bills-dashboard',
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class BillsDashboardComponent implements OnInit {

  bills: Bill[] = [];

  
constructor(
  private service: BillsService,
  private router: Router,
  private cdr: ChangeDetectorRef
) {}


  ngOnInit() {
    this.load();
  }

  
  load() {
    this.service.getBills().subscribe(res => {
      this.bills = [...res];
      this.cdr.detectChanges();
    });
  }


  pay(id: number) {
    this.service.markPaid(id).subscribe(() => {
      this.load();
    });
  }

  goToCreate() {
    this.router.navigate(['/bills/create']);
  }
}
