#! /usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh
source ${ABSDIR}/switch.sh

IDLE_PORT=$(find_idle_port)

echo "> Health Check Start"
echo "> IDLE_PORT: $IDLE_PORT"
sleep 10

for RETRY_COUNT in {1..10}
do
  RESPONSE=$(curl -s http://localhost:${IDLE_PORT}/profile)
  UP_COUNT=$(echo ${RESPONSE} | grep 'production' | wc -l)

  if [ ${UP_COUNT} -ge 1 ]
  then
    echo "> Health Check Success"
    switch_proxy
    break
  else
    echo "> Health Check does not response or not running"
    echo "> Health Check: ${RESPONSE}"
  fi

  if [ ${RETRY_COUNT} -eq 10 ]
  then
    echo "> Health Check Fail"
    echo "> Terminate deployment without connecting Nginx"
    exit 1
  fi

  echo "> Retry Health Check"
  sleep 10
done