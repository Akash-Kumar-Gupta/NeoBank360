import { Injectable, ApplicationRef, ComponentRef, createComponent } from '@angular/core';
import { ConfirmDialogComponent } from './confirm-dialog.component';

@Injectable({ providedIn: 'root' })
export class ConfirmService {

  private dialogRef?: ComponentRef<ConfirmDialogComponent>;

  constructor(private appRef: ApplicationRef) {}

  open(message: string, onConfirm: () => void): void {
    // Create the dialog dynamically
    this.dialogRef = createComponent(ConfirmDialogComponent, {
      environmentInjector: this.appRef.injector
    });

    const instance = this.dialogRef.instance;
    instance.message = message;
    instance.confirm = onConfirm;
    instance.close = () => this.close();

    // Attach to DOM
    this.appRef.attachView(this.dialogRef.hostView);
    document.body.appendChild(
      this.dialogRef.location.nativeElement
    );
  }

  private close(): void {
    if (this.dialogRef) {
      this.appRef.detachView(this.dialogRef.hostView);
      this.dialogRef.destroy();
      this.dialogRef = undefined;
    }
  }
}