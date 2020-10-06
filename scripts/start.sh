#! /usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

REPOSITORY=/home/ec2-user/app/step3
PROJECT_NAME=all-review

echo "> Copy Build File"
cp $REPOSITORY/zip/*.jar $REPOSITORY/

echo "> New Application Deployment"
JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1 | awk -F'/' '{print $6}')

echo "> Jar Name: $JAR_NAME"

chmod +x $JAR_NAME

IDLE_PROFILE=$(find_idle_profile)

echo "> Execute $JAR_NAME by $IDLE_PROFILE"

nohup java -jar \-Dspring.config.location=classpath:/application.properties,classpath:/application-$IDLE_PROFILE.properties,/home/ec2-user/app/application-oauth.properties,/home/ec2-user/app/application-prod-db.properties \-Dspring.profiles.active=$IDLE_PROFILE \/home/ec2-user/app/step3/$JAR_NAME > /home/ec2-user/app/step3/nohup.out 2>&1 &
