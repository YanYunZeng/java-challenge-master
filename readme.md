### How to use this spring-boot project

- Install packages with `mvn package`
- Run `mvn spring-boot:run` for starting the application (or use your IDE)

Application (with the embedded H2 database) is ready to be used ! You can access the url below for testing it :

- Swagger UI : http://localhost:8080/swagger-ui.html
user:admin / password:admin123

- H2 UI : http://localhost:8080/h2-console
JDBC URL:jdbc:h2:mem:mydb / user:sa / password:password

>1. Login H2 UI : http://localhost:8080/h2-console
-JDBC URL:jdbc:h2:mem:mydb / user:sa / password:password

>2. insert data -- 
INSERT INTO USERINFO (ID,USERNAME, PASSWORD, ROLE) VALUES (null,'admin','admin123','ADMIN,EDITOR,VIEWER,');
INSERT INTO USERINFO (ID,USERNAME, PASSWORD, ROLE) VALUES (null,'editor','editor123','EDITOR');
INSERT INTO USERINFO (ID,USERNAME, PASSWORD, ROLE) VALUES (null,'viewer','viewer123','VIEWER');
INSERT INTO EMPLOYEE (ID, EMPLOYEE_NAME, EMPLOYEE_SALARY, DEPARTMENT) VALUES (1,'Emp1','959500','TECH1');
INSERT INTO EMPLOYEE (ID, EMPLOYEE_NAME, EMPLOYEE_SALARY, DEPARTMENT) VALUES (2,'Emp2','656500','TECH2');

>3. Test System Swagger UI : http://localhost:8080/swagger-ui.html
-user:admin / password:admin123

> Don't forget to set the `JDBC URL` value as `jdbc:h2:mem:testdb` for H2 UI.

### Instructions

- download the zip file of this project
- create a repository in your own github named 'java-challenge'
- clone your repository in a folder on your machine
- extract the zip file in this folder
- commit and push

- Enhance the code in any ways you can see, you are free! Some possibilities:
  - Add tests
  - Change syntax
  - Protect controller end points
  - Add caching logic for database calls
  - Improve doc and comments
  - Fix any bug you might find
- Edit readme.md and add any comments. It can be about what you did, what you would have done if you had more time, etc.
- Send us the link of your repository.

#### Restrictions
- use java 8


#### What we will look for
- Readability of your code
- Documentation
- Comments in your code 
- Appropriate usage of spring boot
- Appropriate usage of packages
- Is the application running as expected
- No performance issues

#### Your experience in Java

Please let us know more about your Java experience in a few sentences. For example:

- I have 5 years experience in Java and use Spring Boot around 3 years
