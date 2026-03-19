export class Carta {
    cardId: bigint;
    nombreCard: string;
    descripcion: string;
    coleccion: string;
    empresa: string;

    constructor(
        cardId: bigint,
        nombreCard: string,
        descripcion: string,
        coleccion: string,
        empresa: string
    ) {
        this.cardId = cardId;
        this.nombreCard = nombreCard;
        this.descripcion = descripcion;
        this.coleccion = coleccion;
        this.empresa = empresa;
    }

    // Getter y Setter cardId
    public getCardId(): bigint {
        return this.cardId;
    }

    public setCardId(cardId: bigint): void {
        this.cardId = cardId;
    }


    public getNombreCard(): string {
        return this.nombreCard;
    }

    public setNombreCard(nombreCard: string): void {
        this.nombreCard = nombreCard;
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