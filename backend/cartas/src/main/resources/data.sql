INSERT INTO usuario (nombre_usr, email, reputacion,passwd) 
VALUES 
('John Doe', 'johndoe@gmail.com', 0, md5('paswd')),
('Jane Doe', 'janedoe@gmail.com', 0 , md5('maswd')),
('Johnathan Dobee', 'johnathancomp@hotmail.com', 0, md5('heydidyouknowyourcarbecomesmoreflammablewithgasinit'))
ON CONFLICT (email) DO NOTHING;;

INSERT INTO carta (nombreCard, descripcion, coleccion, empresa) 
VALUES 
('Blue Eyes White Dragon', 'idk Kaiba used it a couple of times ig', 'OG', 'YugiOh'),
('Charizard Original Edition', 'People pay way too much for these', 'OG 1995', 'Pokemon'),
('Forest', 'Green Grass Eat my a-', 'Basic Set', 'Magic')
ON CONFLICT (nombreCard) DO NOTHING;


INSERT INTO inventario (usrId, cardId, valor, estado, copias)
SELECT u.usrId, c.cardId, 100, 'Perfecto', 1
FROM usuario u, carta c
WHERE u.email = 'johndoe@gmail.com' AND c.nombreCard = 'Blue Eyes White Dragon'
AND NOT EXISTS (
    SELECT 1 FROM inventario i 
    WHERE i.usrId = u.usrId AND i.cardId = c.cardId AND i.estado = 'Perfecto'
);


-- Por ahora insertamos las imágenes manualmente, estamos planeando alguna manera de automatizarlo
INSERT INTO imagenes(nombre,data) VALUES 
('logoInvizimals',pg_read_binary_file('/images/logosMarcas/invizimals.png')),
('logoLorcana',pg_read_binary_file('/images/logosMarcas/lorcanalogo.png')),
('logoMagic',pg_read_binary_file('/images/logosMarcas/Magic-The-Gathering-logo.png')),
('logoMarvelC',pg_read_binary_file('/images/logosMarcas/marvelChampions.png')),
('logoPokTCG',pg_read_binary_file('/images/logosMarcas/Pokémon_Trading_Card_Game_logo.svg.png')),
('logoYugioh',pg_read_binary_file('/images/logosMarcas/Yugioh_Logo.webp')),
('logoMulligan',pg_read_binary_file('/images/logoMulligan.png'))
ON CONFLICT (nombre) DO NOTHING;

--No soy el mayor fan de usar DO NOTHING, pero por ahora funciona 16/04/2026






--
--INSERT INTO inventario (usrId, cardId, valor, estado, copias)
--SELECT * FROM (
--    VALUES 
--    (1, 1, 100, 'Perfecto', 1),
--    (2, 2, 999.99, 'Sellado', 1),
--    (3, 3, 2.99, 'Buena', 30)
--) AS tmp (uId, cId, v, est, cop)
--WHERE NOT EXISTS (SELECT 1 FROM inventario);