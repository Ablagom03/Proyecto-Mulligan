import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
// Importamos withInterceptors
import { provideHttpClient, withInterceptors } from '@angular/common/http'; 
import { authInterceptor } from './service/auth.interceptor';
import { routes } from './app.routes';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideHttpClient(
      withInterceptors([authInterceptor]) 
    )
  ]
};