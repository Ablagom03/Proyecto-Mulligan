import { Inventario } from './Inventario';
import { Tipo } from './TipoUsuario';

export interface Usuario {
    usrId?: number;
    nombre_usr: string;
    email: string;
    passwd?: string;
    reputacion?: number;
    ofertas?: Inventario[]; 
    tipo?: Tipo;
}