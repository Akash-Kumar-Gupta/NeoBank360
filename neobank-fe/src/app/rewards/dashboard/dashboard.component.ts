import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RewardsService, Reward } from '../rewards.service';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  standalone: true,
  selector: 'app-rewards-dashboard',
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class RewardsDashboardComponent implements OnInit {

  rewards: Reward[] = [];
  totalPoints = 0;

  // ✅ LEVEL SYSTEM
  nextLevel = '';
  nextLevelPoints = 0;
  progressPercent = 0;

  constructor(
    private service: RewardsService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.load();
  }

  load() {
    this.service.getRewards().subscribe(res => {

      this.rewards = res;

      // ✅ TOTAL POINTS
      this.totalPoints = res.reduce((sum, r) => sum + r.points, 0);

      // ✅ CALCULATE LEVEL PROGRESS
      this.calculateProgress();

      // ✅ FORCE UI REFRESH (important for Angular updates)
      this.cdr.detectChanges();
    });
  }

  // ✅ LEVEL + PROGRESS LOGIC
  calculateProgress() {
    const points = this.totalPoints;

    if (points < 50) {
      this.nextLevel = 'Bronze';
      this.nextLevelPoints = 50;
    } 
    else if (points < 100) {
      this.nextLevel = 'Silver';
      this.nextLevelPoints = 100;
    } 
    else if (points < 200) {
      this.nextLevel = 'Gold';
      this.nextLevelPoints = 200;
    } 
    else {
      this.nextLevel = 'Platinum';
      this.nextLevelPoints = 500;
    }

    // ✅ PROGRESS %
    this.progressPercent = (points / this.nextLevelPoints) * 100;

    // ✅ PREVENT OVERFLOW > 100%
    if (this.progressPercent > 100) {
      this.progressPercent = 100;
    }
  }
}