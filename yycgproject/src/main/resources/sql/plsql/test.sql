select user from dual;
select to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') from dual;--获得当前系统时间

select SYS_CONTEXT('USERENV','TERMINAL') from dual;--获得主机名

select SYS_CONTEXT('USERENV','language') from dual;--获得当前 locale

select dbms_random.random from dual;--获得一个随机数

-- Create table
create table YYCGD2019
(
  id       VARCHAR2(32) not null,
  bm       VARCHAR2(10) not null,
  mc       VARCHAR2(128) not null,
  useryyid VARCHAR2(64) not null,
  lxr      VARCHAR2(64),
  lxdh     VARCHAR2(64),
  cjr      VARCHAR2(64),
  cjtime   TIMESTAMP(6) not null,
  tjtime   TIMESTAMP(6),
  xgtime   TIMESTAMP(6),
  bz       VARCHAR2(256),
  ksghdate DATE,
  jsghdate DATE,
  zt       CHAR(1) not null,
  shyj     VARCHAR2(256),
  shtime   TIMESTAMP(6),
  vchar1   VARCHAR2(64),
  vchar2   VARCHAR2(64),
  vchar3   VARCHAR2(64),
  vchar4   VARCHAR2(128),
  vchar5   VARCHAR2(128)
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
create index INDEX_YYCGD2014_1 on YYCGD2019 (USERYYID, ZT)
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
create index INDEX_YYCGD2014_2 on YYCGD2019 (CJTIME)
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
alter table YYCGD2019
  add constraint PK_YYCGD2014 primary key (ID)
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
alter table YYCGD2019
  add constraint UNI_YYCGD2014 unique (BM)
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

--------------------------------------------------------------------------------
-- Create table
create table YYCGDMX2019
(
  id        VARCHAR2(32) not null,
  yycgdid   VARCHAR2(32) not null,
  usergysid VARCHAR2(64) not null,
  ypxxid    VARCHAR2(32) not null,
  zbjg      FLOAT not null,
  jyjg      FLOAT,
  cgl       INTEGER,
  cgje      FLOAT,
  cgzt      CHAR(1) not null,
  vchar1    VARCHAR2(64),
  vchar2    VARCHAR2(64),
  vchar3    VARCHAR2(64),
  vchar4    VARCHAR2(128),
  vchar5    VARCHAR2(128)
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
-- Create/Recreate primary, unique and foreign key constraints
alter table YYCGDMX2019
  add constraint PK_YYCGDMX2019 primary key (ID)
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
alter table YYCGDMX2019
  add constraint UNI_YYCGDMX2019 unique (YYCGDID, YPXXID)
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
alter table YYCGDMX2019
  add constraint FK_YYCGDMX2019_1 foreign key (YYCGDID)
references YYCGD2019 (ID);
alter table YYCGDMX2019
  add constraint FK_YYCGDMX2019_3 foreign key (YPXXID)
references YPXX (ID);

-----------------------------------------
DROP TABLE YYCGDMX2019;
