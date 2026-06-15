GestionQCM — Version JSP / Servlet (Maven)

Pré-requis:

- Java 11+
- Maven
- Tomcat 10+ ou GlassFish 6+
- PostgreSQL

Étapes de configuration:

1. Construisez le projet et générez le WAR:

   mvn clean package

2. Créez la base PostgreSQL `gestion_qcm` et importez le schéma:

   psql -U postgres -c "CREATE DATABASE gestion_qcm;"
   psql -U postgres -d gestion_qcm -f src/main/resources/db/schema.sql

   Si votre utilisateur ou mot de passe sont différents:

   psql -h localhost -U <utilisateur> -d gestion_qcm -f src/main/resources/db/schema.sql

3. Configurez les variables d'environnement de connexion si nécessaire:
   - GESTION_QCM_DB_URL (par défaut `jdbc:postgresql://localhost:5432/gestion_qcm`)
   - GESTION_QCM_DB_USER (par défaut `postgres`)
   - GESTION_QCM_DB_PASS (par défaut `postgres`)

   Exemple Windows (PowerShell):

   $env:GESTION_QCM_DB_URL = "jdbc:postgresql://localhost:5432/gestion_qcm"
   $env:GESTION_QCM_DB_USER = "postgres"
   $env:GESTION_QCM_DB_PASS = "postgres"

   Ou utilisez le script de configuration Tomcat fourni :

   create-tomcat-setenv.bat C:\Users\hp\apache-tomcat-9.0.116 jdbc:postgresql://localhost:5432/gestion_qcm postgres postgres

4. Déploiement sur Tomcat:
   - Copiez `target/gestion-qcm.war` dans `TOMCAT_HOME/webapps/`.
   - Démarrez Tomcat avec `TOMCAT_HOME/bin/startup.bat`.
   - Ouvrez `http://localhost:8080/gestion-qcm/`

   Vous pouvez aussi utiliser le script fourni `deploy-tomcat.bat` après avoir défini `TOMCAT_HOME`.

5. Déploiement sur GlassFish:
   - Démarrez le domaine GlassFish avec `GLASSFISH_HOME/bin/asadmin.bat start-domain`.
   - Déployez le WAR avec `GLASSFISH_HOME/bin/asadmin.bat deploy --force --contextroot gestion-qcm target/gestion-qcm.war`.
   - Ouvrez `http://localhost:8080/gestion-qcm/`

   Le script `deploy-glassfish.bat` est fourni pour faciliter cette étape.

6. Tester l'application dans le navigateur:
   - Page d'accueil: `http://localhost:8080/gestion-qcm/`
   - Dashboard: `http://localhost:8080/gestion-qcm/dashboard`
   - Étudiants: `http://localhost:8080/gestion-qcm/etudiants`
   - QCM: `http://localhost:8080/gestion-qcm/qcm`
   - Examens: `http://localhost:8080/gestion-qcm/examens`

Informations complémentaires:

- Le code utilise JDBC via `com.gestionqcm.dao.DBConnection` et lit la configuration depuis les variables d'environnement.
- Le fichier `src/main/resources/db/schema.sql` contient le schéma et les données initiales.
- Le WAR généré se trouve dans `target/gestion-qcm.war`.

Conseils:

- Si Tomcat ne trouve pas les variables d'environnement, définissez-les dans `TOMCAT_HOME/bin/setenv.bat`.
- Pour GlassFish, utilisez `asadmin create-jvm-options -D...` si vous voulez conserver les variables dans le serveur.
