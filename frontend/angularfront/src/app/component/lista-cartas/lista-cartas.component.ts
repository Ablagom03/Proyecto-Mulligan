import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Carta } from '../../model/Carta';
import { CartasService } from '../../service/cartas.service';

@Component({
  selector: 'app-lista-cartas',
  templateUrl: './lista-cartas.component.html',
  styleUrls: ['./lista-cartas.component.css']
})
export class ListaCartasComponent implements OnInit {

  listadoCartas : Carta[] = [];
  constructor(private cartasService : CartasService, private cdr : ChangeDetectorRef) { }

  ngOnInit() {
    this.cargarCartas();
  }

  cargarCartas(){
    this.cartasService.getCartas().subscribe({
      next : (cartas) => {
        this.listadoCartas = cartas;
        this.cdr.detectChanges();
      },
      error : (error) => {
        console.log(error);
      }
    });
  }
}
