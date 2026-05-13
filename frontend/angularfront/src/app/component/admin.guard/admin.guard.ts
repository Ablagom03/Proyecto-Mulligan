import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from '../../service/auth.service';
import { Observable } from 'rxjs';
import { map, filter, take, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  canActivate(): Observable<boolean> {
    return this.authService.user$.pipe(

      // Esperar a que el usuario no sea undefined
      filter(user => user !== undefined),

      take(1),

      map(user => !!user && user.tipo === 'ADMIN'),

      tap(isAdmin => {
        console.log('¿Es admin?', isAdmin);

        if (!isAdmin) {
          this.router.navigate(['/inicio']);
        }
      })
    );
  }
}