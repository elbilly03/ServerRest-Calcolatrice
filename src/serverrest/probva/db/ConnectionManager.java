package serverrest.probva.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionManager {

    // ── Parametri di connessione — modifica questi valori ────────────
    private static final String URL =
        "jdbc:mysql://localhost:3306/anagrafica_db?useSSL=false&serverTimezone=UTC";
    private static final String USER     = "root";
    private static final String PASSWORD = "";

    /**
     * Restituisce una nuova connessione al database.
     * Il chiamante è responsabile di chiuderla (preferibilmente con try-with-resources).
     */
    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Classe di sola utilità: costruttore privato
    private ConnectionManager() {}
}
