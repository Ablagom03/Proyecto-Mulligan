import { Inventario } from './Inventario';

export interface Usuario {
    usrId?: number;
    nombre_usr: string;
    email: string;
    passwd?: string;
    reputacion?: number;
    ofertas?: Inventario[]; 
}