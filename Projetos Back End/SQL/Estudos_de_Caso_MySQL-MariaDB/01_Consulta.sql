-- criando a tabela departamento
CREATE TABLE departamento
(cd_depto INTEGER,
nm_depto VARCHAR(30),
vl_orcto DECIMAL(8,2),
PRIMARY KEY (cd_depto)
);
-- criando a tabela funcionário
CREATE TABLE funcionario
(cd_func INTEGER,
cd_depto INTEGER,
nm_fuc VARCHAR(50),
PRIMARY KEY (cd_func),
FOREIGN KEY(cd_depto) REFERENCES departamento (cd_depto)
departamento);