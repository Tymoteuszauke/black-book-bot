#! /bin/bash

set -e # when any of the script fail, it will return the non-zer code immediately

mvn clean package -q

java -jar persistencebot/target/persistencebot-1.0-exec.jar &

java -jar czytamplscraper/target/czytamplscraper-1.0-exec.jar &
java -jar gandalfscraper/target/gandalfscraper-1.0-exec.jar &
java -jar googlecrawler/target/googlecrawler-1.0-exec.jar &
java -jar matrasscraper/target/matrasscraper-1.0-exec.jar &
java -jar taniaksiazkascraper/target/taniaksiazkascraper-1.0-exec.jar &