#!/usr/bin/env bash

mvn clean package

echo 'Copy files...'

scp -i ~/.ssh/id_rsa \
      target/sweater-1.0-SNAPSHOT.jar \
      kurilov_ie@vmubuntu1:~/sweater/

echo 'Restart server...'

ssh -i ~/.ssh/id_rsa kurilov_ie@vmubuntu1 << EOF

sudo pgrep java | xargs kill -9
nohup java -jar ~/sweater/sweater-1.0-SNAPSHOT.jar > log.txt &

EOF

echo 'Bye'