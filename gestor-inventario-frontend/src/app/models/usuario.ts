export class Usuario {
    //Operador de seguridad puedo acceder a este atributo aunque tenga valor
    id?:number;
    username?: string;
    email?:string;
    phone?:string;


  constructor(data:any) {
    this.id = data.id;
    this.username = data.username;
    this.email = data.email;
    this.phone = data.phone;
  }
}
