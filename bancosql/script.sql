CREATE DATABASE IF NOT EXISTS carros;
USE carros;

CREATE TABLE IF NOT EXISTS carro (
    id INT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) DEFAULT NULL,
    tipo VARCHAR(100) DEFAULT NULL,
    PRIMARY KEY (id)
);

INSERT INTO carro (id, nome, tipo) VALUES
(1, 'Tucker 1948', 'classicos'),
(2, 'Chevrolet Corvette', 'classicos'),
(3, 'Chevrolet Impala Coupe', 'classicos'),
(4, 'Ferrari FF', 'esportivos'),
(5, 'AUDI GT Spyder', 'esportivos'),
(6, 'Porsche Panamera', 'esportivos'),
(7, 'Bugatti Veyron', 'luxo'),
(8, 'Ferrari Enzo', 'luxo'),
(9, 'Lamborghini Reventon', 'luxo');