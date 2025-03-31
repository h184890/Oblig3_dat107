package database;

import java.util.List;

public class Avdeling {
    private int avdelingId;
    private String navn;
    private int sjefId;
    private List<Ansatt> ansatte; // Liste over ansatte i avdelingen

    public Avdeling(int avdelingId, String navn, int sjefId) {
        this.avdelingId = avdelingId;
        this.navn = navn;
        this.sjefId = sjefId;
    }

    // Gettere og settere
    public int getAvdelingId() {
        return avdelingId;
    }

    public void setAvdelingId(int avdelingId) {
        this.avdelingId = avdelingId;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public int getSjefId() {
        return sjefId;
    }

    public void setSjefId(int sjefId) {
        this.sjefId = sjefId;
    }

    public List<Ansatt> getAnsatte() {
        return ansatte;
    }

    public void setAnsatte(List<Ansatt> ansatte) {
        this.ansatte = ansatte;
    }

    // Hjelpemetode for å finne sjefen
    public Ansatt finnSjef(List<Ansatt> alleAnsatte) {
        for (Ansatt ansatt : alleAnsatte) {
            if (ansatt.getAnsattId() == this.sjefId) {
                return ansatt;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return String.format("Avdeling ID: %d, Navn: %s, Sjef ID: %d", 
                            avdelingId, navn, sjefId);
    }

    // Alternativ toString med mer informasjon
    public String toStringMedAnsatte(List<Ansatt> alleAnsatte) {
        StringBuilder sb = new StringBuilder();
        sb.append(toString()).append("\nAnsatte:\n");
        
        Ansatt sjef = finnSjef(alleAnsatte);
        if (sjef != null) {
            sb.append(String.format("- %s %s (Sjef)\n", 
                                  sjef.getFornavn(), sjef.getEtternavn()));
        }
        
        if (ansatte != null) {
            for (Ansatt a : ansatte) {
                if (a.getAnsattId() != sjefId) { // Unngå å vise sjefen to ganger
                    sb.append(String.format("- %s %s\n", 
                                          a.getFornavn(), a.getEtternavn()));
                }
            }
        }
        return sb.toString();
    }
}
