import { ChangeDetectorRef, Component, OnInit, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Sidebar } from './component/sidebar/sidebar';
import { Navbar } from './component/navbar/navbar';
import { CrearOferta } from './component/crear-oferta/crear-oferta';
import { FooterComponent } from './component/footer/footer.component';
import { ModalService } from './service/modal.service';
@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Sidebar, Navbar, CommonModule, CrearOferta, FooterComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {
  protected readonly title = signal('angularfront');
  inventarioId: number | null = null;

  sidebarAbierto = false;

  constructor(
    private cdr: ChangeDetectorRef,
    private modalService: ModalService
  ) { }

  ngOnInit() {
    
    this.modalService.abrirModal$.subscribe((id) => {
      console.log('Evento recibido en App para ID:', id);
      this.inventarioId = id;
      this.modalAbierto = true;
      this.cdr.detectChanges();
    });
  }

  abrirSide() {
    this.sidebarAbierto = !this.sidebarAbierto;
  }
  modalAbierto = false;

  abrirModal(id: number | null = null) {
    this.inventarioId = id;
    this.modalAbierto = true;
  }

  cerrarModal() {
    this.modalAbierto = false;
    this.inventarioId = null;
    this.cdr.detectChanges();
  }
}
