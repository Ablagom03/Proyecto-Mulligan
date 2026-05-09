# Proyecto Mulligan

Mulligan consta de una página web de compraventa de cartas de varios juegos, como Yu-Gi-Oh™️, Magic: The Gathering™️ y más.

En dicha página web, los usuarios pueden comprar y vender cartas que posean en su inventario.

Además, Mulligan permite a los usuarios interactuar entre ellos con un sistema de chat, comentarios para los vendedores y un sistema de reputación.

## Para frontend:
- Angular
- TypeScript (dentro de Angular)
- JavaScript (eventos en HTML)
- CSS (estilos)
- PHP (manejo de conexiones al servidor/backend)

## Para backend:
- SpringBoot (manejo de datos en backend y conexión a bbdd)
- Manejo de cookies y sesión con Java

## Para base de datos:
- Contenedor docker con postgresql

### Instrucciones de uso

1. Clonar el repositorio en un directorio

2. Ejecutar el siguiente comando para levantarlo 

 ```sh
   docker compose up
   ```

3. Si es necesario reiniciar la base de datos

 ```sh
   docker compose down -v
   ```

