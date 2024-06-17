import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Producto } from '../models/producto';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {

  private baseUrl = 'http://localhost:8080/api/productos';

  constructor(private http: HttpClient) { }

  //Endpoints para listar los productos
  getProductosByUser(userId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/user/${userId}`);
  }

  //Endpoint para editar los productos
  getProductById(id: number): Observable<Producto> {
    return this.http.get<Producto>(`${this.baseUrl}/${id}`);
  }
  //Endpoint para traer todos los productos por categorias
  getProductosPorCategoria(categoriaId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/categoria/${categoriaId}`);
  }

  //EndPoint para borrar el producto
  deleteProducto(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
