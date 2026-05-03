import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ModalService {
  private abrirModalSubject = new Subject<number | null>();
  private ofertaCambiadaSubject = new Subject<void>();
  ofertaCambiada$ = this.ofertaCambiadaSubject.asObservable();

  abrirModal$ = this.abrirModalSubject.asObservable();

  abrir(id: number | null = null): void {
    this.abrirModalSubject.next(id);
  }

  cerrar(): void {
    this.abrirModalSubject.next(null); 
  }

  notificarCambio(): void {
    this.ofertaCambiadaSubject.next();
  }
}