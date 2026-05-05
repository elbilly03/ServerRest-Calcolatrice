package serverrest.dsagvdz;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PostHandler implements HttpHandler {
    
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        
        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            inviaErrore(exchange, 405, "Metodo non consentito. Usa POST");
            return;
        }
        
        try {
            String path = exchange.getRequestURI().getPath();
            String[] pathParts = path.split("/");
            if (pathParts.length < 3) {
                inviaErrore(exchange, 400, "URL non valido.");
                return;
            }
            
            String entita = pathParts[2].toLowerCase();
            
            // Legge il body in arrivo
            InputStreamReader reader = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
            String jsonRisposta = "";
            
            if (entita.equals("user")) {
                // Converte il JSON nel POJO User
                User nuovoUser = gson.fromJson(reader, User.class);
                // Simula la creazione di un ID se non fornito
                if (nuovoUser.getId() == null || nuovoUser.getId().isEmpty()) {
                    nuovoUser.setId(UUID.randomUUID().toString());
                }
                // Lo salva in memoria
                DatabaseMock.users.add(nuovoUser);
                jsonRisposta = gson.toJson(nuovoUser);
                
            } else if (entita.equals("arnie")) {
                // Converte il JSON nel POJO Arnie
                Arnie nuovaArnia = gson.fromJson(reader, Arnie.class);
                // Simula la creazione di un ID se non fornito
                if (nuovaArnia.getId() == null || nuovaArnia.getId().isEmpty()) {
                    nuovaArnia.setId(UUID.randomUUID().toString());
                }
                // Lo salva in memoria
                DatabaseMock.arnie.add(nuovaArnia);
                jsonRisposta = gson.toJson(nuovaArnia);
                
            } else {
                inviaErrore(exchange, 400, "Entità non valida. Supportate: 'user', 'arnie'");
                return;
            }
            
            // Restituisce 201 (Created) e l'oggetto appena "salvato"
            inviaRisposta(exchange, 201, jsonRisposta);
            
        } catch (Exception e) {
            inviaErrore(exchange, 500, "Errore interno del server: " + e.getMessage());
        }
    }
    
    private void inviaRisposta(HttpExchange exchange, int codice, String jsonRisposta) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        byte[] bytes = jsonRisposta.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(codice, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }
    
    private void inviaErrore(HttpExchange exchange, int codice, String messaggio) throws IOException {
        Map<String, Object> errore = new HashMap<>();
        errore.put("errore", messaggio);
        errore.put("status", codice);
        inviaRisposta(exchange, codice, gson.toJson(errore));
    }
}