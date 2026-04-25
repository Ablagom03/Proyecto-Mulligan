import { CommonModule } from '@angular/common';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Usuario } from '../../model/Usuario';
import { AuthService } from '../../service/auth.service';
import { OfertaService } from '../../service/oferta.service';

@Component({
  selector: 'crear-oferta',
  imports: [CommonModule,ReactiveFormsModule],
  standalone: true,
  templateUrl: './crear-oferta.html',
  styleUrl: './crear-oferta.css',
})
export class CrearOferta implements OnInit {
  @Output() cerrarModal = new EventEmitter<void>();
  currentUser: Usuario | null = null;
  ofertaForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private ofertaService: OfertaService,
    private authService: AuthService
  ) {
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

  ngOnInit() {
    this.authService.getUsuarioEnUso().subscribe({
      next: (user) => this.currentUser = user,
      error: () => this.currentUser = null
    });
  }

  guardar() {
    this.cerrarModal.emit();
  }
}
