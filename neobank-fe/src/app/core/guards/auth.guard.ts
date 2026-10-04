import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../../auth/auth.service';

/**
 * ✅ Functional Auth Guard (Angular 16+/21 style)
 */
export const authGuard: CanActivateFn = () => {
  const auth = inject(AuthService);
  const router = inject(Router);

  // ✅ If token exists → allow navigation
  if (auth.token) {
    return true;
  }

  // ❌ No token → redirect to login
  router.navigate(['/login']);
  return false;
};