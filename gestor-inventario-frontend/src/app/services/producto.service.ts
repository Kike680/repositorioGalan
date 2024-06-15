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

  getProductosByUser(userId: number): Observable<Producto[]> {
    return this.http.get<Producto[]>(`${this.baseUrl}/user/${userId}`);
  }

  getProducto(userId: number, id: number): Observable<Producto> {
    return this.http.get<Producto>(`${this.baseUrl}/user/${userId}/producto/${id}`);
  }

  deleteProducto(userId: number, id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/user/${userId}/producto/${id}`);
  }
}
