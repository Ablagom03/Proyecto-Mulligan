import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { Carta } from '../../model/Carta';
import { CartasService } from '../../service/cartas.service';


@Component({
  selector: 'app-marca',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './marca.component.html',
  styleUrls: ['./marca.component.css']
})

export class MarcaComponent implements OnInit {

  listadoCartas : Carta[] = [];
  constructor(private cartasService : CartasService, private cdr : ChangeDetectorRef) { }

  ngOnInit() {
  }

  cargarCartas(marca){
    this.cartasService//...Está en WIP
  }

}

