create database safesync2;
use  safesync2;

CREATE TABLE IF NOT EXISTS empresas (
    idEmpresa INT PRIMARY KEY AUTO_INCREMENT,
    nomeFantasia VARCHAR(100) NOT NULL,
    razaoSocial VARCHAR(100) NOT NULL,
    cnpj CHAR(14) NOT NULL UNIQUE,
    cep CHAR(8) NOT NULL,
    numero INT NOT NULL,
    complemento VARCHAR(10),
    email VARCHAR(100) NOT NULL,
    telefone VARCHAR(20) NOT NULL,
    senhaEmpresa VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS funcionarios (
    idFuncionario INT PRIMARY KEY AUTO_INCREMENT,
    nomeFuncionario VARCHAR(100) NOT NULL,
    cargo VARCHAR(50) NOT NULL,
    cpf CHAR(11) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    telefone VARCHAR(15) NOT NULL,
    senha VARCHAR(20),
    fkEmpresa INT,
    FOREIGN KEY (fkEmpresa) REFERENCES empresas(idEmpresa)
);

CREATE TABLE IF NOT EXISTS hardwares (
    idHardware INT PRIMARY KEY AUTO_INCREMENT,
    sistemaOperacional varchar(50),
    processador int NOT NULL,
    ram double NOT NULL,
    disco double NOT NULL,
    fkFuncionario INT,
    FOREIGN KEY (fkFuncionario) REFERENCES funcionarios(idFuncionario)
);

create table if not exists historicos(
id int primary key auto_increment,
consumoDisco double not null,
consumoRam double not null,
consumoCpu double not null,
temperaturaHardware double not null,
dataHora datetime,
fkHardware int,
foreign key (fkHardware) references hardwares(idHardware)
);

create table if not exists tipoDeHardware(
id int primary key auto_increment,
tipoComponente varchar(45),
min int,
max int,
fkHardware int,
foreign key (fkHardware) references hardwares(idHardware)
);

CREATE TABLE IF NOT EXISTS arquivos (
  id INT PRIMARY KEY AUTO_INCREMENT,
  nome_arquivo VARCHAR(255) NOT NULL,
  tipo_arquivo VARCHAR(100) NOT NULL,
  tamanho_arquivo INT NOT NULL,
  dados_arquivo LONGBLOB NOT NULL,
  data_upload TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  fkFuncionario INT,
  FOREIGN KEY (fkfuncionario) REFERENCES funcionarios(idFuncionario)
);

