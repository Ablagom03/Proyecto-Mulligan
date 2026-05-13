CREATE TABLE IF NOT EXISTS imagenes (
  idImg bigserial PRIMARY KEY,
  nombre varchar(30) UNIQUE,
  data BYTEA 
);

CREATE TABLE IF NOT EXISTS usuario (
  usrId bigserial PRIMARY KEY,
  nombre_usr varchar(50),
  email varchar(80) UNIQUE,
  reputacion int,
  passwd varchar(80),
  tipo varchar(50)
);

CREATE TABLE IF NOT EXISTS carta (
  cardId bigserial PRIMARY KEY,
  nombreCard varchar(80) UNIQUE,
  descripcion varchar(800),
  coleccion varchar(50),
  empresa varchar(10),
  idimg bigserial, 
  FOREIGN KEY (idimg) REFERENCES imagenes(idimg)
);


CREATE TABLE IF NOT EXISTS inventario (
  invId bigserial PRIMARY KEY,
  usrId bigint,
  cardId bigint,
  valor float(5),
  estado varchar(20),
  copias int,
  tipo varchar(20),
  CONSTRAINT fk_invusr
  FOREIGN KEY(usrId) REFERENCES usuario(usrId),
  CONSTRAINT fk_invcard
  FOREIGN KEY(cardId) REFERENCES carta(cardId)
);



CREATE TABLE IF NOT EXISTS deseados (
  desId bigserial PRIMARY KEY,
  usrId bigint,
  cardId bigInt,
  precioMax float
);

CREATE TABLE IF NOT EXISTS comentario (
  comentarioId bigserial PRIMARY KEY,
  texto TEXT,
  tipo varchar(50),
  usr_id_comprador bigint NOT NULL,
  usr_id_vendedor bigint NOT NULL,
  inventario_id bigint NOT NULL,
  fecha_creacion timestamp WITH TIME ZONE,
  CONSTRAINT fk_comentario_comprador FOREIGN KEY(usr_id_comprador) REFERENCES usuario(usrId),
  CONSTRAINT fk_comentario_vendedor FOREIGN KEY(usr_id_vendedor) REFERENCES usuario(usrId),
  CONSTRAINT fk_comentario_inventario FOREIGN KEY(inventario_id) REFERENCES inventario(invId)
);
