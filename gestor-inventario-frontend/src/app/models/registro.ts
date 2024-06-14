export class Registro {
  password?:string;
  username?: string;
  email?:string;
  phone?:string;

  constructor(data:Partial<Registro>){
    Object.assign(this,data);
  }
}
