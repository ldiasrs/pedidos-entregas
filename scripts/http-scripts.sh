# Teste autenticação usuário

# 1) Ao fazer uma primeira requisicao com usuário e senha o Spring retorna um JSESSIONID que é o token de acesso da sessao
http --verbose -a myuser:senhalocal localhost:8080/api/pedidoentrega/1

# 2) Com o JSESSIONID é possivel realizar outras requisições sem mandar mais usuario e senha
http --verbose localhost:8080/api/pedidoentrega/1 Cookie:'JSESSIONID=EC85751C48D8109D7072F19611E86DD8'

## 3) Cria uma massa de dados para ser consultada usando auttenticação
http --verbose -a myuser:senhalocal localhost:8080/api/pedidoentrega/create < src/test/resources/create_pedido_entrega.json && \
http --verbose -a myuser:senhalocal localhost:8080/api/pedidoentrega/1

# 4) Esse mapeamento esta configurado para não pedir autenticação
http --verbose  localhost:8080/api/pedidoentrega/info

# 5) Esse mapeamento esta configurado para apenas o usuarío admin acessar
http --verbose -a myuser:senhalocal localhost:8080/api/pedidoentrega/admin #Error 403 Forbidden - myuser
http --verbose -a admin:senhalocal localhost:8080/api/pedidoentrega/admin


http --verbose -a myuser:senhalocal localhost:8080/api/pedidoentrega/auth
export JWT_AUTH_TOKEN=" eyJhbGciOiJIUzM4NCJ9.eyJpc3MiOiJwZWRpZG9zLWVudHJlZ2FzLWFwaSIsInN1YiI6IkpXVC1Ub2tlbiIsInVzZXJuYW1lIjoibXl1c2VyIiwiYXV0aG9yaXRpZXMiOiJST0xFX1VTRVIiLCJpYXQiOjE2Mjk4MDY5MjQsImV4cCI6MTYyOTgwNzIyNH0.s3jnEK1SpXAoTImmXk2aMZJev8RxSFsHIZ4KuXCmjmNQcaPYWeX7wALN1rrBi4we"
http  --verbose  localhost:8080/api/pedidoentrega/create Authorization:"${JWT_AUTH_TOKEN}" < src/test/resources/create_pedido_entrega.json
http --verbose localhost:8080/api/pedidoentrega/1 Authorization:"${JWT_AUTH_TOKEN}"
curl -H 'Accept: application/json' -H "Authorization:${JWT_AUTH_TOKEN}" localhost:8080/api/pedidoentrega/1

http --verbose localhost:8080/api/pedidoentrega/create < src/test/resources/create_pedido_entrega.json API_KEY:mySecretSameShouldBeOnSystemEnv
http --verbose localhost:8080/api/pedidoentrega/1 API_KEY:mySecretSameShouldBeOnSystemEnv
