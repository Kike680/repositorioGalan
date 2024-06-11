import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {catchError, map, Observable, tap, throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseUrl= 'http://localhost:8080/api/auth';


  constructor(private http?:HttpClient, private router?:Router) { }

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
            localStorage.setItem('token', response.token);
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

    register(username:string,password:string, email:string, phone:string):Observable<any>{
      if(!this.http){
        throw new Error('Http client no inicializado');
      }
      return this.http?.post<any>(`${this.baseUrl}/register`, {username, password, email, phone})
        ?.pipe(
          catchError(this.handleError)
        )
    }

    logout(){
      localStorage.removeItem('token');
      this.router?.navigate(['/login']);
    }

    getToken():string | null{
      return localStorage.getItem('token');

    }

    isAuthenticated():boolean{
      const token = this.getToken();
      //El !! convierte ese valor a booleano hace lo mismo que un ternario
      return !!token;
    }

    handleError(error: any) {
      console.error('Error en la solicitud : ', error);
      return throwError(() => error);
    }

}
