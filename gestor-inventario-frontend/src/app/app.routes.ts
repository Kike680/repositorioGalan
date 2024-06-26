import { Routes } from '@angular/router';
import {LoginComponent} from "./components/login/login.component";
import {RegisterComponent} from "./components/register/register.component";
import {HomeComponent} from "./components/home/home.component";
import {authGuard} from "./guards/auth.guard";
import {unAuthGuard} from "./guards/un-auth.guard";
import {ProductoComponent} from "./components/producto/producto.component";
import {ListaProductosComponent} from "./components/lista-productos/lista-productos.component";
import {EditarProductoComponent} from "./components/editar-producto/editar-producto.component";
import {AboutUsComponent} from "./components/about-us/about-us.component";
import {CategoriaComponent} from "./components/categoria/categoria.component";

export const routes: Routes = [
  {path:'', redirectTo:'/login', pathMatch:'full'},
  {path:'login',component:LoginComponent, canActivate:[unAuthGuard]},
  {path:'register',component:RegisterComponent},
  {path:'home', component:HomeComponent, canActivate: [authGuard]},
  {path:'producto', component:ProductoComponent, canActivate: [authGuard]},
  {path:'listaproductos', component:ListaProductosComponent, canActivate: [authGuard], data:["ROLE_USER", "ROLE_ADMIN"]},
  {path:'editarproducto/:id', component:EditarProductoComponent, canActivate: [authGuard]},
  {path:'about-us', component:AboutUsComponent, canActivate: [authGuard]},
  {path:'categoria', component:CategoriaComponent, canActivate: [authGuard], data:["ROLE_USER", "ROLE_ADMIN"]}


];
