import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../service/auth.service';
import { Usuario } from '../../model/Usuario';

@Component({
  selector: 'app-signin',
  standalone: true,
  imports: [RouterModule, FormsModule],
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

  constructor(private auth: AuthService, private router: Router) {}

  register() {
    this.auth.registro(this.user).subscribe({
      next: () => this.router.navigate(['/login']),
      error: err => console.error(err)
    });
  }
}