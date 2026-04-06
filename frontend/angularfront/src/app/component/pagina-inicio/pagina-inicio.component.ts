import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-pagina-inicio',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './pagina-inicio.component.html',
  styleUrls: ['./pagina-inicio.component.css']
})
export class PaginaInicioComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit() {
  }

  navigateTo(route: string): void {
    this.router.navigate([route]);
  }

}
