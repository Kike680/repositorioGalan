import {ApplicationConfig, importProvidersFrom} from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import {provideHttpClient, withFetch, withInterceptors} from "@angular/common/http";
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import {provideClientHydration} from "@angular/platform-browser";
import {authInterceptor} from "./interceptors/auth.interceptor";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes), provideClientHydration(),provideHttpClient(withFetch(), withInterceptors([authInterceptor])),
    importProvidersFrom([BrowserAnimationsModule])
  ]
};
