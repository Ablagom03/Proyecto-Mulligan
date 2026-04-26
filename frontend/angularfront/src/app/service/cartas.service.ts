import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Carta } from '../model/Carta';

@Injectable({
  providedIn: 'root'
})

export class CartasService {
  url: string = "http://localhost:8080/carta";

constructor(private http: HttpClient) { 

}

  getCartas(): Observable<Carta[]> {
    return this.http.get<Carta[]>(this.url);
  }

  getCartasPorMarca(marca : string): Observable<Carta[]>  {
    return this.http.get<Carta[]>(`${this.url}/marca/${marca}`);
  }

  getCartaPorId(id : bigint): Observable<Carta> {
    return this.http.get<Carta>(`${this.url}/${id}`);
  }
}
