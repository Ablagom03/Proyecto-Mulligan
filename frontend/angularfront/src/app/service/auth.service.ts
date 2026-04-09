import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Usuario } from '../model/Usuario';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

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

  logout(): Observable<any> {
    return this.http.post(`${this.baseUrl}/auth/logout`, {}, {
      withCredentials: true
    });
  }

  getUsuarioEnUso(): Observable<Usuario> {
    return this.http.get<Usuario>(`${this.baseUrl}/auth/me`, {
      withCredentials: true
    });
  }
}