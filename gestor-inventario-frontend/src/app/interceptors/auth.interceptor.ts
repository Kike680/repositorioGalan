import { HttpInterceptorFn } from '@angular/common/http';
import { AuthService } from '../services/auth.service';
import {inject} from "@angular/core";

/**
 * Interceptor HTTP para añadir el token de autenticación a las solicitudes salientes.
 * Este interceptor se asegura de que todas las solicitudes HTTP incluyan el token JWT en la cabecera de autorización.
 *
 * @param {HttpRequest<any>} req - La solicitud HTTP saliente.
 * @param {HttpHandler} next - El siguiente manejador en la cadena de interceptores.
 * @returns {Observable<HttpEvent<any>>} Un observable que emite el evento HTTP.
 */
export const authInterceptor: HttpInterceptorFn = (req, next) => {
  // Crear una instancia del AuthService para obtener el token.
  const authService = inject(AuthService) ;
  const token = authService.getToken();

  console.log(token);

  // Si hay un token disponible, clonar la solicitud y añadir la cabecera de autorización.
  if (token) {
    
    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
  }
  // Pasar la solicitud al siguiente manejador en la cadena de interceptores.
  return next(req);

};
