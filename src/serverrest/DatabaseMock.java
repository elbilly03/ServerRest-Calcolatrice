package serverrest;

import java.util.ArrayList;
import java.util.List;

public class DatabaseMock {
    
    public static final List<User> users = new ArrayList<>();
    public static final List<Arnie> arnie = new ArrayList<>();
    
    // Inizializzatore statico per inserire qualche dato finto all'avvio
    static {
        users.add(new User("usr_001", "mario.rossi@email.it", true));
        users.add(new User("usr_002", "luigi.verdi@email.it", false));
        
        arnie.add(new Arnie("arn_001", "2024-03-01", true, "00:1B:44:11:3A:B7", 1, 101));
        arnie.add(new Arnie("arn_002", "2024-03-15", false, "00:1B:44:11:3A:C8", 2, 102));
    }
}