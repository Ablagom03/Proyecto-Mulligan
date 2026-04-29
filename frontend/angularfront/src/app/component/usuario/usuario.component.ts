import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../../service/auth.service';
import { switchMap, catchError } from 'rxjs/operators';
import { BehaviorSubject, of } from 'rxjs';
import { InventarioService } from '../../service/inventario.service';
import { map } from 'rxjs';
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
  private refresh$ = new BehaviorSubject<void>(undefined);

  vistaActual: 'inventario' | 'deseados' = 'inventario';

  perfilPublico$ = this.route.paramMap.pipe(
    switchMap(p => {
      const nombre = p.get('usuario');
      return nombre ? this.authService.getUsuarioPorNombre(nombre) : this.authService.getUsuarioEnUso();
    }),
    catchError(() => of(null))
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


  mostrarInventario() { this.vistaActual = 'inventario'; }
  mostrarDeseados() { this.vistaActual = 'deseados'; }
}