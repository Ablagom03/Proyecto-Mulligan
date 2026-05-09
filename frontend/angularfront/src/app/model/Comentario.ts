import { Usuario } from './Usuario';
import { Inventario } from './Inventario';

export type TipoComentario = 'POSITIVO' | 'NEGATIVO';

export interface Comentario {
  comentarioId?: number;
  texto: string;
  tipo: TipoComentario;
  usuarioComprador?: Usuario;
  usuarioVendedor?: Usuario;
  inventario?: Inventario;
  fechaCreacion?: string;
}
