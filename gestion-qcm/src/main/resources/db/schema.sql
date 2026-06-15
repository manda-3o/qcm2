-- PostgreSQL schema for gestion_qcm

CREATE TABLE IF NOT EXISTS etudiants (
  num_etudiant VARCHAR(20) PRIMARY KEY,
  nom VARCHAR(100) NOT NULL,
  prenoms VARCHAR(100),
  niveau VARCHAR(10),
  adr_email VARCHAR(200),
  photo VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS questions (
  num_quest INTEGER PRIMARY KEY,
  question TEXT NOT NULL,
  categorie VARCHAR(100),
  reponse1 TEXT,
  reponse2 TEXT,
  reponse3 TEXT,
  reponse4 TEXT,
  bonne VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS examens (
  num_exam VARCHAR(50) PRIMARY KEY,
  num_etudiant VARCHAR(20) REFERENCES etudiants(num_etudiant),
  annee_univ VARCHAR(20),
  note INTEGER,
  duree INTEGER DEFAULT 0,
  statut VARCHAR(50) DEFAULT 'non démarré'
);

-- Sample data
INSERT INTO etudiants(num_etudiant, nom, prenoms, niveau, adr_email) VALUES
('E001','Rakoto','Jean','L2','jean.rakoto@univ.mg'),
('E002','Rabe','Marie','M1','marie.rabe@univ.mg'),
('E003','Andry','Paul','L1','paul.andry@univ.mg'),
('E004','Solo','Haja','L2','haja.solo@univ.mg'),
('E005','Tiana','Luc','M2','luc.tiana@univ.mg')
ON CONFLICT (num_etudiant) DO NOTHING;

INSERT INTO questions(num_quest, question, reponse1, reponse2, reponse3, reponse4, bonne) VALUES
(1,'Qu''est-ce qu''une variable ?','Un mot-clé','Un espace mémoire nommé','Une fonction','Un opérateur','reponse2'),
(2,'Quelle structure permet la répétition ?','if','switch','for','return','reponse3'),
(3,'HTML signifie ?','Hyper Text Markup Language','High Tech Modern Language','Hyper Transfer Method Link','Home Tool Markup Language','reponse1'),
(4,'Un tableau en Java est ?','Un objet','Une primitive','Une interface','Un fichier','reponse1'),
(5,'SQL est utilisé pour ?','Dessiner','Gérer des bases de données','Créer des images','Compiler du code','reponse2')
ON CONFLICT (num_quest) DO NOTHING;

INSERT INTO examens(num_exam, num_etudiant, annee_univ, note, duree, statut) VALUES
('X001','E001','2023-2024',7, 45, 'terminé'),
('X002','E002','2023-2024',9, 60, 'terminé'),
('X003','E003','2023-2024',5, 30, 'terminé'),
('X004','E004','2023-2024',8, 50, 'terminé'),
('X005','E005','2023-2024',10, 40, 'terminé')
ON CONFLICT (num_exam) DO NOTHING;
