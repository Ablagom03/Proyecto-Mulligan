import { Component, OnInit, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-prueba',
  standalone: true,
  imports: [],
  templateUrl: 'prueba.html',
  styleUrl: 'prueba.css'

})
export class Prueba implements OnInit {

  cartas = signal<any[]>([]);
  private http = inject(HttpClient);

  ngOnInit() {
    this.http.get<any[]>('http://localhost:8080/carta/').subscribe({
      next: (res) => this.cartas.set(res),
      error: (err) => console.error('Error de conexión:', err)
    });
  }

}
