import { Routes } from '@angular/router';
import { ListaCartasComponent } from './component/lista-cartas/lista-cartas.component';

import { UsuarioComponent } from './component/usuario/usuario.component';
import { MarcaComponent } from './component/marca/marca.component';

import { LoginComponent } from './component/login/login.component';
import { SigninComponent } from './component/signin/signin.component';

import { NotFoundComponent } from './component/not-found/not-found.component';


export const routes: Routes = [
    { path: 'cartas', component: ListaCartasComponent },
    { path: 'login', component: LoginComponent},
    { path: '404', component: NotFoundComponent},
    { path: 'usuario',component: UsuarioComponent},
    { path: 'signIn', component: SigninComponent},
    { path: 'marcas', component: MarcaComponent}
];
