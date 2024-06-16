import { Component } from '@angular/core';
import Swal from 'sweetalert2';
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  standalone: true,
  imports: [
    RouterLink
  ],
  styleUrls: ['./footer.component.css']
})
export class FooterComponent {
  showAlert() {
    Swal.fire({
      title: 'Subscribe',
      text: 'Thank you for subscribing to our newsletter!',
      icon: 'success',
      confirmButtonText: 'Close'
    });
  }
}
