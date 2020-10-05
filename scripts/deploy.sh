#! /bin/bash

REPOSITORY=/home/ec2-user/app/step2
PROJECT_NAME=all-review

echo "> Copy Build File"

cp $REPOSITORY/zip/*.jar $REPOSITORY/

echo "> Check Running Application PID"

CURRENT_PID=$(pgrep -fl all-review | grep jar | awk '{print $1}')

echo "Running Application PID : $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
	echo "> There's no running application."
else
	echo "> Kill $CURRENT_PID"
	kill -15 $CURRENT_PID
	sleep 5
fi

echo "> New Application Deployment"

JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo "> Jar Name : $JAR_NAME"

chmod +x $JAR_NAME

echo "> Execute $JAR_NAME"

nohup java -jar \-Dspring.config.location=classpath:/application.properties,/home/ec2-user/app/application-oauth.properties,/home/ec2-user/app/application-prod-db.properties \-Dspring.profiles.active=production \/home/ec2-user/app/step2/$JAR_NAME > /home/ec2-user/app/step2/nohup.out 2>&1 &