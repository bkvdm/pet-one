--liquibase formatted sql
--changeset bkvdm:1
CREATE TABLE daily_report (
  id_daily_report BIGINT PRIMARY KEY AUTO_INCREMENT,
  id_pet BIGINT,
  date_time TIMESTAMP NOT NULL,
  well VARCHAR(255),
  reaction VARCHAR(255),
  foto_pet BLOB,
  is_check BOOLEAN NOT NULL,
  FOREIGN KEY (id_pet) REFERENCES Pets(id_pet)
);
CREATE TABLE pets (
  id_pet BIGINT PRIMARY KEY AUTO_INCREMENT,
  id_shelter BIGINT,
  name VARCHAR(255),
  type VARCHAR(255),
  busy_free BOOLEAN NOT NULL,
  date_take TIMESTAMP,
  data_present LONGBLOB,
  FOREIGN KEY (id_shelter) REFERENCES Shelters(id_shelter)
);
CREATE TABLE clients (
  id_client BIGINT PRIMARY KEY AUTO_INCREMENT,
  chat_id BIGINT NOT NULL,
  name VARCHAR(255),
  contact VARCHAR(255)
);
CREATE TABLE shelter (
  id_shelter BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255),
  operation_mode VARCHAR(255),
  contact VARCHAR(255),
  address VARCHAR(255),
  drilling_director VARCHAR(255),
  security_contact VARCHAR(255)
);
CREATE TABLE volunteers (
  id_volunteer BIGINT PRIMARY KEY AUTO_INCREMENT,
  chat_id BIGINT NOT NULL,
  name VARCHAR(255),
  contact VARCHAR(255)
);
