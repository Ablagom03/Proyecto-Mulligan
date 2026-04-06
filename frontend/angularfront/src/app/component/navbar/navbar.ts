import { Component, EventEmitter, Input, input, Output } from '@angular/core';

@Component({
  selector: 'app-navbar',
  imports: [],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class Navbar {
  @Input() sidebarAbierto = false;
  @Output() toggleSidebar = new EventEmitter<void>();

  abrirSide() {
    this.toggleSidebar.emit();
  }
}
