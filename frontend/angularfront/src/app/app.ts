import { ChangeDetectorRef, Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Sidebar } from './component/sidebar/sidebar';
import { Navbar } from './component/navbar/navbar';
import { CrearOferta } from './component/crear-oferta/crear-oferta';
import { FooterComponent } from './component/footer/footer.component';
@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Sidebar, Navbar, CommonModule, CrearOferta, FooterComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('angularfront');

  sidebarAbierto = false;

  constructor(private cdr: ChangeDetectorRef) {
    
  }

  abrirSide() {
    this.sidebarAbierto = !this.sidebarAbierto;
  }
  modalAbierto = false;

  abrirModal() {
    this.modalAbierto = true;
  }

  cerrarModal() {
    this.modalAbierto = false;
    this.cdr.detectChanges();
  }
}
