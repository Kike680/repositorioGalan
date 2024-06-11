import { Component } from '@angular/core';
import { AuthService } from "../../services/auth.service";
import { Router } from "@angular/router";
import Swal from "sweetalert2";
import { FormsModule } from "@angular/forms";
import { RouterLink } from "@angular/router";

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  username: string = '';
  password: string = '';
  email: string = '';
  phone: string = '';
  error: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit(): void {
    this.authService.register(this.username,this.email, this.phone,this.password ).subscribe({
      next: () => {
        Swal.fire({
          title: "Usuario registrado",
          text: "Verifique sus credenciales",
          icon: "success"
        });
        this.router.navigate(["/login"]);
      },
      error: () => {
        this.error = 'Error en el registro. Por favor, intentelo de nuevo';
        Swal.fire({
          title: "Error",
          text: "Error en el registro. Por favor, intentelo de nuevo",
          icon: "error"
        });
      }
    });
  }
}
