import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule, Params } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Observable } from 'rxjs';
import { Carta } from '../../model/Carta';
import { Empresa } from '../../model/Empresa';
import { CartasService } from '../../service/cartas.service';
import { EmpresaService } from '../../service/empresa.service';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  constructor(private cartasService: CartasService,private empresaService: EmpresaService ,private cdr: ChangeDetectorRef, private ar: ActivatedRoute) { }

  ngOnInit() {
    this.cargarCartas();
    this.cargarEmpresas();
  }

  listadoCartas$!: Observable<Carta[]>;

  cargarCartas() {
    this.listadoCartas$ = this.cartasService.getCartas();
  }

  listadoEmpresas$!: Observable<Empresa[]>;

  cargarEmpresas() {
    this.listadoEmpresas$ = this.empresaService.getEmpresas();
  }

}
