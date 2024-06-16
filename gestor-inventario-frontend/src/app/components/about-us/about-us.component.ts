import { Component } from '@angular/core';
import Swal from "sweetalert2";
import {MatCard, MatCardActions, MatCardContent, MatCardHeader, MatCardModule} from "@angular/material/card";
import {MatGridList, MatGridListModule, MatGridTile} from "@angular/material/grid-list";

@Component({
  selector: 'app-about-us',
  standalone: true,
  imports: [
    MatCardHeader,
    MatCardModule,
    MatGridTile,
    MatCardContent,
    MatGridListModule,
    MatCardActions
  ],
  templateUrl: './about-us.component.html',
  styleUrl: './about-us.component.css'
})
export class AboutUsComponent {
  customOptions: any = {
    loop: true,
    autoplay: true,
    autoplayTimeout: 3000,
    autoplayHoverPause: true,
    items: 1,
    dots: true,
    nav: true,
    navText: ['Anterior', 'Siguiente']
  };
  showAlert() {
    Swal.fire({
      title: '¡Bienvenido a MyApp!',
      text: 'Gracias por visitar nuestra aplicación.',
      icon: 'info',
      confirmButtonText: 'Cerrar'
    });
  }

}
