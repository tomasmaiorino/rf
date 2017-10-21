It's a test application which perform a simple CRUD in to different resources and also provide a way to filter the values returned.

This application is running under Spring Boot and it uses a embedded


## Used Technologies

**1. Java version 8.**

**2. JPA:** Mapping persistent entities in domains objects.

**3. Spring Data JPA:** It's used to generate part of the code of the persistence layer.

## Additional Technologies

**Tests:** The tests are defined as use case of the Junit. The tests have been made available in the structure: src/test/java.

**Spring Boot:** It is important to check the application's profiles once that its use more than one. 

**Maven:** Life cycle management and project build.

## Considerations
 As I choose do not to return the model direct to the view, but to use a resource class for any model instead. I couldn't to use the spring's Projection to apply that return filter required on the test and didn't have enough time to found a more elegant solution.

 
## Business Rules
**1. The product's name and description are required.
**2. The image's type is required.
**3. If an image has been useb by a product, the image can not be deleted.
**4. If a product has a sub product it can be deleted.
**5. A product can only be associated to an image that has not been used by any other product.
 
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

###### To create an image using curl:
$ curl -i -H "Content-Type:application/json" -H "Accept:application/json" -X POST http://localhost:8080/api/v1/images -d "{\"type\": \"jpg\"}"
Sample Response:
{"id":1,"type":"jpg"}

###### To update an image using curl:
$ curl -i -H "Content-Type:application/json" -H "Accept:application/json" -X PUT http://localhost:8080/api/v1/images/1 -d "{\"type\": \"png\"}"
Sample Response:
{"id":1,"type":"png"}

###### To find an image using curl:
$ curl -i -H "Content-Type:application/json" -H "Accept:application/json" -X GET http://localhost:8080/api/v1/images/1
Sample Response:
{"id":1,"type":"png"}


###### To delete an image using curl:
$ curl -i -H "Content-Type:application/json" -H "Accept:application/json" -X DELETE http://localhost:8080/api/v1/images/1
Sample Response:
HTTP/1.1 204 
Date: Thu, 12 Oct 2017 19:41:45 GMT

###### To create a product with neither image nor parent using curl:
$ curl -i -H "Content-Type:application/json" -H "Accept:application/json" -X POST http://localhost:8080/api/v1/products -d "{\"name\": \"product name\", \"description\":\"product description\"}"
Sample Response:
{"id":1,"name":"product name","description":"product description","parent":null,"subProducts":null,"productImages":null}

###### To update a product with neither image nor parent using curl:
$ curl -i -H "Content-Type:application/json" -H "Accept:application/json" -X PUT http://localhost:8080/api/v1/products/1 -d "{\"name\": \"product name\", \"description\":\"product description updated\"}"
Sample Response:
{"id":1,"name":"product name","description":"product description updated","parent":null,"subProducts":null,"productImages":null}


###### To delete a product with neither image nor parent using curl:
$ curl -i -H "Content-Type:application/json" -H "Accept:application/json" -X DELETE http://localhost:8080/api/v1/products/1
Sample Response:
HTTP/1.1 204 
Date: Thu, 12 Oct 2017 19:41:45 GMT

###### To create a product with a parent:
$ curl -i -H "Content-Type:application/json" -H "Accept:application/json" -X POST http://localhost:8080/api/v1/products -d "{\"name\": \"child product name\",\"description\":\"product description\", \"parent\":{\"id\":1}}"
Sample Response:
{"id":2,"name":"child product name","parent":null,"subProducts":null,"productImages":null}


###### To create a product with one or more images using curl:
$ curl -i -H "Content-Type:application/json" -H "Accept:application/json" -X POST http://localhost:8080/api/v1/products -d "{\"name\": \"product name\",\"description\":\"product description\", \"productImages\":[{\"id\":1}]}"
Sample Response:
{"id":4,"name":"product name","description":"product description","parent":null,"subProducts":null,"productImages":[{"id":2,"type":"jpg"}]}


