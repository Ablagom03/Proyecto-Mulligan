-- Por ahora insertamos las imágenes manualmente, estamos planeando alguna manera de automatizarlo
INSERT INTO imagenes(nombre,data) VALUES 
('logoInvizimals',pg_read_binary_file('/images/logosMarcas/invizimals.png')),
('logoLorcana',pg_read_binary_file('/images/logosMarcas/lorcanalogo.png')),
('logoMagic',pg_read_binary_file('/images/logosMarcas/Magic-The-Gathering-logo.png')),
('logoMarvelC',pg_read_binary_file('/images/logosMarcas/marvelChampions.png')),
('logoPokTCG',pg_read_binary_file('/images/logosMarcas/Pokémon_Trading_Card_Game_logo.svg.png')),
('logoYugioh',pg_read_binary_file('/images/logosMarcas/Yugioh_Logo.webp')),
('logoMulligan',pg_read_binary_file('/images/logoMulligan.png')),
('CharizardOriginal',pg_read_binary_file('/images/cartas/chariz.png')),
('Blue Eyes White Dragon',pg_read_binary_file('/images/cartas/bewd.jpg'))
ON CONFLICT (nombre) DO NOTHING;

INSERT INTO carta (nombreCard, descripcion, coleccion, empresa, idimg) 
VALUES 
('Blue Eyes White Dragon', 'idk Kaiba used it a couple of times ig', 'OG', 'YugiOh',
(SELECT idimg FROM imagenes WHERE nombre = 'Blue Eyes White Dragon')),
('Pot of Greed', 'Lets the user draw 2 cards', 'OG', 'YugiOh', 1),
('Charizard Original Edition', 'People pay way too much for these', 'OG 1995', 'Pokemon', 
(SELECT idimg FROM imagenes WHERE nombre = 'CharizardOriginal')),
('Pikachu Original Edition', 'People pay way too much for these', 'OG 1995', 'Pokemon', 2),
('Forest', 'Green Grass Eat my a-', 'Basic Set', 'Magic', 3),
('Swamp', 'Swamp', 'Basic Set', 'Magic', 4)
ON CONFLICT (nombreCard) DO NOTHING;

INSERT INTO usuario (nombre_usr, email, reputacion,passwd) 
VALUES 
('JohnDoe', 'johndoe@gmail.com', 0, md5('paswd')),
('JaneDoe', 'janedoe@gmail.com', 0 , md5('maswd')),
('JohnathanDobee', 'johnathancomp@hotmail.com', 0, md5('heydidyouknowyourcarbecomesmoreflammablewithgasinit'))
ON CONFLICT (email) DO NOTHING;;




INSERT INTO inventario (usrId, cardId, valor, estado, copias)
SELECT u.usrId, c.cardId, 100, 'Perfecto', 1
FROM usuario u, carta c
WHERE u.email = 'johndoe@gmail.com' AND c.nombreCard = 'Blue Eyes White Dragon'
AND NOT EXISTS (
    SELECT 1 FROM inventario i 
    WHERE i.usrId = u.usrId AND i.cardId = c.cardId AND i.estado = 'Perfecto'
);




--No soy el mayor fan de usar DO NOTHING, pero por ahora funciona 16/04/2026