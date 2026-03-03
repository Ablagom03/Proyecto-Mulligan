CREATE TABLE usuario (
  usrId varchar(5),
  nombre_usr varchar(80),
  email varchar(80),
  passwd varchar(80),
  PRIMARY KEY(usrId)
);

CREATE TABLE carta (
  cardId varchar(5),
  nombreCard varchar(80),
  descripcion varchar(80),
  coleccion varchar(80),
  empresa varchar(20),
  PRIMARY KEY(cardId)
);

CREATE TABLE inventario (
  usrId varchar(15),
  cardId varchar(5),
  valor float(5),
  estado varchar(20),
  copias int,
  CONSTRAINT fk_invusr
  FOREIGN KEY(usrId) REFERENCES usuario(usrId),
  CONSTRAINT fk_invcard
  FOREIGN KEY(cardId) REFERENCES carta(cardId)
);

INSERT INTO usuario(usrId, nombre_usr, email, passwd) 
VALUES
  ('AAAAA','John Doe', 'johndoe@gmail.com',md5('paswd')),
  ('BBBBB','Jane Doe', 'janedoe@gmail.com',md5('maswd')),
  ('CCCCC','Johnathan Dobee','johnathancomp@hotmail.com',md5('heydidyouknowyourcarbecomesmoreflammablewithgasinit'))  
  ;

INSERT INTO carta (cardId,nombreCard,descripcion,coleccion,empresa)
VALUES
  ('bewd1','Blue Eyes White Dragon','idk Kaiba used it a couple of times ig', 'OG', 'YugiOh'),
  ('ogchr','Charizard Original Edition', 'People pay way too much for these', 'OG 1995', 'Pokemon'),
  ('forst','Forest','Green Grass Eat my a-','Basic Set', 'Magic: The Gathering')
  ;

INSERT INTO inventario (usrId, cardId,valor,estado,copias)
VALUES
  ('AAAAA','bewd1',100,'Perfecto',1),
  ('BBBBB','ogchr',999.99,'Sellado',1),
  ('CCCCC','forst',2.99,'Buena',30)
;
