import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import {Router, RouterLink} from '@angular/router';
import { ProductoService } from '../../services/producto.service';
import { FileUploadService } from '../../services/file-upload.service';
import Swal from 'sweetalert2';
import {Producto} from "../../models/producto";
import {Categoria} from "../../models/categoria";
import {AuthService} from "../../services/auth.service";
import {MatFormField} from "@angular/material/form-field";
import {MatOption, MatSelect} from "@angular/material/select";
import {CategoriaService} from "../../services/categoria.service";

@Component({
  selector: 'app-producto',
  templateUrl: './producto.component.html',
  standalone: true,
  styleUrls: ['./producto.component.css'],
  imports: [
    ReactiveFormsModule,
    HttpClientModule,
    RouterLink,
    MatFormField,
    MatSelect,
    MatOption
  ]
})
export class ProductoComponent {
  productoForm: FormGroup;
  selectedFile: File | null = null;
  base64Image!: string;
  categorias: any[] = [];

  ngOnInit(): void {
    this.categoriaService.getCategorias().subscribe(data => {
      this.categorias = data;
    });
  }
  constructor(
    private fb: FormBuilder,
    private productoService: ProductoService,
    private fileUploadService: FileUploadService,
    private authService: AuthService,
    private router: Router,
    private categoriaService: CategoriaService
  ) {

    //Para crear un producto no me interesa ponmerle cantidad
    this.productoForm = this.fb.group({
      nombre: ['', Validators.required],
      descripcion: ['', Validators.required],
      precio: ['', Validators.required],
      cantidad: ['', Validators.required],
      categoria: ['', Validators.required],
      imagen:['', Validators.required],
    });
  }




  onFileSelected(event: Event): void {
    const fileInput = event.target as HTMLInputElement;

    if (fileInput.files && fileInput.files.length > 0) {
      const file = fileInput.files[0];
      const reader = new FileReader();

      reader.onload = (e) => {
        this.base64Image = e.target?.result as string;
        //Esta linea la he añadido
        this.productoForm.patchValue({ imagen: this.base64Image });
      };

      reader.readAsDataURL(file);
    }
  }

  onSubmit(): void {
    console.log(this.productoForm);
    this.productoForm.markAllAsTouched();

    if (this.productoForm.valid && this.base64Image) {
      const producto = new Producto({id:0,nombre:this.productoForm.value.nombre, descripcion: this.productoForm.value.descripcion, cantidad:this.productoForm.value.cantidad, precio:this.productoForm.value.precio, imagenUrl:this.base64Image});

      producto.categoria={id:this.productoForm.value.categoria};
      producto.usuario= this.authService.getUserInfoCookie();
      //Inserto el id diciendole que es el id de la categoria el quel e paso por el form, y el ya se trae el resto de informacion de categoria
      const categoria1 = new Categoria({id: this.productoForm.value.categoria})
      this.fileUploadService.upload(producto).subscribe({
        next: (event: any) => {
          Swal.fire({
            title: 'Producto creado',
            text: 'El producto se ha creado correctamente',
            icon: 'success'
          });
          this.productoForm.reset();
          this.selectedFile = null;
        },
        error: (err: any) => {
          Swal.fire({
            title: 'Error',
            text: 'Hubo un error al crear el producto',
            icon: 'error'
          });
        }
      });
    } else {
      Swal.fire({
        title: 'Error con los campos',
        text: 'Los campos no son válidos',
        icon: 'error'
      });
    }
  }
  goBack(): void {
    this.router.navigate(['/listaproductos']);
  }


}
