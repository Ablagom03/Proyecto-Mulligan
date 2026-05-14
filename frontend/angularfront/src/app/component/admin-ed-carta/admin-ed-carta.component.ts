import {
  ChangeDetectorRef,
  Component,
  OnInit
} from '@angular/core';

import { CommonModule } from '@angular/common';
import { Observable } from 'rxjs';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import { Empresa } from '../../model/Empresa';

import { CartasService } from '../../service/cartas.service';
import { EmpresaService } from '../../service/empresa.service';

@Component({
  selector: 'app-admin-ed-carta',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterModule
  ],
  templateUrl: './admin-ed-carta.component.html',
  styleUrls: ['./admin-ed-carta.component.css']
})

export class AdminEdCartaComponent implements OnInit {

  constructor(
    private cartasService: CartasService,
    private empresaService: EmpresaService,
    private cdr: ChangeDetectorRef
  ) { }

  listadoEmpresas$!: Observable<Empresa[]>;

  cartas: any[] = [];

  resultados: any[] = [];

  busqueda: string = '';

  mensajeError: string | null = null;
  mensajeExito: string | null = null;
  erroresValidacionPorCarta: { [key: number]: { [key: string]: string } } = {};

  ngOnInit() {

    this.cargarCartas();

    this.cargarEmpresas();
  }

  cargarCartas() {

    this.cartasService.getCartas().subscribe(data => {

      this.cartas = data.map(carta => ({

        ...carta,

        preview: null,

        nombreImagen: carta.imagen?.nombre || ''

      }));

      // Mostrar todas inicialmente
      this.resultados = [...this.cartas];

      this.cdr.detectChanges();
    });
  }

  cargarEmpresas() {

    this.listadoEmpresas$ =
      this.empresaService.getEmpresas();
  }

  buscarCartas() {

    const texto = this.busqueda.toLowerCase();

    this.resultados = this.cartas.filter(carta =>

      carta.nombrecard
        .toLowerCase()
        .includes(texto)

      ||

      carta.coleccion
        .toLowerCase()
        .includes(texto)

      ||

      carta.empresa
        .toLowerCase()
        .includes(texto)
    );
  }

  onFileSelected(event: any, carta: any) {

    const file = event.target.files[0];

    if (!file) return;

    const reader = new FileReader();

    reader.onload = () => {

      carta.preview = reader.result;

      const base64 =
        (reader.result as string).split(',')[1];

      carta.imagen = {

        nombre: carta.nombreImagen || file.name,

        data: base64
      };

      this.cdr.detectChanges();
    };

    reader.readAsDataURL(file);
  }

  actualizarCarta(carta: any) {

    this.mensajeError = null;
    this.mensajeExito = null;
    this.erroresValidacionPorCarta[carta.cardid] = {};

    console.log('Actualizando carta:', carta);

    this.cartasService
      .updateCarta(carta, carta.cardid)
      .subscribe({

        next: () => {

          this.mensajeExito = 'Carta actualizada correctamente';
          setTimeout(() => {
            this.cargarCartas();
            this.mensajeExito = null;
          }, 1500);
        },

        error: (err: any) => {

          console.error('Error completo:', err);
          if (err.error && err.error.errors) {
            this.erroresValidacionPorCarta[carta.cardid] = err.error.errors;
            this.mensajeError = 'Por favor, corrige los errores en el formulario';
          } else if (err.error && err.error.message) {
            this.mensajeError = err.error.message;
          } else if (err.error && typeof err.error === 'string') {
            this.mensajeError = err.error;
          } else if (err.status === 400) {
            this.mensajeError = 'Datos inválidos. Por favor, verifica los campos.';
          } else {
            this.mensajeError = 'Error al actualizar carta';
          }
        }
      });
  }

  trackByCarta(index: number, carta: any): number {

    return carta.cardid;
  }

  getErrores(cartaId: number): { [key: string]: string } {
    return this.erroresValidacionPorCarta[cartaId] || {};
  }
}