@ECHO OFF

REM Make sure the following variables are correctly set
SET HOMEDRIVE=C:
SET HOMEPATH=\Temp\
SET JAVA_HOME=C:\Program Files\Java\jdk-11.0.13
SET HEROKU_PATH=C:\heroku\bin\
SET BROWSER=C:\Program Files\Google\Chrome\Application\chrome.exe
REM SET HEROKU_DEBUG=1

SET APP_NAME=servermanagement98
SET PROJECT_PATH=D:\Geek\projects\ServerManager-Backend\server\server\
SET PROJECT_HEROKU_PATH=%PROJECT_PATH%heroku\

REM Use the following command to create the API key/token and set it
REM heroku authorizations:create
SET HEROKU_API_KEY=69e56146-963a-48a6-898b-da41c2576b73

REM My heroku authorization
REM Creating OAuth Authorization... done
REM Client:      <none>
REM ID:          3bc4d416-9ec9-4525-88f7-3856120bdb2f
REM Description: Long-lived user authorization
REM Scope:       global
REM Token:       69e56146-963a-48a6-898b-da41c2576b73
REM Updated at:  Sat Oct 01 2022 22:49:07 GMT+0100 (heure normale d’Afrique de l’Ouest) (less than a minute ago)


