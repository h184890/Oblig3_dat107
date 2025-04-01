package database;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

	public class Main {
	    public static void main(String[] args) {
	        Scanner scanner = new Scanner(System.in);
	        AnsattDAO ansattDAO = new AnsattDAO();
	        AvdelingDAO avdelingDAO = new AvdelingDAO();
	        ProsjektDAO prosjektDAO = new ProsjektDAO();
	        AnsattProsjektDAO ansattProsjektDAO = new AnsattProsjektDAO();
	        
	        while (true) {
	            System.out.println("\nHovedmeny:");
	            System.out.println("1. Ansatt-relaterte operasjoner");
	            System.out.println("2. Avdeling-relaterte operasjoner");
	            System.out.println("3. Prosjekt-relaterte operasjoner");
	            System.out.println("4. Avslutt");
	            System.out.print("Velg et alternativ: ");
	            
	            int hovedvalg = scanner.nextInt();
	            scanner.nextLine();
	            
	            switch (hovedvalg) {
	                case 1:
	                    ansattMeny(scanner, ansattDAO, avdelingDAO);
	                    break;
	                case 2:
	                    avdelingMeny(scanner, ansattDAO, avdelingDAO);
	                    break;
	                case 3:
	                    prosjektMeny(scanner, ansattDAO, prosjektDAO, ansattProsjektDAO);
	                    break;
	                case 4:
	                    System.out.println("Avslutter programmet...");
	                    ansattDAO.lukkForbindelse();
	                    avdelingDAO.lukkForbindelse();
	                    prosjektDAO.lukkForbindelse();
	                    ansattProsjektDAO.lukkForbindelse();
	                    scanner.close();
	                    return;
	                default:
	                    System.out.println("Ugyldig valg. Prøv igjen.");
	            }
	        }
	    }

	    private static void ansattMeny(Scanner scanner, AnsattDAO ansattDAO, AvdelingDAO avdelingDAO) {
	        while (true) {
	            System.out.println("\nAnsattmeny:");
	            System.out.println("1. Søk etter ansatt på ansatt-ID");
	            System.out.println("2. Søk etter ansatt på brukernavn");
	            System.out.println("3. Vis alle ansatte");
	            System.out.println("4. Oppdater stilling/lønn for en ansatt");
	            System.out.println("5. Legg til ny ansatt");
	            System.out.println("6. Endre avdeling for ansatt");
	            System.out.println("7. Tilbake til hovedmeny");
	            System.out.print("Velg et alternativ: ");
	            
	            int valg = scanner.nextInt();
	            scanner.nextLine();
	            
	            switch (valg) {
	                case 1:
	                    System.out.print("Skriv inn ansatt-ID: ");
	                    int id = scanner.nextInt();
	                    scanner.nextLine();
	                    Ansatt ansatt = ansattDAO.finnAnsattMedId(id);
	                    if (ansatt != null) {
	                        System.out.println(ansatt);
	                    } else {
	                        System.out.println("Ingen ansatt funnet med ID " + id);
	                    }
	                    break;
	                case 2:
	                    System.out.print("Skriv inn brukernavn: ");
	                    String brukernavn = scanner.nextLine();
	                    Ansatt ansattBrukernavn = ansattDAO.finnAnsattMedBrukernavn(brukernavn);
	                    if (ansattBrukernavn != null) {
	                        System.out.println(ansattBrukernavn);
	                    } else {
	                        System.out.println("Ingen ansatt funnet med brukernavn " + brukernavn);
	                    }
	                    break;
	                case 3:
	                    List<Ansatt> ansatte = ansattDAO.hentAlleAnsatte();
	                    if (ansatte.isEmpty()) {
	                        System.out.println("Ingen ansatte funnet i databasen.");
	                    } else {
	                        for (Ansatt a : ansatte) {
	                            System.out.println(a);
	                        }
	                    }
	                    break;
	                case 4:
	                    System.out.print("Skriv inn ansatt-ID: ");
	                    int oppdaterId = scanner.nextInt();
	                    scanner.nextLine();
	                    System.out.print("Ny stilling (trykk enter for å beholde nåværende): ");
	                    String nyStilling = scanner.nextLine();
	                    System.out.print("Ny lønn (skriv 0 for å beholde nåværende): ");
	                    int nyLonn = scanner.nextInt();
	                    scanner.nextLine();
	                    
	                    ansattDAO.oppdaterStillingOgLonn(oppdaterId, nyStilling, nyLonn);
	                    System.out.println("Oppdatering utført.");
	                    break;
	                case 5:
	                    System.out.println("Tilgjengelige avdelinger:");
	                    List<Avdeling> avdelinger = avdelingDAO.hentAlleAvdelinger();
	                    for (Avdeling avd : avdelinger) {
	                        System.out.println(avd.getAvdelingId() + ": " + avd.getNavn());
	                    }
	                    
	                    System.out.println("\nLegg til ny ansatt:");
	                    System.out.print("Brukernavn (3-4 tegn): ");
	                    String nyttBrukernavn = scanner.nextLine();
	                    System.out.print("Fornavn: ");
	                    String nyttFornavn = scanner.nextLine();
	                    System.out.print("Etternavn: ");
	                    String nyttEtternavn = scanner.nextLine();
	                    System.out.print("Stilling: ");
	                    String nyStillingNy = scanner.nextLine();
	                    System.out.print("Månedslønn: ");
	                    int nyMaanedslonn = scanner.nextInt();
	                    System.out.print("Avdeling-ID (fra listen over): ");
	                    int nyAvdelingId = scanner.nextInt();
	                    scanner.nextLine();
	                    
	                    Ansatt nyAnsatt = new Ansatt(0, nyttBrukernavn, nyttFornavn, nyttEtternavn, 
	                                                new Date(System.currentTimeMillis()), 
	                                                nyStillingNy, nyMaanedslonn, nyAvdelingId);
	                    if (ansattDAO.leggTilAnsatt(nyAnsatt)) {
	                        System.out.println("Ny ansatt lagt til med ID: " + nyAnsatt.getAnsattId());
	                    } else {
	                        System.out.println("Kunne ikke legge til ny ansatt.");
	                    }
	                    break;
	                case 6:
	                    System.out.print("Skriv inn ansatt-ID: ");
	                    int ansattId = scanner.nextInt();
	                    scanner.nextLine();
	                    
	                    System.out.println("Tilgjengelige avdelinger:");
	                    List<Avdeling> alleAvdelinger = avdelingDAO.hentAlleAvdelinger();
	                    for (Avdeling avd : alleAvdelinger) {
	                        System.out.println(avd.getAvdelingId() + ": " + avd.getNavn());
	                    }
	                    
	                    System.out.print("Ny avdeling-ID: ");
	                    int nyAvdeling = scanner.nextInt();
	                    scanner.nextLine();
	                    
	                    if (ansattDAO.oppdaterAvdeling(ansattId, nyAvdeling)) {
	                        System.out.println("Avdeling oppdatert.");
	                    } else {
	                        System.out.println("Kunne ikke oppdatere avdeling. Er ansatten sjef for en avdeling?");
	                    }
	                    break;
	                case 7:
	                    return;
	                default:
	                    System.out.println("Ugyldig valg. Prøv igjen.");
	            }
	        }
	    }

	    private static void avdelingMeny(Scanner scanner, AnsattDAO ansattDAO, AvdelingDAO avdelingDAO) {
	        while (true) {
	            System.out.println("\nAvdelingmeny:");
	            System.out.println("1. Søk etter avdeling på ID");
	            System.out.println("2. Vis alle avdelinger");
	            System.out.println("3. Vis alle ansatte på en avdeling");
	            System.out.println("4. Legg til ny avdeling");
	            System.out.println("5. Tilbake til hovedmeny");
	            System.out.print("Velg et alternativ: ");
	            
	            int valg = scanner.nextInt();
	            scanner.nextLine();
	            
	            switch (valg) {
	                case 1:
	                    System.out.print("Skriv inn avdeling-ID: ");
	                    int id = scanner.nextInt();
	                    scanner.nextLine();
	                    Avdeling avdeling = avdelingDAO.finnAvdelingMedId(id);
	                    if (avdeling != null) {
	                        System.out.println(avdeling);
	                    } else {
	                        System.out.println("Ingen avdeling funnet med ID " + id);
	                    }
	                    break;
	                case 2:
	                    List<Avdeling> avdelinger = avdelingDAO.hentAlleAvdelinger();
	                    if (avdelinger.isEmpty()) {
	                        System.out.println("Ingen avdelinger funnet i databasen.");
	                    } else {
	                        for (Avdeling a : avdelinger) {
	                            System.out.println(a);
	                        }
	                    }
	                    break;
	                case 3:
	                    System.out.print("Skriv inn avdeling-ID: ");
	                    int avdelingId = scanner.nextInt();
	                    scanner.nextLine();
	                    
	                    Avdeling valgtAvdeling = avdelingDAO.finnAvdelingMedId(avdelingId);
	                    if (valgtAvdeling == null) {
	                        System.out.println("Ingen avdeling med ID " + avdelingId);
	                        break;
	                    }
	                    
	                    List<Ansatt> ansatte = ansattDAO.hentAnsatteForAvdeling(avdelingId);
	                    if (ansatte.isEmpty()) {
	                        System.out.println("Ingen ansatte i avdeling " + avdelingId);
	                    } else {
	                        System.out.println("\nAnsatte i avdeling " + valgtAvdeling.getNavn() + ":");
	                        for (Ansatt a : ansatte) {
	                            String sjefMarkering = (a.getAnsattId() == valgtAvdeling.getSjefId()) ? " (Sjef)" : "";
	                            System.out.println(a + sjefMarkering);
	                        }
	                    }
	                    break;
	                case 4:
	                    System.out.println("Tilgjengelige ansatte som kan bli sjefer:");
	                    List<Ansatt> potensielleSjefer = ansattDAO.hentAlleAnsatte();
	                    for (Ansatt a : potensielleSjefer) {
	                        System.out.println(a.getAnsattId() + ": " + a.getFornavn() + " " + a.getEtternavn() + 
	                                         " (Avdeling " + a.getAvdelingId() + ")");
	                    }
	                    
	                    System.out.println("\nLegg til ny avdeling:");
	                    System.out.print("Avdelingsnavn: ");
	                    String navn = scanner.nextLine();
	                    System.out.print("Sjef-ID (fra listen over): ");
	                    int sjefId = scanner.nextInt();
	                    scanner.nextLine();
	                    
	                    if (avdelingDAO.leggTilAvdeling(navn, sjefId)) {
	                        System.out.println("Ny avdeling lagt til.");
	                    } else {
	                        System.out.println("Kunne ikke legge til ny avdeling.");
	                    }
	                    break;
	                case 5:
	                    return;
	                default:
	                    System.out.println("Ugyldig valg. Prøv igjen.");
	            }
	        }
	    }

	    private static void prosjektMeny(Scanner scanner, AnsattDAO ansattDAO, ProsjektDAO prosjektDAO, 
	                                    AnsattProsjektDAO ansattProsjektDAO) {
	        while (true) {
	            System.out.println("\nProsjektmeny:");
	            System.out.println("1. Vis alle prosjekter");
	            System.out.println("2. Legg til nytt prosjekt");
	            System.out.println("3. Registrer prosjektdeltagelse");
	            System.out.println("4. Føre timer for ansatt på prosjekt");
	            System.out.println("5. Vis prosjektinfo med deltagere");
	            System.out.println("6. Tilbake til hovedmeny");
	            System.out.print("Velg et alternativ: ");
	            
	            int valg = scanner.nextInt();
	            scanner.nextLine();
	            
	            switch (valg) {
	                case 1:
	                    List<Prosjekt> prosjekter = prosjektDAO.hentAlleProsjekter();
	                    if (prosjekter.isEmpty()) {
	                        System.out.println("Ingen prosjekter funnet i databasen.");
	                    } else {
	                        for (Prosjekt p : prosjekter) {
	                            System.out.println(p);
	                        }
	                    }
	                    break;
	                case 2:
	                    System.out.println("Legg til nytt prosjekt:");
	                    System.out.print("Navn: ");
	                    String navn = scanner.nextLine();
	                    System.out.print("Beskrivelse: ");
	                    String beskrivelse = scanner.nextLine();
	                    
	                    Prosjekt nyttProsjekt = new Prosjekt(0, navn, beskrivelse);
	                    if (prosjektDAO.leggTilProsjekt(nyttProsjekt)) {
	                        System.out.println("Nytt prosjekt lagt til med ID: " + nyttProsjekt.getProsjektId());
	                    } else {
	                        System.out.println("Kunne ikke legge til prosjekt.");
	                    }
	                    break;
	                case 3:
	                    System.out.println("Tilgjengelige ansatte:");
	                    List<Ansatt> ansatte = ansattDAO.hentAlleAnsatte();
	                    for (Ansatt a : ansatte) {
	                        System.out.println(a.getAnsattId() + ": " + a.getFornavn() + " " + a.getEtternavn());
	                    }
	                    
	                    System.out.println("\nTilgjengelige prosjekter:");
	                    List<Prosjekt> alleProsjekter = prosjektDAO.hentAlleProsjekter();
	                    for (Prosjekt p : alleProsjekter) {
	                        System.out.println(p.getProsjektId() + ": " + p.getNavn());
	                    }
	                    
	                    System.out.println("\nRegistrer prosjektdeltagelse:");
	                    System.out.print("Ansatt-ID: ");
	                    int ansattId = scanner.nextInt();
	                    System.out.print("Prosjekt-ID: ");
	                    int prosjektId = scanner.nextInt();
	                    scanner.nextLine();
	                    System.out.print("Rolle: ");
	                    String rolle = scanner.nextLine();
	                    
	                    if (ansattProsjektDAO.registrerProsjektdeltagelse(ansattId, prosjektId, rolle)) {
	                        System.out.println("Prosjektdeltagelse registrert.");
	                    } else {
	                        System.out.println("Kunne ikke registrere deltagelse.");
	                    }
	                    break;
	                case 4:
	                    System.out.println("Tilgjengelige prosjektdeltagelser:");
	                    List<ProsjektDeltakelse> deltagelser = ansattProsjektDAO.hentAlleDeltagelser();
	                    for (ProsjektDeltakelse pd : deltagelser) {
	                        System.out.println(pd.getAnsattProsjektId() + ": " + pd.getAnsattNavn() + 
	                                           " på prosjekt " + pd.getProsjektId() + 
	                                           " (" + pd.getRolle() + ")");
	                    }
	                    
	                    System.out.print("\nVelg deltagelse-ID: ");
	                    int deltagelseId = scanner.nextInt();
	                    System.out.print("Antall timer å registrere: ");
	                    int timer = scanner.nextInt();
	                    scanner.nextLine();
	                    
	                    if (ansattProsjektDAO.registrerTimer(deltagelseId, timer)) {
	                        System.out.println("Timer registrert.");
	                    } else {
	                        System.out.println("Kunne ikke registrere timer.");
	                    }
	                    break;
	                case 5:
	                    System.out.print("Skriv inn prosjekt-ID: ");
	                    int visProsjektId = scanner.nextInt();
	                    scanner.nextLine();
	                    
	                    Prosjekt prosjekt = prosjektDAO.finnProsjektMedId(visProsjektId);
	                    if (prosjekt == null) {
	                        System.out.println("Ingen prosjekt med ID " + visProsjektId);
	                        break;
	                    }
	                    
	                    System.out.println("\nProsjektinfo:");
	                    System.out.println(prosjekt);
	                    
	                    List<ProsjektDeltakelse> prosjektDeltagelser = 
	                        ansattProsjektDAO.hentDeltagelserForProsjekt(visProsjektId);
	                    
	                    if (prosjektDeltagelser.isEmpty()) {
	                        System.out.println("\nIngen deltagere på dette prosjektet.");
	                    } else {
	                        System.out.println("\nDeltagere:");
	                        int totalTimer = 0;
	                        for (ProsjektDeltakelse pd : prosjektDeltagelser) {
	                            System.out.println(pd.getAnsattNavn() + " - " + pd.getRolle() + 
	                                             " (" + pd.getTimer() + " timer)");
	                            totalTimer += pd.getTimer();
	                        }
	                        System.out.println("\nTotalt antall timer: " + totalTimer);
	                    }
	                    break;
	                case 6:
	                    return;
	                default:
	                    System.out.println("Ugyldig valg. Prøv igjen.");
	            }
	        }
	    }
	}