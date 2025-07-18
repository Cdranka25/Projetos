%Dados(Titulo, Genero, Diretor, Ano, Duracao, Avaliacao).


dados(amnesia,	suspense,	nolan,	2000, 113, 8.3).
dados(babel,	drama,	inarritu,	2006, 142, 7.4).
dados(capote,	drama,	miller,	2005, 98, 9.1).
dados(casablanca,	romance,	curtiz,	1942, 102, 6.0).
dados(matrix,	ficcao,	wachowsk,	1999, 136, 9.4).
dados(rebecca,	suspense,	hichock,	1940, 130, 4.1).
dados(sherek,	aventura,	adamson,	2001, 90, 9.0).
dados(sinais,	ficcao,	shymalan,	2002, 106, 9.6).
dados(spartacus,	acao,	kubrik,	1960, 184, 7.0).
dados(superman,	aventura,	donner,	1978, 413, 8.9).
dados(titanic,	romance,	cameron,	1997, 194, 9.5).
dados(tubarao,	suspense,	spielberg,	1975, 124, 4.5).
dados(volver,	drama,	almodovar,	2006, 121, 5.2).
%dados(teste,	drama,	almodovar,	2006, 121, 10).

%B)
diretor(X,Y) :- dados(X,_,Y,_,_,_).
pesquisa(X,Y) :- dados(Y,X,_,_,_,_).
donner(X,Y) :- dados(Y,_,X,_,_,_).
ano(X,Y) :- dados(X,_,_,Y,_,_).
inf100(X,Y) :- dados(Y,_,_,_,X,_), X=<100.
ent(X,Y) :- dados(Y,_,_,X,_,_), 2000=<X,X=<2005.
avi10(X,Y) :- dados(Y,_,_,_,_,X), X=10.

%C)
classico(X,Y) :- dados(X,_,_,Y,_,_), ano(X,Y),Y=<1980.

%D)
classificacao(X,Y) :- dados(X,_,_,_,_,Y), Y>=9,write(X), write(" Otimo"), nl, fail.
classificacao(X,Y) :- dados(X,_,_,_,_,Y), Y>=6.5, Y<9,write(X), write(" Bom"), nl, fail.
classificacao(X,Y) :- dados(X,_,_,_,_,Y), Y>=4.5, Y<6.5,write(X), write(" Regular"), nl, fail.
classificacao(X,Y) :- dados(X,_,_,_,_,Y), Y<4.5,write(X), write(" Ruim"), nl, fail.

%E)
clas_otimo(X,Y) :- dados(X,_,_,_,_,Y), Y>=9,write(X),nl, fail.
clas_bom(X,Y) :- dados(X,_,_,_,_,Y), Y>=6.5, Y<9,write(X), nl, fail.
clas_regular(X,Y) :- dados(X,_,_,_,_,Y), Y>=4.5, Y<6.5,write(X), nl, fail.
clas_ruim(X,Y) :- dados(X,_,_,_,_,Y), Y<4.5,write(X),nl, fail.
clas_filme(X,Y) :- dados(X,_,_,_,_,Y), classificacao(X,Y).



