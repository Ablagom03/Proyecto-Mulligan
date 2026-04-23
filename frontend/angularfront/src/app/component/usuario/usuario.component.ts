import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../service/auth.service';
import { Usuario } from '../../model/Usuario';

@Component({
  selector: 'app-usuario',
  templateUrl: './usuario.component.html',
  imports: [CommonModule],
  styleUrls: ['./usuario.component.css']
})
export class UsuarioComponent implements OnInit {
  currentUser: Usuario | null = null;
  vistaActual: 'inventario' | 'deseados' = 'inventario';

  constructor(private authService: AuthService) { }

  ngOnInit() {
    this.loadCurrentUser();
  }

  loadCurrentUser() {
    this.authService.getUsuarioEnUso().subscribe({
      next: (user) => {
        this.currentUser = user;
      },
      error: (err) => {
        console.error('Error loading user:', err);
        this.currentUser = null;
      }
    });
  }

  mostrarInventario() {
    this.vistaActual = 'inventario';
  }

  mostrarDeseados() {
    this.vistaActual = 'deseados';
  }
}
