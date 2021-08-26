# API para pedidos de entrega

- Projeto pedidos de entregas tem a finalidade de ser usado como um caso de uso para estudo e ensino
- Projeto está com algumas classes em português para facilitar o ensino e compreensão. 

# Como funciona

* Quando é iniciado o backend é criado um banco de dados em memória usando H2 DB
* Após isso é criado uma entrada de entrega de pedido para consulta

## Instalando um cliente http

* Nesse caso eu estou usando o https://httpie.io/ por ser mais leve
* Outro fácil de usar é Postman https://www.postman.com/downloads/

## Como consultar pedidos?

* Para consultar os pedidos depende da configuração do modo do Spring Security
  * O padrão é usar o user e senha:  usário:myuser senha:senhalocal
  * Atenção: Esse usuário é meramente para aprendizado caso seja utilizado em PROD deve ser protegido

```
http --verbose -a myuser:senhalocal localhost:8080/api/pedidoentrega/1
```

## Como criar pedidos?

* Os pedidos são criados fazendo um POST no endpoint _/api/pedidoentrega/create_
```
http --verbose -a myuser:senhalocal localhost:8080/api/pedidoentrega/create < src/test/resources/create_pedido_entrega.json
```

# Spring Security

*Segurança de API*

- O spring security prove a segurança do acesso a API 
- É possivel criar diferentes usuários onde cada usuário tem um determinado acesso via roles
  - Exemplo: user: myuser role:USER user: admin role:ADMIN
  - Pode ser definido que o endpoint /admin por exemplo só é acessado para usuário com role /ADMIN
- O spring security tambem fornece mecanismos de TOKEN com JWT,XSRF-TOKEN, e API_TOKEN
- Os Tokens são chaves de acesso para evitar autenticação a cada acesso
- Uma vez autenticado é passado um token/chave para o usuário para que ele possa apenas passar o token e não mais usuário e senha
- Em alguns Tokens existe a possíbilidade de configurar tempo de validade e outros atributos

*Segurança de acesso a métodos*
- É possível configurar nívels de acesso a métodos e classes usando o spring-security-method-security
  - https://www.baeldung.com/spring-security-method-security

## Spring Security modos dentro do projeto

* Nesse projeto o spring security esta configurado com diferentes modos sendo eles

### API_KEY

https://en.wikipedia.org/wiki/Application_programming_interface_key

Alterar propriedade no arquivo *resources/application.properties*
```
api.security.mode=API_KEY
```
*Requisições de teste*

A API_KEY é uma chave unica que é utilizada no header para validar
```
http --verbose localhost:8080/api/pedidoentrega/1 API_KEY:mySecretSameShouldBeOnSystemEnv

GET /api/pedidoentrega/1 HTTP/1.1
API_KEY: mySecretSameShouldBeOnSystemEnv
Accept: */*
Accept-Encoding: gzip, deflate
Connection: keep-alive
Host: localhost:8080
User-Agent: HTTPie/2.4.0
```
Resposta
```
HTTP/1.1 200 
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Connection: keep-alive
Content-Type: application/json
Date: Wed, 25 Aug 2021 21:10:46 GMT
Expires: 0
Keep-Alive: timeout=60
Pragma: no-cache
Transfer-Encoding: chunked
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
X-Content-Type-Options: nosniff
X-Frame-Options: DENY
X-XSS-Protection: 1; mode=block
<BODY>
```

*Exemplo para criar um pedido*
```
http --verbose localhost:8080/api/pedidoentrega/create < src/test/resources/create_pedido_entrega.json API_KEY:mySecretSameShouldBeOnSystemEnv
```

### SESSION_ID

https://docs.spring.io/spring-session/docs/current/reference/html5/#introduction

Alterar propriedade no arquivo *resources/application.properties*
```
api.security.mode=SESSION_ID
```
*Requisições de teste*

1) Ao fazer uma primeira requisicao com usuário e senha o Spring retorna um JSESSIONID que é o token de acesso da sessao
```
http --verbose -a myuser:senhalocal localhost:8080/api/pedidoentrega/1

GET /api/pedidoentrega/1 HTTP/1.1
Accept: */*
Accept-Encoding: gzip, deflate
Authorization: Basic bXl1c2VyOnNlbmhhbG9jYWw=
Connection: keep-alive
Host: localhost:8080
User-Agent: HTTPie/2.4.0
```
Resposta
```
HTTP/1.1 200 
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Connection: keep-alive
Content-Type: application/json
Date: Wed, 25 Aug 2021 21:07:00 GMT
Expires: 0
Keep-Alive: timeout=60
Pragma: no-cache
Set-Cookie: JSESSIONID=8BF308578DBF2F8617E7E4999C7234A0; Path=/; HttpOnly
Transfer-Encoding: chunked
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
X-Content-Type-Options: nosniff
X-Frame-Options: DENY
X-XSS-Protection: 1; mode=block
<BODY>

```
2) Com o JSESSIONID é possivel realizar outras requisições sem mandar mais usuario e senha
```
http --verbose localhost:8080/api/pedidoentrega/1 Cookie:'JSESSIONID=8BF308578DBF2F8617E7E4999C7234A0'
```
Resposta
```
GET /api/pedidoentrega/1 HTTP/1.1
Accept: */*
Accept-Encoding: gzip, deflate
Connection: keep-alive
Cookie: JSESSIONID=8BF308578DBF2F8617E7E4999C7234A0
Host: localhost:8080
User-Agent: HTTPie/2.4.0
```

### JWT

https://jwt.io/introduction

