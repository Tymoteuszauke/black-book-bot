# Welcome to the Black Book Bot wiki!

BlackBootBot project is project of robot for searching information about promotions
and free books on bookstore websites.

#Full requirements:
[Requirements](https://git.epam.com/tomasz_borek/ja-materials/wikis/project-robot)

#Authors:
 - Patrycja Zaremba
 - Mateusz Słaboński
 - Siarhei Shauchenka
 - Piotr Sekuła
 
#Project wiki with information for demo
[BlackBookBot wiki](https://github.com/Tymoteuszauke/black-book-bot/wiki)

# Managing features
[Waffle.io board](https://waffle.io/Tymoteuszauke/black-book-bot) 

# Functional requirements
- [] At least one crawler scratching bookstore website
- [] At least one crawler communicating via bookstore website REST Api
- [] Searching 5 bookstore websites
- [] Robot runs once per day, results are appended
- [] Storing search results in database
- [] Web UI in Angular
- [] Multiple filters for searching data from database in UI (see Full requirements for more details)
- [] At least 75% of test coverage

# Running project (TODO)
- For now you need to run in IDE main method in BotApplication class, and
go to localhost:8080/api/books to see records from database

# Code quality
- Run SonarQube server on default port (9000)
- Run **checkQuality.sh**
This script will generate Checkstyle, Findbugs, JaCoCo and Sonar reports. 
It also opens index.html and sonar reports in Mozilla browser.

Reports will be created in:
- **/target/site/index.html** (Checkstyle, Findbugs report)
- **/target/site/jacoco/** (directory with JaCoCo reports)
- **/target/sonar/** (directory with sonar generated files)