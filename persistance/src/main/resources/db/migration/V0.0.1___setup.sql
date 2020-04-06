CREATE TABLE USERS(
                      ID        SERIAL PRIMARY KEY,
                      NAME      varchar(40) NOT NULL UNIQUE,
                      PASSWORD  varchar(60),
                      DISCORD_ID varchar(60),
                      TEAMSPEAK_ID varchar(60),
                      CREATED_DATE timestamp
);


CREATE TABLE EVENTS(
                       ID        SERIAL PRIMARY KEY,
                       NAME      varchar(40) NOT NULL UNIQUE,
                       CREATED_BY BIGINT NOT NULL REFERENCES USERS(ID),
                       CREATED_DATE timestamp NOT NULL ,
                       DATE timestamp NOT NULL

);


CREATE TABLE USER_TO_EVENT (
                        EVENT_ID BIGINT REFERENCES EVENTS(ID),
                        USER_ID BIGINT REFERENCES USERS(ID),
                        STATE SMALLINT NOT NULL,
                        PRIMARY KEY (EVENT_ID, USER_ID)

);

ALTER TABLE USERS ADD COLUMN LATEST_EVENT BIGINT REFERENCES EVENTS(ID);
