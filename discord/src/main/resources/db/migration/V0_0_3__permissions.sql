CREATE TABLE ROLES(
                      ID        SERIAL PRIMARY KEY,
                      NAME varchar(20) NOT NULL
);

CREATE TABLE RIGHTS(
                       ID        SERIAL PRIMARY KEY,
                       NAME varchar(20) NOT NULL
);

CREATE TABLE ROLES_TO_RIGHTS(
                                ROLE_ID INTEGER NOT NULL,
                                RIGHT_ID INTEGER NOT NULL,
                                PRIMARY KEY(ROLE_ID , RIGHT_ID)
);

CREATE TABLE ROLES_TO_USER(
                              ROLE_ID INTEGER NOT NULL,
                              USER_ID INTEGER NOT NULL,
                              PRIMARY KEY(USER_ID, ROLE_ID)
);

INSERT INTO ROLES VALUES
(1, 'Administrator');

INSERT INTO RIGHTS VALUES
(1, 'CREATE_USER');

INSERT INTO ROLES_TO_USER VALUES
(1, 1);

INSERT INTO ROLES_TO_RIGHTS VALUES
(1, 1);
