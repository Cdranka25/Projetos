


-- a)     Listar a média de km rodados entre os veículos cadastrados;
SELECT AVG (v.qt_km_rodado)
FROM veiculo v;

-- b)     Listar o ano de fabricação do(s) veículo(s) mais novo e ano de fabricação do(s) veículo(s) mais antigo cadastrado na base de dados;
SELECT MIN(v.nr_ano_fab) AS ano_fab_mais_antigo, MAX(v.nr_ano_fab) AS ano_fab_mais_novo
FROM veiculo v ;

-- c)     Listar o ano modelo do veículo com a respectiva quantidade de veículos correspondente ao ano listado;
SELECT v.nr_ano_mod, COUNT(*) AS quantidade_veiculos
FROM veiculo v;
GROUP BY v.nr_ano_mod;

-- d)     Listar a descrição do combustível com a respectiva quantidade de veículos que apresentam o combustível, ordenando pelo maior número de veículos descendente;
SELECT c.ds_combustivel, COUNT(vc.nr_placa) AS quantidade_veiculos
FROM veiculo_combustivel vc
JOIN combustivel c ON vc.cd_combustivel = c.cd_combustivel
GROUP BY c.ds_combustivel
ORDER BY quantidade_veiculos DESC;

-- e)     Listar a descrição do modelo com a respectiva quantidade de veículo(s) correspondente ao modelo listado;
SELECT m.ds_modelo, COUNT(v.nr_placa) AS quantidade_veiculos
FROM veiculo v
JOIN modelo m ON v.cd_modelo = m.cd_modelo
GROUP BY m.ds_modelo;

-- f)     Listar todas as marcas (descrição) disponíveis com o respetivo modelo (descrição), bem como a quantidade de veículo associada a marca e modelo listado;
SELECT ma.ds_marca, mo.ds_modelo, COUNT(v.nr_placa) AS quantidade_veiculos
FROM veiculo v
JOIN modelo mo ON v.cd_modelo = mo.cd_modelo
JOIN marca ma ON mo.cd_marca = ma.cd_marca
GROUP BY ma.ds_marca, mo.ds_modelo;

-- g)     Listar a descrição do acessório com a respectiva quantidade de veículo que possuem o referido acessório listado.
SELECT a.ds_acessorio, COUNT(va.nr_placa) AS quantidade_veiculos
FROM veiculo_acessorio va
JOIN acessorio a ON va.cd_acessorio = a.cd_acessorio
GROUP BY a.ds_acessorio;


-- h)     Listar o nome da localidade com a respectiva quantidade de proprietários vinculado(s) a cada localidade, restringindo ao estado de "SC";
SELECT l.nm_localidade, COUNT(p.cd_proprietario) AS quantidade_proprietarios
FROM proprietario p
JOIN localidade l ON p.cd_localidade = l.cd_localidade
WHERE p.sg_uf = 'SC'
GROUP BY l.nm_localidade;


-- i)     Listar dados dos veículos: placa, descrição da marca, descrição do modelo e quantidade de acessórios associado a cada veículo listado,
--			 sendo que os veículos devem apresentar dois ou mais acessórios.
SELECT v.nr_placa, ma.ds_marca, mo.ds_modelo, COUNT(va.cd_acessorio) AS quantidade_acessorios
FROM veiculo v
JOIN modelo mo ON v.cd_modelo = mo.cd_modelo
JOIN marca ma ON mo.cd_marca = ma.cd_marca
JOIN veiculo_acessorio va ON v.nr_placa = va.nr_placa
GROUP BY v.nr_placa, ma.ds_marca, mo.ds_modelo
HAVING COUNT(va.cd_acessorio) >= 2;


-- j)     Listar dados dos veículos: placa, descrição da marca, descrição do modelo e quantidade de combustíveis associados a cada veículo listado.
SELECT v.nr_placa, ma.ds_marca, mo.ds_modelo, COUNT(vc.cd_combustivel) AS quantidade_combustiveis
FROM veiculo v
JOIN modelo mo ON v.cd_modelo = mo.cd_modelo
JOIN marca ma ON mo.cd_marca = ma.cd_marca
JOIN veiculo_combustivel vc ON v.nr_placa = vc.nr_placa
GROUP BY v.nr_placa, ma.ds_marca, mo.ds_modelo;



-- Código:

