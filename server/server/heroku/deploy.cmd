@ECHO OFF

CALL .\setenv
echo 'hello'

"%JAVA_HOME%"\bin\java -version
IF ERRORLEVEL 1 PAUSE
CD %PROJECT_PATH%
CMD /C mvn clean package -Dmaven.test.skip=true
IF ERRORLEVEL 1 PAUSE
echo 'hello2'
CD %PROJECT_HEROKU_PATH%
echo 'hooo'
CD %HEROKU_PATH%
REM CMD /C heroku update
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
