DROP DATABASE IF EXISTS Amv;
CREATE DATABASE Amv;
USE Amv;

CREATE OR REPLACE TABLE Kurs
(
    KursID   SMALLINT           NOT NULL AUTO_INCREMENT PRIMARY KEY,
    KursNavn VARCHAR(50) UNIQUE NOT NULL
);

CREATE OR REPLACE TABLE Ansatt
(
    AnsattID         SMALLINT           NOT NULL AUTO_INCREMENT PRIMARY KEY,
    AnsattMail       VARCHAR(50) UNIQUE NOT NULL,
    AnsattTelefon    VARCHAR(8) UNIQUE  NOT NULL,
    AnsattOrganisert BOOLEAN            NOT NULL,
    AnsattAdmin      BOOLEAN            NOT NULL

);

CREATE OR REPLACE TABLE Sertifikat
(
    KursID   SMALLINT,
    AnsattID SMALLINT,
    PRIMARY KEY (KursID, AnsattID),
    FOREIGN KEY (KursID) REFERENCES Kurs (KursID),
    FOREIGN KEY (AnsattID) REFERENCES Ansatt (AnsattID)
);

CREATE OR REPLACE TABLE Verktoy
(
    VerktoyID          SMALLINT           NOT NULL AUTO_INCREMENT PRIMARY KEY,
    VerktoyNavn        VARCHAR(50) UNIQUE NOT NULL,
    VerktoyType        VARCHAR(50)        NOT NULL,
    VerktoyBeskrivelse VARCHAR(50) DEFAULT 'Mangler beskrivelse',
    VerktoySkadet      BOOLEAN            NOT NULL,
    VerktoyLedig       BOOLEAN            NOT NULL
);

CREATE OR REPLACE TABLE Bestilling
(
    BestillingID    SMALLINT  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    BestillingStart TIMESTAMP NOT NULL,
    BestillingSlutt TIMESTAMP NOT NULL,
    BetalingStatus  BOOLEAN   NOT NULL,
    AnsattID        SMALLINT,
    FOREIGN KEY (AnsattID) REFERENCES Ansatt (AnsattID)
);

CREATE OR REPLACE TABLE BestillingLinje
(
    BestillingLinjeID SMALLINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    BestillingID      SMALLINT NOT NULL,
    VerktoyID         SMALLINT NOT NULL,
    FOREIGN KEY (BestillingID) REFERENCES Bestilling (BestillingID),
    FOREIGN KEY (VerktoyID) REFERENCES Verktoy (VerktoyID)
);


INSERT INTO Kurs (KursNavn)
VALUES ('Kurs1'),
       ('Kurs2'),
       ('Kurs3'),
       ('Sertifikat1'),
       ('Sertifikat2');

INSERT INTO Ansatt (AnsattMail, AnsattTelefon, AnsattOrganisert, AnsattAdmin)
VALUES ('abdi@hotmail.no', '97782133', false, true),
       ('kenneth@hotmail.com', '79982355', false, false),
       ('henrik@outlook.com', '98756321', true, false),
       ('markus@gmail.com', '58965412', false, false),
       ('hanne@wihabu.net', '98856200', false, false),
       ('jenny@skavlan.no', '95020800', false, false),
       ('JohnArne@Riise.no', '90090090', false, false),
       ('Bill.Gates@microsoft.com', '12345678', true, false),
       ('jostein@amv.no', '97719800', false, false),
       ('henke@spacemonkymail.no', '97719300', true, false);

INSERT INTO Sertifikat(KursID, AnsattID)
VALUES (1, 1),
       (1, 2);

INSERT INTO Verktoy (VerktoyNavn, VerktoyType, VerktoyBeskrivelse, VerktoySkadet, VerktoyLedig)
VALUES ('Eksternsliper', 'Småutstyr', DEFAULT, false, false),
       ('Vedkløyver', 'Vedhogst', DEFAULT, false, true),
       ('Tilhenger liten', 'Tilhengere', 'Kan frakte opp til x kilo', false, true),
       ('Tilhenger boggi', 'Tilhengere', 'Kan frakte opp til x kilo', false, true),
       ('Båndsliper', 'Småutstyr', DEFAULT, false, false),
       ('Spikerpistol liten', 'Spikerpistoler', DEFAULT, false, true),
       ('Spikerpistol stor', 'Spikerpistoler', DEFAULT, false, true),
       ('Skruautomat', 'Småutstyr', DEFAULT, false, false),
       ('Meislemaskin', 'Småutstyr', DEFAULT, false, true),
       ('Strømaggregat', 'Større utstyr', 'Dieseldrevet', false, true);

/*
 La alle bestillinger som pleide være default som måned 09. Må teste at spørringene fremdeles fungerer
 med det vi har skrevet i teksten. Mindre for oss å endre isåfall.
 */

