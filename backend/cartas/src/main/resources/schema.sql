CREATE TABLE IF NOT EXISTS imagenes (
  idImg bigserial PRIMARY KEY,
  nombre varchar(30) UNIQUE,
  data BYTEA 
);

CREATE TABLE IF NOT EXISTS usuario (
  usrId bigserial PRIMARY KEY,
  nombre_usr varchar(80),
  email varchar(80) UNIQUE,
  reputacion int,
  passwd varchar(80)
);

CREATE TABLE IF NOT EXISTS carta (
  cardId bigserial PRIMARY KEY,
  nombreCard varchar(80) UNIQUE,
  descripcion varchar(80),
  coleccion varchar(80),
  empresa varchar(20),
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
)

--Constraints
--ALTER TABLE usuario ADD CONSTRAINT unique_email UNIQUE (email);
--ALTER TABLE carta ADD CONSTRAINT unique_nombreCard UNIQUE (nombreCard);
--ALTER TABLE imagenes ADD CONSTRAINT unique_nombre UNIQUE (nombre);