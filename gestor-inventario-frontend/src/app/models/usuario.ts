export class Usuario {
    //Operador de seguridad puedo acceder a este atributo aunque tenga valor
    id?:number;
    username?: string;
    email?:string;
    phone?:string;


  constructor(data:Partial<Usuario>){
    Object.assign(this,data);
  }
}