-- exclusão das tabelas
drop table veiculo_combustivel;
drop table veiculo_acessorio;
drop table veiculo;
drop table modelo;
drop table proprietario;
drop table localidade;
drop table cor;
drop table marca;
drop table acessorio;
drop table combustivel;

-- criação das tabelas
create table localidade
(cd_localidade integer,
 nm_localidade varchar(50),
 constraint localidade_pk primary key (cd_localidade)
);

create table marca
(cd_marca   integer,
 ds_marca   varchar(30),
 primary key (cd_marca)
);

create table combustivel
(cd_combustivel integer,
 ds_combustivel varchar(30),
 primary key (cd_combustivel)
);

create table cor
(cd_cor   integer,
 ds_cor   varchar(30),
 primary key (cd_cor)
);

create table acessorio
(cd_acessorio  integer,
 ds_acessorio  varchar(50),
 primary key (cd_acessorio)
);

create table modelo
(cd_modelo integer,
 cd_marca integer,
 ds_modelo varchar(50),
 primary key (cd_modelo)
);
alter table modelo
  add foreign key (cd_marca) references marca(cd_marca);
  

create table proprietario
(cd_proprietario  integer,
 cd_localidade    integer,
 nm_proprietario  varchar(50),
 ds_logradouro    varchar(50),
 ds_complemento   varchar(50),
 ds_bairro        varchar(50),
 nr_telefone      varchar(15),
 ds_email         varchar(50),
 sg_uf            char(2),
 constraint proprietario_pk primary key (cd_proprietario),
 constraint proprietario_localidade_fk foreign key 
     (cd_localidade) references localidade(cd_localidade)
);  

create table veiculo
(nr_placa        char(7),
 cd_cor          integer,
 cd_proprietario integer,
 cd_modelo       integer,
 nr_ano_fab      integer,
 nr_ano_mod      integer,
 qt_km_rodado    integer,
 qt_portas       integer,
 ds_observacao   varchar(100),
 constraint veiculo_pk primary key (nr_placa),
 constraint veiculo_cor_fk foreign key (cd_cor) 
    references cor(cd_cor),
 constraint veiculo_modelo_fk foreign key (cd_modelo) 
    references modelo(cd_modelo),
 constraint veiculo_proprietario_fk foreign key (cd_proprietario) 
    references proprietario (cd_proprietario) 
);

create table veiculo_acessorio
(nr_placa     char(7),
 cd_acessorio integer,
 constraint veiculo_acessorio_pk 
   primary key (nr_placa, cd_acessorio),
 constraint veiculo_acessorio_placa_fk foreign key (nr_placa)
   references veiculo(nr_placa),
 constraint veiculo_acessorio_acessorio_fk foreign key (cd_acessorio)
   references acessorio(cd_acessorio) 
);

create table veiculo_combustivel
(cd_combustivel integer,
 nr_placa       char(7),
 constraint veiculo_combustivel_pk 
    primary key (cd_combustivel, nr_placa)
);
alter table veiculo_combustivel
add constraint veic_combus_combustivel_fk foreign key (cd_combustivel)
    references combustivel(cd_combustivel);
alter table veiculo_combustivel
add constraint veic_combus_placa_fk foreign key (nr_placa)
    references veiculo(nr_placa);	


-- inserindo dados nas tabelas...
insert into localidade (cd_localidade,nm_localidade) values (1,'Blumenau');
insert into localidade (cd_localidade,nm_localidade) values (2,'Gaspar');
insert into localidade (cd_localidade,nm_localidade) values (3,'Ilhota');
insert into localidade (cd_localidade,nm_localidade) values (4,'Curitiba');
insert into localidade (cd_localidade,nm_localidade) values (5,'Porto Alegre');
insert into localidade (cd_localidade,nm_localidade) values (6,'São Paulo');

insert into marca (cd_marca, ds_marca) values (1,'VW');
insert into marca (cd_marca, ds_marca) values (2,'Chevrolet');
insert into marca (cd_marca, ds_marca) values (3,'Ford');
insert into marca (cd_marca, ds_marca) values (4,'Fiat');
insert into marca (cd_marca, ds_marca) values (5,'Renault');
insert into marca (cd_marca, ds_marca) values (6,'Jeep');
insert into marca (cd_marca, ds_marca) values (7,'Audi');
insert into marca (cd_marca, ds_marca) values (8,'BMW');
insert into marca (cd_marca, ds_marca) values (9,'Mitsubish');
insert into marca (cd_marca, ds_marca) values (10,'Citroen');

