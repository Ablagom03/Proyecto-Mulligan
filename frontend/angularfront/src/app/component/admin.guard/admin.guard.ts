import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from '../../service/auth.service';
import { Observable } from 'rxjs';
import { tap } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class AdminGuard implements CanActivate {

    constructor(private authService: AuthService, private router: Router) { }

    canActivate(): Observable<boolean> {
        return this.authService.isAdmin().pipe(
            tap(isAdmin => {
                console.log('¿Es admin?', isAdmin);
                if (!isAdmin) {
                    this.router.navigate(['/inicio']);
                }
            })
        );
    }
}