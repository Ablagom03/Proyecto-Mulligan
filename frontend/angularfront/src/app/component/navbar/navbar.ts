import { Component, EventEmitter, Input, Output } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class Navbar {
  @Input() sidebarAbierto = false;
  @Output() toggleSidebar = new EventEmitter<void>();

  abrirSide() {
    this.toggleSidebar.emit();
  }

  ngOnInit() {
  }
}

