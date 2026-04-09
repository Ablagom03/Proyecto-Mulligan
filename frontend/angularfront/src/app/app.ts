import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Sidebar } from './component/sidebar/sidebar';
import { Navbar } from './component/navbar/navbar';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet ,Sidebar, Navbar , CommonModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('angularfront');

  sidebarAbierto = false;

  abrirSide(){
    this.sidebarAbierto = !this.sidebarAbierto;
    console.log('sidebarAbierto:', this.sidebarAbierto);
  }
}
