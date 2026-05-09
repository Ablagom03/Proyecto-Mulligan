import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../service/auth.service';
import { switchMap, catchError, map } from 'rxjs/operators';
import { BehaviorSubject, of, combineLatest, Subscription } from 'rxjs';
import { InventarioService } from '../../service/inventario.service';
import { ComentarioService } from '../../service/comentario.service';
import { Component, inject, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ModalService } from '../../service/modal.service';

@Component({
  selector: 'app-usuario',
  standalone: true,
  templateUrl: './usuario.component.html',
  styleUrl: './usuario.component.css',
  imports: [CommonModule],
})
export class UsuarioComponent implements OnInit, OnDestroy {
  private authService = inject(AuthService);
  private inventarioService = inject(InventarioService);
  private comentarioService = inject(ComentarioService);
  private route = inject(ActivatedRoute);
  private modalService = inject(ModalService);

  private refresh$ = new BehaviorSubject<void>(undefined);
  private subscripcionCambios?: Subscription;

  vistaActual: 'inventario' | 'deseados' | 'comentarios' = 'inventario';

  perfilPublico$ = this.route.paramMap.pipe(
    switchMap(p => {
      const nombre = p.get('usuario');
      return nombre ? this.authService.getUsuarioPorNombre(nombre) : this.authService.getUsuarioEnUso();
    }),
    catchError(() => of(null))
  );

  esPropioPerfil$ = combineLatest([
    this.perfilPublico$,
    this.authService.getUsuarioEnUso()
  ]).pipe(
    map(([perfil, usuarioLogueado]) => {
      return !!perfil && !!usuarioLogueado && perfil.usrId === usuarioLogueado.usrId;
    })
  );

  private inventarioCompleto$ = this.refresh$.pipe(
    switchMap(() => this.perfilPublico$),
    switchMap(user => {
      if (user?.usrId) return this.inventarioService.getInventarioPorUsuario(user.usrId);
      return of([]);
    })
  );

  ofertasVenta$ = this.inventarioCompleto$.pipe(
    map(items => items.filter(i => i.tipo === 'VENTA'))
  );

  ofertasCompra$ = this.inventarioCompleto$.pipe(
    map(items => items.filter(i => i.tipo === 'COMPRA'))
  );

  comentariosRecibidos$ = this.refresh$.pipe(
    switchMap(() => this.perfilPublico$),
    switchMap(user => {
      if (user?.usrId) return this.comentarioService.obtenerComentariosRecibidos(user.usrId);
      return of([]);
    })
  );

  ngOnInit(): void {
    this.subscripcionCambios = this.modalService.ofertaCambiada$.subscribe(() => {
      console.log('Recibida notificación de cambio, recargando inventario...');
      this.recargarDatos();
    });
  }

  ngOnDestroy(): void {
    this.subscripcionCambios?.unsubscribe();
  }

  recargarDatos() {
    this.refresh$.next();
  }

  eliminarOferta(id: number) {
    if (confirm('¿Estás seguro de que deseas eliminar esta oferta?')) {
      this.inventarioService.deleteInventario(id).subscribe({
        next: () => {
          this.recargarDatos();
        },
        error: (err: any) => alert('Error al eliminar: ' + err.message)
      });
    }
  }

  editarOferta(id: number) {
    this.modalService.abrir(id);
  }

  mostrarInventario() { this.vistaActual = 'inventario'; }
  mostrarDeseados() { this.vistaActual = 'deseados'; }
  mostrarComentarios() { this.vistaActual = 'comentarios'; }
}