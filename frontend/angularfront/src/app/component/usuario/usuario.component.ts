import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../../service/auth.service';
import { Usuario } from '../../model/Usuario';
import { Observable, switchMap, of, catchError } from 'rxjs';

@Component({
  selector: 'app-usuario',
  standalone: true,
  templateUrl: './usuario.component.html',
  imports: [CommonModule],
  styleUrl: './usuario.component.css',
})
export class UsuarioComponent {
  private authService = inject(AuthService);
  private route = inject(ActivatedRoute);

  vistaActual: 'inventario' | 'deseados' = 'inventario';

  currentUser$: Observable<Usuario | null> = this.route.paramMap.pipe(
    switchMap((p) => {
      const u = p.get('usuario');

      if (u) {
        return this.authService
          .getUsuarioPorNombre(u)
          .pipe(catchError(() => this.authService.getUsuarioEnUso()));
      }

      return this.authService.getUsuarioEnUso();
    }),

    catchError((err) => {
      console.error('Error al obtener el usuario: ', err);
      return of(null);
    }),
  );

  mostrarInventario() {
    this.vistaActual = 'inventario';
  }

  mostrarDeseados() {
    this.vistaActual = 'deseados';
  }
}