###### To update a product with one or more images using curl:

$ curl -i -H "Content-Type:application/json" -H "Accept:application/json" -X PUT http://localhost:8080/api/v1/products/1 -d "{\"name\": \"product name changed\",\"description\":\"product description\", \"productImages\":[{\"id\":1}]}"
Sample Response:
{"id":4,"name":"product name changed","description":"product description","parent":null,"subProducts":null,"productImages":[{"id":2,"type":"jpg"}]}


###### To find a product using curl:
$ curl -i -H "Content-Type:application/json" -H "Accept:application/json" -X GET http://localhost:8080/api/v1/products/1
Sample Response:
{"id":2,"name":"product name","description":"product description","parent":null,"subProducts":[{"id":3,"name":"child product name","description":"product description","parent":null,"subProducts":null,"productImages":null}]


###### To find the product's images using curl:
$ curl -i -H "Content-Type:application/json" -H "Accept:application/json" -X GET http://localhost:8080/api/v1/products/1/images
Sample Response:
[{"id":2,"type":"jpg"}]

###### To find the product's sub-products using curl:
$ curl -i -H "Content-Type:application/json" -H "Accept:application/json" -X GET http://localhost:8080/api/v1/products/1/sub-products
Sample Response:
[{"id":3,"name":"child product name","description":"product description","parent":null,"subProducts":null,"productImages":null}]


###### To find all product's using curl:
$ curl -i -H "Content-Type:application/json" -H "Accept:application/json" -X GET http://localhost:8080/api/v1/products
Sample Response:
[{"id":3,"name":"child product name","description":"product description","parent":null,"subProducts":[],"productImages":[]},{"id":2,"name":"product name","description":"product description","parent":null,"subProducts":[{"id":3,"name":"child product name","description":"product description","parent":null,"subProducts":null,"productImages":null}],"productImages":[]},{"id":4,"name":"product name changed","description":"product description","parent":null,"subProducts":[],"productImages":[{"id":2,"type":"jpg"}]}]t


###### To find all product's but not returning neither sub-products nor images:
$ curl -i -H "Content-Type:application/json" -H "Accept:application/json" -X GET "http://localhost:8080/api/v1/products?sub=false&images=false"
Sample Response:
[{"id":2,"name":"product name","description":"product description","parent":null,"subProducts":[],"productImages":[]},{"id":4,"name":"product name changed","description":"product description","parent":null,"subProducts":[],"productImages":[]},{"id":3,"name":"child product name","description":"product description","parent":null,"subProducts":[],"productImages":[]}]t

###### To find all product's but not returning sub-products:
$ curl -i -H "Content-Type:application/json" -H "Accept:application/json" -X GET "http://localhost:8080/api/v1/products?images=false"
Sample Response:
[{"id":4,"name":"product name changed","description":"product description","parent":null,"subProducts":[],"productImages":[]},{"id":2,"name":"product name","description":"product description","parent":null,"subProducts":[{"id":3,"name":"child product name","description":"product description","parent":null,"subProducts":null,"productImages":null}],"productImages":[]},{"id":3,"name":"child product name","description":"product description","parent":null,"subProducts":[],"productImages":[]}]

###### To find all product's but not returning product images:
$ curl -i -H "Content-Type:application/json" -H "Accept:application/json" -X GET "http://localhost:8080/api/v1/products?images=false"
Sample Response:
[{"id":4,"name":"product name changed","description":"product description","parent":null,"subProducts":[],"productImages":[]},{"id":2,"name":"product name","description":"product description","parent":null,"subProducts":[{"id":3,"name":"child product name","description":"product description","parent":null,"subProducts":null,"productImages":null}],"productImages":[]},{"id":3,"name":"child product name","description":"product description","parent":null,"subProducts":[],"productImages":[]}]


## Final Considerations
In order to facilitate some tests, I created simple swagger doc through the spring-fox api.
The majority of tests can be made but the parameter's values need to be changed.

swagger url:
http://localhost:8080/swagger-ui.html#/
 