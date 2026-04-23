import { Component, EventEmitter, Input, Output, OnInit } from '@angular/core';
import { RouterModule, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../service/auth.service';
import { Usuario } from '../../model/Usuario';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterModule, CommonModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class Navbar implements OnInit {
  @Input() sidebarAbierto = false;
  @Output() toggleSidebar = new EventEmitter<void>();

  isLoggedIn$;
  currentUser: Usuario | null = null;

  constructor(private auth: AuthService, private router: Router) {
    this.isLoggedIn$ = this.auth.Logeado$;
  }

  ngOnInit() {
    this.loadCurrentUser();
  }

  loadCurrentUser() {
    this.auth.getUsuarioEnUso().subscribe({
      next: (user) => {
        this.currentUser = user;
      },
      error: (err) => {
        this.currentUser = null;
      }
    });
  }

  abrirSide() {
    this.toggleSidebar.emit();
  }

  logout() {
    this.auth.logout().subscribe({
      next: () => {
        this.auth.setLoggedIn(false);
        this.currentUser = null;
        window.location.href = '/inicio';
      },
      error: err => console.error('Logout Fallido:', err)
    });
  }
}

