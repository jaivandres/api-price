:lemon:  api-price
-----

API PRICE

Install tools
-----

- Java 17
- Maven 3.8 >

Setup 🔧
-----

- Clone and open in Intellij Idea IDE or Eclipse
- Install maven dependencies using IDE auto import or using the command ``mvn install``
- Run the app using ``mvn spring-boot:run``
- Create package for deployment using ``mvn package`` (change properties if required)
- Clean, compile and execute unit test using ``mvn clean test``
- Spotless: Keep your code spotless with Maven ``mvn spotless:apply``

Skip Test
-----
- ``mvn clean install -DskipTests=true -o``
- ``mvn clean test -Djacoco.skip=true``
- ``mvn clean verify -Djacoco.skip=true``
- ``mvn clean package -Djacoco.skip=true``
- ``mvn clean install -Djacoco.skip=true``


### Swagger

- http://localhost:8080/

### H2

- http://localhost:8080/h2-console
- url: jdbc:h2:mem:api-price;DB_CLOSE_DELAY=-1
- username: admin
- password: admin
