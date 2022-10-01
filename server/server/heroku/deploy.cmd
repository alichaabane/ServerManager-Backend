@ECHO OFF

CALL .\setenv

%JAVA_HOME%\bin\java -version
IF ERRORLEVEL 1 PAUSE

CD %PROJECT_PATH%
CMD /C mvn clean package -Dmaven.test.skip=true
IF ERRORLEVEL 1 PAUSE

CD %PROJECT_HEROKU_PATH%
CMD /C %HEROKU_PATH%heroku update
IF ERRORLEVEL 1 PAUSE

CMD /C %HEROKU_PATH%heroku plugins:install heroku-config
IF ERRORLEVEL 1 PAUSE

CMD /C %HEROKU_PATH%heroku plugins:install heroku-cli-deploy
IF ERRORLEVEL 1 PAUSE

CMD /C %HEROKU_PATH%heroku apps:destroy --app %APP_NAME%
IF ERRORLEVEL 1 PAUSE

CMD /C %HEROKU_PATH%heroku apps:create --app %APP_NAME%
IF ERRORLEVEL 1 PAUSE

CMD /C %HEROKU_PATH%heroku config:push --app %APP_NAME%
IF ERRORLEVEL 1 PAUSE

CMD /C %HEROKU_PATH%heroku ps:stop --app %APP_NAME% dyno
IF ERRORLEVEL 1 PAUSE

CMD /C %HEROKU_PATH%heroku buildpacks:clear --app %APP_NAME%
IF ERRORLEVEL 1 PAUSE

CMD /C %HEROKU_PATH%heroku war:deploy %PROJECT_PATH%target\%APP_NAME%.war --app %APP_NAME%
IF ERRORLEVEL 1 PAUSE

CMD /C %HEROKU_PATH%heroku ps:restart --app %APP_NAME% dyno
IF ERRORLEVEL 1 PAUSE

%BROWSER% https://%APP_NAME%.herokuapp.com
IF ERRORLEVEL 1 PAUSE

REM CMD /C %HEROKU_PATH%heroku logs -t --app %APP_NAME%
