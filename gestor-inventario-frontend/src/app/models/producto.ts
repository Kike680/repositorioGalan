import {Categoria} from "./categoria";

export class Producto {
  id?: number;
  nombre?: string;
  descripcion?: string;
  precio?: number;
  cantidad?: number;
  //Cambiar categoriaId
  categoria?: Categoria;
  imagenUrl!: string;

  constructor(data:Partial<Producto>){
  Object.assign(this,data);
  }

}
