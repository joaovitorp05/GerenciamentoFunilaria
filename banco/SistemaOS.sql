/**
 * Sistema para gestão de OS
 *@author João Vitor Pereira
 */
 
 create database dbsistema;
 use dbsistema;
 
 show tables;
 
 drop tables users;
 
-- unique (não permite valores  duplicados no campo)
 create table users (
     id int primary key auto_increment,
     nome varchar (250) not null,
     login varchar (250) not null unique,
     senha varchar (250) not null,
     perfil varchar(10) not null
 );
 
 describe users;
 -- uso do md5() para criptografar uma senha
insert into users(nome,login,senha,perfil)
values('Administrador','joao', md5('1234'), 'admin');
 select * from users;
 
 insert into users (nome,login,senha,perfil)
 values ('João Vitor','teste', md5('12345'),'user');
 
 insert into users (nome,login,senha)
 values ('João Vitor','admin', md5('12345'));
 
 insert into users (nome,login,senha)
 values ('Manoel Gomes','canetaazul','laele');
 
 insert into users (nome,login,senha)
 values ('Neymar Jr','neymito','craque');
 
 insert into users (nome,login,senha)
 values ('Gustavo Lima','gustalima','100010');
 
 insert into users (nome,login,senha)
 values ('Patrão','adm','102030');
 
 select * from users;
 select * from users where login = "admin";
 select * from users order by nome;
 select * from users order by nome desc;
 
 -- login (autenticação)
 select * from users where login = 'joao' and senha = md5('1234');
 
 -- busca avançada pelo nome (estilo google)
 select nome from users where nome like 'm%' order by nome;
 

  create table clientes (
     idcli int primary key auto_increment,
     nome varchar (500) not null,
     fone varchar (500) not null,
     cep varchar (100) not null,
     endereco varchar (400) not null,
     numero varchar (100) not null,
     complemento varchar (200),
     bairro varchar (300) not null,
     cidade varchar (300) not null,
     uf char (3) not null,
     veiculo varchar (250) not null,
     ano varchar (250) not null,
     placa varchar (250) not null unique,
     cor varchar (250) not null

     
);

use dbsistema;
select * from clientes;


/* Relacionamento de tabelas 1- N */

-- timestamp default current_timestamp (data e hora automática)
-- decimal (números não inteiros) 10,2 (dígitos, casas decimais)
-- 1 (FK) --------- N (PK)

create table servicos (
     os int primary key auto_increment,
     dataOS timestamp default current_timestamp,
     equipamento varchar(200) not null,
     defeito varchar(200) not null,
     valor decimal(000000000000000.00),
     idcli int not null,
     foreign key (idcli) references clientes(idcli)
); 

alter table servicos modify valor decimal(0000000000000000.00) not null;
insert into servicos (equipamento,defeito,valor,idcli)
values ('BMW X1','Lanternas traseiras',1000,5);

select * from servicos;

-- selecionando o conteúdo de 2 ou mais tabelas
select * from servicos
inner join clientes
on servicos.idcli = clientes.idcli;

drop table clientes;


/** RELATÓRIOS **/
-- clientes
select nome,fone from clientes order by nome ;

-- servicos
select 
servicos.os,servicos.dataOS,servicos.equipamento,servicos.defeito,
servicos.valor,
clientes.nome
from servicos
inner join clientes
on servicos.idcli = clientes.idcli;



 create table fornecedores (
     idfor int primary key auto_increment,
     nome varchar (500) not null,
     cnpj decimal (00000000000000) not null,
     fone varchar (500) not null,
     cep varchar (100) not null,
     endereco varchar (400) not null,
     numero varchar (100) not null,
     cidade varchar (300) not null,
     uf char (3) not null
     
);

select * from fornecedores;
drop table produtos;


create table produtos (
   codigoproduto int primary key auto_increment,
   codigodebarras decimal (14,0) not null,
   descricao varchar (500) not null,
   foto longblob not null,
   estoque int not null,
   estoquemin int not null,
   valor decimal (10,2) not null,
   unidadedemedida varchar (5) not null,
   localdearmazenagem varchar (30) not null
);

select * from produtos;

select
fornecedores.nome,fornecedores.cnpj,fornecedores.fone,fornecedores.cep,fornecedores.endereco,fornecedores.numero,fornecedores.cidade,fornecedores.uf,
produtos.descricao
from fornecedores
inner join produtos
on fornecedores.idfor = produtos.codigoproduto;


ALTER TABLE fornecedores ADD complemento VARCHAR(60);
ALTER TABLE fornecedores ADD bairro VARCHAR(100);
ALTER TABLE fornecedores MODIFY cnpj DECIMAL(14, 0) NOT NULL;

drop table users;


   
   




   




     
 