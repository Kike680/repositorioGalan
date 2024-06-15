import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductoService } from '../../services/producto.service';
import { Producto } from '../../models/producto';
import { CommonModule } from '@angular/common';
import {of} from "rxjs";
import {AuthService} from "../../services/auth.service";
import any = jasmine.any;

@Component({
  selector: 'app-lista-productos',
  templateUrl: './lista-productos.component.html',
  standalone: true,
  styleUrls: ['./lista-productos.component.css'],
  imports: [CommonModule]
})
export class ListaProductosComponent implements OnInit {
  productos: Producto[] = [];
  userId: number;

  constructor(private productoService: ProductoService, private authService: AuthService) {
    this.userId = 0; // Inicialización
  }

  ngOnInit(): void {

    this.userId = this.authService.getUserIdFromCookie();
    this.productoService.getProductosByUser(this.userId).subscribe(data => {
      this.productos = data;
    });
  }

  deleteProducto(id: number): void {
    this.productoService.deleteProducto(this.userId, id).subscribe(() => {
      this.productos = this.productos.filter(producto => producto.id !== id);
    });
  }

  protected readonly of = of;
}
