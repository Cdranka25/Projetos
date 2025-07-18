
-- busca pelos dados do veiculo
SELECT v.nr_placa AS "número da placa",
		 v.nr_ano_mod ano_modelo, 
		 v.ds_observacao AS "obsercação",
		 UPPER(v.ds_observacao) AS maiu,
		 LOWER(v.ds_observacao) AS minu
		 
FROM veiculo v
WHERE v.ds_observacao LIKE '%leil_o%'
