import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

/**
 * Guardián de ruta para verificar la autenticación del usuario.
 * Este guardián determina si una ruta puede ser activada basándose en el estado de autenticación del usuario.
 *
 * @param {ActivatedRouteSnapshot} route - La ruta activada que se está intentando cargar.
 * @param {RouterStateSnapshot} state - El estado actual del enrutador.
 * @returns {boolean} Retorna true si el usuario está autenticado, de lo contrario redirige a la página de login y retorna false.
 */
export const authGuard: CanActivateFn = (route, state) => {
  const authService = new AuthService();
  const router = new Router();

  // Si está autenticado, permite la navegación a la ruta solicitada.
  if (authService.isAuthenticated()) {
    return true;
  } else {
    // Si no está autenticado, redirige al usuario a la página de login.
    router.navigate(['/login']);
    return false;
  }
  return false;
};
