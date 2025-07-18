-- Created by Caio Dalagnoli Dranka
-- Last modification date: 2024-04-24 01:45:35.204

-- tables
-- Table: anuncio_produto
CREATE TABLE anuncio_produto (
    cd_anuncioProduto int  NOT NULL,
    cd_anuncio int  NOT NULL,
    cd_produto int  NOT NULL,
    CONSTRAINT anuncio_produto_pk PRIMARY KEY (cd_anuncioProduto)
);

-- Table: anuncios
CREATE TABLE anuncios (
    cd_anuncio int  NOT NULL,
    dt_validade date  NOT NULL,
    nl_prioridade int  NOT NULL,
    cd_usuario int  NOT NULL,
    nm_titulo varchar(50)  NOT NULL,
    ds_anuncio varchar(400)  NOT NULL,
    cd_classificacao int  NOT NULL,
    cd_regiao int  NOT NULL,
    CONSTRAINT anuncios_pk PRIMARY KEY (cd_anuncio)
);

-- Table: categoria
CREATE TABLE categoria (
    cd_categoria int  NOT NULL,
    nm_categoria varchar(50)  NOT NULL,
    ds_categoria varchar(50)  NOT NULL,
    CONSTRAINT categoria_pk PRIMARY KEY (cd_categoria)
);

-- Table: chat
CREATE TABLE chat (
    cd_chat int  NOT NULL,
    anuncios_cd_anuncio int  NOT NULL,
    cd_usuario int  NOT NULL,
    ds_mensagem varchar(400)  NOT NULL,
    dt_mensagem date  NOT NULL,
    hr_mensagem time  NOT NULL,
    bl_leituraMnesagem bool  NOT NULL,
    ds_resposta varchar(400)  NOT NULL,
    dt_resposta date  NOT NULL,
    hr_resposta time  NOT NULL,
    bl_leituraResposta bool  NOT NULL,
    CONSTRAINT chat_pk PRIMARY KEY (cd_chat)
);

-- Table: cidade
CREATE TABLE cidade (
    cd_cidade int  NOT NULL,
    nm_cidade varchar(50)  NOT NULL,
    uf_estado varchar(2)  NOT NULL,
    CONSTRAINT cidade_pk PRIMARY KEY (cd_cidade)
);

-- Table: classificacao
CREATE TABLE classificacao (
    cd_classificacao int  NOT NULL,
    nm_classificacao varchar(50)  NOT NULL,
    nr_tot_fotos int  NOT NULL,
    CONSTRAINT classificacao_pk PRIMARY KEY (cd_classificacao)
);

-- Table: endereco
CREATE TABLE endereco (
    cd_endereco int  NOT NULL,
    ds_logradouro varchar(50)  NOT NULL,
    nm_bairro varchar(50)  NOT NULL,
    nr_cep int(8)  NOT NULL,
    nr_casa int  NOT NULL,
    cd_regiao int  NOT NULL,
    cd_cidade int  NOT NULL,
    CONSTRAINT endereco_pk PRIMARY KEY (cd_endereco)
);

-- Table: exclusao
CREATE TABLE exclusao (
    cd_exclusao int  NOT NULL,
    ds_motivo varchar(50)  NOT NULL,
    dt_exclusao date  NOT NULL,
    hr_exclusao time  NOT NULL,
    cd_anuncio int  NOT NULL,
    CONSTRAINT exclusao_pk PRIMARY KEY (cd_exclusao)
);

-- Table: fotos
CREATE TABLE fotos (
    cd_fotos int  NOT NULL,
    url_foto varchar(100)  NOT NULL,
    cd_anuncio int  NOT NULL,
    CONSTRAINT fotos_pk PRIMARY KEY (cd_fotos)
);

-- Table: produto
CREATE TABLE produto (
    cd_produto int  NOT NULL,
    nm_produto varchar(50)  NOT NULL,
    ds_produto varchar(100)  NOT NULL,
    ds_situacao varchar(10)  NOT NULL,
    vl_produto decimal(6,2)  NOT NULL,
    cd_tipo int  NOT NULL,
    cd_categoria int  NOT NULL,
    CONSTRAINT produto_pk PRIMARY KEY (cd_produto)
);

-- Table: regiao
CREATE TABLE regiao (
    cd_regiao int  NOT NULL,
    nm_regiao int  NOT NULL,
    nr_cep int(13)  NOT NULL,
    CONSTRAINT regiao_pk PRIMARY KEY (cd_regiao)
);

