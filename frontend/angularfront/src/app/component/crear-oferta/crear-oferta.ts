import { Component, EventEmitter, inject, OnInit, Output, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators, FormGroup, FormsModule } from '@angular/forms';
import { Observable } from 'rxjs';

import { AuthService } from '../../service/auth.service';
import { OfertaService } from '../../service/oferta.service';
import { Usuario } from '../../model/Usuario';
import { Empresa } from '../../model/Empresa';
import { EmpresaService } from '../../service/empresa.service';
import { Carta } from '../../model/Carta';
import { CartasService } from '../../service/cartas.service';
import { InventarioService } from '../../service/inventario.service';
import { Inventario } from '../../model/Inventario';
import { ModalService } from '../../service/modal.service';

@Component({
  selector: 'crear-oferta',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
  templateUrl: './crear-oferta.html',
  styleUrl: './crear-oferta.css',
})
export class CrearOferta implements OnInit {
  @Input() inventarioId: number | null = null;
  @Output() cerrarModal = new EventEmitter<void>();

  private inventarioService = inject(InventarioService);
  private authService = inject(AuthService);
  private fb = inject(FormBuilder);
  private ofertaService = inject(OfertaService);
  private empresaService = inject(EmpresaService);
  private cartasService = inject(CartasService);
  private modalService = inject(ModalService);

  currentUser$: Observable<Usuario | null> = this.authService.getUsuarioEnUso();
  listadoEmpresas$!: Observable<Empresa[]>;
  ofertaForm: FormGroup;

  datos: Carta[] = [];
  resultados: Carta[] = [];
  mostrar: boolean = false;
  mensajeError: string | null = null;

  constructor() {
    this.ofertaForm = this.fb.group({
      tipo: ['VENTA', Validators.required],
      nombreCard: ['', Validators.required],
      coleccion: [{ value: '', disabled: false }, Validators.required],
      empresa: [{ value: '', disabled: false }, Validators.required],
      valor: [0, [Validators.required, Validators.min(0.01)]],
      estado: ['', Validators.required],
      copias: [1, [Validators.required, Validators.min(1)]]
    });
  }

  ngOnInit(): void {
    this.cargarEmpresas();
    this.cartasService.getCartas().subscribe(cartas => this.datos = cartas);

    if (this.inventarioId) {
      this.cargarDatosParaEditar(this.inventarioId);
    }
  }

  cargarEmpresas() {
    this.listadoEmpresas$ = this.empresaService.getEmpresas();
  }

  cargarDatosParaEditar(id: number) {
    this.inventarioService.getOferta(id).subscribe({
      next: (oferta: Inventario) => {
        this.ofertaForm.patchValue({
          tipo: oferta.tipo,
          nombreCard: oferta.carta.nombrecard,
          coleccion: oferta.carta.coleccion,
          empresa: oferta.carta.empresa,
          valor: oferta.valor,
          estado: oferta.estado,
          copias: oferta.copias
        });
      },
      error: (err) => {
        console.error('Error al cargar la oferta para editar:', err);
        alert('No se pudo cargar la información de la oferta.');
      }
    });
  }

  buscarCartas() {
    const valorBusqueda = this.ofertaForm.get('nombreCard')?.value?.toLowerCase() || '';

    if (valorBusqueda.length > 0) {
      this.resultados = this.datos.filter(carta =>
        carta.nombrecard.toLowerCase().includes(valorBusqueda)
      );
      this.mostrar = true;
    } else {
      this.resultados = [];
      this.mostrar = false;
    }
  }

  seleccionar(carta: Carta) {
    this.ofertaForm.patchValue({
      nombreCard: carta.nombrecard,
      coleccion: carta.coleccion,
      empresa: carta.empresa
    });
    this.mostrar = false;
  }

  guardar() {
    if (this.ofertaForm.invalid) return;
    this.mensajeError = null;

    this.authService.getUsuarioEnUso().subscribe(user => {
      if (!user?.usrId) {
        this.mensajeError = "Sesión expirada. Identifícate de nuevo.";
        return;
      }

      const formValues = this.ofertaForm.getRawValue(); // rawValue para pillar los campos readonly
      
      const observable = this.inventarioId 
        ? this.inventarioService.updateInventario(this.inventarioId, formValues)
        : this.ofertaService.crearOferta({ ...formValues, usrId: user.usrId });

      observable.subscribe({
        next: () => {
          this.modalService.notificarCambio();
          this.finalizarAccion();
        },
        error: (err) => {
          this.mensajeError = "Error al procesar la oferta. Inténtalo de nuevo.";
          console.error(err);
        }
      });
    });
  }

  private finalizarAccion() {
    this.ofertaForm.reset();
    this.cerrarModal.emit();
  }

  ocultar() {
    setTimeout(() => {
      this.mostrar = false;
    }, 200);
  }
}