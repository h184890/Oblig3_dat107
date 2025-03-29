package database;

import java.sql.Date;

public class Ansatt {
    private int ansattId;
    private String brukernavn;
    private String fornavn;
    private String etternavn;
    private Date datoAnsatt;
    private String stilling;
    private int maanedslonn;
    private int avdelingId;

    public Ansatt(int ansattId, String brukernavn, String fornavn, String etternavn, Date datoAnsatt, String stilling, int maanedslonn, int avdelingId) {
        this.ansattId = ansattId;
        this.brukernavn = brukernavn;
        this.fornavn = fornavn;
        this.etternavn = etternavn;
        this.datoAnsatt = datoAnsatt;
        this.stilling = stilling;
        this.maanedslonn = maanedslonn;
        this.avdelingId = avdelingId;
    }

    // Gettere og settere
    public int getAnsattId() { return ansattId; }
    public void setAnsattId(int ansattId) { this.ansattId = ansattId; }

    public String getBrukernavn() { return brukernavn; }
    public void setBrukernavn(String brukernavn) { this.brukernavn = brukernavn; }

    public String getFornavn() { return fornavn; }
    public void setFornavn(String fornavn) { this.fornavn = fornavn; }

    public String getEtternavn() { return etternavn; }
    public void setEtternavn(String etternavn) { this.etternavn = etternavn; }

    public Date getDatoAnsatt() { return datoAnsatt; }
    public void setDatoAnsatt(Date datoAnsatt) { this.datoAnsatt = datoAnsatt; }

    public String getStilling() { return stilling; }
    public void setStilling(String stilling) { this.stilling = stilling; }

    public int getMaanedslonn() { return maanedslonn; }
    public void setMaanedslonn(int maanedslonn) { this.maanedslonn = maanedslonn; }

    public int getAvdelingId() { return avdelingId; }
    public void setAvdelingId(int avdelingId) { this.avdelingId = avdelingId; }

    @Override
    public String toString() {
        return "Ansatt{" +
                "ansattId=" + ansattId +
                ", brukernavn='" + brukernavn + '\'' +
                ", fornavn='" + fornavn + '\'' +
                ", etternavn='" + etternavn + '\'' +
                ", datoAnsatt=" + datoAnsatt +
                ", stilling='" + stilling + '\'' +
                ", maanedslonn=" + maanedslonn +
                ", avdelingId=" + avdelingId +
                '}';
    }
}
