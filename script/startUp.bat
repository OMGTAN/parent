sc create tomcat_8080 binPath=%~dp0\server.bat start=auto
pause
::sc delete tomcat_8080