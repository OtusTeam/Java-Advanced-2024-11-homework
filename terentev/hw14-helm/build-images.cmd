call mvn clean package

call docker build --tag=salterentev/otushelm-homework:latest --rm=true .
call docker push salterentev/otushelm-homework:latest
