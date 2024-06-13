
import { Component } from '@angular/core';
import { AuthService } from "../../services/auth.service";
import { Router, RouterLink } from "@angular/router";
import Swal from 'sweetalert2';
import {FormBuilder, FormGroup,ReactiveFormsModule, Validators} from "@angular/forms";
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    RouterLink,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    ReactiveFormsModule
  ],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  error: string = '';
  loginForm!: FormGroup;
  constructor(private authService: AuthService, private router: Router,private fb: FormBuilder) {}

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  onSubmit(): void {
    if(this.loginForm.valid){
      const { username, password } = this.loginForm.value;
      this.authService.login(username,password).subscribe({
        next: () => {
          Swal.fire({
            title: "Usuario autenticado",
            text: "Bienvenido " + username,
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
}

