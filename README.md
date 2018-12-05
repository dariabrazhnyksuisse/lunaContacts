# lunaContacts
Simple application exposing a REST API for handling contacts and skills

# Install
`mvn clean install`

# Run the tests
`mvn test`

# Start the server
`mvn spring-boot:run`

- Swagger : <http://localhost:8080/swagger-ui.html>
- H2 console : <http://localhost:8080/h2-console>
- To use `jdbc:h2:mem:testdb` as JDBC URL

# GIT: 
https://github.com/dariabrazhnyksuisse/lunaContacts.git

#
- We use data.sql to populate the initial contact/skill data.

- pom.xml - Contains all the dependencies needed to build this project. 

- H2 as in memory database.


# Used for learning:  
1) www.baeldung.com
2) www.in28minutes.com
3) For tests was useful this repo: https://github.com/finsterthecat/heroes-backend
4) Dependencies: https://mvnrepository.com
5) Generate a project: start.spring.io
6) Using Bean Validation Constraints: https://docs.oracle.com/javaee/7/tutorial/bean-validation001.htm
