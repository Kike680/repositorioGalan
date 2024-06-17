import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {CategoriaService} from "../../services/categoria.service";
import {ProductoService} from "../../services/producto.service";
import {AuthService} from "../../services/auth.service";
import {MatCard, MatCardContent} from "@angular/material/card";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatOption, MatSelect} from "@angular/material/select";

@Component({
  selector: 'app-categoria',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatCard,
    MatCardContent,
    MatLabel,
    MatSelect,
    MatOption,
    MatFormField
  ],
  templateUrl: './categoria.component.html',
  styleUrl: './categoria.component.css'
})
export class CategoriaComponent implements OnInit{
  productoForm: FormGroup;
  categorias: any[] = [];
  productos: any[] = [];
  userId:number;

  constructor(
    private fb: FormBuilder,
    private categoriaService: CategoriaService,
    private productoService: ProductoService,
    private authService: AuthService,
  ) {
    this.userId = 0; // Inicialización
    this.productoForm = this.fb.group({
      categoria: ['']
    });
  }

  ngOnInit(): void {

    this.userId = this.authService.getUserIdFromCookie();
    this.productoService.getProductosByUser(this.userId).subscribe(data => {
      console.log(data);
      this.productos = data;
    });
    this.categoriaService.getCategorias().subscribe(
      data => {
        this.categorias = data;
      },
      error => console.error('Error al obtener categorías', error)
    );

    // Obtener productos del usuario al cargar el componente
    this.productoService.getProductosByUser(this.userId).subscribe(
      data => {
        this.productos = data;
      },
      error => console.error('Error al obtener productos del usuario', error)
    );

  }

  onCategoriaChange(): void {
    const categoriaId = this.productoForm.get('categoria')?.value;
    if (categoriaId) {
      this.productoService.getProductosPorCategoria(categoriaId).subscribe(
        data => {
          this.productos = data;
        },
        error => console.error('Error al obtener productos por categoría', error)
      );
    } else {
      // Si no se selecciona ninguna categoría, obtener todos los productos del usuario
      this.productoService.getProductosByUser(this.userId).subscribe(
        data => {
          this.productos = data;
        },
        error => console.error('Error al obtener productos del usuario', error)
      );
    }
  }

}
