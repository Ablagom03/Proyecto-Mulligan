import { ActivatedRoute, Router } from '@angular/router'; 
import { AuthService } from '../../service/auth.service';
import { switchMap, catchError, map } from 'rxjs/operators';
import { BehaviorSubject, of, combineLatest } from 'rxjs'; 
import { InventarioService } from '../../service/inventario.service';
import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-usuario',
  standalone: true,
  templateUrl: './usuario.component.html',
  styleUrl: './usuario.component.css',
  imports: [CommonModule],
})
export class UsuarioComponent {
  private authService = inject(AuthService);
  private inventarioService = inject(InventarioService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private refresh$ = new BehaviorSubject<void>(undefined);

  vistaActual: 'inventario' | 'deseados' = 'inventario';

  // Obtiene el perfil que se está visitando
  perfilPublico$ = this.route.paramMap.pipe(
    switchMap(p => {
      const nombre = p.get('usuario');
      return nombre ? this.authService.getUsuarioPorNombre(nombre) : this.authService.getUsuarioEnUso();
    }),
    catchError(() => of(null))
  );

  // Comprueba si el usuario logueado es el mismo que el del perfil
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

  recargarDatos() {
    this.refresh$.next();
  }

  // Métodos de acción
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
    // Redirige a tu componente de formulario de edición
    this.router.navigate(['/editar-oferta', id]);
  }

  mostrarInventario() { this.vistaActual = 'inventario'; }
  mostrarDeseados() { this.vistaActual = 'deseados'; }
}