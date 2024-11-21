use LifeGreen
CREATE TABLE cadastro(
id int not null IDENTITY(1,1) PRIMARY KEY,
nome varchar(50) NOT NULL,
CPF varchar(14) NOT NULL,
telefone varchar(20) NOT NULL,
email varchar (40) NOT NULL,
senha varchar(20) NOT NULL,
);
alter table cadastro ADD endereco_id  int not null
alter table cadastro ADD CONSTRAINT FK_cadastro_endereco FOREIGN KEY(endereco_id) REFERENCES endereco(id);
truncate table cadastro
drop table cadastro


