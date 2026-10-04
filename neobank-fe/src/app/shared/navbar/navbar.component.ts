import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../auth/auth.service';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  userName = '';

  constructor(
    public auth: AuthService,
    private router: Router
  ) {}

  ngOnInit() {

    // ✅ REAL-TIME UPDATE FIX 
    this.auth.userName$.subscribe(name => {
      this.userName = name;
    });

    // ✅ LOAD INITIAL VALUE (for refresh cases)
    this.auth.getUserName();
  }

  logout() {
    this.auth.logout();
    this.router.navigate(['/login']);
  }
}