import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import Swal from 'sweetalert2';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import {NgForOf} from "@angular/common";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterLink, MatCardModule, MatButtonModule, MatIconModule, NgForOf],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  title = 'Bienvenido a Gestor de Inventario';
  subtitle = 'La solución integral para la gestión de tu inventario';
  features = [
    { icon: 'inventory', title: 'Gestión de Productos', description: 'Administra todos tus productos de manera eficiente y organizada.' },
    { icon: 'analytics', title: 'Creacion de Categorias', description: 'Crea tus propias Categorias para organizar tus productos' },
    { icon: 'security', title: 'Seguridad', description: 'Protege tus datos con nuestras avanzadas medidas de seguridad implementamos JWT!.' }
  ];

  constructor() { }

  showFeatureAlert(featureTitle: string) {
    Swal.fire({
      title: featureTitle,
      text: 'Explora esta funcionalidad en detalle en nuestro Dashboard.',
      icon: 'info',
      confirmButtonText: 'Aceptar'
    });
  }
}
