package database;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AnsattDAO ansattDAO = new AnsattDAO();
        
        while (true) {
            System.out.println("\nMeny:");
            System.out.println("1. Søk etter ansatt på ansatt-ID");
            System.out.println("2. Søk etter ansatt på brukernavn");
            System.out.println("3. Vis alle ansatte");
            System.out.println("4. Oppdater stilling/lønn for en ansatt");
            System.out.println("5. Legg til ny ansatt");
            System.out.println("6. Avslutt");
            System.out.print("Velg et alternativ: ");
            
            int valg = scanner.nextInt();
            scanner.nextLine(); // Konsumere linjeskift
            
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
                    System.out.println("Legg til ny ansatt:");
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
                    System.out.print("Avdeling-ID: ");
                    int nyAvdelingId = scanner.nextInt();
                    scanner.nextLine();
                    
                    // Bruker dagens dato som ansettelsesdato
                    Ansatt nyAnsatt = new Ansatt(0, nyttBrukernavn, nyttFornavn, nyttEtternavn, 
                                                new Date(System.currentTimeMillis()), 
                                                nyStillingNy, nyMaanedslonn, nyAvdelingId);
                    ansattDAO.leggTilAnsatt(nyAnsatt);
                    System.out.println("Ny ansatt lagt til med ID: " + nyAnsatt.getAnsattId());
                    break;
                case 6:
                    System.out.println("Avslutter programmet...");
                    scanner.close();
                    ansattDAO.lukkForbindelse();
                    return;
                default:
                    System.out.println("Ugyldig valg. Prøv igjen.");
            }
        }
    }
}