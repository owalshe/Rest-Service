# Rest-Service

Compile at root with "mvn clean install"

Rest Controller found in service\src\main\java\rest\service\ResourceController
Consists of main CRUD endpoints for data model as well as additional post endpoints to add resources from file based on a given paramater.

Data Model outlined in service\src\main\resources\PracticeFile.csv
Persistence layer using JPARepository library and H2 database.
Unit tests located in service\src\test\java\rest\service
