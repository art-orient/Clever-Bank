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

DROP TABLE IF EXISTS accounts;
CREATE table accounts (
   code_iban varchar(40) NOT NULL PRIMARY KEY,
   bank_bic_code varchar(8) NOT NULL,
   user_passport_id varchar(16) NOT NULL,
   currency varchar(8) NOT NULL,
   created_at timestamp NOT NULL,
   balance numeric
);

DROP TABLE IF EXISTS transactions;
CREATE table transactions (
   action_id serial NOT NULL PRIMARY KEY,
   completed_at timestamp NOT NULL,
   transaction_type varchar(40) NOT NULL,
   from_iban varchar(40) NOT NULL,
   to_iban varchar(40) NOT NULL,
   currency varchar(8) NOT NULL,
   amount numeric
);