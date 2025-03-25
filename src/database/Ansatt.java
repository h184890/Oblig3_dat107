package database;

import jakarta.persistence.*;

@Entity
@Table(name = "Ansatt", schema = "oving_jpa")
public class Ansatt {

    @Id
    private String brukernavn;

    private String fornavn;
    private String etternavn;
    private String stilling;
    private int maanedslonn;

    // Konstruktører
    public Ansatt() {}

    public Ansatt(String brukernavn, String fornavn, String etternavn, String stilling, int maanedslonn) {
        this.brukernavn = brukernavn;
        this.fornavn = fornavn;
        this.etternavn = etternavn;
        this.stilling = stilling;
        this.maanedslonn = maanedslonn;
    }

    // Gettere og settere
    public String getBrukernavn() { return brukernavn; }
    public void setBrukernavn(String brukernavn) { this.brukernavn = brukernavn; }
    public String getFornavn() { return fornavn; }
    public void setFornavn(String fornavn) { this.fornavn = fornavn; }
    public String getEtternavn() { return etternavn; }
    public void setEtternavn(String etternavn) { this.etternavn = etternavn; }
    public String getStilling() { return stilling; }
    public void setStilling(String stilling) { this.stilling = stilling; }
    public int getMaanedslonn() { return maanedslonn; }
    public void setMaanedslonn(int maanedslonn) { this.maanedslonn = maanedslonn; }

    @Override
    public String toString() {
        return String.format("Ansatt{brukernavn='%s', fornavn='%s', etternavn='%s', stilling='%s', maanedslonn=%d}",
                brukernavn, fornavn, etternavn, stilling, maanedslonn);
    }
}