insert into combustivel (cd_combustivel, ds_combustivel) values (1,'Gasolina');
insert into combustivel (cd_combustivel, ds_combustivel) values (2,'Etanol');
insert into combustivel (cd_combustivel, ds_combustivel) values (3,'Diesel');
insert into combustivel (cd_combustivel, ds_combustivel) values (4,'GNV');
insert into combustivel (cd_combustivel, ds_combustivel) values (5,'Elétrico');

insert into cor (cd_cor, ds_cor) values (1,'Branco');
insert into cor (cd_cor, ds_cor) values (2,'Preto');
insert into cor (cd_cor, ds_cor) values (3,'Prata');
insert into cor (cd_cor, ds_cor) values (4,'Azul');
insert into cor (cd_cor, ds_cor) values (5,'Vermelho');
insert into cor (cd_cor, ds_cor) values (6,'Amarelo');
insert into cor (cd_cor, ds_cor) values (7,'Verde');
insert into cor (cd_cor, ds_cor) values (8,'Cinza');
insert into cor (cd_cor, ds_cor) values (9,'Areia');
insert into cor (cd_cor, ds_cor) values (10,'Chumbo');

insert into acessorio (cd_acessorio, ds_acessorio) values (1,'Direção Hidráulica');
insert into acessorio (cd_acessorio, ds_acessorio) values (2,'Ar Condicionado');
insert into acessorio (cd_acessorio, ds_acessorio) values (3,'Direção Elétrica');
insert into acessorio (cd_acessorio, ds_acessorio) values (4,'Bancos em couro');
insert into acessorio (cd_acessorio, ds_acessorio) values (5,'Multimídia');
insert into acessorio (cd_acessorio, ds_acessorio) values (6,'GPS');
insert into acessorio (cd_acessorio, ds_acessorio) values (7,'Teto solar');
insert into acessorio (cd_acessorio, ds_acessorio) values (8,'Rodas Liga-leve');
insert into acessorio (cd_acessorio, ds_acessorio) values (9,'Painel digital');
insert into acessorio (cd_acessorio, ds_acessorio) values (10,'Air Bag frontais');
insert into acessorio (cd_acessorio, ds_acessorio) values (11,'Air Bag laterais');
insert into acessorio (cd_acessorio, ds_acessorio) values (12,'Suspenção Hidráulica');

insert into modelo (cd_modelo, cd_marca, ds_modelo) values (1,1,'Up TSI');
insert into modelo (cd_modelo, cd_marca, ds_modelo) values (2,1,'Gol 1.6');
insert into modelo (cd_modelo, cd_marca, ds_modelo) values (3,1,'T-Cross 1.0');
insert into modelo (cd_modelo, cd_marca, ds_modelo) values (4,2,'Cruze LTZ');
insert into modelo (cd_modelo, cd_marca, ds_modelo) values (5,2,'Onix Turbo LT');
insert into modelo (cd_modelo, cd_marca, ds_modelo) values (6,3,'Fiesta 1.0');
insert into modelo (cd_modelo, cd_marca, ds_modelo) values (7,3,'Ranger CD');
insert into modelo (cd_modelo, cd_marca, ds_modelo) values (8,4,'Argo 1.0');
insert into modelo (cd_modelo, cd_marca, ds_modelo) values (9,5,'Kwid 1.0');
insert into modelo (cd_modelo, cd_marca, ds_modelo) values (10,5,'Sandero');
insert into modelo (cd_modelo, cd_marca, ds_modelo) values (11,7,'A4');
insert into modelo (cd_modelo, cd_marca, ds_modelo) values (12,7,'Q5');
insert into modelo (cd_modelo, cd_marca, ds_modelo) values (13,8,'X1');
insert into modelo (cd_modelo, cd_marca, ds_modelo) values (14,8,'X3');
insert into modelo (cd_modelo, cd_marca, ds_modelo) values (15,8,'X5');

insert into proprietario (cd_proprietario, cd_localidade, nm_proprietario, ds_logradouro, ds_complemento, ds_bairro, nr_telefone, ds_email, sg_uf)
  values (1,1,'Proprietario 1 Souza e Silva','Rua Antonio da Veiga','140','Itoupava Seca','4733210200','proprietario1@gmail.com','SC');
insert into proprietario (cd_proprietario, cd_localidade, nm_proprietario, ds_logradouro, ds_complemento, ds_bairro, nr_telefone, ds_email, sg_uf)
  values (2,2,'Proprietario 2 Camargo','Rua Sete Setembro','400','Centro','4733210001','camargo1@hotmail.com','SC');
