import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../service/auth.service';
import { Usuario } from '../../model/Usuario';

@Component({
  selector: 'app-signin',
  standalone: true,
  imports: [RouterModule, FormsModule, CommonModule],
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class SigninComponent {
  user: Usuario = {
    nombre_usr: '',
    email: '',
    passwd: '',
    reputacion: 0
  };

  mensajeError: string | null = null; 

  constructor(private auth: AuthService, private router: Router) {}

  register() {
    this.mensajeError = null; 

    this.auth.registro(this.user).subscribe({
      next: () => {
        console.log('Registro exitoso');
        this.router.navigate(['/login']);
      },
      error: (err: any) => {
        console.error('Error en el registro:', err);
        if (err.error && err.error.error) {
          this.mensajeError = err.error.error;
        } 
        else if (typeof err.error === 'string') {
          this.mensajeError = err.error;
        } 
        else {
          this.mensajeError = 'No se pudo completar el registro. Inténtalo de nuevo.';
        }
      }
    });
  }
}