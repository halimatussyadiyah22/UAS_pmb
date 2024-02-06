create table Users(
    id bigserial not null primary key ,
    name varchar(100) not null,
    email varchar(100) not null ,
    phone varchar(100) not null ,
    password varchar(100) not null ,
    address varchar(100) not null ,
    enabled    BOOLEAN      DEFAULT TRUE,
    non_locked BOOLEAN      DEFAULT TRUE,
    using_mfa  BOOLEAN      DEFAULT FALSE,
    created_at TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    image_url  VARCHAR(255) DEFAULT 'https://cdn-icons-png.flaticon.com/512/149/149071.png',
    CONSTRAINT UQ_Users_Email UNIQUE (email)
);
CREATE TABLE Roles(
    id         BIGSERIAL    NOT NULL PRIMARY KEY,
    name       VARCHAR(50)  NOT NULL,
    permission VARCHAR(255) NOT NULL,
    CONSTRAINT UQ_Roles_Name UNIQUE (name)
);

CREATE TABLE UserRoles
(
    id      BIGSERIAL NOT NULL PRIMARY KEY,
    user_id BIGSERIAL NOT NULL,
    role_id BIGSERIAL NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (role_id) REFERENCES Roles (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT UQ_UserRoles_User_Id UNIQUE (user_id)
);

CREATE TABLE Events
(
    id          BIGSERIAL    NOT NULL PRIMARY KEY,
    type        VARCHAR(255) NOT NULL CHECK (type IN ('LOGIN_ATTEMPT', 'LOGIN_ATTEMPT_FAILURE', 'LOGIN_ATTEMPT_SUCCESS',
                                                      'PROFILE_UPDATE', 'PROFILE_PICTURE_UPDATE', 'ROLE_UPDATE',
                                                      'ACCOUNT_SETTINGS_UPDATE', 'PASSWORD_UPDATE', 'MFA_UPDATE')),
    description VARCHAR(255) NOT NULL,
    CONSTRAINT UQ_Events_Type UNIQUE (type)
);

CREATE TABLE UserEvents
(
    id         BIGSERIAL NOT NULL PRIMARY KEY,
    user_id    BIGSERIAL NOT NULL,
    event_id   BIGSERIAL NOT NULL,
    device     VARCHAR(100) DEFAULT NULL,
    ip_address VARCHAR(100) DEFAULT NULL,
    created_at TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (event_id) REFERENCES Events (id) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE AccountVerifications
(
    id      BIGSERIAL    NOT NULL PRIMARY KEY,
    user_id BIGSERIAL    NOT NULL,
    url     VARCHAR(255) NOT NULL,
    -- date     TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT UQ_AccountVerifications_User_Id UNIQUE (user_id),
    CONSTRAINT UQ_AccountVerifications_Url UNIQUE (url)
);

CREATE TABLE ResetPasswordVerifications
(
    id              BIGSERIAL    NOT NULL PRIMARY KEY,
    user_id         BIGSERIAL    NOT NULL,
    url             VARCHAR(255) NOT NULL,
    expiration_date TIMESTAMP    NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT UQ_ResetPasswordVerifications_User_Id UNIQUE (user_id),
    CONSTRAINT UQ_ResetPasswordVerifications_Url UNIQUE (url)
);

CREATE TABLE TwoFactorVerifications
(
    id              BIGSERIAL   NOT NULL PRIMARY KEY,
    user_id         BIGSERIAL   NOT NULL,
    code            VARCHAR(10) NOT NULL,
    expiration_date TIMESTAMP   NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT UQ_TwoFactorVerifications_User_Id UNIQUE (user_id),
    CONSTRAINT UQ_TwoFactorVerifications_Code UNIQUE (code)
);

INSERT INTO roles (name, permission)
VALUES ('ROLE_ADMIN', 'READ:USER'),
       ('ROLE_MAHASISWA',  'CREATE:USER, READ:USER, UPDATE:USER, DELETE:USER');
INSERT INTO public.events (id, type, description)
VALUES (1, 'LOGIN_ATTEMPT', 'You tried to login');

INSERT INTO public.events (id, type, description)
VALUES (2, 'LOGIN_ATTEMPT_FAILURE', 'You Tried to login and you failed');

INSERT INTO public.events (id, type, description)
VALUES (3, 'LOGIN_ATTEMPT_SUCCESS', 'You Tried to login and you success');

INSERT INTO public.events (id, type, description)
VALUES (4, 'PROFILE_UPDATE', 'You update your profile information');

INSERT INTO public.events (id, type, description)
VALUES (5, 'PROFILE_PICTURE_UPDATE', 'You update your profile pictures');

INSERT INTO public.events (id, type, description)
VALUES (6, 'ROLE_UPDATE', 'You update your roles and permission');

INSERT INTO public.events (id, type, description)
VALUES (7, 'ACCOUNT_SETTINGS_UPDATE', 'Your updated account settings');

INSERT INTO public.events (id, type, description)
VALUES (8, 'MFA_UPDATE', 'You update your MFA setting');

INSERT INTO public.events (id, type, description)
VALUES (9, 'PASSWORD_UPDATE', 'You update your password');

create table biodata(
    id bigserial not null primary key ,
    nama varchar not null ,
    tempat_lahir varchar not null ,
    tanggal_lahir date not null ,
    jk varchar not null ,
    agama varchar not null ,
    alamat varchar not null ,
    email varchar not null ,
    statusKawin varchar not null ,
    hobi varchar not null ,
    anak_ke bigint not null ,
    jml_saudara bigint not null ,
    status_verifikasi boolean default false,
    CONSTRAINT UQ_Biodata_Email UNIQUE (email)
);
create table dokumen(
    id bigserial not null primary key ,
    id_biodata bigint not null ,
    kartu_keluarga varchar not null ,
    akte varchar not null,
    ijazah varchar not null ,
    pas_foto varchar not null,
    status_verifikasi boolean default false,
    FOREIGN KEY (id_biodata) REFERENCES biodata (id) ON DELETE CASCADE ON UPDATE CASCADE
);
create table card(
    id bigserial not null primary key ,
    id_biodata bigint not null ,
    username varchar not null ,
    password varchar not null ,
    tanggal date not null ,
    waktu int not null ,
    ruang varchar not null ,
    FOREIGN KEY (id_biodata) REFERENCES biodata (id) ON DELETE CASCADE ON UPDATE CASCADE
);
ALTER TABLE card
    ADD CONSTRAINT uq_card_username UNIQUE (username);
ALTER TABLE biodata
    ALTER COLUMN jk SET DATA TYPE smallint USING jk::smallint;
ALTER TABLE card
    ALTER COLUMN waktu TYPE TIME
        USING to_timestamp(waktu::text, 'HH24:MI:SS')::TIME without time zone;
