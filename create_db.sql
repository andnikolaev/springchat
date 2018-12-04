create table USER_ROLE
(
  ID NUMBER not null
    primary key,
  NAME VARCHAR2(50) not null,
  DESCRIPTION VARCHAR2(255) not null
)
/

create unique index USER_ROLE_NAME_UINDEX
  on USER_ROLE (NAME)
/

create table USER_STATUS
(
  ID NUMBER not null
    primary key,
  NAME VARCHAR2(50) not null,
  DESCRIPTION VARCHAR2(255) not null
)
/

create unique index USER_STATUS_NAME_UINDEX
  on USER_STATUS (NAME)
/

create table EVENT_TYPE
(
  ID NUMBER not null
    primary key,
  NAME VARCHAR2(50) not null,
  DESCRIPTION VARCHAR2(255) not null
)
/

create unique index EVENT_TYPE_NAME_UINDEX
  on EVENT_TYPE (NAME)
/

create table "USER"
(
  ID NUMBER not null
    primary key,
  NAME VARCHAR2(100) not null,
  PASSWORD VARCHAR2(255) not null,
  ROLE_ID NUMBER not null
    constraint USER_ROLE_ID_FK
    references USER_ROLE,
  STATUS_ID NUMBER not null
    constraint USER_STATUS_ID_FK
    references USER_STATUS
)
/

create unique index USER_NAME_UINDEX
  on ALL_USER (NAME)
/


create table EVENT
(
  ID NUMBER not null
    primary key,
  TIME_STAMP TIMESTAMP(6) not null,
  OWNER_ID NUMBER not null
    constraint OWNER_ID_FK
    references ALL_USER,
  assigne_ID NUMBER not null
    constraint ASSIGNE_ID_FK
    references ALL_USER,
  EVENT_TYPE_ID NUMBER not null
    constraint EVENT_TYPE_ID_FK
    references EVENT_TYPE,
  MESSAGE VARCHAR2(255) not null,
  IP VARCHAR2(20)
)
/
CREATE SEQUENCE USER_STATUS_PK_SEQUENCE;
/
CREATE SEQUENCE USER_ROLE_PK_SEQUENCE;
/
CREATE SEQUENCE EVENT_TYPE_PK_SEQUENCE;
/
CREATE SEQUENCE EVENT_PK_SEQUENCE;
/
CREATE SEQUENCE USER_PK_SEQUENCE;
/
CREATE OR REPLACE TRIGGER USER_STATUS_Auto_Id before
  INSERT ON "USER_STATUS" FOR EACH row BEGIN IF inserting THEN IF :NEW."ID" IS NULL THEN
  SELECT USER_STATUS_PK_SEQUENCE.nextval INTO :NEW."ID" FROM dual;
END IF;
END IF;
END;
/
CREATE OR REPLACE TRIGGER USER_ROLE_Auto_Id before
  INSERT ON "USER_ROLE" FOR EACH row BEGIN IF inserting THEN IF :NEW."ID" IS NULL THEN
  SELECT USER_ROLE_PK_SEQUENCE.nextval INTO :NEW."ID" FROM dual;
END IF;
END IF;
END;
/
CREATE OR REPLACE TRIGGER EVENT_TYPE_Auto_Id before
  INSERT ON "EVENT_TYPE" FOR EACH row BEGIN IF inserting THEN IF :NEW."ID" IS NULL THEN
  SELECT EVENT_TYPE_PK_SEQUENCE.nextval INTO :NEW."ID" FROM dual;
END IF;
END IF;
END;
/
CREATE OR REPLACE TRIGGER EVENT_Auto_Id before
  INSERT ON "EVENT" FOR EACH row BEGIN IF inserting THEN IF :NEW."ID" IS NULL THEN
  SELECT EVENT_PK_SEQUENCE.nextval INTO :NEW."ID" FROM dual;
END IF;
END IF;
END;
/
CREATE OR REPLACE TRIGGER USER_Auto_Id before
  INSERT ON ALL_USER FOR EACH row BEGIN IF inserting THEN IF :NEW."ID" IS NULL THEN
  SELECT USER_PK_SEQUENCE.nextval INTO :NEW."ID" FROM dual;
END IF;
END IF;
END;
/
INSERT
INTO USER_ROLE
(
  name,
  description
)
VALUES
  (
    'ANONYMOUS',
    'Anonymous user (unauthorized)'
  );
INSERT
INTO USER_ROLE
(
  name,
  description
)
VALUES
  (
    'USER',
    'Common user'
  );
INSERT
INTO USER_ROLE
(
  name,
  description
)
VALUES
  (
    'ADMIN',
    'Chat administrator'
  );

INSERT
INTO USER_STATUS
(
  name,
  description
)
VALUES
  (
    'ACTIVE',
    'Value after user registration.'
  );
INSERT
INTO USER_STATUS
(
  name,
  description
)
VALUES
  (
    'BANNED',
    'Banned user'
  );
INSERT
INTO USER_STATUS
(
  name,
  description
)
VALUES
  (
    'DELETED',
    'Deleted user'
  );
INSERT
INTO EVENT_TYPE
(
  name,
  description
)
VALUES
  (
    'REGISTERED',
    'User was registered'
  );
INSERT
INTO EVENT_TYPE
(
  name,
  description
)
VALUES
  (
    'MESSAGE',
    'User send message'
  );
INSERT
INTO EVENT_TYPE
(
  name,
  description
)
VALUES
  (
    'LOGOUT',
    'User logout from chat'
  );
INSERT
INTO EVENT_TYPE
(
  name,
  description
)
VALUES
  (
    'LOGIN',
    'User login in chat'
  );
INSERT
INTO EVENT_TYPE
(
  name,
  description
)
VALUES
  (
    'KICKED',
    'User was kicked by admin'
  );
INSERT
INTO EVENT_TYPE
(
  name,
  description
)
VALUES
  (
    'BANNED',
    'User was banned by admin'
  );
INSERT
INTO EVENT_TYPE
(
  name,
  description
)
VALUES
  (
    'DELETED',
    'User was deleted by admin'
  );
COMMIT;