import { Component } from '@angular/core';

@Component({
  standalone: true,
  selector: 'app-confirm-dialog',
  template: `
    <div class="modal-backdrop fade show"></div>

    <div class="modal d-block">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content p-4">
          <h5 class="mb-3">Confirm Action</h5>

          <p>{{ message }}</p>

          <div class="d-flex justify-content-end gap-2 mt-3">
            <button class="btn btn-danger" (click)="yes()">Confirm</button>
            <button class="btn btn-secondary" (click)="no()">Cancel</button>
          </div>
        </div>
      </div>
    </div>
  `
})
export class ConfirmDialogComponent {
  message = '';
  confirm!: () => void;
  close!: () => void;

  yes(): void {
    this.confirm();
    this.close();
  }

  no(): void {
    this.close();
  }
}