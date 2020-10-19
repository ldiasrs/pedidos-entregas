CREATE TABLE IF NOT EXISTS `contato` (
    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `nome` varchar(255),
    `telefone` varchar(255)
);
CREATE TABLE IF NOT EXISTS `endereco` (
    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `cep` varchar(50),
    `complemento` varchar(255),
    `numero` varchar(50)
);
CREATE TABLE IF NOT EXISTS `local` (
    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `contato_id` int,
    `endereco_id` int
);
CREATE TABLE IF NOT EXISTS `pacote` (
    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `altura_em_centimetros` int,
    `largura_em_centimetros` int,
    `profundidade_em_centimetros` int,
    `peso_pacote_em_gramas` decimal(19,2)
);
CREATE TABLE IF NOT EXISTS `pedido_entrega` (
    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `data_hora_busca_pacote_origem` timestamp,
    `observacoes` varchar(255),
    `valor_da_entrega` decimal(19,2),
    `peso_pacote_em_gramas` float,
    `local_destino_id` int,
    `local_origem_id` int,
    `pacote_id` int
);
alter table local add constraint FK_local_contato foreign key (contato_id) references contato;
alter table local add constraint FK_local_endereco foreign key (endereco_id) references endereco;
alter table pedido_entrega add constraint FK_pedido_entrega_local_destino foreign key (local_destino_id) references local;
alter table pedido_entrega add constraint FK_pedido_entrega_local_origem foreign key (local_origem_id) references local;
alter table pedido_entrega add constraint FK_pedido_entrega_pacote_id foreign key (pacote_id) references pacote;