insert into proprietario (cd_proprietario, cd_localidade, nm_proprietario, ds_logradouro, ds_complemento, ds_bairro, nr_telefone, ds_email, sg_uf)
  values (3,2,'Proprietario 3 Antunez','Rua XV de Novembro','500','Centro','47999990200','proprietario3@gmail.com','SC');
insert into proprietario (cd_proprietario, cd_localidade, nm_proprietario, ds_logradouro, ds_complemento, ds_bairro, nr_telefone, ds_email, sg_uf)
  values (4,4,'Proprietario 4 Silva Almeida','Av. Brasil','999','Vila Nova','472221200','proprietario4@gmail.com','PR');
insert into proprietario (cd_proprietario, cd_localidade, nm_proprietario, ds_logradouro, ds_complemento, ds_bairro, nr_telefone, ds_email, sg_uf)
  values (5,5,'Proprietario 5 Martins','Alameda Tricolor','100','Taubaté','5533210200','martins@gmail.com','RS');
insert into proprietario (cd_proprietario, cd_localidade, nm_proprietario, ds_logradouro, ds_complemento, ds_bairro, nr_telefone, ds_email, sg_uf)
  values (6,6,'Proprietario 6 Sem Sobrenome','Av. Paulista','1070','Centro','15777210200',null,'SP');
insert into proprietario (cd_proprietario, cd_localidade, nm_proprietario, ds_logradouro, ds_complemento, ds_bairro, nr_telefone, ds_email, sg_uf)
  values (7,6,'Proprietario 7 ','Av. Paulista','2177','Centro',null,'proprietario7@live.com','SP');

 
insert into veiculo (nr_placa,cd_cor,cd_proprietario,cd_modelo,nr_ano_fab,nr_ano_mod, qt_km_rodado,qt_portas,ds_observacao)
  values ('AAA1234',1,1,1,2010,2010,150000,4,null);
insert into veiculo (nr_placa,cd_cor,cd_proprietario,cd_modelo,nr_ano_fab,nr_ano_mod, qt_km_rodado,qt_portas,ds_observacao)
  values ('BBB5678',1,1,2,2014,2015,8000,2,null);  
insert into veiculo (nr_placa,cd_cor,cd_proprietario,cd_modelo,nr_ano_fab,nr_ano_mod, qt_km_rodado,qt_portas,ds_observacao)
  values ('LLL9999',1,2,4,2017,2017,30000,4,null);  
insert into veiculo (nr_placa,cd_cor,cd_proprietario,cd_modelo,nr_ano_fab,nr_ano_mod, qt_km_rodado,qt_portas,ds_observacao)
  values ('LXL7755',4,3,5,2012,2012,120000,2,'Passagem por leilão');    
insert into veiculo (nr_placa,cd_cor,cd_proprietario,cd_modelo,nr_ano_fab,nr_ano_mod, qt_km_rodado,qt_portas,ds_observacao)
  values ('AVX0010',5,4,6,2013,2014,99000,2,null);    
insert into veiculo (nr_placa,cd_cor,cd_proprietario,cd_modelo,nr_ano_fab,nr_ano_mod, qt_km_rodado,qt_portas,ds_observacao)
  values ('XVA1010',2,5,6,2019,2020,9000,4,null);    
insert into veiculo (nr_placa,cd_cor,cd_proprietario,cd_modelo,nr_ano_fab,nr_ano_mod, qt_km_rodado,qt_portas,ds_observacao)
  values ('AXV0010',3,7,9,2020,2020,20000,4,null);      
insert into veiculo (nr_placa,cd_cor,cd_proprietario,cd_modelo,nr_ano_fab,nr_ano_mod, qt_km_rodado,qt_portas,ds_observacao)
  values ('XXT0010',8,6,11,2018,2018, 30000,4,'Ótimo estado de conservação');        
insert into veiculo (nr_placa,cd_cor,cd_proprietario,cd_modelo,nr_ano_fab,nr_ano_mod, qt_km_rodado,qt_portas,ds_observacao)
  values ('QQL8790',9,5,12,2021,2021, 15000,4,'Estado de zero km');          
insert into veiculo (nr_placa,cd_cor,cd_proprietario,cd_modelo,nr_ano_fab,nr_ano_mod, qt_km_rodado,qt_portas,ds_observacao)
  values ('MLT9010',7,5,13,2019,2020, 20000,4,null);          

