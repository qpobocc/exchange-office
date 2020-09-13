# Rest Service for the currency exchange office


## Technologies used:
1) Maven
2) Spring Boot
3) Spring Web
4) Spring Data Jpa
5) Microsoft SQL Server
6) Lombok
7) Swagger

## Configuration
Needs to configure the application as follows:

* Crate a sample MS SQL SERVER database using sql script `CREATE_DB.sql` from `src/main/resources/sql`
* Configure database connection using properties file `application.properties` from `src/main/resources`


## Usage

### Build
Maven build by using following command:

    mvn clean install

### Run
To run application use following command:

    java -jar exchange-office-1.0.0.jar
    
### Api documentation 
Available on:
 [localhost:7880/swagger-ui.html](http://localhost:7880/swagger-ui.html)

