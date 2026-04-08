import { Component, EventEmitter, Input, input, Output } from '@angular/core';
import { Router, RouterModule } from '@angular/router';

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

  constructor(private router: Router) { }

  ngOnInit() {
  }

  navigateTo(route: string): void {
    this.router.navigate([route]);
  }
}
