import { Component, OnInit } from '@angular/core';
import { ListaInventarioComponent } from './lista-inventario/lista-inventario.component';
import { ListaDeseadosComponent } from './lista-deseados/lista-deseados.component'; 

@Component({
  selector: 'app-usuario',
  templateUrl: './usuario.component.html',
  imports: [ListaInventarioComponent, ListaDeseadosComponent],
  styleUrls: ['./usuario.component.css']
})
export class UsuarioComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  //Propiedad para comprobar la vista actual, cuál lista se ve
  vistaActual: 'inventario' | 'deseados' = 'inventario';

  mostrarInventario() {
    this.vistaActual = 'inventario';
  }

  mostrarDeseados() {
    this.vistaActual = 'deseados';
  }
}
