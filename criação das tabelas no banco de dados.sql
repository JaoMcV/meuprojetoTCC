
create database dbteste;

use dbteste;
create table usuarios(
iduser int primary key,
usuario varchar(50) not null,
fone varchar(15) null,
login varchar(15) not null unique,
senha varchar(15)not null,
perfil varchar(20) not null
);

insert into usuarios (iduser, usuario,fone,login,senha,perfil) 
values ("1","joao","40999990808", "joao","1234","admin");

create table clientes(
idcliente int primary key auto_increment,
numeroprocesso varchar(20),
nome varchar (50) not null,
CPF   varchar(20) not null,
RG    varchar(20),
telefone varchar(20),
email varchar(50),
endereco varchar(50) not null,
numerodacasa varchar(8),
complemento varchar(30),
bairro varchar(50) not null,
cep varchar(10),
cidade varchar(50)
);

create table OS (
os int primary key auto_increment,
data_os timestamp default current_timestamp,
tipo varchar(100) not null,
tipo_de_servico varchar(50) not null,
total decimal(10, 2)  not null,
valor_entrada decimal(10,2)  not null,
forma_pagamento varchar(15) not null,
numero_de_parcelas varchar(50) not null,
valor_parcelas decimal(10,2) not null,
obs varchar(150) null,
idcliente int not null,
foreign key (idcliente) references clientes(idcliente)
);
















