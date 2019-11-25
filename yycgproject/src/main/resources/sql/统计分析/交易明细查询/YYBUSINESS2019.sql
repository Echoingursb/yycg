-- Create table
create table YYBUSINESS2019
(
  id        VARCHAR2(32) not null,
  yycgdid   VARCHAR2(32) not null,
  useryyid  VARCHAR2(64) not null,
  ypxxid    VARCHAR2(32) not null,
  zbjg      FLOAT not null,
  jyjg      FLOAT not null,
  cgl       INTEGER not null,
  cgje      FLOAT not null,
  cgzt      CHAR(1) not null,
  rkl       INTEGER,
  rkje      FLOAT,
  rkdh      VARCHAR2(32),
  ypph      VARCHAR2(32),
  ypyxq     FLOAT,
  rktime    TIMESTAMP(6),
  fhtime    TIMESTAMP(6),
  yythdid   VARCHAR2(32),
  thl       VARCHAR2(32),
  thje      FLOAT,
  thzt      CHAR(1),
  thyy      VARCHAR2(100),
  yyjsdid   VARCHAR2(32),
  jsl       INTEGER,
  jsje      FLOAT,
  jszt      CHAR(1),
  vchar1    VARCHAR2(64),
  vchar2    VARCHAR2(64),
  vchar3    VARCHAR2(64),
  usergysid VARCHAR2(64)
)
tablespace YYCG
pctfree 10
initrans 1
maxtrans 255
storage
(
initial 64K
minextents 1
maxextents unlimited
);
-- Create/Recreate indexes
create index INDEX_YYBUSINESS2019_1 on YYBUSINESS2019 (CGZT, THZT, YYCGDID, YYJSDID, USERGYSID, YYTHDID, JSZT)
tablespace YYCG
pctfree 10
initrans 2
maxtrans 255
storage
(
initial 64K
minextents 1
maxextents unlimited
);
-- Create/Recreate primary, unique and foreign key constraints
alter table YYBUSINESS2019
  add constraint PK_YYBUSINESS2019 primary key (ID)
  using index
  tablespace YYCG
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
  initial 64K
  minextents 1
  maxextents unlimited
  );
alter table YYBUSINESS2019
  add constraint UNI_YYBUSINESS2019 unique (YYCGDID, YPXXID)
  using index
  tablespace YYCG
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
  initial 64K
  minextents 1
  maxextents unlimited
  );
alter table YYBUSINESS2019
  add constraint FK_YYBUSINESS2019_2 foreign key (USERYYID)
references USERYY (ID);
alter table YYBUSINESS2019
  add constraint FK_YYBUSINESS2019_3 foreign key (YPXXID)
references YPXX (ID);
