package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AvdelingDAO {
    private Connection connection;

    public AvdelingDAO() {
        try {
            // Erstatt med dine databaseparametere
            String url = "jdbc:postgresql://ider-database.westeurope.cloudapp.azure.com:5433/h184890";
            String bruker = "h184890";
            String passord = "sander2122";
            connection = DriverManager.getConnection(url, bruker, passord);
        } catch (SQLException e) {
            System.err.println("Feil ved opprettelse av databaseforbindelse: " + e.getMessage());
        }
    }

    // Hent en avdeling med ID
    public Avdeling finnAvdelingMedId(int id) {
        String sql = "SELECT * FROM oblig.Avdeling WHERE avdeling_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Avdeling(
                        rs.getInt("avdeling_id"),
                        rs.getString("navn"),
                        rs.getInt("sjef_id")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Feil ved søk på avdeling-ID: " + e.getMessage());
        }
        return null;
    }

    // Hent alle avdelinger
    public List<Avdeling> hentAlleAvdelinger() {
        List<Avdeling> avdelinger = new ArrayList<>();
        String sql = "SELECT * FROM oblig.Avdeling ORDER BY avdeling_id";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                avdelinger.add(new Avdeling(
                    rs.getInt("avdeling_id"),
                    rs.getString("navn"),
                    rs.getInt("sjef_id")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Feil ved henting av avdelinger: " + e.getMessage());
        }
        return avdelinger;
    }

    // Legg til en ny avdeling
    public boolean leggTilAvdeling(String navn, int sjefId) {
        String sql = "INSERT INTO oblig.Avdeling (navn, sjef_id) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, navn);
            stmt.setInt(2, sjefId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Feil ved innsetting av avdeling: " + e.getMessage());
            return false;
        }
    }

    // Hent ansatte for en avdeling
    public List<Ansatt> hentAnsatteForAvdeling(int avdelingId) {
        List<Ansatt> ansatte = new ArrayList<>();
        String sql = "SELECT * FROM oblig.Ansatt WHERE avdeling_id = ? ORDER BY ansatt_id";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, avdelingId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ansatte.add(new Ansatt(
                        rs.getInt("ansatt_id"),
                        rs.getString("brukernavn"),
                        rs.getString("fornavn"),
                        rs.getString("etternavn"),
                        rs.getDate("dato_ansatt"),
                        rs.getString("stilling"),
                        rs.getInt("maanedslonn"),
                        rs.getInt("avdeling_id")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Feil ved henting av ansatte for avdeling: " + e.getMessage());
        }
        return ansatte;
    }

    // Oppdater sjef for en avdeling
    public boolean oppdaterSjef(int avdelingId, int nySjefId) {
        String sql = "UPDATE oblig.Avdeling SET sjef_id = ? WHERE avdeling_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, nySjefId);
            stmt.setInt(2, avdelingId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Feil ved oppdatering av sjef: " + e.getMessage());
            return false;
        }
    }

    // Lukk databaseforbindelsen
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