INSERT INTO Bestilling (BestillingStart, BestillingSlutt, BetalingStatus, AnsattID)
VALUES ('2021-09-26 10:00:00', '2020-09-27 15:00:00', false, 1),
       ('2021-09-23 10:00:00', '2020-09-27 15:00:00', false, 2),
       ('2021-09-26 10:00:00', '2020-09-29 15:00:00', false, 1),
       ('2021-10-26 10:00:00', '2021-10-27 15:00:00', false, 3),
       ('2021-09-20 10:00:00', '2020-09-24 15:00:00', false, 6),
       ('2021-10-25 10:00:00', '2021-10-26 15:00:00', false, 3),
       ('2021-09-26 10:00:00', '2020-09-30 15:00:00', false, 7),
       ('2021-10-27 09:09:09', '2021-10-29 15:00:00', false, 3),
       ('2021-09-15 10:00:00', '2020-09-19 15:00:00', false, 9),
       ('2021-09-16 10:00:00', '2020-09-18 15:00:00', false, 5);

/*
 La til en ekstra bestillinglinje med samme bestillingid for å vise at vi kan ha flere verktøy i samme bestilling
 Gjenstår enda å teste at dette passer med spørringer.
 */

INSERT INTO BestillingLinje (BestillingID, VerktoyID)
VALUES (1, 1),
       (2, 4),
       (3, 7),
       (4, 2),
       (5, 2),
       (6, 8),
       (7, 5),
       (8, 6),
       (9, 10),
       (3, 2),
       (10, 1);

SELECT *
FROM Kurs
LIMIT 5;

SELECT *
FROM Ansatt
LIMIT 5;

SELECT *
FROM Bestilling
LIMIT 5;

SELECT *
FROM Verktoy
LIMIT 5;

SELECT *
FROM BestillingLinje
LIMIT 5;


SELECT VerktoyNavn, VerktoyType, VerktoyLedig
FROM Verktoy
WHERE VerktoyLedig = true;


DELETE
FROM Verktoy
WHERE VerktoyID = 1; /*feilkode*/

/*
 Spørring for hvilket verktøy som har flest bestillinger
 */
SELECT V.VerktoyNavn, COUNT(BestillingStart)
FROM Verktoy V
         INNER JOIN BestillingLinje L on V.VerktoyID = L.VerktoyID
         INNER JOIN Bestilling B on L.BestillingID = B.BestillingID
GROUP BY VerktoyNavn
ORDER BY COUNT(BestillingStart) DESC;


SELECT V.VerktoyNavn, V.VerktoyType, V.VerktoyLedig, A.AnsattMail
FROM Verktoy V
         INNER JOIN BestillingLinje L on V.VerktoyID = L.VerktoyID
         INNER JOIN Bestilling B on L.BestillingID = B.BestillingID
         INNER JOIN Ansatt A on B.AnsattID = A.AnsattID
WHERE V.VerktoyLedig = false;


SELECT VerktoyNavn, VerktoyType
FROM Verktoy;


SELECT V.VerktoyNavn, A.AnsattMail, B.BestillingSlutt
FROM BestillingLinje L
         INNER JOIN Bestilling B on L.BestillingID = B.BestillingID
         INNER JOIN Verktoy V on V.VerktoyID = L.VerktoyID
         INNER JOIN Ansatt A on B.AnsattID = A.AnsattID
WHERE CURRENT_TIMESTAMP > B.BestillingSlutt
  AND BestillingStart > TIMESTAMP '2021-10-24 10:00:00'
  AND VerktoyLedig = false;


SELECT *
FROM Verktoy
WHERE VerktoySkadet = false;

SELECT AnsattMail, KursNavn
FROM Sertifikat S
         INNER JOIN Ansatt A on S.AnsattID = A.AnsattID
         INNER JOIN Kurs K on S.KursID = K.KursID
WHERE S.KursID = 1;

/*
 oppgave 7.5, spørring etter verktøy leid med sub-query som spør etter ansatt med flest leide verktøy
 */
SELECT BestillingStart, VerktoyNavn, AnsattMail
FROM BestillingLinje L
         INNER JOIN Verktoy V on L.VerktoyID = V.VerktoyID
         INNER JOIN Bestilling B on L.BestillingID = B.BestillingID
         INNER JOIN Ansatt A on B.AnsattID = A.AnsattID
WHERE A.AnsattID = (
    SELECT AnsattID
    FROM Bestilling
    GROUP BY AnsattID
    ORDER BY COUNT(AnsattID)
            DESC LIMIT 1)
ORDER BY BestillingStart ASC;

/*
Finner ansatt med flest bestillinger
 */
SELECT A.AnsattMail, COUNT(BestillingID)
FROM Ansatt A
INNER JOIN Bestilling B on A.AnsattID = B.AnsattID
GROUP BY A.AnsattMail
ORDER BY COUNT(BestillingID) DESC LIMIT 3;
