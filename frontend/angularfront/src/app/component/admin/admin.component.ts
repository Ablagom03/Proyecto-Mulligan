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
    nombreCard: '',
    descripcion: '',
    coleccion: '',
    empresa: '',
    imagen: {
      nombre: '',
      datos: ''
    }
  };

  nombreImagen: string = '';
  preview: string | ArrayBuffer | null = null;

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
    console.log(this.carta);

    this.cartasService.insertCarta(this.carta).subscribe({
      next: () => {
        alert('Carta guardada correctamente');
        console.log(this.carta);
        this.resetFormulario();
      },
      error: err => {
        console.error(err);
        alert('Error al guardar carta');
      }
    });
  }

  resetFormulario() {
    this.carta = {
      nombreCard: '',
      descripcion: '',
      coleccion: '',
      empresa: '',
      imagen: {
        nombre: '',
        datos: ''
      }
    };

    this.preview = null;
    this.nombreImagen = '';
  }
}