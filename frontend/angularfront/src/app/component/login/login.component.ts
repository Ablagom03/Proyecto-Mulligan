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
    this.auth.login({ email: this.email, passwd: this.passwd }).subscribe({
      next: () => {
        this.auth.setLoggedIn(true);
        this.router.navigate(['/inicio']);
      },
      error: err => console.error('Login fallido:', err)
    });
  }
}
