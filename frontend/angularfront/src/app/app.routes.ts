import { Routes } from '@angular/router';
import { ListaCartasComponent } from './component/lista-cartas/lista-cartas.component';
import { LoginComponent } from './component/login/login.component';
import { PaginaInicioComponent } from './component/pagina-inicio/pagina-inicio.component';
import { NotFoundComponent } from './component/not-found/not-found.component';


export const routes: Routes = [
    { path: 'cartas', component: ListaCartasComponent },
    { path: 'login', component: LoginComponent},
    { path: 'inicio', component: PaginaInicioComponent},

    { path: '404', component: NotFoundComponent}
];
