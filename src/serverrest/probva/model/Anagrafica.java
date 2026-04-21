package serverrest.probva.model;

public class Anagrafica {

    private Integer id;          // null finché non è salvata nel DB
    private String  nome;
    private String  cognome;
    private int     annoNascita;

    // Costruttore per INSERT (senza id)
    public Anagrafica(String nome, String cognome, int annoNascita) {
        this.nome         = nome;
        this.cognome      = cognome;
        this.annoNascita  = annoNascita;
    }

    // Costruttore per lettura dal DB (con id)
    public Anagrafica(int id, String nome, String cognome, int annoNascita) {
        this.id           = id;
        this.nome         = nome;
        this.cognome      = cognome;
        this.annoNascita  = annoNascita;
    }

    // Getter e Setter ─────────────────────────────────────
    public Integer getId()          { return id; }
    public void    setId(int id)     { this.id = id; }

    public String  getNome()         { return nome; }
    public void    setNome(String v) { this.nome = v; }

    public String  getCognome()         { return cognome; }
    public void    setCognome(String v) { this.cognome = v; }

    public int     getAnnoNascita()         { return annoNascita; }
    public void    setAnnoNascita(int v)   { this.annoNascita = v; }

    @Override
    public String toString() {
        return "Anagrafica{id=" + id +
               ", nome='" + nome + '\'' +
               ", cognome='" + cognome + '\'' +
               ", annoNascita=" + annoNascita + '}';
    }
}
