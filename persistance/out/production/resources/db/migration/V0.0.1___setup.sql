CREATE TABLE USERS(
                      ID        SERIAL PRIMARY KEY,
                      NAME      varchar(40) NOT NULL UNIQUE,
                      PASSWORD  varchar(60),
                      DISCORD_ID varchar(60),
                      TEAMSPEAK_ID varchar(60),
                      CREATED_DATE timestamp
);
