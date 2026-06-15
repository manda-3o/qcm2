@echo off
setlocal
if "%TOMCAT_HOME%"=="" (
  echo ERREUR: la variable d'environnement TOMCAT_HOME n'est pas définie.
  echo Exemple: set TOMCAT_HOME=C:\apache-tomcat-10.1.XX
  goto end
)
if not exist "%TOMCAT_HOME%\bin\catalina.bat" (
  echo ERREUR: %TOMCAT_HOME% ne semble pas être un répertoire Tomcat valide.
  goto end
)
if not exist "target\gestion-qcm.war" (
  echo ERREUR: le WAR cible est introuvable. Executez: mvn clean package
  goto end
)
copy /Y "target\gestion-qcm.war" "%TOMCAT_HOME%\webapps\"
echo WAR deployee dans %TOMCAT_HOME%\webapps
echo Lancez Tomcat avec: %TOMCAT_HOME%\bin\startup.bat
:end
endlocal
