import { Carta } from './Carta'; 

export interface Inventario {
    id: number;
    valor: number;
    estado: string;
    copias: number;
    tipo: 'VENTA' | 'COMPRA'; 
    carta: Carta;
    usrId?: number;
    cardId?: number;
}