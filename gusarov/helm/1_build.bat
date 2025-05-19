call mvn clean package

call docker build --tag=20dockerdev22/t14:0.0.1 --rm=true .
call docker push 20dockerdev22/t14:0.0.1
