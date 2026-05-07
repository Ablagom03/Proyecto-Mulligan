import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject, of } from 'rxjs';
import { catchError, map, tap, switchMap } from 'rxjs/operators';
import { Usuario } from '../model/Usuario';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private baseUrl = '/api';

  private userSubject = new BehaviorSubject<Usuario | null>(null);
  public user$ = this.userSubject.asObservable();

  private isLoggedInSubject = new BehaviorSubject<boolean>(false);
  public Logeado$ = this.isLoggedInSubject.asObservable();

  constructor(private http: HttpClient) {
    this.EstadoLogin();
  }

  getUsuarioEnUsoSincrono(): Usuario | null {
    return this.userSubject.value;
  }

  private EstadoLogin() {
    this.getUsuarioEnUso().subscribe({
      next: (user) => this.actualizarEstado(user),
      error: () => this.actualizarEstado(null)
    });
  }

  private actualizarEstado(user: Usuario | null) {
    this.userSubject.next(user);
    this.isLoggedInSubject.next(!!user);
  }

  registro(user: Usuario): Observable<Usuario> {
    return this.http.post<Usuario>(`${this.baseUrl}/usuario`, user, {
      withCredentials: true
    });
  }

  login(credenciales: { email: string; passwd: string }): Observable<Usuario | null> {
    return this.http.post<any>(`${this.baseUrl}/auth/login`, credenciales, {
      withCredentials: true
    }).pipe(
      switchMap(() => this.getUsuarioEnUso()),
      tap(user => this.actualizarEstado(user))
    );
  }

  logout(): Observable<any> {
    return this.http.post(`${this.baseUrl}/auth/logout`, {}, {
      withCredentials: true
    }).pipe(
      // Limpiamos el estado al cerrar sesión
      tap(() => this.actualizarEstado(null))
    );
  }

  getUsuarioEnUso(): Observable<Usuario | null> {
    return this.http.get<Usuario>(`${this.baseUrl}/auth/me`, { withCredentials: true }).pipe(
      catchError(() => {
        return of(null);
      })
    );
  }

  getUsuarioPorNombre(nombre: string): Observable<Usuario> {
    return this.http.get<Usuario>(`${this.baseUrl}/usuario/nombre/${nombre}`, {
      withCredentials: true
    });
  }

  actualizarReputacion(idUsuario: number, nuevaRep: number): Observable<any> {
    return this.http.patch(`${this.baseUrl}/usuario/${idUsuario}/reputacion`, { reputacion: nuevaRep }, {
      withCredentials: true
    });
  }

  getUsuarioPorId(id: number): Observable<Usuario> {
    return this.http.get<Usuario>(`${this.baseUrl}/usuario/${id}`);
  }

  isLoggedInValue(): boolean {
    return this.isLoggedInSubject.value;
  }

  setLoggedIn(value: boolean) {
    this.isLoggedInSubject.next(value);
  }

  isAdmin(): Observable<boolean> {
    return this.user$.pipe(
      map(user => user?.tipo === 'ADMIN')
    );
  }
}