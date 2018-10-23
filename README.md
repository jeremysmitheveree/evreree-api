# Project: everee-api 

## Overview:
This Java + Spring Boot project is the primary server API for everee.  It is built as a web service leveraging:
- Spring Boot
- Plugins: Web, JPA, Lombok, Actuator
- Gradle
- Java 10+



## Project Structure
The Gradle build process will produce an executable jar that can run on any server with Java
installed.  The project is organized a proto-typical spring-boot/gradle project.  Below is
an quick overview
```text
db
|- init_db.sql                          (initial schema for database)
src
|- main
    |- java
        |- com.everee.api.controllers   (routing/processing setup for API)
        |- com.everee.api.exceptions    (custom exceptions and exception handler)
        |- com.everee.api.model         (primary models/DTOs and mappings to the database)
        |- com.everee.api.reposiory     (JPA interfaces and inherited CRUD functionality)
        |- com.everee.api.EvereeApiApplication  (base Java/spring application - loads context)
    |- resources
        |- application.properties       (configuration file - can be copied and used external)
|- test
    |- java
        |- com.evere.api.controller     (controller tests, etc...)
        |- etc...
|- build.gradle                         (gradle dependency managment setup)
|- README                               (you are here!)
            
```


## Get Started
Assumptions: Installation of Java 10 (latest), Gradel 4.x (latest), git and access to or installation of a local 
PostgreSQL server

- Git clone the project into your project directory.  For example:  /<project-dir/everee-api
- Make sure you "get" all the required packages into your 


- Setup your database (for example on a MacOS)
```shell
/Library/PostgreSQL/10/bin/createdb -U postgres evereeDB
/Library/PostgreSQL/10/bin/psql -d evereeDB -U postgres -f init_db.sql
```
- Configure your application database connection in the project (update local
 application.properties file accordingly with location and credentials)
- Build the project (creates an executable jar)
```shell
 cd everee-api
 ./gradlew bootJar
 
 # to run jar
 java -jar build/libs/everee-api-0.0.1-SNAPSHOT.jar
 
 # shortcut to run the server
 ./gradlew bootRun
```
The project is also easily imported into IDE's (IntelliJ, Spring Tool Suite, etc..) and
can be run in interactive debug mode.


## Temp API Examples until we add auto documentation
- POST /api/v1/companies
```text
Request Body:
{
    "name": "Test Compamy 400",
    "description": "My big fat company description 400",
    "createdDate": "2018-10-01T21:19:57.647158-06:00",
    "active": true,
    "firstName": "Another",
    "lastName": "McTestFace",
    "email": "another@company2.com",
    "phone": "9991231234",
    "country": "United States of America",
    "state": "UT",
    "zip": "84501"
}

Response Body:
{
    "id": 12,
    "name": "Test Compamy 400",
    "description": "My big fat company description 400",
    "createdDate": "2018-10-02T03:19:57.647+0000",
    "active": true,
    "firstName": "Another",
    "lastName": "McTestFace",
    "email": "another@company2.com",
    "phone": "9991231234",
    "country": "United States of America",
    "state": "UT",
    "zip": "84501"
}
```

- GET /api/v1/companies/12 
```text
Response Body:
{
    "id": 12,
    "name": "Test Compamy 400",
    "description": "My big fat company description 400",
    "createdDate": "2018-10-02T03:19:57.647+0000",
    "active": true,
    "firstName": "Another",
    "lastName": "McTestFace",
    "email": "another@company2.com",
    "phone": "9991231234",
    "country": "United States of America",
    "state": "UT",
    "zip": "84501"
}
```

