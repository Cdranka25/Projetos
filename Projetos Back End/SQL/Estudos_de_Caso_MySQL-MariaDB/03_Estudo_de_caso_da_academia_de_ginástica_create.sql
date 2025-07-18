-- Created by Caio Dalagnoli Dranka
-- Last modification date: 2024-04-11 22:51:24.839

-- tables
-- Table: Alunos
CREATE TABLE Alunos (
    cd_Aluno int  NOT NULL,
    nm_aluno varchar(50)  NOT NULL,
    ds_email varchar(50)  NOT NULL,
    nr_telefone varchar(15)  NOT NULL,
    fl_genero char(1)  NOT NULL,
    vl_peso decimal(4,1)  NOT NULL,
    vl_altura decimal(3,1)  NOT NULL,
    tp_plano char(1)  NOT NULL,
    nr_cartao int  NOT NULL,
    cd_endereco int  NOT NULL,
    cd_grau_instr int  NOT NULL,
    cd_profissao int  NOT NULL,
    cd_Plano int  NOT NULL,
    CONSTRAINT Alunos_pk PRIMARY KEY (cd_Aluno)
);

-- Table: Endereco
CREATE TABLE Endereco (
    cd_endereco int  NOT NULL,
    ds_Logradouro varchar(50)  NOT NULL,
    ds_Bairro varchar(50)  NOT NULL,
    nr_Casa int  NOT NULL,
    nr_CEP int(8)  NOT NULL,
    cd_cidade int  NOT NULL,
    CONSTRAINT Endereco_pk PRIMARY KEY (cd_endereco)
);

-- Table: Instrutores
CREATE TABLE Instrutores (
    cd_instrutor int  NOT NULL,
    nome varchar(50)  NOT NULL,
    fl_genero char(1)  NOT NULL,
    telefone varchar(50)  NOT NULL,
    email varchar(50)  NOT NULL,
    cd_endereco int  NOT NULL,
    CONSTRAINT Instrutores_pk PRIMARY KEY (cd_instrutor)
);

-- Table: Matricula
CREATE TABLE Matricula (
    Alunos_cd_Aluno int  NOT NULL,
    Turmas_cd_Turma int  NOT NULL,
    dt_matricula int  NOT NULL,
    CONSTRAINT Matricula_pk PRIMARY KEY (Alunos_cd_Aluno,Turmas_cd_Turma)
);

-- Table: Modalidade_dos_Instrutores
CREATE TABLE Modalidade_dos_Instrutores (
    cd_Modalidades int  NOT NULL,
    cd_instrutor int  NOT NULL,
    CONSTRAINT Modalidade_dos_Instrutores_pk PRIMARY KEY (cd_Modalidades,cd_instrutor)
);

-- Table: Modalidades
CREATE TABLE Modalidades (
    cd_Modalidades int  NOT NULL,
    nm_Modalidades varchar(100)  NOT NULL,
    ds_detalhamento varchar(100)  NOT NULL,
    CONSTRAINT Modalidades_pk PRIMARY KEY (cd_Modalidades)
);

-- Table: Municipio
CREATE TABLE Municipio (
    cd_cidade int  NOT NULL,
    nm_Cidade varchar(50)  NOT NULL,
    sg_Estado char(2)  NOT NULL,
    CONSTRAINT Municipio_pk PRIMARY KEY (cd_cidade)
);

-- Table: Plano
CREATE TABLE Plano (
    cd_Plano int  NOT NULL,
    nm_plano varchar(50)  NOT NULL,
    ds_Plano varchar(100)  NOT NULL,
    vl_Mensal decimal(6,2)  NOT NULL,
    vl_Trimestral decimal(6,2)  NOT NULL,
    vl_Semestral decimal(6,2)  NOT NULL,
    vl_Anual decimal(6,2)  NOT NULL,
    qtd_Turmas int  NOT NULL,
    CONSTRAINT Plano_pk PRIMARY KEY (cd_Plano)
);

-- Table: Presenca
CREATE TABLE Presenca (
    id_registro int  NOT NULL,
    hr_registro time  NOT NULL,
    dt_registro date  NOT NULL,
    cd_Aluno int  NOT NULL,
    cd_Turma int  NOT NULL,
    CONSTRAINT Presenca_pk PRIMARY KEY (id_registro)
);

-- Table: Profissao
CREATE TABLE Profissao (
    cd_profissao int  NOT NULL,
    nm_profissao varchar(50)  NOT NULL,
    CONSTRAINT Profissao_pk PRIMARY KEY (cd_profissao)
);

