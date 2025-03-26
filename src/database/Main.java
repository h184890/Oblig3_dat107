package database;

import java.util.Scanner;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("personPersistenceUnit");
        EntityManager em = emf.createEntityManager();
        Scanner scanner = new Scanner(System.in);

        try {
            while (true) {
                System.out.print("Skriv inn brukernavn (eller 'exit' for å avslutte): ");
                String brukernavn = scanner.nextLine();
                if (brukernavn.equalsIgnoreCase("exit")) break;

                Ansatt ansatt = em.find(Ansatt.class, brukernavn);
                if (ansatt != null) {
                    System.out.println("Funnet ansatt: " + ansatt);
                } else {
                    System.out.println("Ingen ansatt funnet med brukernavn: " + brukernavn);
                }
            }
        } finally {
            em.close();
            emf.close();
            scanner.close();
        }
    }
}