package serverrest;

public class Arnie {
    private String _id;
    private String arn_dataInst;
    private boolean arn_piena;
    private String arn_MacAddress;
    private int arn_api_id;
    private int arn_id;

    // Costruttore vuoto necessario per GSON
    public Arnie() {}

    public Arnie(String _id, String arn_dataInst, boolean arn_piena, String arn_MacAddress, int arn_api_id, int arn_id) {
        this._id = _id;
        this.arn_dataInst = arn_dataInst;
        this.arn_piena = arn_piena;
        this.arn_MacAddress = arn_MacAddress;
        this.arn_api_id = arn_api_id;
        this.arn_id = arn_id;
    }

    public String getId() { return _id; }
    public void setId(String _id) { this._id = _id; }
}