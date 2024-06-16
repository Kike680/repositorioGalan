import { Component, OnInit } from '@angular/core';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatMenuModule } from '@angular/material/menu';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { AuthService } from '../../services/auth.service';
import { FormsModule } from '@angular/forms';
import Swal from 'sweetalert2';
import {Router, RouterLink} from '@angular/router'; // Asegúrate de importar el Router

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    MatToolbarModule,
    MatButtonModule,
    MatMenuModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    FormsModule,
    RouterLink
  ],
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
 /* searchQuery: string = '';
  options: string[] = ['Producto 1', 'Producto 2', 'Producto 3', 'Producto 4'];
  filteredOptions: string[] = [];*/

  constructor(private authService: AuthService, private router: Router) {} // Añadir Router en el constructor

  ngOnInit() {
    /*this.filteredOptions = this.options;*/
  }
/*

  onSearchChange(event: Event) {
    const value = (event.target as HTMLInputElement).value.toLowerCase();
    this.filteredOptions = this.options.filter(option => option.toLowerCase().includes(value));
  }
*/

 /* onSearch() {
    console.log('Buscando:', this.searchQuery);
    // Aquí puedes añadir la lógica para manejar la búsqueda, por ejemplo, redirigir a una página de resultados de búsqueda.
  }*/

  logout(): void {
    Swal.fire({
      title: '¿Estás seguro?',
      text: "¡No podrás revertir esto!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, salir',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.authService.logout();
        Swal.fire(
          'Desconectado!',
          'Has salido de la sesión.',
          'success'
        ).then(() => {
          this.router.navigate(['/login']); // Redirige al usuario a la página de login
        });
      }
    })
  }
}
