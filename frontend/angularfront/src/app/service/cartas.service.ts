import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Carta } from '../model/Carta';

@Injectable({
  providedIn: 'root'
})

export class CartasService {
  url: string = "/api/carta";

  constructor(private http: HttpClient) {

  }

  getCartas(): Observable<Carta[]> {
    return this.http.get<Carta[]>(this.url);
  }

  getCartasPorMarca(marca: string): Observable<Carta[]> {
    return this.http.get<Carta[]>(`${this.url}/marca/${marca}`);
  }

  getCartaPorId(id: bigint): Observable<Carta> {
    return this.http.get<Carta>(`${this.url}/${id}`);
  }

  insertCarta(carta: any): Observable<Carta> {
    return this.http.post<Carta>(this.url, carta);
  }
  
  updateCarta(carta: Carta, id: bigint): Observable<any> {
    return this.http.put(`${this.url}/${id}`, carta);
  }
}
