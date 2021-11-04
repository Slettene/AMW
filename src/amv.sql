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
    AnsattID      SMALLINT           NOT NULL AUTO_INCREMENT PRIMARY KEY,
    AnsattMail    VARCHAR(50) UNIQUE NOT NULL,
    AnsattTelefon VARCHAR(8) UNIQUE  NOT NULL,
    Organisert    BOOLEAN            NOT NULL
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
    BestillingStart TIMESTAMP NOT NULL DEFAULT '2021-10-26 10:00:00',
    BestillingSlutt TIMESTAMP NOT NULL DEFAULT '2021-10-30 10:00:00',
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

INSERT INTO Ansatt (AnsattMail, AnsattTelefon, Organisert)
VALUES ('abdi@hotmail.no', '97782133', false),
       ('kenneth@hotmail.com', '79982355', false),
       ('henrik@outlook.com', '98756321', true),
       ('markus@gmail.com', '58965412', false),
       ('hanne@wihabu.net', '98856200', false),
       ('jenny@skavlan.no', '95020800', false),
       ('JohnArne@Riise.no', '90090090', false),
       ('Bill.Gates@microsoft.com', '12345678', true),
       ('jostein@amv.no', '97719800', false),
       ('henke@spacemonkymail.no', '97719300', true);

INSERT INTO Sertifikat(KursID, AnsattID)
VALUES (1, 1),
       (1, 2);

INSERT INTO Verktoy (VerktoyNavn, VerktoyType, VerktoyBeskrivelse, VerktoySkadet, VerktoyLedig)
VALUES ('Eksternsliper', 'Småutstyr', DEFAULT, false, false),
       ('Vedkløyver', 'Vedhogst', DEFAULT, false, true),
       ('Tilhenger liten', 'Tilhengere', 'Kan frakte opp til x kilo', false, true),
       ('Tilhenger, boggi', 'Tilhengere', 'Kan frakte opp til x kilo', false, true),
       ('Båndsliper', 'Småutstyr', DEFAULT, false, false),
       ('Spikerpistol liten', 'Spikerpistoler', DEFAULT, false, true),
       ('Spikerpistol stor', 'Spikerpistoler', DEFAULT, false, true),
       ('Skruautomat', 'Småutstyr', DEFAULT, false, false),
       ('Meislemaskin', 'Småutstyr', DEFAULT, false, true),
       ('Strømaggregat', 'Større utstyr', 'Dieseldrevet', false, true);

INSERT INTO Bestilling (BestillingStart, BestillingSlutt, BetalingStatus, AnsattID)
VALUES (DEFAULT, DEFAULT, false, 1),
       (DEFAULT, DEFAULT, false, 2),
       (DEFAULT, DEFAULT, false, 1),
       ('2021-10-26 10:00:00', '2021-10-27 10:00:00', false, 3),
       (DEFAULT, DEFAULT, false, 6),
       ('2021-10-25 10:00:00', '2021-10-26 10:00:00', false, 3),
       (DEFAULT, DEFAULT, false, 7),
       ('2021-10-27 09:09:09', '2021-10-29 10:00:00', false, 3),
       (DEFAULT, DEFAULT, false, 9),
       (DEFAULT, DEFAULT, false, 5);

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


SELECT VerktoyNavn
FROM Verktoy
WHERE VerktoyLedig = TRUE;


DELETE
FROM Verktoy
WHERE VerktoyID = 1; /*feilkode*/

SELECT V.VerktoyNavn, COUNT(BestillingStart)
FROM Verktoy V
         INNER JOIN BestillingLinje L on V.VerktoyID = L.VerktoyID
         INNER JOIN Bestilling B on L.BestillingID = B.BestillingID
GROUP BY VerktoyNavn
ORDER BY COUNT(BestillingStart) DESC;

SELECT V.VerktoyNavn
FROM Verktoy V
         INNER JOIN BestillingLinje L ON V.VerktoyID = L.VerktoyID
         INNER JOIN Bestilling B on L.BestillingID = B.BestillingID
GROUP BY VerktoyNavn
ORDER BY COUNT(BestillingStart) DESC;

SELECT V.VerktoyNavn, V.VerktoyType, A.AnsattMail
FROM Verktoy V
         INNER JOIN BestillingLinje L on V.VerktoyID = L.VerktoyID
         INNER JOIN Bestilling B on L.BestillingID = B.BestillingID
         INNER JOIN Ansatt A on B.AnsattID = A.AnsattID
WHERE VerktoyLedig = FALSE;

SELECT VerktoyNavn, VerktoyType
FROM Verktoy;

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

SELECT B.BestillingStart, V.VerktoyNavn, A.AnsattMail
FROM BestillingLinje L
         INNER JOIN Bestilling B on L.BestillingID = B.BestillingID
         INNER JOIN Verktoy V on V.VerktoyID = L.VerktoyID
         INNER JOIN Ansatt A on B.AnsattID = A.AnsattID
WHERE A.AnsattMail = (
    SELECT AnsattID
    FROM Bestilling
    GROUP BY AnsattID
    ORDER BY COUNT(AnsattID)
            DESC
    LIMIT 1)
ORDER BY BestillingStart;

SELECT KursNavn, AnsattMail
FROM Sertifikat S
         INNER JOIN Ansatt A on S.AnsattID = A.AnsattID
         INNER JOIN Kurs K on S.KursID = K.KursID
WHERE S.KursID = 1;

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
            DESC
    LIMIT 1)
ORDER BY BestillingStart DESC;

SELECT BestillingStart, VerktoyNavn, AnsattMail
FROM Verktoy V
         INNER JOIN BestillingLinje L on V.VerktoyID = L.VerktoyID
         INNER JOIN Bestilling B on L.BestillingID = B.BestillingID
         INNER JOIN Ansatt A on B.AnsattID = A.AnsattID
WHERE AnsattMail = 'henrik@outlook.com'
ORDER BY BestillingStart;