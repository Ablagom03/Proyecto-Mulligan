import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../service/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [RouterModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  email: string = '';
  passwd: string = '';

  constructor(private auth: AuthService, private router: Router) {}

  ngOnInit() {}

  login() {
  // Creamos el objeto con las credenciales
  const datos = { email: this.email, passwd: this.passwd };

  this.auth.login(datos).subscribe({
    next: () => {
      console.log('Login OK');
      this.auth.setLoggedIn(true);
      this.router.navigate(['/']);
    },
    error: (err: any) => {
      console.error('Login fallido:', err);
      alert('Error al iniciar sesión. Revisa tus credenciales.');
    }
  });
}
}
