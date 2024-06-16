import {Producto} from "./producto";


export class Categoria {

  id?:number;
  nombre?:string;
  descripcion?:string;
  productos?:Producto[];


  constructor(data:Partial<Categoria>){
    Object.assign(this,data);
  }
}
