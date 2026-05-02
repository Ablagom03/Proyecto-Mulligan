import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Inventario } from '../model/Inventario';

@Injectable({
  providedIn: 'root'
})
export class InventarioService {
  private http = inject(HttpClient);
  private urlBase = '/api/inventario';

  
  getInventarioPorUsuario(usrId: number): Observable<Inventario[]> {
    return this.http.get<Inventario[]>(`${this.urlBase}/usuario/${usrId}`);
  }
}