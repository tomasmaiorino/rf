This is a study application that performs two tasks: create a transaction by calculating its rate from a pre-established rule returning the created transaction and provides a url to retrieve all the transactions created.

This application is running under Spring Boot and it uses a embedded

## Used Technologies

**1. Java version 8.**

**2. JPA:** Mapping persistent entities in domains objects.

**3. Spring Data JPA:** It's used to generate part of the code of the persistence layer.

## Additional Technologies

**Tests:** The tests are defined as use case of the Junit. The tests have been made available in the structure: src/test/java.
The integration tests have been made available in the structure: src/it/java.

**Spring Boot:** It is important to check the application's profiles once that its use more than one.

**Maven:** Life cycle management and project build.

**1. Tanto a conta de destino quanto a origem são obrigatorias.
**2. O tamanho da conta de destino e origem deve estar entre 3 e 20.
**3. A data agendada não deve ser anterior a hoje.
**4. A data agendada deve ter esse formato aaaa-MM-dd (ano-mês-dia).
**5. O valor de transferência deve ser maior que 0.

## Business Rules
**1. Both destination and origin account are required.
**2. The destination and origin account length must be between 3 and 20.
**3. The schedule date must not be before today.
**4. The schedule date must follow this format yyyy-MM-dd.
**5. The transfer value must be higher than 0.

## Usage In Local Machine

###### Pré-requisitos

JDK - Java version 1.8.

Any Java IDE with support Maven.

Maven for build and dependencies.


###### After download the fonts, to install the application and test it execute the maven command:
$ mvn clean install

###### To only test the application execute the maven command:
$ mvn clean test

###### To run the integrations tests execute the maven command:
$ mvn verify -DskipItTest=false

###### To run the application execute the maven command:
$ mvn spring-boot:run

###### To create a transaction using curl:
$ curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' -d "{\"destinationAccount\": \"889977\", \"originAccount\": \"775535\", \"scheduleDate\": \"2017-12-30\", \"transferValue\": 122.10}" 'http://localhost:8080/api/v1/transfers'
Sample Response:
{"id":3,"destinationAccount":"889977","originAccount":"775535","scheduleDate":"2017-12-30","transferValue":122.1,"createdDate":"2017-10-20","tax":2.4419999999999997}

###### To recover all transactions using curl:
$ curl -i -H "Content-Type:application/json" -H "Accept:application/json" -X GET http://localhost:8080/api/v1/transfers
Sample Response:
[{"id":3,"destinationAccount":"889977","originAccount":"775535","scheduleDate":"2017-12-30","transferValue":122.1,"createdDate":"2017-10-20","tax":2.4419999999999997},{"id":2,"destinationAccount":"889977","originAccount":"775535","scheduleDate":"2017-12-30","transferValue":122.1,"createdDate":"2017-10-20","tax":2.4419999999999997},{"id":1,"destinationAccount":"889977","originAccount":"775535","scheduleDate":"2017-12-30","transferValue":122.1,"createdDate":"2017-10-20","tax":2.4419999999999997}]

## Final Considerations
In order to facilitate some tests, I created simple swagger doc through the spring-fox api.
The majority of tests can be made but the parameter's values need to be changed.
There is also a html page built in order to help the tests.

swagger url:
http://localhost:8080/swagger-ui.html#/

html page:
http://localhost:8080/
