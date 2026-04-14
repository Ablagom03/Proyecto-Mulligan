import { Component, EventEmitter, Input, Output } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../service/auth.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterModule, CommonModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class Navbar {
  @Input() sidebarAbierto = false;
  @Output() toggleSidebar = new EventEmitter<void>();

  isLoggedIn$;

  constructor(private auth: AuthService) {
    this.isLoggedIn$ = this.auth.Logeado$;
  }

  abrirSide() {
    this.toggleSidebar.emit();
  }

  logout() {
    this.auth.logout().subscribe({
      next: () => {
        this.auth.setLoggedIn(false);
        window.location.href = '/inicio';
      },
      error: err => console.error('Logout Fallido:', err)
    });
  }

  ngOnInit() {
  }
}

