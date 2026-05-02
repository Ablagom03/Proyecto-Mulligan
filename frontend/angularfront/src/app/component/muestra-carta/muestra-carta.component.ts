import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute,RouterModule, Params } from '@angular/router';
import { CommonModule  } from '@angular/common';
import { map, Observable } from 'rxjs';
import { Carta } from '../../model/Carta';
import { CartasService } from '../../service/cartas.service';
import { OfertaService } from '../../service/oferta.service';


@Component({
  selector: 'app-muestra-carta',
  standalone: true,
  imports: [RouterModule, CommonModule],
  templateUrl: './muestra-carta.component.html',
  styleUrls: ['./muestra-carta.component.css']
})
export class MuestraCartaComponent implements OnInit {

  cartaEncontrada$ !: Observable<Carta>;
  ofertasVenta$!: Observable<any[]>;

  constructor(
    private cartasService: CartasService, 
    private ofertaService: OfertaService,
    private ar: ActivatedRoute
  ) { }

  ngOnInit() {
    this.ar.queryParams.subscribe((entrada: Params) => {
      const id = entrada['id'];
      if (id) {
        this.cargarDatos(id);
        console.log(id);
      }
    });
  }
  cargarDatos(id: bigint) { 
  this.cartaEncontrada$ = this.cartasService.getCartaPorId(id);
  
  this.ofertasVenta$ = this.ofertaService.getOfertasPorCarta(id).pipe(
    map(ofertas => {
      console.log('Ofertas recibidas:', ofertas);
      return ofertas.filter(o => o.tipo === 'VENTA');
    })
  );
}

}
