DROP DATABASE IF EXISTS Amv;
CREATE DATABASE Amv;
USE Amv;

CREATE OR REPLACE TABLE Ansatte (
    AnsattID SMALLINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    AMail VARCHAR(50) UNIQUE NOT NULL,
    ATelefon VARCHAR(8) UNIQUE NOT NULL,
    Organisert BIT NOT NULL
);

CREATE OR REPLACE TABLE Verktoy (
    VerktoyID SMALLINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    VerktoyNavn VARCHAR(50) UNIQUE NOT NULL,
    VerktoyBeskrivelse VARCHAR(50) DEFAULT 'x'
);

CREATE OR REPLACE TABLE Bestilling (
    BestillingID SMALLINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    BestillingTidspunkt timestamp NOT NULL,
    BetalingStatus BIT NOT NULL,
    AnsattID SMALLINT,
    FOREIGN KEY (AnsattID) REFERENCES Ansatte(AnsattID)
);

CREATE OR REPLACE TABLE BestillingLinje (
    BestillingLinjeID SMALLINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    BestillingLengdeDager integer NOT NULL check (BestillingLengdeDager between 1 and 4),
    VerktoyStatus BIT NOT NULL,
    BestillingID SMALLINT NOT NULL,
    VerktoyID SMALLINT NOT NULL,
    FOREIGN KEY (BestillingID) REFERENCES Bestilling(BestillingID),
    FOREIGN KEY (VerktoyID) REFERENCES Verktoy(VerktoyID)
);

INSERT INTO Ansatte (AMail, ATelefon, Organisert)
    VALUES
           ('abdi@hotmail.no', '97782133', 0),
           ('kenneth@hotmail.com', '79982355', 0),
           ('henrik@outlook.com', '98756321', 1),
           ('markus@gmail.com', '58965412', 0),
           ('hanne@wihabu.net', '98856200', 0),
           ('jenny@skavlan.no', '95020800', 0),
           ('JohnArne@Riise.no', '90090090', 0),
           ('Bill.Gates@microsoft.com', '12345678', 1),
           ('jostein@amv.no', '97719800', 0),
           ('henke@spacemonkymail.no', '97719300', 1);

INSERT INTO Verktoy (VerktoyNavn, VerktoyBeskrivelse)
VALUES
           ('Drill', DEFAULT),
           ('Vedkløyver', DEFAULT),
           ('Tilhenger liten', 'Kan frakte opp til x kilo'),
           ('Tilhenger stor', 'Kan frakte opp til x kilo'),
           ('Båndsliper', DEFAULT),
           ('Spikerpistol liten', DEFAULT),
           ('Spikerpistol stor', DEFAULT),
           ('Skruautomat', DEFAULT),
           ('Meislemaskin', DEFAULT),
           ('Kantklipper', 'Bensindrevet');
