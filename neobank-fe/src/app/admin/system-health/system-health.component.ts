import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SystemHealthService } from './system-health.service';
import { ChangeDetectorRef } from '@angular/core';


@Component({
  selector: 'app-system-health',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './system-health.component.html',
  styleUrls: ['./system-health.component.css']
})
export class SystemHealthComponent
implements OnInit, OnDestroy {

  health: any;

  loading = true;

  backendOnline = true;

  intervalId: any;

  constructor(
    private service: SystemHealthService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {

    this.loadHealth();

    // ✅ AUTO REFRESH EVERY 5 sec

    this.intervalId = setInterval(() => {

      this.loadHealth();

    }, 5000);
  }

  loadHealth() {

    this.service.getSystemHealth().subscribe({

      next: (res) => {

        console.log("✅ Health:", res);

        this.health = res;

        this.backendOnline = true;

        this.loading = false;

        this.cdr.detectChanges();
      },

      error: (err) => {

        console.error("❌ Backend Offline", err);

        this.backendOnline = false;

        this.health = {
          serverUptimeSeconds: 0
        };

        this.loading = false;

        this.cdr.detectChanges();
      }

    });
  }

  ngOnDestroy(): void {

    clearInterval(this.intervalId);
  }
}