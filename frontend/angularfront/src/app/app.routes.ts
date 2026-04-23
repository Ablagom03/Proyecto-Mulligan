import { Routes } from '@angular/router';
import { PaginaInicioComponent } from './component/pagina-inicio/pagina-inicio.component';
import { ListaCartasComponent } from './component/lista-cartas/lista-cartas.component';

import { UsuarioComponent } from './component/usuario/usuario.component';
import { MarcaComponent } from './component/marca/marca.component';

import { LoginComponent } from './component/login/login.component';
import { SigninComponent } from './component/signin/signin.component';

import { NotFoundComponent } from './component/not-found/not-found.component';


export const routes: Routes = [
    { path: '', redirectTo: '/inicio', pathMatch: 'full' },
    { path: 'inicio', component: PaginaInicioComponent },
    { path: 'cartas', component: ListaCartasComponent },
    { path: 'login', component: LoginComponent },
    { path: '404', component: NotFoundComponent },
    { path: 'usuario', component: UsuarioComponent },
    { path: 'signIn', component: SigninComponent },
    { path: 'marca', component: MarcaComponent }
];
