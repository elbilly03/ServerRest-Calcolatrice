package serverrest.probva.repository;
import serverrest.probva.model.Anagrafica;
import java.util.List;
import java.util.Optional;

public interface AnagraficaRepository {
/** Inserisce un nuovo record e imposta l'id generato sull'oggetto */
void save(Anagrafica a) throws Exception;
/** Legge tutti i record */
List<Anagrafica> findAll() throws Exception;
/** Cerca per id; ritorna Optional.empty() se non trovato */
Optional<Anagrafica> findById(int id) throws Exception;
/** Aggiorna nome, cognome e anno di un record esistente */
void update(Anagrafica a) throws Exception;
/** Cancella il record con l'id specificato */
void deleteById(int id) throws Exception;
}