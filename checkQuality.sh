#! /bin/bash

set -e # when any of the script fail, it will return the non-zer code immediately

#create quality analysis
mvn clean install -q
mvn site -q
mvn sonar:sonar -q
mvn site:stage -q

#display results
firefox http://localhost:9000 target/staging/index.html &
