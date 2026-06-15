@echo off
setlocal
if "%GLASSFISH_HOME%"=="" (
  echo ERREUR: la variable d'environnement GLASSFISH_HOME n'est pas définie.
  echo Exemple: set GLASSFISH_HOME=C:\glassfish6
  goto end
)
if not exist "%GLASSFISH_HOME%\bin\asadmin.bat" (
  echo ERREUR: %GLASSFISH_HOME% ne semble pas être un répertoire GlassFish valide.
  goto end
)
if not exist "target\gestion-qcm.war" (
  echo ERREUR: le WAR cible est introuvable. Executez: mvn clean package
  goto end
)
"%GLASSFISH_HOME%\bin\asadmin.bat" deploy --force --contextroot gestion-qcm target\gestion-qcm.war
echo Deploy GlassFish termine.
:end
endlocal
