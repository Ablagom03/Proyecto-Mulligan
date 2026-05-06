import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule, Params } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { map, Observable, switchMap, catchError, of, shareReplay } from 'rxjs';
import { Carta } from '../../model/Carta';
import { CartasService } from '../../service/cartas.service';
import { OfertaService } from '../../service/oferta.service';
interface DatosPrecio {
  precio: string;
  url: string | null;
}

@Component({
  selector: 'app-muestra-carta',
  standalone: true,
  imports: [RouterModule, CommonModule],
  templateUrl: './muestra-carta.component.html',
  styleUrls: ['./muestra-carta.component.css']
})
export class MuestraCartaComponent implements OnInit {

  cartaEncontrada$!: Observable<Carta>;
  ofertasVenta$!: Observable<any[]>;
  precioExterno$!: Observable<DatosPrecio>;

  constructor(
    private cartasService: CartasService, 
    private ofertaService: OfertaService,
    private ar: ActivatedRoute,
    private http: HttpClient
  ) { }

  ngOnInit() {
    this.ar.queryParams.subscribe((entrada: Params) => {
      const id = entrada['id'];
      if (id) {
        this.cargarDatos(id);
      }
    });
  }

  cargarDatos(id: bigint) { 
    this.cartaEncontrada$ = this.cartasService.getCartaPorId(id).pipe(
      shareReplay(1)
    );

    this.ofertasVenta$ = this.ofertaService.getOfertasPorCarta(id).pipe(
      map(ofertas => ofertas.filter(o => o.tipo === 'VENTA'))
    );

    this.precioExterno$ = this.cartaEncontrada$.pipe(
      switchMap(carta => this.buscarPrecioEnNuestroBackend(carta.nombrecard, carta.empresa)),
      catchError(() => of({ precio: 'No disponible', url: null }))
    );
  }

  private buscarPrecioEnNuestroBackend(nombre: string, empresa: string): Observable<DatosPrecio> {
    const url = `/api/precios/externo`;
    
    const params = {
      nombre: nombre,
      empresa: empresa
    };

    return this.http.get<DatosPrecio>(url, { params }).pipe(
      catchError(err => {
        console.error('Error al conectar con el backend de precios:', err);
        return of({ precio: 'No disponible', url: null });
      })
    );
  }
}