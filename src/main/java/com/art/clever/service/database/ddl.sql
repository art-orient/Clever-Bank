--DROP DATABASE IF EXISTS clever;
--CREATE DATABASE clever;

DROP TABLE IF EXISTS users;
CREATE table users (
   passport varchar(16) NOT NULL PRIMARY KEY,
   lastname varchar(32) NOT NULL,
   firstname varchar(32) NOT NULL,
   surname varchar(32)
);
CREATE INDEX users_lastname_index ON users (lastname);

DROP TABLE IF EXISTS banks;
CREATE table banks (
   bic_code varchar(8) NOT NULL PRIMARY KEY,
   bank_name varchar(64) NOT NULL
);