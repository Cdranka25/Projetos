
-- ALUNO: CAIO DALAGNOLI DRANKA
-- DATA: 27/06/2024

-- -----------------------------------------------------
-- -----------------------------------------------------
-- -----------------------------------------------------

-- Questão 01 - Criação das tabelas

CREATE TABLE Categoria
	(cd_categoria INTEGER,
	ds_categoria VARCHAR(50),
	PRIMARY KEY (cd_categoria)
	);

CREATE TABLE Hotel
	(cd_hotel INTEGER,
	nm_hotel VARCHAR(50),
	ds_website VARCHAR(50),
	ds_email VARCHAR(50),
	PRIMARY KEY (cd_hotel)
	);
	
CREATE TABLE Funcionario
(cd_funcionario INTEGER,
	cd_hotel INTEGER,
	nm_funcionario VARCHAR(50),
	ds_email VARCHAR(50),
	PRIMARY KEY (cd_funcionario),
	FOREIGN KEY (cd_hotel) REFERENCES Hotel (cd_hotel)
	);
	

CREATE TABLE Quarto
	(nr_quarto INTEGER,
	cd_hotel INTEGER,
	cd_categoria INTEGER,
	ds_quarto VARCHAR(50),
	nr_ocupantes INTEGER,
	PRIMARY KEY (nr_quarto),
	FOREIGN KEY (cd_hotel) REFERENCES Hotel (cd_hotel),
	FOREIGN KEY(cd_categoria) REFERENCES Categoria (cd_categoria)
	);

-- -----------------------------------------------------
-- -----------------------------------------------------
-- -----------------------------------------------------

-- Questão 02
-- a) alterar tamanho permitido de email para 100
ALTER TABLE Funcionario
MODIFY COLUMN ds_email VARCHAR(100);

-- b) tornar categoria unica
 ALTER TABLE Categoria
 ADD CONSTRAINT unique_ds_categoria UNIQUE (ds_categoria);

-- -----------------------------------------------------
-- -----------------------------------------------------
-- -----------------------------------------------------

-- Questão 03 - Inserir dados
-- 3 hoteis
-- 10 quartos
-- 3 categorias
-- 5 funcionarios ligados a 2 hoteis diferentes

INSERT INTO Categoria(cd_categoria, ds_categoria)
	VALUES
			(1,'Luxo'),
			(2,'Custo Beneficio'),
			(3,'Medio');

INSERT INTO Hotel (cd_hotel,nm_hotel,ds_website,ds_email)
	VALUES
		(1,'Beira-mar','www.beiramar.com','beiramar@gmail.com'),
		(2,'Monthez','www.Monthez.com','Monthez@gmail.com'),
		(3,'Jurassic Park Hotel', 'www.jurassicParkHotel.com','jurassicParkHotel.com');

INSERT INTO Quarto (nr_quarto, cd_hotel,cd_categoria, ds_quarto, nr_ocupantes)
	VALUES
		(1,1,1, 'Quarto 01 Beira Mar', 3),
		(2,1,2, 'Quarto 02 Beira Mar', 4),
		(3,1,3, 'Quarto 03 Beira Mar', 3),
		(4,2,1, 'Quarto 04 Monthez', 1),
		(5,2,2, 'Quarto 05 Monthez', 3),
		(6,2,3, 'Quarto 06 Monthez', 2),
		(7,3,1, 'Quarto 07 Jurassic Park Hotel', 6),
		(8,3,2, 'Quarto 08 Jurassic Park Hotel', 3),
		(9,3,3, 'Quarto 09 Jurassic Park Hotel', 4),
		(10,3,1, 'Quarto 10 Jurassic Park Hotel', 2);
		
INSERT INTO funcionario (cd_funcionario, cd_hotel, nm_funcionario, ds_email)
	VALUES
		(1, 1, 'Funcionario1', 'funcionario1@gmail.com'),
		(2, 2, 'Funcionario2', 'funcionario2@gmail.com'),
		(3, 3, 'Funcionario3', 'funcionario3@gmail.com'),
		(4, 1, 'Funcionario4', 'funcionario4@gmail.com'),
		(5, 3, 'Funcionario5', 'funcionario5@gmail.com');
		

-- -----------------------------------------------------
-- -----------------------------------------------------
-- -----------------------------------------------------

-- Questão 04 - Listar nome, website e quantidade de quartos em cada hotel listado
SELECT 
    h.nm_hotel AS Nome_Hotel,
    h.ds_website AS Website,
    COUNT(q.nr_quarto) AS Quantidade_Quartos
FROM 
    Hotel h
LEFT JOIN 
    Quarto q ON h.cd_hotel = q.cd_hotel
GROUP BY 
    h.nm_hotel, h.ds_website;

-- -----------------------------------------------------
-- -----------------------------------------------------
-- -----------------------------------------------------

-- Questão 05 - Listar nome dos hoteis que o numero de ocupantes foi igual ou maior a 4
SELECT 
    h.nm_hotel AS Nome_Hotel, h.ds_quarto
FROM 
    Hotel h
INNER JOIN 
    Quarto q ON h.cd_hotel = q.cd_hotel
WHERE 
    q.nr_ocupantes >= 4;
    
-- -----------------------------------------------------
-- -----------------------------------------------------
-- -----------------------------------------------------

-- Questão 06 - Listar categoria que não apresenta nenhum quarto cadastrado
-- Obs, todas as categorias estão sendo utilizadas
SELECT 
    c.ds_categoria AS DescricaoDaCategoria
FROM 
    Categoria c
WHERE 
    NOT EXISTS (
        SELECT 
            1
        FROM 
            Quarto q
        WHERE 
            q.cd_categoria = c.cd_categoria
    );
    
-- ----------------------------------------------------- 
-- -----------------------------------------------------
-- -----------------------------------------------------