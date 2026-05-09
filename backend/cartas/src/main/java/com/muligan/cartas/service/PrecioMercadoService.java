package com.muligan.cartas.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class PrecioMercadoService {

    private final RestTemplate restTemplate;

    public PrecioMercadoService() {
        this.restTemplate = new RestTemplate();
    }
    
    public Map<String, String> obtenerDatosMercado(String nombreCarta, String empresa) {
        try {
            return switch (empresa.toUpperCase()) {
                case "YUGIOH" -> buscarYugioh(nombreCarta);
                case "MAGIC" -> buscarMagic(nombreCarta);
                case "POKEMON" -> buscarPokemon(nombreCarta);
                case "LORCANA", "MARVELC", "INVIZIMALS" -> crearRespuesta("Próximamente (API en desarrollo)", null);
                default -> crearRespuesta("No disponible", null);
            };
        } catch (Exception e) {
            System.err.println("Error consultando " + empresa + " para " + nombreCarta + ": " + e.getMessage());
            return crearRespuesta("No disponible", null);
        }
    }

    private Map<String, String> crearRespuesta(String precio, String url) {
        Map<String, String> res = new HashMap<>();
        res.put("precio", precio);
        res.put("url", url);
        return res;
    }
    /**
     * Busca el precio de una carta de Yu-Gi-Oh! usando una API externa.
     *
     * @param nombre Nombre de la carta.
     * @return Map con el precio y el enlace.
     */
    private Map<String, String> buscarYugioh(String nombre) {
        String urlApi = UriComponentsBuilder
                .fromUriString("https://db.ygoprodeck.com/api/v7/cardinfo.php")
                .queryParam("fname", nombre)
                .build().toUriString();

        Map<String, Object> res = restTemplate.getForObject(urlApi, Map.class);
        if (res != null && res.containsKey("data")) {
            List<Map<String, Object>> data = (List<Map<String, Object>>) res.get("data");
            List<Map<String, String>> prices = (List<Map<String, String>>) data.get(0).get("card_prices");
            
            String precio = prices.get(0).get("cardmarket_price") + " €";
            String link = "https://www.cardmarket.com/en/YuGiOh/Products/Search?searchString=" + nombre.replace(" ", "+");
            
            return crearRespuesta(precio, link);
        }
        return crearRespuesta("No listado", null);
    }
    /**
     * Busca el precio de una carta de Magic usando una API externa.
     *
     * @param nombre Nombre de la carta.
     * @return Map con el precio y el enlace.
     */
    private Map<String, String> buscarMagic(String nombre) {
        String urlApi = UriComponentsBuilder
                .fromUriString("https://api.scryfall.com/cards/named")
                .queryParam("fuzzy", nombre)
                .build().toUriString();

        Map<String, Object> res = restTemplate.getForObject(urlApi, Map.class);
        if (res != null) {
            Map<String, String> prices = (Map<String, String>) res.get("prices");
            Map<String, String> uris = (Map<String, String>) res.get("purchase_uris");
            
            String precio = (prices != null && prices.get("eur") != null) ? prices.get("eur") + " €" : "Sin stock";
            String link = (uris != null) ? uris.get("cardmarket") : null;
            
            return crearRespuesta(precio, link);
        }
        return crearRespuesta("No listado", null);
    }
    /**
     * Busca el precio de una carta de Pokémon usando una API externa.
     *
     * @param nombre Nombre de la carta.
     * @return Map con el precio y el enlace.
     */
    private Map<String, String> buscarPokemon(String nombre) {
        String urlApi = UriComponentsBuilder
                .fromUriString("https://api.pokemontcg.io/v2/cards")
                .queryParam("q", "name:\"" + nombre + "\"")
                .build().toUriString();

        Map<String, Object> res = restTemplate.getForObject(urlApi, Map.class);
        if (res != null && res.containsKey("data")) {
            List<Map<String, Object>> data = (List<Map<String, Object>>) res.get("data");
            if (!data.isEmpty()) {
                Map<String, Object> cardmarket = (Map<String, Object>) data.get(0).get("cardmarket");
                if (cardmarket != null) {
                    Map<String, Object> prices = (Map<String, Object>) cardmarket.get("prices");
                    String link = (String) cardmarket.get("url");
                    
                    Object precioObj = (prices != null) ? prices.get("averageSellPrice") : null;
                    String precio = (precioObj != null) ? precioObj.toString() + " €" : "No disponible";
                    
                    return crearRespuesta(precio, link);
                }
            }
        }
        return crearRespuesta("No listado", null);
    }
}