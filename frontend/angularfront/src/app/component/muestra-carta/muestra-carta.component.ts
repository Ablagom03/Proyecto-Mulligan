import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute,RouterModule, Params } from '@angular/router';
import { CommonModule  } from '@angular/common';
import { Observable } from 'rxjs';
import { Carta } from '../../model/Carta';
import { CartasService } from '../../service/cartas.service';

@Component({
  selector: 'app-muestra-carta',
  standalone: true,
  imports: [RouterModule, CommonModule],
  templateUrl: './muestra-carta.component.html',
  styleUrls: ['./muestra-carta.component.css']
})
export class MuestraCartaComponent implements OnInit {

  constructor(private cartasService: CartasService, private cdr : ChangeDetectorRef, private ar : ActivatedRoute) { }

  ngOnInit() {
    this.ar.queryParams.subscribe((entrada: Params) => {
      const id = entrada['id'];
      this.cargarCarta(id);
      //Para debuguear
      console.log("Se ha encontrado la carta " + id);
    })
  }

  cartaEncontrada$ !: Observable<Carta>;

  cargarCarta(id: bigint) {
    this.cartaEncontrada$ = this.cartasService.getCartaPorId(id);
  }

}