-- Table: Turmas
CREATE TABLE Turmas (
    cd_Turma int  NOT NULL,
    hr_inicio time  NOT NULL,
    hr_duracao int  NOT NULL,
    lmt_vagas int  NOT NULL,
    ds_info_aff varchar(50)  NOT NULL,
    cd_Modalidades int  NOT NULL,
    cd_instrutor int  NOT NULL,
    CONSTRAINT Turmas_pk PRIMARY KEY (cd_Turma)
);

-- Table: grau_Instrucao
CREATE TABLE grau_Instrucao (
    cd_grau_instr int  NOT NULL,
    ds_grau_instr varchar(50)  NOT NULL,
    CONSTRAINT grau_Instrucao_pk PRIMARY KEY (cd_grau_instr)
);

-- foreign keys
-- Reference: Alunos_Endereco (table: Alunos)
ALTER TABLE Alunos ADD CONSTRAINT Alunos_Endereco FOREIGN KEY Alunos_Endereco (cd_endereco)
    REFERENCES Endereco (cd_endereco);

-- Reference: Alunos_Plano (table: Alunos)
ALTER TABLE Alunos ADD CONSTRAINT Alunos_Plano FOREIGN KEY Alunos_Plano (cd_Plano)
    REFERENCES Plano (cd_Plano);

-- Reference: Alunos_Profissao (table: Alunos)
ALTER TABLE Alunos ADD CONSTRAINT Alunos_Profissao FOREIGN KEY Alunos_Profissao (cd_profissao)
    REFERENCES Profissao (cd_profissao);

-- Reference: Alunos_grau_Instrucao (table: Alunos)
ALTER TABLE Alunos ADD CONSTRAINT Alunos_grau_Instrucao FOREIGN KEY Alunos_grau_Instrucao (cd_grau_instr)
    REFERENCES grau_Instrucao (cd_grau_instr);

-- Reference: Endereco_Municipio (table: Endereco)
ALTER TABLE Endereco ADD CONSTRAINT Endereco_Municipio FOREIGN KEY Endereco_Municipio (cd_cidade)
    REFERENCES Municipio (cd_cidade);

-- Reference: Instrutores_Endereco (table: Instrutores)
ALTER TABLE Instrutores ADD CONSTRAINT Instrutores_Endereco FOREIGN KEY Instrutores_Endereco (cd_endereco)
    REFERENCES Endereco (cd_endereco);

-- Reference: Matricula_Alunos (table: Matricula)
ALTER TABLE Matricula ADD CONSTRAINT Matricula_Alunos FOREIGN KEY Matricula_Alunos (Alunos_cd_Aluno)
    REFERENCES Alunos (cd_Aluno);

-- Reference: Matricula_Turmas (table: Matricula)
ALTER TABLE Matricula ADD CONSTRAINT Matricula_Turmas FOREIGN KEY Matricula_Turmas (Turmas_cd_Turma)
    REFERENCES Turmas (cd_Turma);

-- Reference: Modalidade_dos_Instrutores_Instrutores (table: Modalidade_dos_Instrutores)
ALTER TABLE Modalidade_dos_Instrutores ADD CONSTRAINT Modalidade_dos_Instrutores_Instrutores FOREIGN KEY Modalidade_dos_Instrutores_Instrutores (cd_instrutor)
    REFERENCES Instrutores (cd_instrutor);

-- Reference: Modalidade_dos_Instrutores_Modalidades (table: Modalidade_dos_Instrutores)
ALTER TABLE Modalidade_dos_Instrutores ADD CONSTRAINT Modalidade_dos_Instrutores_Modalidades FOREIGN KEY Modalidade_dos_Instrutores_Modalidades (cd_Modalidades)
    REFERENCES Modalidades (cd_Modalidades);

-- Reference: Presenca_Matricula (table: Presenca)
ALTER TABLE Presenca ADD CONSTRAINT Presenca_Matricula FOREIGN KEY Presenca_Matricula (cd_Aluno,cd_Turma)
    REFERENCES Matricula (Alunos_cd_Aluno,Turmas_cd_Turma);

-- Reference: Turmas_Modalidade_dos_Instrutores (table: Turmas)
ALTER TABLE Turmas ADD CONSTRAINT Turmas_Modalidade_dos_Instrutores FOREIGN KEY Turmas_Modalidade_dos_Instrutores (cd_Modalidades,cd_instrutor)
    REFERENCES Modalidade_dos_Instrutores (cd_Modalidades,cd_instrutor);

-- End of file.

