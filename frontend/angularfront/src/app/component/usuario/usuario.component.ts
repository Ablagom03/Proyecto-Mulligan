import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../service/auth.service';
import { Usuario } from '../../model/Usuario';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-usuario',
  templateUrl: './usuario.component.html',
  imports: [CommonModule],
  styleUrls: ['./usuario.component.css']
})
export class UsuarioComponent implements OnInit {
  currentUser: Usuario | null = null;
  vistaActual: 'inventario' | 'deseados' = 'inventario';

  constructor(private authService: AuthService,
    private route: ActivatedRoute
  ) { }

  ngOnInit() {
     this.route.paramMap.subscribe((params: any) => {
    const username = params.get('username');
    if (username) {
      this.authService.getUsuarioPorNombre(username).subscribe({
        next: (user) => this.currentUser = user,
        error: () => this.loadCurrentUser()
      });
    } else {
      this.loadCurrentUser();
    }
  });
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
