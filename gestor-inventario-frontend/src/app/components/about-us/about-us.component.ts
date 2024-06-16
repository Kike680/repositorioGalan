import { Component } from '@angular/core';
import Swal from "sweetalert2";
import {MatCardActions, MatCardContent, MatCardHeader, MatCardModule} from "@angular/material/card";
import {MatGridListModule, MatGridTile} from "@angular/material/grid-list";
import {CarouselModule} from "ngx-owl-carousel-o";
import {MatAccordion, MatExpansionModule, MatExpansionPanel, MatExpansionPanelTitle} from "@angular/material/expansion";
import {MatButtonModule} from "@angular/material/button";

@Component({
  selector: 'app-about-us',
  standalone: true,
  imports: [
    MatCardHeader,
    MatCardModule,
    MatGridTile,
    MatCardContent,
    MatGridListModule,
    MatCardActions,
    CarouselModule,
    MatExpansionPanel,
    MatExpansionPanelTitle,
    MatAccordion,
    MatExpansionModule, MatButtonModule,
  ],
  templateUrl: './about-us.component.html',
  styleUrl: './about-us.component.css'
})
export class AboutUsComponent {
  showAlert() {
    Swal.fire({
      title: "Disponibilidad",
      text: "Lunes-Viernes 9AM-8PM",
      imageUrl: "https://unsplash.it/400/200",
      imageWidth: 400,
      imageHeight: 200,
      imageAlt: "Custom image"
    });
  }

}
