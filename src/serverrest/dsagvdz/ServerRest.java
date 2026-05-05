/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

package serverrest.dsagvdz;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * Server REST per l'interfacciamento con RestDB.io
 * (Collezioni: user e arnie)
 * * @author billy
 */
public class ServerRest {

    public static void avviaServer(int porta) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(porta), 0);
            
            // Registra gli handler
            server.createContext("/api/user/get", new GetHandler());
            server.createContext("/api/user/post", new PostHandler());

            server.createContext("/api/arnie/get", new GetHandler());
            server.createContext("/api/arnie/post", new PostHandler());
            
            server.createContext("/", ServerRest::gestisciBenvenuto);
            
            server.setExecutor(null);
            server.start();
            
            System.out.println("==============================================");
            System.out.println("  Server REST Proxy (RestDB) avviato!");
            System.out.println("==============================================");
            System.out.println("Porta: " + porta);
            System.out.println();
            System.out.println("Endpoint disponibili:");
            System.out.println("  - GET:  http://localhost:" + porta + "/api/user/get");
            System.out.println("  - POST: http://localhost:" + porta + "/api/user/post");
            System.out.println("  - GET:  http://localhost:" + porta + "/api/arnie/get");
            System.out.println("  - POST: http://localhost:" + porta + "/api/arnie/post");
            System.out.println("  - Info: http://localhost:" + porta + "/");
            System.out.println();
            System.out.println("Ricordati di inserire la tua API KEY in GetHandler e PostHandler!");
            System.out.println("Premi Ctrl+C per fermare il server");
            System.out.println("==============================================");
            
        } catch (IOException e) {
            System.err.println("Errore nell'avvio del server: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void gestisciBenvenuto(HttpExchange exchange) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        Map<Object, Object> info = new HashMap<>();
        info.put("messaggio", "Benvenuto all'API Proxy per RestDB.io");
        info.put("versione", "1.0.0");
        info.put("tecnologia", "Java + GSON + HttpURLConnection");
        
        Map<Object, Object> endpoints = new HashMap<>();
        endpoints.put("GET User", "/api/user/get");
        endpoints.put("POST User", "/api/user/post");
        endpoints.put("GET Arnie", "/api/arnie/get");
        endpoints.put("POST Arnie", "/api/arnie/post");
        info.put("endpoints", endpoints);
        
        Map<Object, Object> entita_supportate = new HashMap<>();
        entita_supportate.put("user", "Collezione utenti");
        entita_supportate.put("arnie", "Collezione arnie");
        info.put("collezioni_supportate", entita_supportate);
        
        String jsonRisposta = gson.toJson(info);
        
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        byte[] bytes = jsonRisposta.getBytes();
        exchange.sendResponseHeaders(200, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.getResponseBody().close();
    }
}