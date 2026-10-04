import { ApplicationConfig, provideBrowserGlobalErrorListeners } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient, withInterceptors } from '@angular/common/http';

import { routes } from './app.routes';
import { jwtInterceptor } from './core/interceptors/jwt.interceptor';
import { provideCharts, withDefaultRegisterables } from 'ng2-charts';
import { httpErrorInterceptor } from './core/errors/http-error.interceptor';

import { MatCardModule } from '@angular/material/card';
import { importProvidersFrom } from '@angular/core';

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideRouter(routes),

    provideHttpClient(
      withInterceptors([jwtInterceptor, httpErrorInterceptor]) // ✅ function interceptor
    ),
    provideCharts(withDefaultRegisterables()),

    importProvidersFrom(MatCardModule)
  ]
};