- PUT /api/v1/companies/12 (update existing)
```text
Request Body:
{
    "id": 12,
    "name": "Test Compamy 4001",
    "description": "My big fat company description 4001",
    "createdDate": "2018-10-02T03:19:57.647+0000",
    "active": true,
    "firstName": "Another",
    "lastName": "McTestFace",
    "email": "another@company2.com",
    "phone": "9991231234",
    "country": "United States of America",
    "state": "UT",
    "zip": "84501"
}

Response Body:
{
    "id": 12,
    "name": "Test Compamy 4001",
    "description": "My big fat company description 4001",
    "createdDate": "2018-10-02T03:19:57.647+0000",
    "active": true,
    "firstName": "Another",
    "lastName": "McTestFace",
    "email": "another@company2.com",
    "phone": "9991231234",
    "country": "United States of America",
    "state": "UT",
    "zip": "84501"
}
```

- GET api/v1/employees/1 (retrieve employees for companyid = 1)
```text
Response Body:
[
    {
        "id": 1,
        "companyId": 1,
        "firstName": "AFirstName",
        "lastName": "ALastName",
        "title": "CEO",
        "department": "Crew",
        "i9citizen": true,
        "residentState": "UT",
        "companyEmployeeId": "A123B456",
        "startDate": "2018-01-01T07:00:00.000+0000",
        "endDate": null,
        "active": true
    },
    {
        "id": 2,
        "companyId": 1,
        "firstName": "AFirstName",
        "lastName": "BLastName",
        "title": "Senior Head Assistant Bottle Washer",
        "department": "Crew",
        "i9citizen": true,
        "residentState": "UT",
        "companyEmployeeId": "A123B456",
        "startDate": "2018-01-01T07:00:00.000+0000",
        "endDate": null,
        "active": true
    }
]

```

- POST /api/v1/employees/12 (add a new employee to company = 1)
```text
Request Body:
{
	"companyId": 12,
	"firstName": "AFirstName",
	"lastName": "ALastName",
	"title": "Senior Head Assistant Bottle Washer",
	"department": "Crew", 
	"i9citizen": true,
	"residentState": "UT",
	"companyEmployeeId":"A123B456",
	"startDate":"2018-01-01T21:19:57.647158-06:00",
	"active" : true
}

Response Body:
{
    "id": 4,
    "companyId": 12,
    "firstName": "AFirstName",
    "lastName": "ALastName",
    "title": "Senior Head Assistant Bottle Washer",
    "department": "Crew",
    "i9citizen": true,
    "residentState": "UT",
    "companyEmployeeId": "A123B456",
    "startDate": "2018-01-02T03:19:57.647+0000",
    "endDate": null,
    "active": true
}
```
- PUT /api/v1/employees/12/4 (add a new employee to company = 1 employee = 4)
```text
Request Body:
{
    "id": 4,
	"companyId": 12,
	"firstName": "AFirstName",
	"lastName": "ChangeYourLastName",
	"title": "Senior Head Assistant Bottle Washer",
	"department": "Crew", 
	"i9citizen": true,
	"residentState": "UT",
	"companyEmployeeId":"A123B456",
	"startDate":"2018-01-01T21:19:57.647158-06:00",
	"active" : true
}

Response Body:
{
    "id": 4,
    "companyId": 12,
    "firstName": "AFirstName",
    "lastName": "ChangeYourLastName",
    "title": "Senior Head Assistant Bottle Washer",
    "department": "Crew",
    "i9citizen": true,
    "residentState": "UT",
    "companyEmployeeId": "A123B456",
    "startDate": "2018-01-02T03:19:57.647+0000",
    "endDate": null,
    "active": true
}
```

- GET /api/v1/employees/12/4 (find specific employee)
```text
Response Body:
{
    "id": 4,
    "companyId": 12,
    "firstName": "AFirstName",
    "lastName": "ChangeYourLastName",
    "title": "Senior Head Assistant Bottle Washer",
    "department": "Crew",
    "i9citizen": true,
    "residentState": "UT",
    "companyEmployeeId": "A123B456",
    "startDate": "2018-01-01T07:00:00.000+0000",
    "endDate": null,
    "active": true
}

```


## TO DO (Structural)
- API documenation (add Swagger/OpenAPI or Apiary)
- Logging (for system future monitoring)
- more models
- encryption for fields (probsbly a JPA converter)

