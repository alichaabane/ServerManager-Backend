@ECHO OFF

CALL .\setenv
echo 'hello'

REM "%JAVA_HOME%"\bin\java -version
REM IF ERRORLEVEL 1 PAUSE
REM CD %PROJECT_PATH%
REM CMD /C mvn clean package -Dmaven.test.skip=true
REM IF ERRORLEVEL 1 PAUSE
REM echo 'hello2'
CD %PROJECT_HEROKU_PATH%
echo 'hooo'
CD %HEROKU_PATH%
CMD /C heroku update
IF ERRORLEVEL 1 PAUSE
echo 'hello3'

CMD /C heroku plugins:install heroku-config
IF ERRORLEVEL 1 PAUSE

CMD /C heroku plugins:install heroku-cli-deploy
IF ERRORLEVEL 1 PAUSE

CMD /C heroku apps:destroy --app %APP_NAME%
IF ERRORLEVEL 1 PAUSE

CMD /C heroku apps:create --app %APP_NAME%
IF ERRORLEVEL 1 PAUSE

CMD /C heroku config:push --app %APP_NAME%
IF ERRORLEVEL 1 PAUSE

CMD /C heroku ps:stop --app %APP_NAME% dyno
IF ERRORLEVEL 1 PAUSE

CMD /C heroku buildpacks:clear --app %APP_NAME%
IF ERRORLEVEL 1 PAUSE

CMD /C heroku jar:deploy %PROJECT_PATH%target\%APP_NAME%-%APP_VERSION%.jar --app %APP_NAME%
IF ERRORLEVEL 1 PAUSE

CMD /C heroku ps:restart --app %APP_NAME% dyno
IF ERRORLEVEL 1 PAUSE

"%BROWSER%" https://"%APP_NAME%".herokuapp.com
IF ERRORLEVEL 1 PAUSE

REM CMD /C %HEROKU_PATH%heroku logs -t --app %APP_NAME%
