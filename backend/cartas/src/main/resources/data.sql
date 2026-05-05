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
('Blue-Eyes White Dragon',pg_read_binary_file('/images/cartas/bewd.jpg')),
('Pot Of Greed',pg_read_binary_file('/images/cartas/PotOfGreed.jpg')),
('PikachuOriginal',pg_read_binary_file('/images/cartas/PikachuOG.jpg')),
('Forest',pg_read_binary_file('/images/cartas/ForestMtg.jpg')),
('Swamp',pg_read_binary_file('/images/cartas/Swamp.jpg')),
('Fanbot',pg_read_binary_file('/images/cartas/Fanbot.jpg')),
('Tony Stark',pg_read_binary_file('/images/cartas/tony.png')),
('Elsa',pg_read_binary_file('/images/cartas/Elsa.png'))
ON CONFLICT (nombre) DO NOTHING;

INSERT INTO carta (nombreCard, descripcion, coleccion, empresa, idimg) 
VALUES 
('Blue-Eyes White Dragon', 'Este dragón legendario es una poderos máquina de destrucción. Virtualmente invencible, pocos han visto esta asombrosa criatura y vivido para contarlo.', 'OG', 'YUGIOH',
(SELECT idimg FROM imagenes WHERE nombre = 'Blue-Eyes White Dragon')),
('Pot of Greed', 'Roba 2 cartas.', 'OG', 'YUGIOH', 
(SELECT idimg FROM imagenes WHERE nombre = 'Pot Of Greed')),
('Charizard Original Edition', 'La original carta de Charizard de 1995', 'OG 1995', 'POKEMON', 
(SELECT idimg FROM imagenes WHERE nombre = 'CharizardOriginal')),
('Pikachu Original Edition', 'La original carta de 1995', 'OG 1995', 'POKEMON', 
(SELECT idimg FROM imagenes WHERE nombre = 'PikachuOriginal')),
('Forest', 'Tierra Básica - Mana verde', 'Basic Set', 'MAGIC', 
(SELECT idimg FROM imagenes WHERE nombre = 'Forest')),
('Swamp', 'Tierra Básica - Mana Negro', 'Basic Set', 'MAGIC', 
(SELECT idimg FROM imagenes WHERE nombre = 'Swamp')),
('Fanbot', 'La velocidad es la principal ventaja de Fanbot. Gracias a su facilidad para desplazarse, busca la sorpresa y no la fuerza. Antes de atacar, examina a los rivales con un ojo cámara telescópico y se planta ante ellos en un santiamén.', 'Invizimals: The Resistance', 'INVIZIMALS', 
(SELECT idimg FROM imagenes WHERE nombre = 'Fanbot')),
('Elsa', 'Recreada con tinta mágica, Elsa se encontró en un nuevo mundo. Desafortunadamente, hielo funciona de igual manera en todos lados', '1ª Ronda', 'LORCANA', 
(SELECT idimg FROM imagenes WHERE nombre = 'Elsa')),
('Tony Stark', 'Obviamemente no hago esto por dinero...', '1er Set', 'MARVELC', 
(SELECT idimg FROM imagenes WHERE nombre = 'Tony Stark'))
ON CONFLICT (nombreCard) DO NOTHING;