import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute,RouterModule, Params } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Observable } from 'rxjs';
import { FormsModule } from '@angular/forms';

import { Carta } from '../../model/Carta';
import { Empresa } from '../../model/Empresa';
import { CartasService } from '../../service/cartas.service';
import { EmpresaService } from '../../service/empresa.service';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  constructor(
    private cartasService: CartasService,
    private empresaService: EmpresaService,
    private cdr: ChangeDetectorRef
  ) {}

  listadoCartas$!: Observable<Carta[]>;
  listadoEmpresas$!: Observable<Empresa[]>;

  carta: any = {
    nombrecard: '',
    descripcion: '',
    coleccion: '',
    empresa: '',
    imagen: {
      nombre: '',
      data: ''
    }
  };

  nombreImagen: string = '';
  preview: string | ArrayBuffer | null = null;
  mensajeError: string | null = null;
  mensajeExito: string | null = null;
  erroresValidacion: { [key: string]: string } = {};

  ngOnInit() {
    this.cargarCartas();
    this.cargarEmpresas();
  }

  cargarCartas() {
    this.listadoCartas$ = this.cartasService.getCartas();
  }

  cargarEmpresas() {
    this.listadoEmpresas$ = this.empresaService.getEmpresas();
  }

  onFileSelected(event: any) {
    const file = event.target.files[0];

    if (!file) return;

    const reader = new FileReader();

    reader.onload = () => {
      this.preview = reader.result;

      // Guardando la imagen
      const base64 = (reader.result as string).split(',')[1];

      this.carta.imagen = {
        nombre: this.nombreImagen,
        data: base64
      };

      this.cdr.detectChanges();
    };

    reader.readAsDataURL(file);
  }

  guardarCarta() {
    this.mensajeError = null;
    this.mensajeExito = null;
    this.erroresValidacion = {};

    console.log(this.carta);

    this.cartasService.insertCarta(this.carta).subscribe({
      next: () => {
        this.mensajeExito = 'Carta guardada correctamente';
        console.log(this.carta);
        setTimeout(() => {
          this.resetFormulario();
          this.cargarCartas();
        }, 1500);
      },
      error: (err: any) => {
        console.error('Error completo:', err);
        if (err.error && err.error.errors) {
          this.erroresValidacion = err.error.errors;
          this.mensajeError = 'Por favor, corrige los errores en el formulario';
        } else if (err.error && err.error.message) {
          this.mensajeError = err.error.message;
        } else if (err.error && typeof err.error === 'string') {
          this.mensajeError = err.error;
        } else if (err.status === 400) {
          this.mensajeError = 'Datos inválidos. Por favor, verifica los campos.';
        } else {
          this.mensajeError = 'Error al guardar carta';
        }
      }
    });
  }

  resetFormulario() {
    this.carta = {
      nombrecard: '',
      descripcion: '',
      coleccion: '',
      empresa: '',
      imagen: {
        nombre: '',
        data: ''
      }
    };

    this.preview = null;
    this.nombreImagen = '';
    this.mensajeError = null;
    this.mensajeExito = null;
    this.erroresValidacion = {};
  }
}