import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import { ProductoService } from '../../services/producto.service';
import { Producto } from '../../models/producto';
import { CommonModule } from '@angular/common';
import {of} from "rxjs";
import {AuthService} from "../../services/auth.service";
import Swal from "sweetalert2";


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

  constructor(private productoService: ProductoService, private authService: AuthService, private router: Router) {
    this.userId = 0; // Inicialización
  }

  ngOnInit(): void {

    this.userId = this.authService.getUserIdFromCookie();
    this.productoService.getProductosByUser(this.userId).subscribe(data => {
      console.log(data);
      this.productos = data;
    });
  }

  confirmDelete(productId: number): void {
    Swal.fire({
      title: '¿Estás seguro?',
      text: "No podrás revertir esto",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, eliminarlo'
    }).then((result) => {
      if (result.isConfirmed) {
        this.deleteProducto(productId);
        Swal.fire(
          'Eliminado!',
          'Tu producto ha sido eliminado.',
          'success'
        )
      }
    })
  }
  deleteProducto(id: number): void {
    this.productoService.deleteProducto(id).subscribe(() => {
      this.productos = this.productos.filter(producto => producto.id !== id);
    });
  }
  editProducto(productId: number): void {
    this.router.navigate(['/editarproducto', productId]);
  }

  navigateToCreateProducto(): void {
    this.router.navigate(['/producto']);
  }

  protected readonly of = of;
}
