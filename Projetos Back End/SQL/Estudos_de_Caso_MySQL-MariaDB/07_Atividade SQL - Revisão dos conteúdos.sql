DROP TABLE filme_ator;
DROP TABLE filme;
DROP TABLE ator;
DROP TABLE censura;
DROP TABLE genero;

create table Genero
	(cd_genero INTEGER,
	ds_genero VARCHAR(30),
	primary KEY (cd_genero)
 ); 

create table Censura
	(cd_censura INTEGER,
	ds_censura VARCHAR(30),
 	primary KEY (cd_censura)
 );

create table Ator
	(cd_ator INTEGER,
 	ds_ator VARCHAR(30),
 	primary KEY (cd_ator)
 );
 
CREATE TABLE Filme (
   cd_filme INTEGER,
   cd_censura INTEGER,
   cd_genero INTEGER,
   nm_filme VARCHAR(50),
   ds_sinopse VARCHAR(250),
   nr_duracao VARCHAR(20),
   dt_lancamento DATE,
   fl_dublado CHAR(1),
   PRIMARY KEY (cd_filme),
   FOREIGN KEY (cd_censura) REFERENCES Censura(cd_censura),
   FOREIGN KEY (cd_genero) REFERENCES Genero(cd_genero)
);
  
CREATE TABLE Filme_Ator (
   cd_filme INTEGER,
   cd_ator INTEGER,
   FOREIGN KEY (cd_filme) REFERENCES Filme(cd_filme),
   FOREIGN KEY (cd_ator) REFERENCES Ator(cd_ator)
);

ALTER TABLE Filme
ADD CONSTRAINT unique_nm_filme UNIQUE (nm_filme);

ALTER TABLE ator
ADD CONSTRAINT unique_nome_ator UNIQUE (ds_ator);


INSERT INTO Genero (cd_genero, ds_genero)
VALUES 
    (1, 'Ação'),
    (2, 'Comédia'),
    (3, 'Drama');

INSERT INTO Censura (cd_censura, ds_censura)
VALUES 
    (1, 'Livre'),
    (2, '14 anos'),
    (3, '18 anos');
    
INSERT INTO Ator (cd_ator, ds_ator)
VALUES 
    (1, 'Brad Pitt'),
    (2, 'Tom Hanks'),
    (3, 'Jennifer Lawrence'),
    (4, 'Leonardo DiCaprio'),
    (5, 'Emma Stone');

-- B) POVOAMENTO, , no mínimo: 03 gêneros de filme; 03 formas de censura; 05 atores; 05 filmes sendo 02 deles com mais de um ator associado;
INSERT INTO Filme (cd_filme, cd_censura, cd_genero, nm_filme, ds_sinopse, nr_duracao, dt_lancamento, fl_dublado)
VALUES 
    (1, 1, 2, 'O Rei Leão', 'Rei Leão', '90 minutos', '2023-01-01', 'S');

INSERT INTO Filme (cd_filme, cd_censura, cd_genero, nm_filme, ds_sinopse, dt_lancamento, fl_dublado)
VALUES 
    (2, 2, 1, 'Matrix', 'Matrix', '1999-03-31', 'N');

INSERT INTO Filme_Ator (cd_filme, cd_ator) VALUES (2, 1); 
INSERT INTO Filme_Ator (cd_filme, cd_ator) VALUES (2, 3); 

INSERT INTO Filme (cd_filme, cd_censura, cd_genero, nm_filme, ds_sinopse, nr_duracao, dt_lancamento, fl_dublado)
VALUES 
    (3, 3, 3, 'Harry Potter', 'Harry Potter', '142 minutos', '1994-07-06', 'S');

INSERT INTO Filme (cd_filme, cd_censura, cd_genero, nm_filme, ds_sinopse, nr_duracao, dt_lancamento, fl_dublado)
VALUES 
    (4, 2, 2, 'Madagascar', 'Madagascar', '109 minutos', '2011-07-22', 'S');

INSERT INTO Filme_Ator (cd_filme, cd_ator) VALUES (4, 2); 
INSERT INTO Filme_Ator (cd_filme, cd_ator) VALUES (4, 5); 

INSERT INTO Filme (cd_filme, cd_censura, cd_genero, nm_filme, ds_sinopse, nr_duracao, dt_lancamento, fl_dublado)
VALUES 
    (5, 1, 1, 'Velozes e Furiosos', 'Velozes e Furiosos', '137 minutos', '2001-06-22', 'N');
    

-- c) Alteração de todos os registros de filmes que não apresentam duração cadastrada (nr_duracao) para dublado (fl_dublado = 'S');
UPDATE f.*
FROM filme f
SET f.fl_dublado = 'S'
WHERE f.nr_duracao IS NULL;


-- d) Listagem de todos os filmes (nome, sinopse, duração, data de lançamento e indicação de dublagem (ou não), onde a publicação ocorreu no ano de 2023;
SELECT  f.nm_filme, f.ds_sinopse, f.nr_duracao, f.dt_lancamento, f.fl_dublado
FROM filme f
WHERE YEAR (f.dt_lancamento) = 2023;

--e) Exclusão de todos os filmes que não apresentam sinopse (ds_sinopse) cadastrada.
DELETE FROM filme f
WHERE f.ds_sinopse IS NULL;


SELECT * FROM filme;

