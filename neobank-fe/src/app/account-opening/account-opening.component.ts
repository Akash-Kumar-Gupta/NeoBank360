import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import {
  ReactiveFormsModule,
  FormBuilder,
  Validators,
  FormGroup,
  AbstractControl,
  ValidationErrors
} from '@angular/forms';
import { HttpClient } from '@angular/common/http';

import {
  NAME_REGEX,
  AADHAAR_REGEX,
  PAN_REGEX,
  PHONE_REGEX
} from './validators/kyc-regex.constants';

/**
 * ✅ Minimum age validator (18+ for banking compliance)
 */
function minimumAgeValidator(minAge: number) {
  return (control: AbstractControl): ValidationErrors | null => {
    if (!control.value) return null;

    const dob = new Date(control.value);
    const today = new Date();

    let age = today.getFullYear() - dob.getFullYear();
    const monthDiff = today.getMonth() - dob.getMonth();

    if (
      monthDiff < 0 ||
      (monthDiff === 0 && today.getDate() < dob.getDate())
    ) {
      age--;
    }

    return age >= minAge ? null : { underage: true };
  };
}

@Component({
  standalone: true,
  selector: 'app-account-opening',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './account-opening.component.html'
})
export class AccountOpeningComponent implements OnInit {

  accountType!: 'SAVINGS' | 'CURRENT';
  form!: FormGroup;

  constructor(
    private readonly fb: FormBuilder,
    private readonly route: ActivatedRoute,
    private readonly http: HttpClient,
    private readonly router: Router
  ) {
    this.form = this.fb.nonNullable.group({
      fullName: [
        '',
        [Validators.required, Validators.pattern(NAME_REGEX)]
      ],
      email: ['', [Validators.required, Validators.email]],
      aadhaarNumber: [
        '',
        [Validators.required, Validators.pattern(AADHAAR_REGEX)]
      ],
      panNumber: [
        '',
        [Validators.required, Validators.pattern(PAN_REGEX)]
      ],
      dob: [
        '',
        [Validators.required, minimumAgeValidator(18)]
      ],
      phone: [
        '',
        [Validators.required, Validators.pattern(PHONE_REGEX)]
      ],
      address: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.accountType =
      this.route.snapshot.queryParamMap.get('type') as
        | 'SAVINGS'
        | 'CURRENT';
  }

  /**
   * ✅ Auto-uppercase PAN while typing
   */
  onPanInput(): void {
    const panCtrl = this.form.get('panNumber');
    if (!panCtrl) return;

    const value = panCtrl.value;
    if (value) {
      panCtrl.setValue(value.toUpperCase(), { emitEvent: false });
    }
  }

  submit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.http.post('/api/account-opening', {
      ...this.form.value,
      accountType: this.accountType
    }).subscribe({
      next: () => {
        alert('Account opening request submitted for approval');
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        if (err.error?.errors) {
          Object.keys(err.error.errors).forEach(field => {
            this.form.get(field)?.setErrors({
              serverError: err.error.errors[field]
            });
          });
        }
      }
    });
  }
}
