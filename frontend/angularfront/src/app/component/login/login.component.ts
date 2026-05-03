import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../service/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [RouterModule, FormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  email: string = '';
  passwd: string = '';

  mensajeError: string | null = null;

  constructor(private auth: AuthService, private router: Router) { }

  ngOnInit() { }

  login() {
    this.mensajeError = null;
    const datos = { email: this.email, passwd: this.passwd };

    this.auth.login(datos).subscribe({
      next: () => {
        this.auth.setLoggedIn(true);
        this.router.navigate(['/']);
      },
      error: (err: any) => {
        console.error('Detalles del error:', err);
        if (typeof err.error === 'string') {
          this.mensajeError = err.error;
        }
        else if (err.error?.message) {
          this.mensajeError = err.error.message;
        }
        else {
          this.mensajeError = 'Error al intentar acceder.';
        }
      }
    });
  }
}