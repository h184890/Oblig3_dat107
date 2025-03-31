package database;

public class ProsjektDeltakelse {
    private int ansattProsjektId;
    private int ansattId;
    private String ansattNavn;
    private int prosjektId;
    private String prosjektNavn;  // Nytt felt
    private String rolle;
    private int timer;

    public ProsjektDeltakelse(int ansattProsjektId, int ansattId, String ansattNavn, 
                            int prosjektId, String prosjektNavn, String rolle, int timer) {
        this.ansattProsjektId = ansattProsjektId;
        this.ansattId = ansattId;
        this.ansattNavn = ansattNavn;
        this.prosjektId = prosjektId;
        this.prosjektNavn = prosjektNavn;
        this.rolle = rolle;
        this.timer = timer;
    }

    // Gettere
    public int getAnsattProsjektId() { return ansattProsjektId; }
    public int getAnsattId() { return ansattId; }
    public String getAnsattNavn() { return ansattNavn; }
    public int getProsjektId() { return prosjektId; }
    public String getProsjektNavn() { return prosjektNavn; }
    public String getRolle() { return rolle; }
    public int getTimer() { return timer; }

    @Override
    public String toString() {
        return String.format("Deltagelse ID: %d | Ansatt: %s (%d) | Prosjekt: %s (%d) | Rolle: %s | Timer: %d",
                ansattProsjektId, ansattNavn, ansattId, prosjektNavn, prosjektId, rolle, timer);
    }
}