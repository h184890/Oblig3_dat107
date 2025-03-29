package database;

import jakarta.persistence.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnsattDAO {
    private Connection connection;

    public AnsattDAO() {
        try {
            // Erstatt med dine egne databaseparametere
            String url = "jdbc:postgresql://ider-database.westeurope.cloudapp.azure.com:5433/h184890";
            String bruker = "h184890";
            String passord = "sander2122";
            connection = DriverManager.getConnection(url, bruker, passord);
            connection.setAutoCommit(false); // For bedre transaksjonshåndtering
        } catch (SQLException e) {
            System.err.println("Feil ved opprettelse av databaseforbindelse: " + e.getMessage());
        }
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

    public Ansatt finnAnsattMedId(int id) {
        String sql = "SELECT * FROM oblig.Ansatt WHERE ansatt_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetTilAnsatt(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Feil ved søk på ansatt-ID: " + e.getMessage());
        }
        return null;
    }

    public Ansatt finnAnsattMedBrukernavn(String brukernavn) {
        String sql = "SELECT * FROM oblig.Ansatt WHERE brukernavn = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, brukernavn);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetTilAnsatt(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Feil ved søk på brukernavn: " + e.getMessage());
        }
        return null;
    }

    public List<Ansatt> hentAlleAnsatte() {
        List<Ansatt> ansatte = new ArrayList<>();
        String sql = "SELECT * FROM oblig.Ansatt ORDER BY ansatt_id";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ansatte.add(mapResultSetTilAnsatt(rs));
            }
        } catch (SQLException e) {
            System.err.println("Feil ved henting av alle ansatte: " + e.getMessage());
        }
        return ansatte;
    }

    public boolean oppdaterStillingOgLonn(int ansattId, String nyStilling, int nyLonn) {
        if (nyStilling.isEmpty() && nyLonn == 0) {
            System.out.println("Ingen endringer å utføre.");
            return false;
        }

        StringBuilder sql = new StringBuilder("UPDATE oblig.Ansatt SET ");
        boolean needsComma = false;

        if (!nyStilling.isEmpty()) {
            sql.append("stilling = ?");
            needsComma = true;
        }
        
        if (nyLonn != 0) {
            if (needsComma) sql.append(", ");
            sql.append("maanedslonn = ?");
        }
        
        sql.append(" WHERE ansatt_id = ?");

        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            
            if (!nyStilling.isEmpty()) {
                stmt.setString(paramIndex++, nyStilling);
            }
            
            if (nyLonn != 0) {
                stmt.setInt(paramIndex++, nyLonn);
            }
            
            stmt.setInt(paramIndex, ansattId);
            
            int rowsAffected = stmt.executeUpdate();
            connection.commit();
            return rowsAffected > 0;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.err.println("Feil ved rollback: " + ex.getMessage());
            }
            System.err.println("Feil ved oppdatering: " + e.getMessage());
            return false;
        }
    }

    public boolean leggTilAnsatt(Ansatt ansatt) {
        String sql = "INSERT INTO oblig.Ansatt (brukernavn, fornavn, etternavn, dato_ansatt, stilling, maanedslonn, avdeling_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, ansatt.getBrukernavn());
            stmt.setString(2, ansatt.getFornavn());
            stmt.setString(3, ansatt.getEtternavn());
            stmt.setDate(4, ansatt.getDatoAnsatt());
            stmt.setString(5, ansatt.getStilling());
            stmt.setInt(6, ansatt.getMaanedslonn());
            stmt.setInt(7, ansatt.getAvdelingId());
            
            int rowsAffected = stmt.executeUpdate();
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    ansatt.setAnsattId(generatedKeys.getInt(1));
                }
            }
            
            connection.commit();
            return rowsAffected > 0;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.err.println("Feil ved rollback: " + ex.getMessage());
            }
            System.err.println("Feil ved innsetting: " + e.getMessage());
            return false;
        }
    }

    private Ansatt mapResultSetTilAnsatt(ResultSet rs) throws SQLException {
        return new Ansatt(
                rs.getInt("ansatt_id"),
                rs.getString("brukernavn"),
                rs.getString("fornavn"),
                rs.getString("etternavn"),
                rs.getDate("dato_ansatt"),
                rs.getString("stilling"),
                rs.getInt("maanedslonn"),
                rs.getInt("avdeling_id")
        );
    }
}