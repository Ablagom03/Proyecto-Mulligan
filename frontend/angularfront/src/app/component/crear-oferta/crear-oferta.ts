import { Component, EventEmitter, inject, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators, FormGroup } from '@angular/forms';
import { Observable } from 'rxjs';

import { AuthService } from '../../service/auth.service';
import { OfertaService } from '../../service/oferta.service';
import { Usuario } from '../../model/Usuario';
@Component({
  selector: 'crear-oferta',
  imports: [CommonModule,ReactiveFormsModule],
  standalone: true,
  templateUrl: './crear-oferta.html',
  styleUrl: './crear-oferta.css',
})
export class CrearOferta implements OnInit {
  @Output() cerrarModal = new EventEmitter<void>();

  private authService = inject(AuthService);
  private fb = inject(FormBuilder);
  private ofertaService = inject(OfertaService);

  currentUser$: Observable<Usuario | null> = this.authService.getUsuarioEnUso();

  ofertaForm: FormGroup;

  constructor() {
    this.ofertaForm = this.fb.group({
      tipo: ['venta', Validators.required],
      nombreCard: ['', Validators.required],
      coleccion: ['', Validators.required],
      empresa: ['', Validators.required],
      valor: [0, [Validators.required, Validators.min(0.01)]],
      estado: ['', Validators.required],
      copias: [1, [Validators.required, Validators.min(1)]]
    });
  }
  ngOnInit():void  {
    
  }

  guardar() {
    if (this.ofertaForm.valid) {
      this.ofertaService.crearOferta(this.ofertaForm.value).subscribe({
        next: () => {
          alert('Oferta creada con éxito');
          this.cerrarModal.emit();
        },
        error: () => alert('Error al crear la oferta')
      });
    }
  }
}