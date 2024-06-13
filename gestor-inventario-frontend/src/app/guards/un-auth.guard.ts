import {CanActivateFn, Router} from '@angular/router';
import {inject} from "@angular/core";
import {AuthService} from "../services/auth.service";

export const unAuthGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = new Router();


  if (authService.isAuthenticated()) {
    router.navigate(['/home']);
    return false;
  } else {
    return true;
  }
};
