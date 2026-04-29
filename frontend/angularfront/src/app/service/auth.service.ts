import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { Usuario } from '../model/Usuario';
import { catchError, of } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private baseUrl = 'http://localhost:8080';
  private isLoggedInSubject = new BehaviorSubject<boolean>(false);
  public Logeado$ = this.isLoggedInSubject.asObservable();
  authService: any;
  router: any;

  constructor(private http: HttpClient) {
    this.EstadoLogin();
  }

  private EstadoLogin() {
    this.getUsuarioEnUso().subscribe({
      next: (user) => {
        if (user) {
          this.isLoggedInSubject.next(true);
        } else {
          this.isLoggedInSubject.next(false);
        }
      },
      error: () => this.isLoggedInSubject.next(false)
    });
  }

  registro(user: Usuario): Observable<Usuario> {
    return this.http.post<Usuario>(`${this.baseUrl}/usuario`, user, {
      withCredentials: true
    });
  }

  login(credenciales: { email: string; passwd: string }): Observable<any> {
  return this.http.post(`${this.baseUrl}/auth/login`, credenciales, {
    withCredentials: true
  });
}
  credenciales(credenciales: any) {
    throw new Error('Method not implemented.');
  }

  logout(): Observable<any> {
    return this.http.post(`${this.baseUrl}/auth/logout`, {}, {
      withCredentials: true
    });
  }

  getUsuarioEnUso(): Observable<Usuario | null> {
  return this.http.get<Usuario>(`${this.baseUrl}/auth/me`, { withCredentials: true }).pipe(
    catchError((error) => {
      return of(null); 
    })
  );
}

  getUsuarioPorNombre(nombre: string): Observable<Usuario> {
    return this.http.get<Usuario>(`${this.baseUrl}/usuario/nombre/${nombre}`, {
      withCredentials: true
    });
  }

  setLoggedIn(value: boolean) {
    this.isLoggedInSubject.next(value);
  }

  isLoggedInValue(): boolean {
    return this.isLoggedInSubject.value;
  }
}