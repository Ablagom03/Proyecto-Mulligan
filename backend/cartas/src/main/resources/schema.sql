CREATE TABLE usuario (
  usrId bigserial,
  nombre_usr varchar(80),
  email varchar(80),
  passwd varchar(80),
  PRIMARY KEY(usrId)
);

CREATE TABLE carta (
  cardId bigserial,
  nombreCard varchar(80),
  descripcion varchar(80),
  coleccion varchar(80),
  empresa varchar(20),
  PRIMARY KEY(cardId)
);

CREATE TABLE inventario (
  usrId bigserial,
  cardId bigserial,
  valor float(5),
  estado varchar(20),
  copias int,
  CONSTRAINT fk_invusr
  FOREIGN KEY(usrId) REFERENCES usuario(usrId),
  CONSTRAINT fk_invcard
  FOREIGN KEY(cardId) REFERENCES carta(cardId)
);

INSERT INTO usuario(nombre_usr, email, passwd) 
VALUES
  ('John Doe', 'johndoe@gmail.com',md5('paswd')),
  ('Jane Doe', 'janedoe@gmail.com',md5('maswd')),
  ('Johnathan Dobee','johnathancomp@hotmail.com',md5('heydidyouknowyourcarbecomesmoreflammablewithgasinit'))  
  ;

INSERT INTO carta (nombreCard,descripcion,coleccion,empresa)
VALUES
  ('Blue Eyes White Dragon','idk Kaiba used it a couple of times ig', 'OG', 'YugiOh'),
  ('Charizard Original Edition', 'People pay way too much for these', 'OG 1995', 'Pokemon'),
  ('Forest','Green Grass Eat my a-','Basic Set', 'Magic: The Gathering')
  ;

INSERT INTO inventario (usrId, cardId,valor,estado,copias)
VALUES
  (1,1,100,'Perfecto',1),
  (2,2,999.99,'Sellado',1),
  (3,3,2.99,'Buena',30)
;
