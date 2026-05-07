import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule, Params } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { map, Observable, switchMap, catchError, of, shareReplay } from 'rxjs';
import { Carta } from '../../model/Carta';
import { CartasService } from '../../service/cartas.service';
import { OfertaService } from '../../service/oferta.service';
import { InventarioService } from '../../service/inventario.service';
import { Usuario } from '../../model/Usuario';
import { BehaviorSubject } from 'rxjs';

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
  ofertasVenta$ = new BehaviorSubject<any[]>([]);
  precioExterno$!: Observable<DatosPrecio>;

  constructor(
    private cartasService: CartasService,
    private ofertaService: OfertaService,
    private inventarioService: InventarioService,
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

    this.ofertaService.getOfertasPorCarta(id).pipe(
      map(ofertas => ofertas.filter(o => o.tipo === 'VENTA'))
    ).subscribe(ofertas => {
      this.ofertasVenta$.next(ofertas);
    });

    this.ofertasVenta$.subscribe(ofertas => {
      this.ofertasActuales = [...ofertas];
    });
    this.precioExterno$ = this.cartaEncontrada$.pipe(
      switchMap(carta => this.buscarPrecio(carta.nombrecard, carta.empresa)),
      catchError(() => of({ precio: 'No disponible', url: null }))
    );
  }

  mensajeCompra = '';
  ofertasActuales: any[] = [];

  comprarCarta(id: number, copiasActuales: number) {

    const ofertas = this.ofertasVenta$.value;

    // Si hay más de una copia, simplemente elimina una
    if (copiasActuales > 1) {

      this.inventarioService.updateInventario(id, {
        copias: copiasActuales - 1
      }).subscribe({

        next: () => {

          this.mensajeCompra = 'Compra realizada correctamente';

          const nuevasOfertas = ofertas.map(oferta =>
            oferta.id === id
              ? { ...oferta, copias: oferta.copias - 1 }
              : oferta
          );

          this.ofertasVenta$.next(nuevasOfertas);

        },

        error: (err) => {
          console.error(err);
          this.mensajeCompra = 'Ha habido un error en la compra, por favor, inténtelo más tarde.';
        }

      });

    }

    // Si es la última copia, elimina la oferta
    else {

      this.inventarioService.deleteInventario(id).subscribe({

        next: () => {

          this.mensajeCompra = 'Compra realizada correctamente';

          const nuevasOfertas = ofertas.filter(
            oferta => oferta.id !== id
          );

          this.ofertasVenta$.next(nuevasOfertas);

        },

        error: (err) => {
          console.error(err);
          this.mensajeCompra = 'Ha habido un error en la compra, por favor, inténtelo más tarde.';
        }

      });

    }

  }


  private buscarPrecio(nombre: string, empresa: string): Observable<DatosPrecio> {
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