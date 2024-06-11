/*
import { Component } from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {Router, RouterLink} from "@angular/router";
import Swal from 'sweetalert2';
import {FormsModule} from "@angular/forms";
import {MatFormField} from "@angular/material/form-field";
import {MatCard} from "@angular/material/card";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    FormsModule, RouterLink, MatFormField, MatCard
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  username:string= '';
  password:string= '';
  error:string= '';

  constructor(private authService: AuthService, private router:Router) {}

  onSubmit():void{
    this.authService.login(this.username,this.password).subscribe({
      next:()=>{
        Swal.fire({
          title: "Usuario autenticado",
          text: "Bienvenido" +this.username,
          icon: "success"
        });
        this.router.navigate(['/home']);
        console.log("Token de acceso :  " + this.authService.getToken());
      },
      error : ()=>{
        Swal.fire({
          title: "Credenciales inválidas",
          text: "Verifique sus credenciales",
          icon: "error"
        });
        this.error= 'Credenciales invalidas';
      }
    })
  }


}
*/
import { Component } from '@angular/core';
import { AuthService } from "../../services/auth.service";
import { Router, RouterLink } from "@angular/router";
import Swal from 'sweetalert2';
import { FormsModule } from "@angular/forms";
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    FormsModule,
    RouterLink,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule
  ],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  error: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit(): void {
    this.authService.login(this.username, this.password).subscribe({
      next: () => {
        Swal.fire({
          title: "Usuario autenticado",
          text: "Bienvenido " + this.username,
          icon: "success"
        });
        this.router.navigate(['/home']);
        console.log("Token de acceso: " + this.authService.getToken());
      },
      error: () => {
        Swal.fire({
          title: "Credenciales inválidas",
          text: "Verifique sus credenciales",
          icon: "error"
        });
        this.error = 'Credenciales invalidas';
      }
    });
  }
}

