CREATE TABLE FUNCIONARIO (
    id_funcionario NUMBER(38) NOT NULL,
    nome           VARCHAR2(250) NOT NULL,
    matricula      VARCHAR2(100) UNIQUE NOT NULL,
    PRIMARY KEY (id_funcionario)
);

CREATE TABLE CLIENTE (
    id_cliente NUMBER(38) NOT NULL,
    nome       VARCHAR2(250) NOT NULL,
    cpf        VARCHAR2(14) UNIQUE NOT NULL,
    telefone   VARCHAR2(14) NOT NULL,
    endereco   VARCHAR2(200) NOT NULL,
    email      VARCHAR2(250) NOT NULL,
    PRIMARY KEY (id_cliente)
);

CREATE TABLE CARRO (
    id_carro               NUMBER(38) NOT NULL,
    alugado                CHAR(1),
    nome                   VARCHAR2(200) NOT NULL,
    marca                  VARCHAR2(200) NOT NULL,
    classe                 CHAR(1)       NOT NULL,
    quantidade_passageiros NUMBER(38) NOT NULL,
    km_rodados             NUMBER(38) NOT NULL,
    preco_diaria           DECIMAL(8, 2) NOT NULL,
    PRIMARY KEY (id_carro)
);

CREATE TABLE ALUGUEL (
    id_aluguel   NUMBER(38) NOT NULL,
    id_carro     NUMBER(38) NOT NULL,
    id_cliente   NUMBER(38) NOT NULL,
    diaDoAluguel DATE          NOT NULL,
    diaDaEntrega DATE          NOT NULL,
    valor        DECIMAL(8, 2) NOT NULL,
    PRIMARY KEY (id_aluguel),
    CONSTRAINT FK_CLIENTE_ALUGUEL FOREIGN KEY (ID_CLIENTE) REFERENCES CLIENTE (ID_CLIENTE),
    CONSTRAINT FK_CARRO_ALUGUEL FOREIGN KEY (ID_CARRO) REFERENCES CARRO (ID_CARRO)
);

CREATE SEQUENCE SEQ_FUNCIONARIO
    START WITH 1
    INCREMENT BY 1
    NOCACHE NOCYCLE;

CREATE SEQUENCE SEQ_CLIENTE
    START WITH 1
    INCREMENT BY 1
    NOCACHE NOCYCLE;

CREATE SEQUENCE SEQ_CARRO
    START WITH 1
    INCREMENT BY 1
    NOCACHE NOCYCLE;

CREATE SEQUENCE SEQ_ALUGUEL
    START WITH 1
    INCREMENT BY 1
    NOCACHE NOCYCLE;

-- INSERT'S DOS FUNCIONARIOS;
----------------------------------------------------------------------------------------------
INSERT INTO FUNCIONARIO (ID_FUNCIONARIO, NOME, MATRICULA) VALUES (SEQ_FUNCIONARIO.nextval, 'Fernando Passos', '12141');

INSERT INTO FUNCIONARIO (ID_FUNCIONARIO, NOME, MATRICULA) VALUES (SEQ_FUNCIONARIO.nextval, 'Carine Soares', '12142');

INSERT INTO FUNCIONARIO (ID_FUNCIONARIO, NOME, MATRICULA) VALUES (SEQ_FUNCIONARIO.nextval, 'Patr??cia Campos', '12143');

INSERT INTO FUNCIONARIO (ID_FUNCIONARIO, NOME, MATRICULA) VALUES (SEQ_FUNCIONARIO.nextval, 'Aline Martins', '12144');
----------------------------------------------------------------------------------------------

-- INSERT'S DOS CLIENTES;
--------------------------------------------------------------------------------------------------
INSERT INTO CLIENTE (ID_CLIENTE, NOME, CPF, TELEFONE, ENDERECO, EMAIL) VALUES (SEQ_CLIENTE.nextval, 'Willian Valentim', '000.111.000-10', '(61)99999-9999', 'Rua Presidente Dutra, 09, Centro', 'aaaa@gmail.com');

INSERT INTO CLIENTE (ID_CLIENTE, NOME, CPF, TELEFONE, ENDERECO, EMAIL) VALUES (SEQ_CLIENTE.nextval, 'Paulo Ricardo', '000.222.000-12', '(61)99999-9998', 'Rua Presidente Dutra, 154, Bairro Maria C??ndida', 'bbbb@gmail.com');

