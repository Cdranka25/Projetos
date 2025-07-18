bd_labs
CREATE TABLE equipamento (
  cd_equipamento CHAR(7)  NOT NULL,
  nr_sala INTEGER UNSIGNED  NOT NULL  ,
  dt_equissicao DATE  NULL  ,
  ds_configuracoes VARCHAR(200)  NULL    ,
PRIMARY KEY(cd_equipamento)  ,
INDEX equipamento_FKIndex1(nr_sala),
  FOREIGN KEY(nr_sala)
    REFERENCES sala(nr_sala)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION);



CREATE TABLE equipamento_software (
  cd_equipamento CHAR(7)  NOT NULL  ,
  cd_software INTEGER UNSIGNED  NOT NULL    ,
PRIMARY KEY(cd_equipamento, cd_software)  ,
INDEX equipamento_has_software_FKIndex1(cd_equipamento)  ,
INDEX equipamento_has_software_FKIndex2(cd_software),
  FOREIGN KEY(cd_equipamento)
    REFERENCES equipamento(cd_equipamento)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(cd_software)
    REFERENCES software(cd_software)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION);




