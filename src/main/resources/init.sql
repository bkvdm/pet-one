--liquibase formatted sql
--changeset bkvdm:1
CREATE TABLE clients
(
    id_client BIGINT AUTO_INCREMENT PRIMARY KEY,
    chat_id   BIGINT NOT NULL,
    name      VARCHAR(255),
    contact   VARCHAR(255)
);

CREATE TABLE shelters
(
    id_shelter        BIGINT AUTO_INCREMENT PRIMARY KEY,
    name              VARCHAR(255),
    operation_mode    VARCHAR(255),
    contact           VARCHAR(255),
    address           VARCHAR(255),
    drilling_director VARCHAR(255),
    security_contact  VARCHAR(255)
);

CREATE TABLE pets
(
    id_pet      BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_shelter  BIGINT,
    id_client   BIGINT,
    name        VARCHAR(255),
    type        VARCHAR(255),
    busy_free   BOOLEAN NOT NULL,
    date_take   TIMESTAMP,
    picture_pet LONGBLOB,
    FOREIGN KEY (id_shelter) REFERENCES shelters (id_shelter),
    FOREIGN KEY (id_client) REFERENCES clients (id_client)
);

CREATE TABLE daily_reports
(
    id_daily_report      BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_pet               BIGINT    NOT NULL,
    date_time            TIMESTAMP NOT NULL,
    well                 VARCHAR(255),
    reaction             VARCHAR(255),
    picture_daily_report LONGBLOB,
    is_check             BOOLEAN   NOT NULL,
    FOREIGN KEY (id_pet) REFERENCES pets (id_pet)
);

CREATE TABLE volunteers
(
    id_volunteer BIGINT AUTO_INCREMENT PRIMARY KEY,
    chat_id      BIGINT NOT NULL,
    name         VARCHAR(255),
    contact      VARCHAR(255)
);