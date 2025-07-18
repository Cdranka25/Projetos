-- Created by Caio Dalagnoli Dranka
-- Last modification date: 2024-04-17 17:34:43.956

-- tables
-- Table: OrdemServico_Pecas
CREATE TABLE OrdemServico_Pecas (
    cd_OrdemServico_Pecas int  NOT NULL,
    qt_pecas int  NOT NULL,
    cd_peca int  NOT NULL,
    cd_OS int  NOT NULL,
    CONSTRAINT OrdemServico_Pecas_pk PRIMARY KEY (cd_OrdemServico_Pecas)
);

-- Table: OrdemServico_Profissionais
CREATE TABLE OrdemServico_Profissionais (
    cd_OrdemServico_Profissionais int  NOT NULL,
    cd_funcionario int  NOT NULL,
    cd_OS int  NOT NULL,
    CONSTRAINT OrdemServico_Profissionais_pk PRIMARY KEY (cd_OrdemServico_Profissionais)
);

-- Table: OrdemServico_Servicos
CREATE TABLE OrdemServico_Servicos (
    cd_OrdemServico_Servicos int  NOT NULL,
    cd_OS int  NOT NULL,
    cd_servico int  NOT NULL,
    CONSTRAINT OrdemServico_Servicos_pk PRIMARY KEY (cd_OrdemServico_Servicos)
);

-- Table: cidade
CREATE TABLE cidade (
    cd_cidade int  NOT NULL,
    nm_cidade varchar(50)  NOT NULL,
    uf_estado varchar(2)  NOT NULL,
    CONSTRAINT cidade_pk PRIMARY KEY (cd_cidade)
);

-- Table: clientes
CREATE TABLE clientes (
    cd_cliente int  NOT NULL,
    nm_cliente varchar(50)  NOT NULL,
    nr_cpf int(14)  NOT NULL,
    nr_telefone int(13)  NOT NULL,
    ds_email varchar(50)  NOT NULL,
    cd_endereco int  NOT NULL,
    CONSTRAINT clientes_pk PRIMARY KEY (cd_cliente)
);

-- Table: endereco
CREATE TABLE endereco (
    cd_endereco int  NOT NULL,
    nm_logradouro varchar(50)  NOT NULL,
    nm_bairro varchar(50)  NOT NULL,
    nr_casa varchar(5)  NOT NULL,
    nr_cep varchar(8)  NOT NULL,
    cd_cidade int  NOT NULL,
    CONSTRAINT endereco_pk PRIMARY KEY (cd_endereco)
);

-- Table: notaFiscal
CREATE TABLE notaFiscal (
    cd_notafiscal int  NOT NULL,
    dt_notaFiscal date  NOT NULL,
    vl_total decimal(6,2)  NOT NULL,
    cd_OS int  NOT NULL,
    CONSTRAINT notaFiscal_pk PRIMARY KEY (cd_notafiscal)
);

-- Table: notaFiscal_Pecas
CREATE TABLE notaFiscal_Pecas (
    cd_notaFiscalPecas int  NOT NULL,
    cd_OrdemServico_Pecas int  NOT NULL,
    cd_notafiscal int  NOT NULL,
    CONSTRAINT notaFiscal_Pecas_pk PRIMARY KEY (cd_notaFiscalPecas)
);

-- Table: notaFiscal_Servicos
CREATE TABLE notaFiscal_Servicos (
    cd_notaFiscalServicos int  NOT NULL,
    cd_notafiscal int  NOT NULL,
    cd_OrdemServico_Servicos int  NOT NULL,
    CONSTRAINT notaFiscal_Servicos_pk PRIMARY KEY (cd_notaFiscalServicos)
);

-- Table: ordemServico
CREATE TABLE ordemServico (
    cd_OS int  NOT NULL,
    dt_OS date  NOT NULL,
    ds_status varchar(50)  NOT NULL,
    cd_cliente int  NOT NULL,
    CONSTRAINT ordemServico_pk PRIMARY KEY (cd_OS)
);

-- Table: peca
CREATE TABLE peca (
    cd_peca int  NOT NULL,
    nm_peca varchar(50)  NOT NULL,
    ds_peca varchar(100)  NOT NULL,
    ds_categoria varchar(100)  NOT NULL,
    qt_estoque int  NOT NULL,
    vl_custo decimal(6,2)  NOT NULL,
    vl_venda decimal(6,2)  NOT NULL,
    CONSTRAINT peca_pk PRIMARY KEY (cd_peca)
);

-- Table: profissional
CREATE TABLE profissional (
    cd_funcionario int  NOT NULL,
    nm_funcionario varchar(50)  NOT NULL,
    ds_especialidade varchar(100)  NOT NULL,
    CONSTRAINT profissional_pk PRIMARY KEY (cd_funcionario)
);

-- Table: servico
CREATE TABLE servico (
    cd_servico int  NOT NULL,
    ds_servico varchar(50)  NOT NULL,
    vl_servico decimal(6,2)  NOT NULL,
    CONSTRAINT servico_pk PRIMARY KEY (cd_servico)
);

