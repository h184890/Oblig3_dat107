-- Opprett et nytt schema for øvingen
CREATE SCHEMA IF NOT EXISTS oving_jpa;

-- Velg schema
SET search_path TO oving_jpa;

-- Opprett tabellen Ansatt
CREATE TABLE Ansatt (
    brukernavn VARCHAR(50) PRIMARY KEY,
    fornavn VARCHAR(50),
    etternavn VARCHAR(50),
    stilling VARCHAR(100),
    maanedslonn INTEGER
);

-- Sett inn et par test-rader
INSERT INTO Ansatt (brukernavn, fornavn, etternavn, stilling, maanedslonn)
VALUES 
    ('jdoe', 'John', 'Doe', 'Utvikler', 55000),
    ('asmith', 'Anna', 'Smith', 'Prosjektleder', 75000);
