package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProsjektDAO {
    private Connection connection;

    public ProsjektDAO() {
        try {
            String url = "jdbc:postgresql://ider-database.westeurope.cloudapp.azure.com:5433/h184890";
            String bruker = "h184890";
            String passord = "sander2122";
            connection = DriverManager.getConnection(url, bruker, passord);
        } catch (SQLException e) {
            System.err.println("Feil ved opprettelse av databaseforbindelse: " + e.getMessage());
        }
    }

    public List<Prosjekt> hentAlleProsjekter() {
        List<Prosjekt> prosjekter = new ArrayList<>();
        String sql = "SELECT * FROM oblig.Prosjekt ORDER BY prosjekt_id";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                prosjekter.add(new Prosjekt(
                    rs.getInt("prosjekt_id"),
                    rs.getString("navn"),
                    rs.getString("beskrivelse")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Feil ved henting av prosjekter: " + e.getMessage());
        }
        return prosjekter;
    }

    public boolean leggTilProsjekt(Prosjekt prosjekt) {
        String sql = "INSERT INTO oblig.Prosjekt (navn, beskrivelse, start_dato, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, prosjekt.getNavn());
            stmt.setString(2, prosjekt.getBeskrivelse());
            stmt.setDate(3, prosjekt.getStartDato());
            stmt.setString(4, prosjekt.getStatus());
            
            int rowsAffected = stmt.executeUpdate();
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    prosjekt.setProsjektId(generatedKeys.getInt(1));
                }
            }
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Feil ved innsetting av prosjekt: " + e.getMessage());
            return false;
        }
    }

    public Prosjekt finnProsjektMedId(int id) {
        String sql = "SELECT * FROM oblig.Prosjekt WHERE prosjekt_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Prosjekt(
                        rs.getInt("prosjekt_id"),
                        rs.getString("navn"),
                        rs.getString("beskrivelse")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Feil ved søk på prosjekt-ID: " + e.getMessage());
        }
        return null;
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
