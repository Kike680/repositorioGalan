import {Categoria} from "./categoria";
import {Usuario} from "./usuario";

export class Producto {
  id?: number;
  nombre?: string;
  descripcion?: string;
  precio?: number;
  cantidad?: number;
  //Cambiar categoriaId
  categoria?: Categoria;
  imagenUrl!: string;
  //Me va a hacer falta cuando cree el producto, para decirle quien lo ha creado
  usuario?: Usuario;
  constructor(data:Partial<Producto>){
  Object.assign(this,data);
  }

}
