-- Username: 'david'@'localhost', password: '1234'
-- mysql Ver 8.0.29

DROP DATABASE IF EXISTS catering;

CREATE DATABASE IF NOT EXISTS catering;

USE catering;
GRANT ALL PRIVILEGES ON *.* TO 'david'@'localhost';

CREATE TABLE IF NOT EXISTS Caterings (
    id INT AUTO_INCREMENT,
    name VARCHAR(100),
    menu VARCHAR(100),
    prices DOUBLE(10,2),
    sales VARCHAR(100),
    phoneNumber VARCHAR(100),
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS Deliveries (
    id INT AUTO_INCREMENT,
    Address VARCHAR(100),
    menu INT,
    prices DOUBLE(10,2),
    date VARCHAR(100),
    phoneNumber VARCHAR(100),
    PRIMARY KEY(id),
    FOREIGN KEY (catering_id) REFERENCES Caterings(id)
);

INSERT INTO Caterings(name, menu, prices, sales, phoneNumber) VALUES ("name1", "menuFood", 2.0, "2022-10-10", "0777")