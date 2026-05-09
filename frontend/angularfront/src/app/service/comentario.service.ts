import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Comentario } from '../model/Comentario';

@Injectable({
  providedIn: 'root'
})
export class ComentarioService {
  private http = inject(HttpClient);
  private urlBase = '/api/comentario';

  crearComentario(inventarioId: number, texto: string, tipo: 'POSITIVO' | 'NEGATIVO'): Observable<Comentario> {
    const payload = {
      inventarioId,
      texto,
      tipo
    };
    return this.http.post<Comentario>(this.urlBase, payload);
  }

  obtenerComentariosRecibidos(usrId: number): Observable<Comentario[]> {
    return this.http.get<Comentario[]>(`${this.urlBase}/recibidos/${usrId}`);
  }

  obtenerComentariosDejaos(usrId: number): Observable<Comentario[]> {
    return this.http.get<Comentario[]>(`${this.urlBase}/dejados/${usrId}`);
  }

  obtenerComentariosPorOferta(inventarioId: number): Observable<Comentario[]> {
    return this.http.get<Comentario[]>(`${this.urlBase}/oferta/${inventarioId}`);
  }
}
