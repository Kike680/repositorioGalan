import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {BehaviorSubject, catchError, map, Observable, tap, throwError} from "rxjs";
import {Usuario} from "../models/usuario";
import {CookieService} from "ngx-cookie-service";
import {JwtHelperService} from "@auth0/angular-jwt";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  currentSession: BehaviorSubject<Boolean> = new BehaviorSubject<Boolean>(false);
  sessionToken: BehaviorSubject<String> = new BehaviorSubject<String>('');
  sessionUser: BehaviorSubject<Usuario | null>  = new BehaviorSubject<Usuario | null>(null);

  private setTokenAndUser(token: string, user: Usuario) {
    const currentDate = new Date();
    const expiresAt = new Date(currentDate.getTime() + 86400000); // 24 horas
    this.cookieService.set('token', token, expiresAt);
    this.cookieService.set('user', JSON.stringify(user), expiresAt);

    console.log('Token y usuario guardados en cookies');
    console.log('Token:', this.cookieService.get('token'));
    console.log('User:', this.cookieService.get('user'));
  }

  private baseUrl= 'http://localhost:8080/api/auth';


  constructor(private http:HttpClient, private router:Router, private cookieService:CookieService, private jwtHelper: JwtHelperService) { }

  /**
   * Método para autenticar a un usuario mediante su username y password.
   * Envía una solicitud HTTP POST al endpoint `/login` del backend y maneja la respuesta.
   *
   * @param {string} username - El nombre de usuario del usuario que se está autenticando.
   * @param {string} password - La contraseña del usuario que se está autenticando.
   * @returns {Observable<any>} Un observable que emite la respuesta del servidor después de procesar la autenticación.
   */
    login(username:string,password:string):Observable<any>{
                              //Aqui hay que pasarle un username y un password, por que en nuestro backend en el contralldor
                              //Hay un Request Body con un AuthRequest que contiene username y password
                              //Va entre llaves para indicarle que va en JSON
    if(!this.http){
      throw new Error('Http client no inicializado');
    }
    return this.http?.post<any>(`${this.baseUrl}/login`, {username, password})
      //Lo usamos para tener varios operadores dentro de este flujo, por que vamos a tener un map un cacth
      ?.pipe(
        //Estamos haciendo un efecto segundario que es un log
        //Este operador permite ejecutar efectos secundarios sin modificar la respuesta.
        // Aquí se usa para registrar la respuesta del servidor en la consola.
        tap(response => {console.log('Respuesta del servidor : ', response)
        }),
        // Este operador transforma la respuesta. Verifica si la respuesta contiene un token.
        // Si es así, almacena el token en el localStorage del navegador y devuelve la respuesta. Si no, lanza un error.
        map(response =>{
          //Estamos modificando algo, esa respuesta le vamos a agregar un nuevo item un token, luego voy a devolver esa respuesta
          if(response && response.token){
            const decodedToken = this.jwtHelper.decodeToken(response.token);
            const user: Usuario = {
              id: decodedToken.user.id,
              username: decodedToken.user.username,
              email: decodedToken.user.email,
              phone: decodedToken.user.phone,

            };
            //Para iniciar sesion directamente con la ayuda del interceptor
            this.sessionToken.next(response.token);
            this.sessionUser.next(user);
            this.currentSession.next(true);

            this.setTokenAndUser(response.token,user);
            console.log(this.cookieService.get('user'));
            return response;
          }else{
            throw new Error('No se recibió un token de acceso válido en la respusta del servidor')
          }
        }),
        catchError(error => {
          console.log(error)
          return throwError(() =>error);
        })
      )

    }

    register(usuario:any):Observable<any>{
      if(!this.http){
        throw new Error('Http client no inicializado');
      }
      return this.http?.post<any>(`${this.baseUrl}/register`,usuario)
        ?.pipe(
          catchError(this.handleError)
        )
    }

    logout(){
     this.cookieService.delete("token");
      this.cookieService.delete("user");
      this.router?.navigate(['/login']);
    }

    getToken():string | null{
      return this.cookieService.get('token');

    }

    //Este metodo tendre que usarlo cuando me requiera traerme el usuario para poder obtener datos suyos
  getUser(): Usuario | null {
    const user = this.cookieService.get('user');
    return user ? JSON.parse(user) : null;
  }
    isAuthenticated():boolean{
      const token = this.getToken();
      //El !! convierte ese valor a booleano hace lo mismo que un ternario
      return !!token && !this.jwtHelper.isTokenExpired(token);
    }

    handleError(error: any) {
      console.error('Error en la solicitud : ', error);
      return throwError(() => error);
    }

  public  getUserInfoCookie():any{
      const token = this.getToken();
    if (token != null) {
      const decodedToken = this.jwtHelper.decodeToken(token);
      const user: Usuario = {
        id: decodedToken.user.id,
        username: decodedToken.user.username,
        email: decodedToken.user.email,
        phone: decodedToken.user.phone,

      };
      return user;
    }
    return null;

  }

  public getUserIdFromCookie(): any {
    const token = this.getToken();
    if (token != null) {
      const decodedToken = this.jwtHelper.decodeToken(token);
      const userId = decodedToken.user.id;
      return userId;
    }
    return null;
  }



}
