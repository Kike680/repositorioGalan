import {Component, OnInit} from '@angular/core';
import { AuthService } from "../../services/auth.service";
import { Router } from "@angular/router";
import Swal from "sweetalert2";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import { RouterLink } from "@angular/router";
import {Usuario} from "../../models/usuario";

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registerForm!: FormGroup;

  ngOnInit(): void {
    this.registerForm = this.fb.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  error: string = '';

  constructor(private authService: AuthService, private router: Router, private fb: FormBuilder) {
  }

  onSubmit(): void {
    if (this.registerForm.valid) {
      const {username, email, phone, password} = this.registerForm.value;
      const usuario=  new Usuario(this.registerForm.value);
      this.authService.register(usuario).subscribe({
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
}
