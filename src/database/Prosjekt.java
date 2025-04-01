package database;

import java.sql.Date;

public class Prosjekt {
    private int prosjektId;
    private String navn;
    private String beskrivelse;
    private Date startDato;
    private String status;

    public Prosjekt(int prosjektId, String navn, String beskrivelse) {
        this.prosjektId = prosjektId;
        this.navn = navn;
        this.beskrivelse = beskrivelse;
        this.startDato = startDato;
        this.status = status;
    }

    // Gettere og settere
    public int getProsjektId() { return prosjektId; }
    public void setProsjektId(int prosjektId) { this.prosjektId = prosjektId; }
    public String getNavn() { return navn; }
    public void setNavn(String navn) { this.navn = navn; }
    public String getBeskrivelse() { return beskrivelse; }
    public void setBeskrivelse(String beskrivelse) { this.beskrivelse = beskrivelse; }
    public Date getStartDato() { return startDato; }
    public void setStartDato(Date startDato) { this.startDato = startDato; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Prosjekt{" +
                "prosjektId=" + prosjektId +
                ", navn='" + navn + '\'' +
                ", beskrivelse='" + beskrivelse + '\'' +
                '}';
    }
}
