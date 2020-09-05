# API para pedidos de entrega

Projeto pedidos de entregas tem a finalidade de ser usado como um caso de uso para estudo.

## Pipeline 

* https://app.circleci.com/pipelines/github/ldiasrs/pedidos-entregas

## Rodando o projeto

```
 ./gradlew bootRun
```

## Consumir a API

Usando o https://httpie.org/ como cliente (podendo usar o postman ou outro)

```
 http get localhost:8080/test
```

## Testes unitários
```
 ./gradlew test
```
### Referência técninca

* https://start.spring.io/
* https://medium.com/@mari_azevedo/construindo-uma-api-restful-com-java-e-spring-framework-46b74371d107
* https://www.jenv.be/
* https://projectlombok.org/features/all
* https://www.jenv.be/
* https://www.baeldung.com/spring-boot-h2-database
* https://devcenter.heroku.com/changelog-items/1489
* https://devcenter.heroku.com/articles/deploying-gradle-apps-on-heroku
* https://devcenter.heroku.com/articles/authentication

#### Spring

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/gradle-plugin/reference/html/#build-image)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/reference/htmlsingle/#using-boot-devtools)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)

#### Guides
The following guides illustrate how to use some features concretely:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)

#### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

