import { HttpInterceptorFn, HttpErrorResponse } from "@angular/common/http";
import { inject } from "@angular/core";
import { catchError } from "rxjs";
import { ToastService } from "../toast/toast.service";

export const httpErrorInterceptor: HttpInterceptorFn = (req, next) => {
  const toast = inject(ToastService);

  return next(req).pipe(
    catchError((err: HttpErrorResponse) => {
      const message =
        err.error?.message || 'Unexpected system error';
      toast.error(message);
      throw err;
    })
  );
};