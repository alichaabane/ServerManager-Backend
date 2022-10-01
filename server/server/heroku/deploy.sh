. ./setenv.sh

$JAVA_HOME/bin/java -version

cd $PROJECT_PATH
mvn clean package -Dmaven.test.skip=true

cd $PROJECT_HEROKU_PATH

sudo $HEROKU_PATH/heroku update

sudo $HEROKU_PATH/heroku plugins:install heroku-config

sudo $HEROKU_PATH/heroku plugins:install heroku-cli-deploy

$HEROKU_PATH/heroku apps:destroy --app $APP_NAME

$HEROKU_PATH/heroku apps:create --app $APP_NAME

sudo $HEROKU_PATH/heroku config:push --app $APP_NAME

$HEROKU_PATH/heroku ps:stop --app $APP_NAME dyno

$HEROKU_PATH/heroku buildpacks:clear --app $APP_NAME

sudo $HEROKU_PATH/heroku war:deploy $PROJECT_PATH/target/$APP_NAME.war --app $APP_NAME

$HEROKU_PATH/heroku ps:restart --app $APP_NAME dyno

$BROWSER https://$APP_NAME.herokuapp.com

# $HEROKU_PATH/heroku logs -t --app $APP_NAME
