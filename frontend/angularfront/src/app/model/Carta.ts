export class Carta {
    cardid: bigint;
    nombrecard: string;
    descripcion: string;
    coleccion: string;
    empresa: string;

    constructor(
        cardid: bigint,
        nombrecard: string,
        descripcion: string,
        coleccion: string,
        empresa: string
    ) {
        this.cardid = cardid;
        this.nombrecard = nombrecard;
        this.descripcion = descripcion;
        this.coleccion = coleccion;
        this.empresa = empresa;
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
}