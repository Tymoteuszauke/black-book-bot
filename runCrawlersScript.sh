#! /bin/bash

#This is script that u have to use in crontab
#To add script into crontab sudo crontab -e and set appropriate time you want to run scrappers
#for example * 3 * * * path-to-script will run script every day at 3 am
#remember to provide appropriate address below, edit in need

curl --request POST http://localhost:8102/api/matras-scraper
curl --request POST http://localhost:8103/api/taniaksiazka-scraper
curl --request POST http://localhost:8104/api/taniaksiazka-scraper
