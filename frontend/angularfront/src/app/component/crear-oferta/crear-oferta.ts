import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'crear-oferta',
  imports: [CommonModule],
  standalone: true,
  templateUrl: './crear-oferta.html',
  styleUrl: './crear-oferta.css',
})
export class CrearOferta { 
  @Output() cerrarModal = new EventEmitter<void>();

  guardar() {
    this.cerrarModal.emit();
  }
}
