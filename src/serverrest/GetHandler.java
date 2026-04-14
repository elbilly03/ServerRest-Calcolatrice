package serverrest;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class GetHandler implements HttpHandler {
    
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        
        if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {
            inviaErrore(exchange, 405, "Metodo non consentito. Usa GET");
            return;
        }
        
        try {
            String path = exchange.getRequestURI().getPath();
            String[] pathParts = path.split("/");
            if (pathParts.length < 3) {
                inviaErrore(exchange, 400, "URL non valido.");
                return;
            }
            
            String entita = pathParts[2].toLowerCase(); // Estrae "user" o "arnie"
            String jsonRisposta = "";
            
            // In base all'entità, preleviamo i dati dal finto DB e li trasformiamo in JSON
            if (entita.equals("user")) {
                jsonRisposta = gson.toJson(DatabaseMock.users);
            } else if (entita.equals("arnie")) {
                jsonRisposta = gson.toJson(DatabaseMock.arnie);
            } else {
                inviaErrore(exchange, 400, "Entità non valida. Supportate: 'user', 'arnie'");
                return;
            }
            
            inviaRisposta(exchange, 200, jsonRisposta);
            
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