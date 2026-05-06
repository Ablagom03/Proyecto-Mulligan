package com.muligan.cartas.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;
import java.util.Map;

@Service
public class PrecioMercadoService {

    private final RestTemplate restTemplate;

    public PrecioMercadoService() {
        this.restTemplate = new RestTemplate();
    }

    public String obtenerPrecioComercial(String nombreCarta, String empresa) {
        try {
            switch (empresa.toUpperCase()) {
                case "YUGIOH":
                    return buscarYugioh(nombreCarta);
                case "MAGIC":
                    return buscarMagic(nombreCarta);
                case "POKEMON":
                    return buscarPokemon(nombreCarta);
                case "LORCANA":
                case "MARVELC":
                case "INVIZIMALS":
                    return "Próximamente (API en desarrollo)";
                default:
                    return "No disponible";
            }
        } catch (Exception e) {
            System.err.println("Error consultando " + empresa + " para " + nombreCarta + ": " + e.getMessage());
            return "No disponible";
        }
    }

    private String buscarYugioh(String nombre) {
        String url = UriComponentsBuilder
                .fromUriString("https://db.ygoprodeck.com/api/v7/cardinfo.php")
                .queryParam("fname", nombre) 
                .build().toUriString();

        Map<String, Object> res = restTemplate.getForObject(url, Map.class);
        if (res != null && res.containsKey("data")) {
            List<Map<String, Object>> data = (List<Map<String, Object>>) res.get("data");
            List<Map<String, String>> prices = (List<Map<String, String>>) data.get(0).get("card_prices");
            return prices.get(0).get("cardmarket_price") + " €";
        }
        return "No listado";
    }

    private String buscarMagic(String nombre) {
        String url = UriComponentsBuilder
                .fromUriString("https://api.scryfall.com/cards/named")
                .queryParam("fuzzy", nombre)
                .build().toUriString();

        Map<String, Object> res = restTemplate.getForObject(url, Map.class);
        if (res != null && res.containsKey("prices")) {
            Map<String, String> prices = (Map<String, String>) res.get("prices");
            String precio = prices.get("eur");
            return precio != null ? precio + " €" : "Sin stock";
        }
        return "No listado";
    }

    private String buscarPokemon(String nombre) {
        String url = UriComponentsBuilder
                .fromUriString("https://api.pokemontcg.io/v2/cards")
                .queryParam("q", "name:\"" + nombre + "\"")
                .build().toUriString();

        Map<String, Object> res = restTemplate.getForObject(url, Map.class);
        if (res != null && res.containsKey("data")) {
            List<Map<String, Object>> data = (List<Map<String, Object>>) res.get("data");
            if (!data.isEmpty()) {
                Map<String, Object> cardmarket = (Map<String, Object>) data.get(0).get("cardmarket");
                if (cardmarket != null && cardmarket.containsKey("prices")) {
                    Map<String, Object> prices = (Map<String, Object>) cardmarket.get("prices");
                    
                    Object precio = prices.get("averageSellPrice");
                    return precio != null ? precio.toString() + " €" : "No disponible";
                }
            }
        }
        return "No listado";
    }
}