-- Table: tipo
CREATE TABLE tipo (
    cd_tipo int  NOT NULL,
    nm_tipo varchar(50)  NOT NULL,
    ds_tipo varchar(100)  NOT NULL,
    cd_categoria int  NOT NULL,
    CONSTRAINT tipo_pk PRIMARY KEY (cd_tipo,cd_categoria)
);

-- Table: usuario
CREATE TABLE usuario (
    cd_usuario int  NOT NULL,
    nm_usuario varchar(50)  NOT NULL,
    dt_nascimento date  NOT NULL,
    ds_genero char(1)  NOT NULL,
    tp_usuario varchar(15)  NOT NULL,
    ds_email varchar(50)  NOT NULL,
    nr_telefone int(13)  NOT NULL,
    cd_endereco int  NOT NULL,
    CONSTRAINT usuario_pk PRIMARY KEY (cd_usuario)
);

-- foreign keys
-- Reference: anuncio_produto_anuncios (table: anuncio_produto)
ALTER TABLE anuncio_produto ADD CONSTRAINT anuncio_produto_anuncios FOREIGN KEY anuncio_produto_anuncios (cd_anuncio)
    REFERENCES anuncios (cd_anuncio);

-- Reference: anuncio_produto_produto (table: anuncio_produto)
ALTER TABLE anuncio_produto ADD CONSTRAINT anuncio_produto_produto FOREIGN KEY anuncio_produto_produto (cd_produto)
    REFERENCES produto (cd_produto);

-- Reference: anuncios_classificacao (table: anuncios)
ALTER TABLE anuncios ADD CONSTRAINT anuncios_classificacao FOREIGN KEY anuncios_classificacao (cd_classificacao)
    REFERENCES classificacao (cd_classificacao);

-- Reference: anuncios_regiao (table: anuncios)
ALTER TABLE anuncios ADD CONSTRAINT anuncios_regiao FOREIGN KEY anuncios_regiao (cd_regiao)
    REFERENCES regiao (cd_regiao);

-- Reference: anuncios_usuario (table: anuncios)
ALTER TABLE anuncios ADD CONSTRAINT anuncios_usuario FOREIGN KEY anuncios_usuario (cd_usuario)
    REFERENCES usuario (cd_usuario);

-- Reference: chat_anuncios (table: chat)
ALTER TABLE chat ADD CONSTRAINT chat_anuncios FOREIGN KEY chat_anuncios (anuncios_cd_anuncio)
    REFERENCES anuncios (cd_anuncio);

-- Reference: chat_usuario (table: chat)
ALTER TABLE chat ADD CONSTRAINT chat_usuario FOREIGN KEY chat_usuario (cd_usuario)
    REFERENCES usuario (cd_usuario);

-- Reference: endereco_cidade (table: endereco)
ALTER TABLE endereco ADD CONSTRAINT endereco_cidade FOREIGN KEY endereco_cidade (cd_cidade)
    REFERENCES cidade (cd_cidade);

-- Reference: endereco_regiao (table: endereco)
ALTER TABLE endereco ADD CONSTRAINT endereco_regiao FOREIGN KEY endereco_regiao (cd_regiao)
    REFERENCES regiao (cd_regiao);

-- Reference: exclusao_anuncios (table: exclusao)
ALTER TABLE exclusao ADD CONSTRAINT exclusao_anuncios FOREIGN KEY exclusao_anuncios (cd_anuncio)
    REFERENCES anuncios (cd_anuncio);

-- Reference: fotos_anuncios (table: fotos)
ALTER TABLE fotos ADD CONSTRAINT fotos_anuncios FOREIGN KEY fotos_anuncios (cd_anuncio)
    REFERENCES anuncios (cd_anuncio);

-- Reference: produto_tipo (table: produto)
ALTER TABLE produto ADD CONSTRAINT produto_tipo FOREIGN KEY produto_tipo (cd_tipo,cd_categoria)
    REFERENCES tipo (cd_tipo,cd_categoria);

-- Reference: tipo_categoria (table: tipo)
ALTER TABLE tipo ADD CONSTRAINT tipo_categoria FOREIGN KEY tipo_categoria (cd_categoria)
    REFERENCES categoria (cd_categoria);

-- Reference: usuario_endereco (table: usuario)
ALTER TABLE usuario ADD CONSTRAINT usuario_endereco FOREIGN KEY usuario_endereco (cd_endereco)
    REFERENCES endereco (cd_endereco);

-- End of file.

