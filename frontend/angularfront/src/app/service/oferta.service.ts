import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OfertaService {
  private apiUrl = 'http://localhost:8080/inventario';

  constructor(private http: HttpClient) { }

  crearOferta(oferta: any): Observable<any> {

    return this.http.post(`${this.apiUrl}/agregar`, oferta, { withCredentials: true });
  }

  getOfertasPorCarta(idCarta: bigint): Observable<any[]> {

    return this.http.get<any[]>(`${this.apiUrl}/carta/${idCarta}`);
  }
}