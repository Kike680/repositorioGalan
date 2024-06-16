import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { ProductoService } from '../../services/producto.service';
import { AuthService } from '../../services/auth.service';
import { CategoriaService } from '../../services/categoria.service';
import { Producto } from '../../models/producto';
import { Categoria } from '../../models/categoria';
import {FileUploadService} from "../../services/file-upload.service";


@Component({
  selector: 'app-editar-producto',
  templateUrl: './editar-producto.component.html',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule
  ],
  // Reutilizamos el mismo HTML
  styleUrls: ['./editar-producto.component.css']
})
export class EditarProductoComponent implements OnInit {
  editarProductoForm!: FormGroup;
  selectedFile: File | null = null;
  base64Image!: string;
  categorias: any[] = [];
  productId: number | null = null;
  producto!: Producto;

  constructor(
    private fb: FormBuilder,
    private productoService: ProductoService,
    private fileUploadService: FileUploadService,
    private authService: AuthService,
    private router: Router,
    private categoriaService: CategoriaService,
    private route: ActivatedRoute
  ) {
    this.editarProductoForm = this.fb.group({
      editnombre: ['', Validators.required],
      editdescripcion: ['', Validators.required],
      editprecio: ['', Validators.required],
      editcantidad: ['', Validators.required],
      editcategoria: ['',  Validators.required],
      editimagen:['', Validators.required],

    });


  }


 /* ngOnInit(): void {
    this.categoriaService.getCategorias().subscribe(
      data => this.categorias = data,
      error => console.error('Error al obtener categorías', error)
    );
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.productId = +id;

        this.productoService.getProductById(parseInt(id)).subscribe(
          (data: Producto) => {
            this.producto = data;
            this.productoForm = this.fb.group({
              nombre: [this.producto?.nombre, Validators.required],
              descripcion: [this.producto?.descripcion, Validators.required],
              precio: [this.producto?.precio, Validators.required],
              cantidad: [this.producto?.cantidad, Validators.required],
              categoria: [this.producto?.categoria?.nombre, Validators.required],
              imagen:[this.producto?.imagenUrl, Validators.required],

            });
            this.base64Image = this.producto?.imagenUrl;
          }
        )
      }
    });
    //Para crear un producto no me interesa ponmerle cantidad

  }*/
  ngOnInit(): void {
    this.categoriaService.getCategorias().subscribe(
      data => this.categorias = data,
      error => console.error('Error al obtener categorías', error)
    );

    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.productId = +id;
        this.loadProduct(this.productId);
      }
    });
  }

  loadProduct(id: number): void {
    this.productoService.getProductById(id).subscribe(
      (data: Producto) => {
        this.producto = data;
        console.log(data);
        this.editarProductoForm.patchValue({
          editnombre: this.producto.nombre,
          editdescripcion: this.producto.descripcion,
          editprecio: this.producto.precio,
          editcantidad: this.producto.cantidad,
          editcategoria: this.producto.categoria?.id,  // Asumiendo que categoria tiene un id
          editimagen: this.producto.imagenUrl
        });
        this.base64Image = this.producto.imagenUrl;
      }
    );
  }



  onFileSelected(event: Event): void {
    const fileInput = event.target as HTMLInputElement;

    if (fileInput.files && fileInput.files.length > 0) {
      const file = fileInput.files[0];
      const reader = new FileReader();

      reader.onload = (e) => {
        this.base64Image = e.target?.result as string;
        //Esta linea la he añadido
        this.editarProductoForm.patchValue({ editimagen: this.base64Image });
      };

      reader.readAsDataURL(file);
    }
  }

  onSubmit(): void {
    console.log(this.editarProductoForm);
    this.editarProductoForm.markAllAsTouched();

    if (this.editarProductoForm.valid && this.base64Image) {
      const producto = new Producto({id:this.producto.id,nombre:this.editarProductoForm.value.editnombre, descripcion: this.editarProductoForm.value.editdescripcion, cantidad:this.editarProductoForm.value.editcantidad, precio:this.editarProductoForm.value.editprecio, imagenUrl:this.base64Image});

      producto.categoria={id:this.editarProductoForm.value.editcategoria};
      producto.usuario= this.authService.getUserInfoCookie();
      //Inserto el id diciendole que es el id de la categoria el quel e paso por el form, y el ya se trae el resto de informacion de categoria
      const categoria1 = new Categoria({id: this.editarProductoForm.value.editcategoria})
      this.fileUploadService.upload(producto).subscribe({
        next: (event: any) => {
          Swal.fire({
            title: 'Producto creado',
            text: 'El producto se ha creado correctamente',
            icon: 'success'
          });
          this.editarProductoForm.reset();
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


 /* protected readonly of = of;*/
}
