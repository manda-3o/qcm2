@echo off
rem Génère un setenv.bat dans TOMCAT_HOME\bin pour configurer la connexion PostgreSQL.
setlocal

if "%~1"=="" (
  if not defined TOMCAT_HOME (
    echo ERREUR: TOMCAT_HOME n'est pas défini.
    echo Usage: %~nx0 ^<chemin_TOMCAT_HOME^> [DB_URL] [DB_USER] [DB_PASS]
    exit /b 1
  )
  set "CATALINA_HOME=%TOMCAT_HOME%"
) else (
  set "CATALINA_HOME=%~1"
)

if not exist "%CATALINA_HOME%\bin" (
  echo ERREUR: le répertoire %%CATALINA_HOME%%\bin n'existe pas.
  exit /b 1
)

set "DB_URL=%~2"
if "%DB_URL%"=="" set "DB_URL=jdbc:postgresql://localhost:5432/gestion_qcm"
set "DB_USER=%~3"
if "%DB_USER%"=="" set "DB_USER=postgres"
set "DB_PASS=%~4"
if "%DB_PASS%"=="" set "DB_PASS=postgres"

> "%CATALINA_HOME%\bin\setenv.bat" (
  echo @echo off
  echo rem Fichier généré par create-tomcat-setenv.bat
  echo set "GESTION_QCM_DB_URL=%DB_URL%"
  echo set "GESTION_QCM_DB_USER=%DB_USER%"
  echo set "GESTION_QCM_DB_PASS=%DB_PASS%"
)

echo setenv.bat créé dans %CATALINA_HOME%\bin
echo Valeurs écrites :
echo   GESTION_QCM_DB_URL=%DB_URL%
echo   GESTION_QCM_DB_USER=%DB_USER%
echo   GESTION_QCM_DB_PASS=%DB_PASS%
echo Redémarrez Tomcat pour prendre en compte la configuration.
endlocal
