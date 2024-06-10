import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {FormsModule} from "@angular/forms";


@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    FormsModule
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit{
  searchQuery: string = '';
  options: string[] = ['Producto 1', 'Producto 2', 'Producto 3', 'Producto 4'];
  filteredOptions: string[] = []; // Inicializar la propiedad

  ngOnInit() {
    this.filteredOptions = this.options;
  }

  onSearchChange(event: Event) {
    const value = (event.target as HTMLInputElement).value.toLowerCase();
    this.filteredOptions = this.options.filter(option => option.toLowerCase().includes(value));
  }

  onSearch() {
    console.log('Buscando:', this.searchQuery);
    // Aquí puedes añadir la lógica para manejar la búsqueda, por ejemplo, redirigir a una página de resultados de búsqueda.
  }
  constructor(private authService:AuthService) {}

  logout():void{
    this.authService.logout();

  }

}
