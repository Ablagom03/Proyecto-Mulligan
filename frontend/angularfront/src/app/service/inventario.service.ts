import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Inventario } from '../model/Inventario';

@Injectable({
  providedIn: 'root'
})
export class InventarioService {
  private http = inject(HttpClient);
  private urlBase = 'http://localhost:8080/inventario';

  // Obtener inventario de un usuario específico
  getInventarioPorUsuario(usrId: number): Observable<Inventario[]> {
    return this.http.get<Inventario[]>(`${this.urlBase}/usuario/${usrId}`);
  }

  // Eliminar una oferta por ID
  deleteInventario(id: number): Observable<void> {
    return this.http.delete<void>(`${this.urlBase}/${id}`);
  }

  // Obtener una sola oferta (útil para el formulario de edición)
  getOferta(id: number): Observable<Inventario> {
    return this.http.get<Inventario>(`${this.urlBase}/${id}`);
  }

  // Actualizar una oferta existente
  updateInventario(id: number, inventario: Partial<Inventario>): Observable<Inventario> {
    return this.http.put<Inventario>(`${this.urlBase}/${id}`, inventario);
  }
}