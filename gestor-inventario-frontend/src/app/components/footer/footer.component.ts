import { Component } from '@angular/core';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  standalone: true,
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
