package serverrest.probva.repository;

import serverrest.probva.db.ConnectionManager;
import serverrest.probva.model.Anagrafica;
import java.sql.*;
import java.util.*;

public class AnagraficaRepositoryImpl implements AnagraficaRepository {
// ── Query SQL come costanti ────────────────────────────────────────
private static final String INSERT =
"INSERT INTO anagrafica (nome, cognome, anno_nascita) VALUES (?, ?, ?)";
private static final String SELECT_ALL =
"SELECT id, nome, cognome, anno_nascita FROM anagrafica ORDER BY cognome, nome";
private static final String SELECT_BY_ID =
"SELECT id, nome, cognome, anno_nascita FROM anagrafica WHERE id = ?";
private static final String UPDATE =
"UPDATE anagrafica SET nome = ?, cognome = ?, anno_nascita = ? WHERE id = ?";
private static final String DELETE =
"DELETE FROM anagrafica WHERE id = ?";
// ── SAVE (INSERT) ─────────────────────────────────────────────────
@Override
public void save(Anagrafica a) throws Exception {
// try-with-resources chiude automaticamente Connection e Statement
try (Connection conn = ConnectionManager.getConnection();
PreparedStatement ps = conn.prepareStatement(INSERT,
Statement.RETURN_GENERATED_KEYS)) {
ps.setString(1, a.getNome());
ps.setString(2, a.getCognome());
ps.setInt (3, a.getAnnoNascita());
ps.executeUpdate();
// Recuperiamo l'id auto-generato dal DB
try (ResultSet keys = ps.getGeneratedKeys()) {
if (keys.next()) {
a.setId(keys.getInt(1));
}
}
}
}
// ── FIND ALL (SELECT) ─────────────────────────────────────────────
@Override
public List<Anagrafica> findAll() throws Exception {
List<Anagrafica> result = new ArrayList <>();
try (Connection conn = ConnectionManager.getConnection();
PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
ResultSet rs = ps.executeQuery()) {
while (rs.next()) {
result.add(mapRow(rs)); // metodo di mapping riusabile
}
}
return result;
}
// ── FIND BY ID ────────────────────────────────────────────────────
@Override
public Optional<Anagrafica> findById(int id) throws Exception {
try (Connection conn = ConnectionManager.getConnection();
PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID)) {
ps.setInt(1, id);
try (ResultSet rs = ps.executeQuery()) {
return rs.next()
? Optional.of(mapRow(rs))
: Optional.empty();
}
}
}
// ── UPDATE ────────────────────────────────────────────────────────
@Override
public void update(Anagrafica a) throws Exception {
try (Connection conn = ConnectionManager.getConnection();
PreparedStatement ps = conn.prepareStatement(UPDATE)) {
ps.setString(1, a.getNome());
ps.setString(2, a.getCognome());
ps.setInt (3, a.getAnnoNascita());
ps.setInt (4, a.getId());
ps.executeUpdate();
}
}
// ── DELETE ────────────────────────────────────────────────────────
@Override
public void deleteById(int id) throws Exception {
try (Connection conn = ConnectionManager.getConnection();
PreparedStatement ps = conn.prepareStatement(DELETE)) {
ps.setInt(1, id);
ps.executeUpdate();
}
}
// ── HELPER: mappa una riga del ResultSet in un oggetto ────────────
private Anagrafica mapRow(ResultSet rs) throws SQLException {
return new Anagrafica(
rs.getInt ("id"),
rs.getString("nome"),
rs.getString("cognome"),
rs.getInt ("anno_nascita")
);
}
}

