import { Component, EventEmitter, HostListener, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './sidebar.html',
  styleUrls: ['./sidebar.css'],
})
export class Sidebar {

  @Input() abierto = false;
  @Output() cerrarSidebar = new EventEmitter<boolean>();

  abrirSide() {
    this.abierto = !this.abierto;
    this.cerrarSidebar.emit(this.abierto);
  }

  
  @HostListener('document:click', ['$event'])
  onClick(event: MouseEvent) {
    const target = event.target as HTMLElement;
    const sidebar = document.querySelector('.sidebar') as HTMLElement;

    if (sidebar && !sidebar.contains(target) && this.abierto) {
      this.abierto = false;
      this.cerrarSidebar.emit(this.abierto);
    }
  }

  constructor(private router: Router) { }

  ngOnInit() {
  }

  navigateTo(route: string): void {
    this.router.navigate([route]);
  }
}
