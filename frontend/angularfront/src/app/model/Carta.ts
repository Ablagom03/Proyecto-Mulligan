import { Imagen } from "./Imagen";

export class Carta {
    cardid: bigint;
    nombrecard: string;
    descripcion: string;
    coleccion: string;
    empresa: string;
    imagen: Imagen;

    constructor(
        cardid: bigint,
        nombrecard: string,
        descripcion: string,
        coleccion: string,
        empresa: string,
        imagen: Imagen
    ) {
        this.cardid = cardid;
        this.nombrecard = nombrecard;
        this.descripcion = descripcion;
        this.coleccion = coleccion;
        this.empresa = empresa;
        this.imagen = imagen;
    }

    // Getter y Setter cardid
    public getcardid(): bigint {
        return this.cardid;
    }

    public setcardid(cardid: bigint): void {
        this.cardid = cardid;
    }


    public getNombrecard(): string {
        return this.nombrecard;
    }

    public setNombrecard(nombrecard: string): void {
        this.nombrecard = nombrecard;
    }


    public getDescripcion(): string {
        return this.descripcion;
    }

    public setDescripcion(descripcion: string): void {
        this.descripcion = descripcion;
    }


    public getColeccion(): string {
        return this.coleccion;
    }

    public setColeccion(coleccion: string): void {
        this.coleccion = coleccion;
    }


    public getEmpresa(): string {
        return this.empresa;
    }

    public setEmpresa(empresa: string): void {
        this.empresa = empresa;
    }

    public getImagen(): Imagen {
        return this.imagen;
    }

    public setImagen(imagen: Imagen): void {
        this.imagen = imagen;
    }
}