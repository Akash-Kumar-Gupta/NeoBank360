// import { Component } from '@angular/core';
// import { CommonModule } from '@angular/common';
// import { HttpErrorResponse } from '@angular/common/http';
// import {
//   ReactiveFormsModule,
//   FormBuilder,
//   Validators,
//   FormGroup,
//   FormControl
// } from '@angular/forms';
// import { Router } from '@angular/router';
// import { AuthService } from '../auth.service';

// @Component({
//   standalone: true,
//   selector: 'app-register',
//   imports: [CommonModule, ReactiveFormsModule],
//   templateUrl: './register.component.html'
// })
// export class RegisterComponent {

//   form!: FormGroup<{
//     fullName: FormControl<string>;
//     email: FormControl<string>;
//     password: FormControl<string>;
//   }>;

//   constructor(
//     private readonly fb: FormBuilder,
//     private readonly auth: AuthService,
//     private readonly router: Router
//   ) {
//     // ✅ Initialize inside constructor (strict-safe)
//     this.form = this.fb.nonNullable.group({
//       fullName: ['', Validators.required],
//       email: ['', [Validators.required, Validators.email]],
//       password: [
//         '',
//         [
//           Validators.required,
//           Validators.minLength(8)
//         ]
//       ]
//     });
//   }

//   submit(): void {
//   if (this.form.invalid) {
//     this.form.markAllAsTouched();
//     return;
//   }

//   const payload = this.form.getRawValue();

//   this.auth.register(payload).subscribe({
//     next: () => {
//       this.router.navigateByUrl('/login');
//     },
//     error: (err: HttpErrorResponse) => {
//       console.error('Registration failed', err.message);
//     }
//   });
// }
// }


import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  ReactiveFormsModule,
  FormBuilder,
  Validators,
  FormGroup,
  FormControl,
  AbstractControl,
  ValidationErrors
} from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { AuthService } from '../auth.service';

/**
 * ✅ Password match validator (Password vs Confirm Password)
 */
function passwordMatchValidator(group: AbstractControl): ValidationErrors | null {
  const password = group.get('password')?.value;
  const confirmPassword = group.get('confirmPassword')?.value;

  if (!password || !confirmPassword) return null;
  return password === confirmPassword ? null : { passwordMismatch: true };
}

@Component({
  standalone: true,
  selector: 'app-register',
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './register.component.html'
})
export class RegisterComponent {

  form!: FormGroup<{
    fullName: FormControl<string>;
    email: FormControl<string>;
    password: FormControl<string>;
    confirmPassword: FormControl<string>;
  }>;

  constructor(
    private readonly fb: FormBuilder,
    private readonly auth: AuthService,
    private readonly router: Router
  ) {
    this.form = this.fb.nonNullable.group(
      {
        fullName: ['', Validators.required],
        email: ['', [Validators.required, Validators.email]],
        password: ['', [Validators.required, Validators.minLength(8)]],
        confirmPassword: ['', Validators.required]
      },
      { validators: passwordMatchValidator }
    );
  }

  submit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    // ✅ Exclude confirmPassword from payload
    const { confirmPassword, ...payload } = this.form.getRawValue();

    this.auth.register(payload).subscribe({
      next: () => {
        this.router.navigateByUrl('/login');
      },
      error: (err: HttpErrorResponse) => {
        console.error('Registration failed', err.message);
      }
    });
  }
}
