import { Component, EventEmitter, inject, OnInit, Output, ChangeDetectorRef} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators, FormGroup } from '@angular/forms';
import { Observable } from 'rxjs';
import { FormsModule } from '@angular/forms';

import { AuthService } from '../../service/auth.service';
import { OfertaService } from '../../service/oferta.service';
import { Usuario } from '../../model/Usuario';

import { Empresa } from '../../model/Empresa';
import { EmpresaService } from '../../service/empresa.service';
import { Carta } from '../../model/Carta';
import { CartasService } from '../../service/cartas.service';

@Component({
  selector: 'crear-oferta',
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
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

  constructor(private empresaService: EmpresaService, private cartasService: CartasService) {
    this.ofertaForm = this.fb.group({
      tipo: ['VENTA', Validators.required],
      nombreCard: ['', Validators.required],
      coleccion: ['', Validators.required],
      empresa: ['', Validators.required],
      valor: [0, [Validators.required, Validators.min(0.01)]],
      estado: ['', Validators.required],
      copias: [1, [Validators.required, Validators.min(1)]]
    });

  }
  ngOnInit(): void {
    this.cargarEmpresas();

    this.cartasService.getCartas().subscribe((cartas: Carta[]) => {
      this.datos = cartas.map(carta => carta);
    });
  }

  guardar() {
    if (this.ofertaForm.valid) {

      this.authService.getUsuarioEnUso().subscribe(user => {
        if (user && user.usrId) {

          const datosEnvio = {
            ...this.ofertaForm.value,
            usrId: user.usrId
          };

          this.ofertaService.crearOferta(datosEnvio).subscribe({
            next: () => {
              alert('Oferta creada con éxito');
              this.cerrarModal.emit();
            },
            error: (err) => {
              console.error('Error detalle:', err);
              alert('Error al crear la oferta');
            }
          });
        } else {
          alert('No se pudo identificar al usuario. Reintenta loguearte.');
        }
      });
    
    }
  }

  listadoEmpresas$!: Observable<Empresa[]>;
  cargarEmpresas() {
    this.listadoEmpresas$ = this.empresaService.getEmpresas();
  }

  buscarCartas() {
    this.resultados = this.datos.filter(carta =>
      carta.nombrecard.toLowerCase().includes(this.busqueda.toLowerCase())
    );
    console.log(this.busqueda);

    console.log(this.resultados);
    this.mostrar = true;

    //Para debuguear
    //console.log("Busqueda: ", this.busqueda);
    //console.log("Datos: ", this.datos);
    //console.log("Resultado: ", this.resultados);
  }


  busqueda: string = '';
  resultados: Carta[] = [];
  datos: Carta[] = [];

  //Para el dropdown
  mostrar: boolean = false;

  seleccionar(carta: Carta) {
    this.busqueda = carta.nombrecard;
    this.mostrar = false;
    this.ofertaForm.patchValue({
      coleccion: carta.coleccion,
      empresa: carta.empresa
    })
  }

  ocultar() {
    setTimeout(() => {
      this.mostrar = false;
    }, 3);
  }


}