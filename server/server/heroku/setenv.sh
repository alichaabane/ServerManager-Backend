#!/bin/bash

# Make sure the following variables are correctly set
HOMEDRIVE=/
HOMEPATH=tmp
JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
HEROKU_PATH=/usr/local/bin
BROWSER=/usr/bin/firefox
# HEROKU_DEBUG=1

APP_NAME=evmsh
PROJECT_PATH=/mnt/gd/Instruction/Onu/YouTubeProjects/$APP_NAME
PROJECT_HEROKU_PATH=$PROJECT_PATH/heroku

# Use the following command to create the API key/token and set it
# heroku authorizations:create
HEROKU_API_KEY=<HEROKU_API_KEY>
