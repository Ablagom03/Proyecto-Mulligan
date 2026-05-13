import { Component, EventEmitter, Output, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ComentarioService } from '../../service/comentario.service';

@Component({
  selector: 'app-comentario-modal',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './comentario-modal.component.html',
  styleUrls: ['./comentario-modal.component.css']
})
export class ComentarioModalComponent {
  @Output() cerrar = new EventEmitter<void>();
  @Output() comentarioEnviado = new EventEmitter<void>();

  private comentarioService = inject(ComentarioService);
  private cdr = inject(ChangeDetectorRef);

  isVisible = false;
  inventarioId: number | null = null;
  texto = '';
  tipo: 'POSITIVO' | 'NEGATIVO' = 'POSITIVO';
  cargando = false;
  error = '';

  abrir(inventarioId: number) {
    this.inventarioId = inventarioId;
    this.isVisible = true;
    this.texto = '';
    this.tipo = 'POSITIVO';
    this.error = '';
  }

  enviarComentario() {
    if (!this.texto.trim()) {
      this.error = 'Por favor escribe un comentario';
      return;
    }

    if (!this.inventarioId) {
      this.error = 'Error: No se pudo procesar la oferta';
      return;
    }

    this.cargando = true;
    console.log('Enviando comentario:', {
      inventarioId: this.inventarioId,
      texto: this.texto,
      tipo: this.tipo
    });

    this.comentarioService.crearComentario(this.inventarioId, this.texto, this.tipo).subscribe({
      next: () => {
        this.cargando = false;
        this.cdr.detectChanges();
        console.log('Comentario creado exitosamente');
        this.comentarioEnviado.emit();
        this.cerrarModal();
      },
      error: (err) => {
        this.cargando = false;
        this.cdr.detectChanges();
        console.error('Error completo:', err);
        console.error('Error status:', err.status);
        console.error('Error body:', err.error);
        
        let mensajeError = 'Error desconocido';
        if (err.error && err.error.error) {
          mensajeError = err.error.error;
        } else if (err.message) {
          mensajeError = err.message;
        }
        
        this.error = 'Error: ' + mensajeError;
      }
    });
  }

  cerrarModal() {
    this.isVisible = false;
    this.cerrar.emit();
  }
}
