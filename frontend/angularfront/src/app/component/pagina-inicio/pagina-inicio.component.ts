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
    this.resultados = this.datos.filter(carta =>
      carta.nombrecard.toLowerCase().includes(this.busqueda.toLowerCase())
    );

    this.mostrar = true;

    //Para debuguear
    //console.log("Busqueda: ", this.busqueda);
    //console.log("Datos: ", this.datos);
    //console.log("Resultado: ", this.resultados);
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
    }, 3);
  }
}
