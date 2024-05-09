--liquibase formatted sql
--changeset bkvdm:1
CREATE TABLE clients
(
    id_client   BIGSERIAL PRIMARY KEY,
    chat_id     BIGINT NOT NULL,
    name_client VARCHAR(255),
    contact     VARCHAR(255)
);

CREATE TABLE shelters
(
    id_shelter        BIGSERIAL PRIMARY KEY,
    name_shelter      VARCHAR(255),
    operation_mode    VARCHAR(255),
    contact           VARCHAR(255),
    address           VARCHAR(255),
    drilling_director VARCHAR(255),
    security_contact  VARCHAR(255)
);

CREATE TABLE view_pets
(
    id_view_pet   BIGSERIAL PRIMARY KEY,
    name_view_pet VARCHAR(255) NOT NULL
);

CREATE TABLE pets
(
    id_pet      BIGSERIAL PRIMARY KEY,
    id_shelter  BIGINT  NOT NULL,
    id_client   BIGINT,
    id_view_pet BIGINT  NOT NULL,
    name_pet    VARCHAR(255),
    busy_free   BOOLEAN NOT NULL,
    date_take   TIMESTAMP,
    FOREIGN KEY (id_shelter) REFERENCES shelters (id_shelter),
    FOREIGN KEY (id_client) REFERENCES clients (id_client),
    FOREIGN KEY (id_view_pet) REFERENCES view_pets (id_view_pet)
);

CREATE TABLE picture_pets
(
    id_picture_pet BIGSERIAL PRIMARY KEY,
    file_path      VARCHAR(255),
    file_size      BIGINT,
    media_type     VARCHAR(255),
    data_form      BYTEA,
    id_pet         BIGINT UNIQUE NOT NULL,
    FOREIGN KEY (id_pet) REFERENCES pets (id_pet)
);

CREATE TABLE daily_reports
(
    id_daily_report BIGSERIAL PRIMARY KEY,
    id_pet          BIGINT    NOT NULL,
    date_time       TIMESTAMP NOT NULL,
    well            VARCHAR(255),
    reaction        VARCHAR(255),
    is_check        BOOLEAN   NOT NULL,
    FOREIGN KEY (id_pet) REFERENCES pets (id_pet)
);

CREATE TABLE picture_daily_reports
(
    id_picture_daily_report BIGSERIAL PRIMARY KEY,
    file_path               VARCHAR(255),
    file_size               BIGINT,
    media_type              VARCHAR(255),
    data_form               BYTEA,
    id_daily_report         BIGINT UNIQUE NOT NULL,
    FOREIGN KEY (id_daily_report) REFERENCES daily_reports (id_daily_report)
);

CREATE TABLE volunteers
(
    id_volunteer   BIGINT AUTO_INCREMENT PRIMARY KEY,
    chat_id        BIGINT NOT NULL,
    name_volunteer VARCHAR(255),
    contact        VARCHAR(255)
);

CREATE TABLE content_forms
(
    id_content   BIGSERIAL PRIMARY KEY,
    name_content TEXT NOT NULL,
    content      TEXT
);

CREATE TABLE button_menus
(
    id_button_menu BIGSERIAL PRIMARY KEY,
    menu_name      TEXT NOT NULL,
    menu_header    TEXT
);

CREATE TABLE content_menu
(
    id_content     BIGINT NOT NULL,
    id_button_menu BIGINT NOT NULL,
    PRIMARY KEY (id_content, id_button_menu),
    FOREIGN KEY (id_content) REFERENCES content_forms (id_content),
    FOREIGN KEY (id_button_menu) REFERENCES button_menus (id_button_menu)
);

CREATE TABLE forms
(
    id_form    BIGSERIAL PRIMARY KEY,
    file_path  VARCHAR(255) NOT NULL,
    file_size  BIGINT       NOT NULL,
    media_type VARCHAR(255) NOT NULL,
    data_form  BYTEA     NOT NULL,
    id_content BIGINT,
    UNIQUE (id_content),
    FOREIGN KEY (id_content) REFERENCES content_forms (id_content)
);