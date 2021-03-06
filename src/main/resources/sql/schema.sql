DROP SEQUENCE TBL_MEMBER_SEQUENCE;
CREATE SEQUENCE IF NOT EXISTS TBL_MEMBER_SEQUENCE
START WITH 1
INCREMENT BY 1
NO CYCLE
NO CACHE;

DROP TABLE TBL_MEMBER;
CREATE TABLE TBL_MEMBER(
    IDX LONG PRIMARY KEY,
    MEMBER_NAME VARCHAR(20) NOT NULL,
    LOGIN_ID VARCHAR(20),
    LOGIN_PASSWORD VARCHAR(100) NOT NULL,
    EMAIL VARCHAR(50),
    REG_DATE TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    MOD_DATE TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP()
);