Alterar propriedade no arquivo *resources/application.properties*
```
api.security.mode=JWT
```
Com JWT é preciso primeiro acessar um endpoint para autheticar e pegar o Token
```
http --verbose -a myuser:senhalocal localhost:8080/api/pedidoentrega/auth
```
Depois é possível usar o JWT token nas próximas requesições
```
export JWT_AUTH_TOKEN=" eyJhbGciOiJIUzM4NCJ9.eyJpc3MiOiJwZWRpZG9zLWVudHJlZ2FzLWFwaSIsInN1YiI6IkpXVC1Ub2tlbiIsInVzZXJuYW1lIjoibXl1c2VyIiwiYXV0aG9yaXRpZXMiOiJST0xFX1VTRVIiLCJpYXQiOjE2Mjk4MDY5MjQsImV4cCI6MTYyOTgwNzIyNH0.s3jnEK1SpXAoTImmXk2aMZJev8RxSFsHIZ4KuXCmjmNQcaPYWeX7wALN1rrBi4we"
http --verbose localhost:8080/api/pedidoentrega/1 Authorization:"${JWT_AUTH_TOKEN}"
```
### XSRF-TOKEN

https://laravel.com/docs/5.0/routing#csrf-protection

* Alterar propriedade no arquivo [resources/application.properties]
```
api.security.mode=XSRF-TOKEN
```
1) Ao fazer uma primeira requisicao com usuário e senha o Spring retorna um JSESSIONID que é o token de acesso da sessao
```
http --verbose -a myuser:senhalocal localhost:8080/api/pedidoentrega/1

GET /api/pedidoentrega/1 HTTP/1.1
Accept: */*
Accept-Encoding: gzip, deflate
Authorization: Basic bXl1c2VyOnNlbmhhbG9jYWw=
Connection: keep-alive
Host: localhost:8080
User-Agent: HTTPie/2.4.0
```
Resposta
```
HTTP/1.1 200 
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Connection: keep-alive
Content-Type: application/json
Date: Thu, 26 Aug 2021 14:05:04 GMT
Expires: 0
Keep-Alive: timeout=60
Pragma: no-cache
Set-Cookie: XSRF-TOKEN=06936ade-5797-472a-b630-4da1568ef55f; Path=/
Set-Cookie: JSESSIONID=7D3F1D4234C56651DD8EE41E9C679FE8; Path=/; HttpOnly
Transfer-Encoding: chunked
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
X-Content-Type-Options: nosniff
X-Frame-Options: DENY
X-XSS-Protection: 1; mode=block
<BODY>

```
2) Com o JSESSIONID é possivel realizar outras requisições sem mandar mais usuario e senha
```
http --verbose localhost:8080/api/pedidoentrega/1 Cookie:'XSRF-TOKEN=06936ade-5797-472a-b630-4da1568ef55f'
```


# TODO list

- Escrever a doc do que é o backend
- Escrever doc de como funciona o flyway
  - Explicar como trocar o banco de dados by URL
- melhorar variaveis do application.properties para ambiente
- Colocar variáveis no ambiente do Github
- Estudar soluções de build e deploy com github  actions
- Arrumar testes desabilitados
- Criar modulo de testes smoke test
- Criar modulo de testes e2e
- Criar modulo de testes de segurança
- Mutation test
- Falar sobre JENV para mudar java local
- Configurar documentação API
- Documentar outras referencias
- Criar um App cliente usando React ou Angular

## Nesse projeto é possível ter referências de
 - Spring JPA - Mapeamento de entidades com persistência
 - Lombok para facilidades de builders e Classes
 - Teste unitários de serviços e controllers
 - Flyway Controle de versão do banco de dados
 - Spring Security - segurança de APIs, acesso e permissões
 - CI/CD usando o CircleCI para pipeline + Heroku como PaaS

## Pipeline 

* https://app.circleci.com/pipelines/github/ldiasrs/pedidos-entregas

## Deploy

* https://dashboard.heroku.com/apps/pedidos-entregas/activity

## Rodando o projeto

```
 ./gradlew bootRun
```

## Consumir a API

Usando o https://httpie.org/ como cliente (podendo usar o postman ou outro)

```
 http POST localhost:8080/api/pedidoentrega/create <  src/test/resources/create_pedido_entrega.json
```

```
 http GET localhost:8080/api/pedidoentrega/1
```

## Testes unitários
```
 ./gradlew test
```
# Referência técninca

* https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/reference/html/spring-boot-features.html#boot-features-logging
* https://www.baeldung.com/spring-security-integration-tests
* https://github.com/sakshamsangal/Engineering/tree/0f1c9ba47069cf6e200089a976786e7ad784915f/BackEnd/Java
* https://start.spring.io/
* https://medium.com/@mari_azevedo/construindo-uma-api-restful-com-java-e-spring-framework-46b74371d107
* https://www.jenv.be/
* https://projectlombok.org/features/all
* https://www.jenv.be/
* https://www.baeldung.com/spring-boot-h2-database
* https://devcenter.heroku.com/changelog-items/1489
* https://devcenter.heroku.com/articles/deploying-gradle-apps-on-heroku
* https://devcenter.heroku.com/articles/authentication
* https://devcenter.heroku.com/categories/deployment
* https://www.baeldung.com/spring-email
* https://support.google.com/accounts/answer/185833
* POSTGRE HEROKU: https://devcenter.heroku.com/articles/connecting-to-relational-databases-on-heroku-with-java#using-the-spring_datasource_url-in-a-spring-boot-app
* https://www.baeldung.com/spring-data-jpa-generate-db-schema
* https://spring.io/guides/tutorials/rest/
* https://www.baeldung.com/spring-boot-bean-validation
* https://www.baeldung.com/spring-date-parameters

## Spring

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/gradle-plugin/reference/html/#build-image)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/reference/htmlsingle/#using-boot-devtools)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)
* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

## Guides
The following guides illustrate how to use some features concretely:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)


