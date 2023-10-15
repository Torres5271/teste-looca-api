drop database safesync2;
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



create table if not exists hardwares(
id int primary key auto_increment,
sistemaOperacional varchar(50),
consumoCpu double not null,
consumoDisco double not null,
consumoRam double not null,
totalDisco double NOT NULL,
totalCpu int NOT NULL,
totalRam double NOT NULL,
dataHora datetime,
fkFuncionario int,
foreign key (fkFuncionario) references funcionarios(idFuncionario)
);

create table if not exists tipoDeHardware(
id int primary key auto_increment,
tipoComponente varchar(45),
min int,
max int,
fkHardware int,
foreign key (fkHardware) references hardwares(id)
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

