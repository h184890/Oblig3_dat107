package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnsattProsjektDAO {
    private Connection connection;

    public AnsattProsjektDAO() {
        try {
            String url = "jdbc:postgresql://ider-database.westeurope.cloudapp.azure.com:5433/h184890";
            String bruker = "h184890";
            String passord = "sander2122";
            connection = DriverManager.getConnection(url, bruker, passord);
        } catch (SQLException e) {
            System.err.println("Feil ved opprettelse av databaseforbindelse: " + e.getMessage());
        }
    }

    public boolean registrerProsjektdeltagelse(int ansattId, int prosjektId, String rolle) {
        String sql = "INSERT INTO oblig.Ansatt_Prosjekt (ansatt_id, prosjekt_id, rolle) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, ansattId);
            stmt.setInt(2, prosjektId);
            stmt.setString(3, rolle);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Feil ved registrering av prosjektdeltagelse: " + e.getMessage());
            return false;
        }
    }

    public boolean registrerTimer(int ansattProsjektId, int timer) {
        String sql = "UPDATE oblig.Ansatt_Prosjekt SET timer = timer + ? WHERE ansatt_prosjekt_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, timer);
            stmt.setInt(2, ansattProsjektId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Feil ved registrering av timer: " + e.getMessage());
            return false;
        }
    }

    public List<ProsjektDeltakelse> hentDeltagelserForProsjekt(int prosjektId) {
        List<ProsjektDeltakelse> deltagelser = new ArrayList<>();
        String sql = "SELECT ap.*, a.fornavn, a.etternavn FROM oblig.Ansatt_Prosjekt ap " +
                     "JOIN oblig.Ansatt a ON ap.ansatt_id = a.ansatt_id " +
                     "WHERE ap.prosjekt_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, prosjektId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    deltagelser.add(new ProsjektDeltakelse(
                        rs.getInt("ansatt_prosjekt_id"),
                        rs.getInt("ansatt_id"),
                        rs.getString("fornavn") + " " + rs.getString("etternavn"),
                        rs.getInt("prosjekt_id"),
                        rs.getString("rolle"),
                        sql, rs.getInt("timer")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Feil ved henting av prosjektdeltagelser: " + e.getMessage());
        }
        return deltagelser;
    }
    
    public List<ProsjektDeltakelse> hentAlleDeltagelser() {
        List<ProsjektDeltakelse> deltagelser = new ArrayList<>();
        String sql = "SELECT ap.ansatt_prosjekt_id, ap.ansatt_id, a.fornavn, a.etternavn, " +
                     "ap.prosjekt_id, p.navn as prosjekt_navn, ap.rolle, ap.timer " +
                     "FROM oblig.Ansatt_Prosjekt ap " +
                     "JOIN oblig.Ansatt a ON ap.ansatt_id = a.ansatt_id " +
                     "JOIN oblig.Prosjekt p ON ap.prosjekt_id = p.prosjekt_id " +
                     "ORDER BY ap.ansatt_prosjekt_id";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                deltagelser.add(new ProsjektDeltakelse(
                    rs.getInt("ansatt_prosjekt_id"),
                    rs.getInt("ansatt_id"),
                    rs.getString("fornavn") + " " + rs.getString("etternavn"),
                    rs.getInt("prosjekt_id"),
                    rs.getString("prosjekt_navn"),
                    rs.getString("rolle"),
                    rs.getInt("timer")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Feil ved henting av alle prosjektdeltagelser: " + e.getMessage());
        }
        return deltagelser;
    }

    public void lukkForbindelse() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Feil ved lukking av databaseforbindelse: " + e.getMessage());
        }
    }
}
