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
('Blue Eyes White Dragon', 'idk Kaiba used it a couple of times ig', 'OG', 'YUGIOH',
(SELECT idimg FROM imagenes WHERE nombre = 'Blue Eyes White Dragon')),
('Pot of Greed', 'Lets the user draw 2 cards', 'OG', 'YUGIOH', 1),
('Charizard Original Edition', 'People pay way too much for these', 'OG 1995', 'POKEMON', 
(SELECT idimg FROM imagenes WHERE nombre = 'CharizardOriginal')),
('Pikachu Original Edition', 'People pay way too much for these', 'OG 1995', 'POKEMON', 2),
('Forest', 'Green Grass Eat my a-', 'Basic Set', 'MAGIC', 3),
('Swamp', 'Swamp', 'Basic Set', 'MAGIC', 4)
ON CONFLICT (nombreCard) DO NOTHING;

INSERT INTO usuario (nombre_usr, email, reputacion, passwd) 
VALUES 
-- Hash de Contraseña: paswd
('JohnDoe', 'johndoe@gmail.com', 0, '6e8a990622dcd1027d2201e9f1400352'),

-- Hash de Contraseña: maswd
('JaneDoe', 'janedoe@gmail.com', 0 , '85698b6460677465363ec0f4384d635f'),

-- Hash de Contraseña: heydidyouknowyourcarbecomesmoreflammablewithgasinit
('JohnathanDobee', 'johnathancomp@hotmail.com', 0, 'e1e77983696894c25674061a7a030097')
ON CONFLICT (email) DO NOTHING;

INSERT INTO inventario (usrId, cardId, valor, estado, copias, tipo)
SELECT u.usrId, c.cardId, 100, 'Perfecto', 1, 'VENTA'
FROM usuario u, carta c
WHERE u.email = 'johndoe@gmail.com' 
  AND c.nombreCard = 'Blue Eyes White Dragon'
  AND NOT EXISTS (
    SELECT 1 FROM inventario i 
    WHERE i.usrId = u.usrId 
      AND i.cardId = c.cardId 
      AND i.estado = 'Perfecto'
      AND i.tipo = 'VENTA'
  );