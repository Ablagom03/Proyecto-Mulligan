import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule, Params } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Observable } from 'rxjs';
import { Carta } from '../../model/Carta';
import { CartasService } from '../../service/cartas.service';


@Component({
  selector: 'app-marca',
  standalone: true,
  imports: [RouterModule, CommonModule],
  templateUrl: './marca.component.html',
  styleUrls: ['./marca.component.css']
})

export class MarcaComponent implements OnInit {
  constructor(private cartasService: CartasService, private cdr: ChangeDetectorRef, private ar: ActivatedRoute) { }

  ngOnInit() {
    this.ar.queryParams.subscribe((entrada: Params) => {

      const marca = entrada['nombre'];

      this.cargarCartasMarca(marca);
      //Para debuguear
      //console.log("Se ha buscado " + marca);
    }
    )
  }

  listadoCartas$!: Observable<Carta[]>;

  cargarCartasMarca(marca: string) {
    this.listadoCartas$ = this.cartasService.getCartasPorMarca(marca);
  }

}

