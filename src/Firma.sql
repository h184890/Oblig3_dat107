-- Opprett nytt schema
CREATE SCHEMA IF NOT EXISTS oblig;
SET search_path TO oblig;

-- Opprett tabellen Avdeling
CREATE TABLE Avdeling (
    avdeling_id SERIAL PRIMARY KEY,
    navn VARCHAR(100) NOT NULL,
    sjef_id INTEGER UNIQUE -- Skal referere til en ansatt
);

-- Opprett tabellen Ansatt
CREATE TABLE Ansatt (
    ansatt_id SERIAL PRIMARY KEY,
    brukernavn VARCHAR(4) UNIQUE NOT NULL CHECK (LENGTH(brukernavn) BETWEEN 3 AND 4),
    fornavn VARCHAR(50) NOT NULL,
    etternavn VARCHAR(50) NOT NULL,
    dato_ansatt DATE NOT NULL,
    stilling VARCHAR(100) NOT NULL,
    maanedslonn INTEGER NOT NULL CHECK (maanedslonn > 0),
    avdeling_id INTEGER NOT NULL,
    FOREIGN KEY (avdeling_id) REFERENCES Avdeling(avdeling_id) ON DELETE RESTRICT
);

-- Legg til fremmednøkkel for sjef i Avdeling
ALTER TABLE Avdeling ADD CONSTRAINT fk_sjef FOREIGN KEY (sjef_id) REFERENCES Ansatt(ansatt_id) ON DELETE RESTRICT;

-- Opprett tabellen Prosjekt
CREATE TABLE Prosjekt (
    prosjekt_id SERIAL PRIMARY KEY,
    navn VARCHAR(100) NOT NULL,
    beskrivelse TEXT
);

-- Opprett koblingstabell mellom Ansatt og Prosjekt
CREATE TABLE Ansatt_Prosjekt (
    ansatt_id INTEGER NOT NULL,
    prosjekt_id INTEGER NOT NULL,
    rolle VARCHAR(100) NOT NULL,
    timer INTEGER NOT NULL CHECK (timer >= 0),
    PRIMARY KEY (ansatt_id, prosjekt_id),
    FOREIGN KEY (ansatt_id) REFERENCES Ansatt(ansatt_id) ON DELETE RESTRICT,
    FOREIGN KEY (prosjekt_id) REFERENCES Prosjekt(prosjekt_id) ON DELETE RESTRICT
);
