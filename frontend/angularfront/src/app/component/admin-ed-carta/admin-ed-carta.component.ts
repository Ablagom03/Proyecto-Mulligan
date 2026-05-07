import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Observable } from 'rxjs';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, RouterModule, Params } from '@angular/router';

import { Carta } from '../../model/Carta';
import { Empresa } from '../../model/Empresa';
import { CartasService } from '../../service/cartas.service';
import { EmpresaService } from '../../service/empresa.service';

@Component({
  selector: 'app-admin-ed-carta',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
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

      this.cdr.detectChanges();
    });
  }

  cargarEmpresas() {
    this.listadoEmpresas$ = this.empresaService.getEmpresas();
  }

  onFileSelected(event: any, carta: any) {
    const file = event.target.files[0];
    if (!file) return;

    const reader = new FileReader();

    reader.onload = () => {
      carta.preview = reader.result;

      const base64 = (reader.result as string).split(',')[1];

      carta.imagen = {
        nombre: carta.nombreImagen,
        data: base64
      }

      this.cdr.detectChanges();
    };

    reader.readAsDataURL(file);
  }

  actualizarCarta(carta: any) {
    console.log('Actualizando carta:', carta);

    this.cartasService.updateCarta(carta, carta.cardid).subscribe({
      next: () => {
        alert('Carta actualizada correctamente');
      },
      error: err => {
        console.error(err);
        alert('Error al actualizar carta');
      }
    });
  }
}