-- Table: veiculo
CREATE TABLE veiculo (
    cd_veiculo int  NOT NULL,
    ds_placa varchar(8)  NOT NULL,
    nm_marca varchar(15)  NOT NULL,
    ds_modelo varchar(50)  NOT NULL,
    ano_fabricacao int(4)  NOT NULL,
    ano_modelo varchar(50)  NOT NULL,
    cd_cliente int  NOT NULL,
    CONSTRAINT veiculo_pk PRIMARY KEY (cd_veiculo)
);

-- foreign keys
-- Reference: OrdemServico_Pecas_ordemServico (table: OrdemServico_Pecas)
ALTER TABLE OrdemServico_Pecas ADD CONSTRAINT OrdemServico_Pecas_ordemServico FOREIGN KEY OrdemServico_Pecas_ordemServico (cd_OS)
    REFERENCES ordemServico (cd_OS);

-- Reference: OrdemServico_Pecas_pecas (table: OrdemServico_Pecas)
ALTER TABLE OrdemServico_Pecas ADD CONSTRAINT OrdemServico_Pecas_pecas FOREIGN KEY OrdemServico_Pecas_pecas (cd_peca)
    REFERENCES peca (cd_peca);

-- Reference: OrdemServico_Profissionais_ordemServico (table: OrdemServico_Profissionais)
ALTER TABLE OrdemServico_Profissionais ADD CONSTRAINT OrdemServico_Profissionais_ordemServico FOREIGN KEY OrdemServico_Profissionais_ordemServico (cd_OS)
    REFERENCES ordemServico (cd_OS);

-- Reference: OrdemServico_Servicos_ordemServico (table: OrdemServico_Servicos)
ALTER TABLE OrdemServico_Servicos ADD CONSTRAINT OrdemServico_Servicos_ordemServico FOREIGN KEY OrdemServico_Servicos_ordemServico (cd_OS)
    REFERENCES ordemServico (cd_OS);

-- Reference: OrdemServico_Servicos_servico (table: OrdemServico_Servicos)
ALTER TABLE OrdemServico_Servicos ADD CONSTRAINT OrdemServico_Servicos_servico FOREIGN KEY OrdemServico_Servicos_servico (cd_servico)
    REFERENCES servico (cd_servico);

-- Reference: Table_10_profissional (table: OrdemServico_Profissionais)
ALTER TABLE OrdemServico_Profissionais ADD CONSTRAINT Table_10_profissional FOREIGN KEY Table_10_profissional (cd_funcionario)
    REFERENCES profissional (cd_funcionario);

-- Reference: Table_13_OrdemServico_Servicos (table: notaFiscal_Servicos)
ALTER TABLE notaFiscal_Servicos ADD CONSTRAINT Table_13_OrdemServico_Servicos FOREIGN KEY Table_13_OrdemServico_Servicos (cd_OrdemServico_Servicos)
    REFERENCES OrdemServico_Servicos (cd_OrdemServico_Servicos);

-- Reference: Table_13_notaFiscal (table: notaFiscal_Servicos)
ALTER TABLE notaFiscal_Servicos ADD CONSTRAINT Table_13_notaFiscal FOREIGN KEY Table_13_notaFiscal (cd_notafiscal)
    REFERENCES notaFiscal (cd_notafiscal);

-- Reference: Table_14_OrdemServico_Pecas (table: notaFiscal_Pecas)
ALTER TABLE notaFiscal_Pecas ADD CONSTRAINT Table_14_OrdemServico_Pecas FOREIGN KEY Table_14_OrdemServico_Pecas (cd_OrdemServico_Pecas)
    REFERENCES OrdemServico_Pecas (cd_OrdemServico_Pecas);

-- Reference: Table_14_notaFiscal (table: notaFiscal_Pecas)
ALTER TABLE notaFiscal_Pecas ADD CONSTRAINT Table_14_notaFiscal FOREIGN KEY Table_14_notaFiscal (cd_notafiscal)
    REFERENCES notaFiscal (cd_notafiscal);

-- Reference: clientes_endereco (table: clientes)
ALTER TABLE clientes ADD CONSTRAINT clientes_endereco FOREIGN KEY clientes_endereco (cd_endereco)
    REFERENCES endereco (cd_endereco);

-- Reference: endereco_cidade (table: endereco)
ALTER TABLE endereco ADD CONSTRAINT endereco_cidade FOREIGN KEY endereco_cidade (cd_cidade)
    REFERENCES cidade (cd_cidade);

-- Reference: notaFiscal_ordemServico (table: notaFiscal)
ALTER TABLE notaFiscal ADD CONSTRAINT notaFiscal_ordemServico FOREIGN KEY notaFiscal_ordemServico (cd_OS)
    REFERENCES ordemServico (cd_OS);

-- Reference: ordemServico_clientes (table: ordemServico)
ALTER TABLE ordemServico ADD CONSTRAINT ordemServico_clientes FOREIGN KEY ordemServico_clientes (cd_cliente)
    REFERENCES clientes (cd_cliente);

-- Reference: veiculo_clientes (table: veiculo)
ALTER TABLE veiculo ADD CONSTRAINT veiculo_clientes FOREIGN KEY veiculo_clientes (cd_cliente)
    REFERENCES clientes (cd_cliente);

-- End of file.

