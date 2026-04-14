package serverrest;

public class User {
    private String _id;
    private String email;
    private boolean active;

    // Costruttore vuoto necessario per GSON
    public User() {}

    public User(String _id, String email, boolean active) {
        this._id = _id;
        this.email = email;
        this.active = active;
    }

    public String getId() { return _id; }
    public void setId(String _id) { this._id = _id; }
}