#! /usr/bin/env bash

# 쉬고 있는 profile 찾기 : production1이 사용 중이면 production2가 쉬고 있고, 반대면 production1이 쉬고 있음
function find_idle_profile() {
    RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost/profile)

    if [ ${RESPONSE_CODE} -ge 400 ]
    then
      CURRENT_PROFILE=production2
    else
      CURRENT_PROFILE=$(curl -s http://localhost/profile)
    fi

    if [ ${CURRENT_PROFILE} == production1 ]
    then
      IDLE_PROFILE=production2
    else
      IDLE_PROFILE=production1
    fi

    echo "${IDLE_PROFILE}"
}

# 쉬고 있는 profile의 port 찾기
function find_idle_port() {
    IDLE_PROFILE=$(find_idle_profile)

    if [ ${IDLE_PROFILE} == production1 ]
    then
      echo "8081"
    else
      echo "8082"
    fi
}