INSERT INTO CLIENTE (ID_CLIENTE, NOME, CPF, TELEFONE, ENDERECO, EMAIL) VALUES (SEQ_CLIENTE.nextval, 'Maicon Santos', '000.333.000-23', '(61)99999-9997', 'Avenida Ant??nio Carlos Guimar???es, 154A, Centro', 'cccc@gmail.com');

INSERT INTO CLIENTE (ID_CLIENTE, NOME, CPF, TELEFONE, ENDERECO, EMAIL) VALUES (SEQ_CLIENTE.nextval, 'Rafael Lima', '000.444.000-34', '(61)99999-9996', 'Avenida Boolevard, apartamento 1001, Parque dos Viajantes', 'dddd@gmail.com');

INSERT INTO CLIENTE (ID_CLIENTE, NOME, CPF, TELEFONE, ENDERECO, EMAIL) VALUES (SEQ_CLIENTE.nextval, 'Milton Nascimento', '000.555.000-45', '(61)99999-9995', 'Avenida das Flores, apartamento 505, Pra??a das Flores', 'eeee@gmail.com');
---------------------------------------------------------------------------------------------------

---------------------------------------------------------------------------------------------------

-- INSERT'S DA LISTA DE CARRO;
---------------------------------------------------------------------------------------------------
INSERT INTO CARRO (ID_CARRO, ALUGADO, NOME, MARCA, CLASSE, QUANTIDADE_PASSAGEIROS, KM_RODADOS, PRECO_DIARIA) VALUES(SEQ_CARRO.nextval, 'N', 'P???lio', 'Fiat', 'C', 5, 20000, 200);

INSERT INTO CARRO (ID_CARRO, ALUGADO, NOME, MARCA, CLASSE, QUANTIDADE_PASSAGEIROS, KM_RODADOS, PRECO_DIARIA) VALUES(SEQ_CARRO.nextval, 'N', 'Gol', 'Volkswagen', 'C', 5, 25000, 300);

INSERT INTO CARRO (ID_CARRO, ALUGADO, NOME, MARCA, CLASSE, QUANTIDADE_PASSAGEIROS, KM_RODADOS, PRECO_DIARIA) VALUES(SEQ_CARRO.nextval, 'N', 'Onix', 'Chevrolet', 'C', 5, 30000, 400);

INSERT INTO CARRO (ID_CARRO, ALUGADO, NOME, MARCA, CLASSE, QUANTIDADE_PASSAGEIROS, KM_RODADOS, PRECO_DIARIA) VALUES(SEQ_CARRO.nextval, 'N', 'Compass', 'Jeep', 'B', 5, 10000, 500);

INSERT INTO CARRO (ID_CARRO, ALUGADO, NOME, MARCA, CLASSE, QUANTIDADE_PASSAGEIROS, KM_RODADOS, PRECO_DIARIA) VALUES(SEQ_CARRO.nextval, 'N', 'Civic', 'Honda', 'B', 5, 10000, 500);

INSERT INTO CARRO (ID_CARRO, ALUGADO, NOME, MARCA, CLASSE, QUANTIDADE_PASSAGEIROS, KM_RODADOS, PRECO_DIARIA) VALUES(SEQ_CARRO.nextval, 'N', 'T-Cross', 'Volkswagen', 'B', 5, 15000, 400);

INSERT INTO CARRO (ID_CARRO, ALUGADO, NOME, MARCA, CLASSE, QUANTIDADE_PASSAGEIROS, KM_RODADOS, PRECO_DIARIA) VALUES(SEQ_CARRO.nextval, 'N', 'Elegance', 'Mercedes-Benz', 'A', 5, 20000, 500);

INSERT INTO CARRO (ID_CARRO, ALUGADO, NOME, MARCA, CLASSE, QUANTIDADE_PASSAGEIROS, KM_RODADOS, PRECO_DIARIA) VALUES(SEQ_CARRO.nextval, 'N', '911 GTS', 'Porsche', 'A', 5, 5000, 1000);

INSERT INTO CARRO (ID_CARRO, ALUGADO, NOME, MARCA, CLASSE, QUANTIDADE_PASSAGEIROS, KM_RODADOS, PRECO_DIARIA) VALUES(SEQ_CARRO.nextval, 'N', 'Shelby GT500', 'Mustang', 'A', 5, 5000, 900);



SELECT * FROM ALUGUEL
SELECT * FROM CLIENTE
SELECT * FROM CARRO
SELECT * FROM FUNCIONARIO
SELECT * FROM USUARIO