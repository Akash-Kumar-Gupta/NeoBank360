import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AccountsService, Account } from './accounts.service';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';


@Component({
  standalone: true,
  selector: 'app-accounts',
  imports: [CommonModule],
  templateUrl: './accounts.component.html',
  styleUrls: ['./accounts.component.css']
})
export class AccountsComponent implements OnInit {

  accounts$!: Observable<Account[]>;
  creating = false;

  
constructor(
  private readonly accountsService: AccountsService,
  private readonly router: Router
) {}


  ngOnInit(): void {
    this.loadAccounts();
  }

  loadAccounts(): void {
    this.accounts$ = this.accountsService.getAccounts();
  }

  
openAccount(type: 'SAVINGS' | 'CURRENT'): void {
  this.router.navigate(['/open-account'], {
    queryParams: { type }
  });
}

}