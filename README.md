Esta é uma aplicacao de estudo que executa duas tarefas: cria uma transacao calculando sua taxa a partir de uma regra pre estabelecida retornando a transacao criada e disponiliza uma url para recuperar todas as transacaoes criadas.

A aplicacao foi criada utilizando Spring boot (1.5) o que tornou o desenvolvimento mais agil. 
As transacoes pode ser criadas e consultadas utilizando rest api's, o que alem de permitir que as transacoes possam ser facilmente criadas e consultadas por qualquer client (paginas html, outros rest services, etc),
nao obriga que aplicacao tenha uma camada de frontend para a criacao e consulta das transacoes.
Por nao se tratar de uma aplicacao complexa, ela foi desenvolvida utilizando o MVC basico: um controller que invoca um service que tem acesso a camada de persistencia. Para a criaco do model foi utilizado um Builder com Fluent Interface, o que ajuda na criacao de um model mais conciso "forcando" os atributos obrigatorios serem informados durante a criacao do model e evita a utilizacao de metodos set para inserir os atributos nao obrigatorios.
Para nao expor o model ao client foi um criado um resource que recebe e retorna os dados para o cliente. A conversao do resource para model e do model para resource é feita por uma classe parser.

Para os testes unitarios foi criado uma classe helper para centralizar a criacao do resource, model, atributos invalidos, etc. O que permite com que qualquer alteracao necessaria na massa de teste seja feita em um unico ponto.
Ja para o teste de integracao foi criada um support class, dentro do resource class, utilizando Fluent Interface o que ajuda a especificar os atributos devem ser alterados ou utilizados nos testes.


Obs: Este teste nao exige uma interface grafica, porem para facilitar a validacao foi criado uma pagina html index usando spring mvc, bootstrap na parte de layout e AngularJs (1.5) na parte de consulta da api e manipulacao do DOM.
## Used Technologies

**1. Java version 8.**

**2. JPA:** Mapeamento de entidades persistentes em objetos de domínios.

**3. Spring Data JPA:** É usado para gerar parte do código da camada de persistência.

## Additional Technologies

**Tests:** Os testes são definidos como caso de uso do Junit. Os testes foram disponibilizados na estrutura: src / test / java.

**Maven:** Gerenciamento do ciclo de vida e construção do projeto.

## Considerations
 
## Business Rules
**1. Tanto a conta de destino quanto a origem são obrigatorias.
**2. O tamanho da conta de destino e origem deve estar entre 3 e 20.
**3. A data agendada não deve ser anterior a hoje.
**4. A data agendada deve ter esse formato aaaa-MM-dd (ano-mês-dia).
**5. O valor de transferência deve ser maior que 0.
 
## Usage In Local Machine

###### Pré-requisitos

JDK - Java version 1.8.

Maven para construir e dependências.

###### Clone o projeto do git repository:
$ git clone https://github.com/tomasmaiorino/rf.git

###### Depois de baixar as fontes, para instalar o aplicativo e testá-lo, execute o comando maven:
$ mvn clean install

###### Para testar apenas o aplicativo, execute o comando maven:
$ mvn clean test

###### Para executar os testes de integrações, execute o comando maven:
$ mvn verify -DskipItTest=false

###### Para executar o aplicativo, execute o comando maven:
$ mvn spring-boot:run

###### Para criar uma transacao utilizando o curl:
$ curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' -d "{\"destinationAccount\": \"889977\", \"originAccount\": \"775535\", \"scheduleDate\": \"2017-12-30\", \"transferValue\": 122.10}" 'http://localhost:8080/api/v1/transfers'
Sample Response:
{"id":3,"destinationAccount":"889977","originAccount":"775535","scheduleDate":"2017-12-30","transferValue":122.1,"createdDate":"2017-10-20","tax":2.4419999999999997}

###### Para recoperar as transacoes utlizando o curl:
$ curl -i -H "Content-Type:application/json" -H "Accept:application/json" -X GET http://localhost:8080/api/v1/transfers
Sample Response:
[{"id":3,"destinationAccount":"889977","originAccount":"775535","scheduleDate":"2017-12-30","transferValue":122.1,"createdDate":"2017-10-20","tax":2.4419999999999997},{"id":2,"destinationAccount":"889977","originAccount":"775535","scheduleDate":"2017-12-30","transferValue":122.1,"createdDate":"2017-10-20","tax":2.4419999999999997},{"id":1,"destinationAccount":"889977","originAccount":"775535","scheduleDate":"2017-12-30","transferValue":122.1,"createdDate":"2017-10-20","tax":2.4419999999999997}]

## Final Considerations
A fim de facilitar alguns testes da api rest, criei um documento simples de swagger utlizando o spring-fox. A maioria dos testes podem ser feitos, mas os valores dos parâmetros precisam ser alterados.
Há também uma página html simples para ajudar os testes.

swagger url:
http://localhost:8080/swagger-ui.html#/

html page:
http://localhost:8080/
 