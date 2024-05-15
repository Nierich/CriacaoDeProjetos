CREATE TABLE usuario (
	id SERIAL NOT NULL,
	email VARCHAR(100) NOT NULL,
    senha VARCHAR(100) NOT NULL,
	hierarquia VARCHAR(45) NOT NULL,
	primary key(id)
);

CREATE TABLE projeto(
	id SERIAL NOT NULL,
	nome VARCHAR(100) NOT NULL,
	descricao VARCHAR(100) NOT NULL,
	primary key(id)
);

CREATE TABLE requisito(
	id SERIAL NOT NULL,
	descricao VARCHAR(100) NOT NULL,
	tipo VARCHAR(45) NOT NULL,
	complexidade VARCHAR(45) NOT NULL,
	projeto_id INT NOT NULL,
	primary key(id),
	constraint fk_projeto_id foreign key (projeto_id) references projeto (id)
);