insert into veiculo_acessorio (nr_placa,cd_acessorio) values ('AAA1234',1);
insert into veiculo_acessorio (nr_placa,cd_acessorio) values ('AAA1234',2);
insert into veiculo_acessorio (nr_placa,cd_acessorio) values ('AAA1234',4);
insert into veiculo_acessorio (nr_placa,cd_acessorio) values ('AAA1234',5);
insert into veiculo_acessorio (nr_placa,cd_acessorio) values ('AAA1234',7);
insert into veiculo_acessorio (nr_placa,cd_acessorio) values ('BBB5678',3);
insert into veiculo_acessorio (nr_placa,cd_acessorio) values ('BBB5678',4);
insert into veiculo_acessorio (nr_placa,cd_acessorio) values ('LLL9999',2);
insert into veiculo_acessorio (nr_placa,cd_acessorio) values ('LLL9999',3);
insert into veiculo_acessorio (nr_placa,cd_acessorio) values ('LLL9999',7);
insert into veiculo_acessorio (nr_placa,cd_acessorio) values ('LXL7755',1);
insert into veiculo_acessorio (nr_placa,cd_acessorio) values ('LXL7755',4);
insert into veiculo_acessorio (nr_placa,cd_acessorio) values ('LXL7755',5);
insert into veiculo_acessorio (nr_placa,cd_acessorio) values ('LXL7755',7);
insert into veiculo_acessorio (nr_placa,cd_acessorio) values ('LXL7755',8);
insert into veiculo_acessorio (nr_placa,cd_acessorio) values ('AVX0010',2);
insert into veiculo_acessorio (nr_placa,cd_acessorio) values ('AVX0010',5);
insert into veiculo_acessorio (nr_placa,cd_acessorio) values ('XVA1010',3);
insert into veiculo_acessorio (nr_placa,cd_acessorio) values ('XXT0010',3);
insert into veiculo_acessorio (nr_placa,cd_acessorio) values ('XXT0010',4);
insert into veiculo_acessorio (nr_placa,cd_acessorio) values ('XXT0010',5);
insert into veiculo_acessorio (nr_placa,cd_acessorio) values ('XXT0010',6);
insert into veiculo_acessorio (nr_placa,cd_acessorio) values ('XXT0010',7);
insert into veiculo_acessorio (nr_placa,cd_acessorio) values ('QQL8790',1);
insert into veiculo_acessorio (nr_placa,cd_acessorio) values ('QQL8790',4);
insert into veiculo_acessorio (nr_placa,cd_acessorio) values ('QQL8790',6);
insert into veiculo_acessorio (nr_placa,cd_acessorio) values ('QQL8790',7);
insert into veiculo_acessorio (nr_placa,cd_acessorio) values ('MLT9010',7);

insert into veiculo_combustivel (cd_combustivel, nr_placa) VALUES (1,'AAA1234');
insert into veiculo_combustivel (cd_combustivel, nr_placa) VALUES (2,'AAA1234');
insert into veiculo_combustivel (cd_combustivel, nr_placa) VALUES (1,'BBB5678');
insert into veiculo_combustivel (cd_combustivel, nr_placa) VALUES (2,'LLL9999');
insert into veiculo_combustivel (cd_combustivel, nr_placa) VALUES (1,'LXL7755');
insert into veiculo_combustivel (cd_combustivel, nr_placa) VALUES (2,'LXL7755');
insert into veiculo_combustivel (cd_combustivel, nr_placa) VALUES (4,'LXL7755');
insert into veiculo_combustivel (cd_combustivel, nr_placa) VALUES (3,'AVX0010');
insert into veiculo_combustivel (cd_combustivel, nr_placa) VALUES (1,'XVA1010');
insert into veiculo_combustivel (cd_combustivel, nr_placa) VALUES (1,'XXT0010');
insert into veiculo_combustivel (cd_combustivel, nr_placa) VALUES (2,'XXT0010');
insert into veiculo_combustivel (cd_combustivel, nr_placa) VALUES (1,'QQL8790');
insert into veiculo_combustivel (cd_combustivel, nr_placa) VALUES (5,'QQL8790');
insert into veiculo_combustivel (cd_combustivel, nr_placa) VALUES (1,'MLT9010');
insert into veiculo_combustivel (cd_combustivel, nr_placa) VALUES (4,'MLT9010');
