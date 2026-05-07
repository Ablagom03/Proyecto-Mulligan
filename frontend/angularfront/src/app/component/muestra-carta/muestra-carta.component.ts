import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute, RouterModule, Params } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Observable, switchMap, catchError, of, shareReplay, BehaviorSubject } from 'rxjs';

import { Carta } from '../../model/Carta';
import { CartasService } from '../../service/cartas.service';
import { OfertaService } from '../../service/oferta.service';
import { InventarioService } from '../../service/inventario.service';
import { AuthService } from '../../service/auth.service';

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
  private authService = inject(AuthService);
  private cartasService = inject(CartasService);
  private ofertaService = inject(OfertaService);
  private inventarioService = inject(InventarioService);
  private ar = inject(ActivatedRoute);
  private http = inject(HttpClient);

  cartaEncontrada$!: Observable<Carta>;
  precioExterno$!: Observable<DatosPrecio>;

  // Subjects para manejar las listas reactivas de ofertas
  ofertasVenta$ = new BehaviorSubject<any[]>([]);
  ofertasCompra$ = new BehaviorSubject<any[]>([]);

  vistaActual: 'VENTA' | 'COMPRA' = 'VENTA';
  usuarioLogueado$ = this.authService.user$;
  mensajeCompra: string = '';

  ngOnInit() {
    this.ar.queryParams.subscribe((entrada: Params) => {
      const idCarta = entrada['id'];
      if (idCarta) {
        this.cargarDatos(idCarta);
      }
    });
  }

  cargarDatos(idCarta: bigint) {
    // 1. Obtener datos de la carta
    this.cartaEncontrada$ = this.cartasService.getCartaPorId(idCarta).pipe(
      shareReplay(1)
    );

    // 2. Cargar ofertas y distribuirlas por tipo
    this.ofertaService.getOfertasPorCarta(idCarta).subscribe({
      next: (ofertas) => {
        console.log('Ofertas cargadas:', ofertas);
        this.ofertasVenta$.next(ofertas.filter(o => o.tipo === 'VENTA'));
        this.ofertasCompra$.next(ofertas.filter(o => o.tipo === 'COMPRA'));
      },
      error: (err) => console.error('Error cargando ofertas:', err)
    });

    // 3. Buscar precio en API externa
    this.precioExterno$ = this.cartaEncontrada$.pipe(
      switchMap(carta => this.buscarPrecio(carta.nombrecard, carta.empresa)),
      catchError(() => of({ precio: 'No disponible', url: null }))
    );
  }

  mostrarVentas() { 
    this.vistaActual = 'VENTA'; 
    this.mensajeCompra = ''; 
  }
  
  mostrarCompras() { 
    this.vistaActual = 'COMPRA'; 
    this.mensajeCompra = ''; 
  }

  procesarTransaccion(idInventarioReal: number, copiasActuales: number, idPropietario: number) {
    const usuarioActual = this.authService.getUsuarioEnUsoSincrono();

    // Validación: No puedes comprarte a ti mismo
    if (usuarioActual && usuarioActual.usrId === idPropietario) {
      this.mensajeCompra = 'No puedes realizar transacciones con tus propias ofertas.';
      return;
    }

    console.log('Iniciando transacción para Oferta ID:', idInventarioReal);

    const subjectActual = this.vistaActual === 'VENTA' ? this.ofertasVenta$ : this.ofertasCompra$;
    const ofertas = subjectActual.value;

    let operacion$: Observable<any>;
    
    // Si quedan varias copias, restamos una (PUT). Si es la última, eliminamos (DELETE).
    if (copiasActuales > 1) {
      operacion$ = this.inventarioService.updateInventario(idInventarioReal, { copias: copiasActuales - 1 });
    } else {
      // Este método debe llamar a /api/inventario/transaccion/{id}
      operacion$ = this.inventarioService.deleteInventario(idInventarioReal);
    }

    operacion$.subscribe({
      next: () => {
        this.mensajeCompra = 'Operación realizada correctamente.';

        // Lógica de Reputación: Sumar 1 punto al dueño original de la oferta
        this.authService.getUsuarioPorId(idPropietario).subscribe(user => {
          if (user) {
            const nuevaRep = (user.reputacion || 0) + 1;
            this.authService.actualizarReputacion(idPropietario, nuevaRep).subscribe();
          }
        });

        // Actualizar la interfaz sin recargar
        const nuevasOfertas = copiasActuales > 1
          ? ofertas.map(o => o.id === idInventarioReal ? { ...o, copias: o.copias - 1 } : o)
          : ofertas.filter(o => o.id !== idInventarioReal);

        subjectActual.next(nuevasOfertas);
      },
      error: (err) => {
        console.error('Error en la transacción:', err);
        this.mensajeCompra = 'Error al procesar la transacción. Es posible que la oferta ya no exista.';
      }
    });
  }

  private buscarPrecio(nombre: string, empresa: string): Observable<DatosPrecio> {
    const url = `/api/precios/externo`;
    const params = { nombre, empresa };
    return this.http.get<DatosPrecio>(url, { params }).pipe(
      catchError(() => of({ precio: 'No disponible', url: null }))
    );
  }
}