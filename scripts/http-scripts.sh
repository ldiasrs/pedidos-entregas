# Teste autenticação usuário


## 3) Cria uma massa de dados para ser consultada usando auttenticação
http --verbose -a myuser:senhalocal localhost:8080/api/pedidoentrega/create < src/test/resources/create_pedido_entrega.json && \
http --verbose -a myuser:senhalocal localhost:8080/api/pedidoentrega/1

# 4) Esse mapeamento esta configurado para não pedir autenticação
http --verbose  localhost:8080/api/pedidoentrega/info

# 5) Esse mapeamento esta configurado para apenas o usuarío admin acessar
http --verbose -a myuser:senhalocal localhost:8080/api/pedidoentrega/admin #Error 403 Forbidden - myuser
http --verbose -a admin:senhalocal localhost:8080/api/pedidoentrega/admin



export JWT_AUTH_TOKEN=" eyJhbGciOiJIUzM4NCJ9.eyJpc3MiOiJwZWRpZG9zLWVudHJlZ2FzLWFwaSIsInN1YiI6IkpXVC1Ub2tlbiIsInVzZXJuYW1lIjoibXl1c2VyIiwiYXV0aG9yaXRpZXMiOiJST0xFX1VTRVIiLCJpYXQiOjE2Mjk4MDY5MjQsImV4cCI6MTYyOTgwNzIyNH0.s3jnEK1SpXAoTImmXk2aMZJev8RxSFsHIZ4KuXCmjmNQcaPYWeX7wALN1rrBi4we"
http  --verbose  localhost:8080/api/pedidoentrega/create Authorization:"${JWT_AUTH_TOKEN}" < src/test/resources/create_pedido_entrega.json

curl -H 'Accept: application/json' -H "Authorization:${JWT_AUTH_TOKEN}" localhost:8080/api/pedidoentrega/1

