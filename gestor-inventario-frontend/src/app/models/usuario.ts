export class Usuario {
    //Operador de seguridad puedo acceder a este atributo aunque tenga valor
    username?: string;
    password?: string;
    email?:string;
    phone?:string;


  constructor(data:any) {
    this.username = data.username;
    this.password = data.password;
    this.email = data.email;
    this.phone = data.phone;
  }
}
