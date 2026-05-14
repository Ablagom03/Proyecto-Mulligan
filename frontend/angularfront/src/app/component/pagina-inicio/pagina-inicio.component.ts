import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CartasService } from '../../service/cartas.service';
import { Carta } from '../../model/Carta';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common'


@Component({
  selector: 'app-pagina-inicio',
  standalone: true,
  imports: [RouterModule, FormsModule, CommonModule],
  templateUrl: './pagina-inicio.component.html',
  styleUrls: ['./pagina-inicio.component.css']
})
export class PaginaInicioComponent implements OnInit {

  busqueda: string = '';
  resultados: Carta[] = [];
  datos: Carta[] = [];

  constructor(private cartasService: CartasService) { }

  ngOnInit() {
    this.cartasService.getCartas().subscribe((cartas: Carta[]) => {
      this.datos = cartas.map(carta => carta);
    });
  }

  buscarCartas() {

    const texto = this.busqueda.trim().toLowerCase();

    if (!texto) {
      this.resultados = [];
      return;
    }

    this.resultados = this.datos.filter(carta =>
      carta.nombrecard.toLowerCase().includes(texto)
    );

    this.mostrar = true;
  }

  //Para el dropdown
  mostrar: boolean = false;

  seleccionar(carta: string) {
    this.busqueda = carta;
    this.mostrar = false;
  }

  ocultar() {
    setTimeout(() => {
      this.mostrar = false;
    }, 150);
  }
}
