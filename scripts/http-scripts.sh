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
http --verbose -a admin:senhalocal localhost:8080/api/pedidoentrega/1

