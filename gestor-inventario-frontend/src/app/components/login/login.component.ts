import { Component } from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {Router, RouterLink} from "@angular/router";
import Swal from 'sweetalert2';
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    FormsModule, RouterLink
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
