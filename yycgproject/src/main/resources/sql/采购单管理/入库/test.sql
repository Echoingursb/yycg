-- Create table
create table YYCGDRK2019
(
  id      VARCHAR2(32) not null,
  yycgdid VARCHAR2(32) not null,
  ypxxid  VARCHAR2(32) not null,
  vchar1  VARCHAR2(64),
  vchar2  VARCHAR2(64),
  vchar3  VARCHAR2(64),
  vchar4  VARCHAR2(128),
  vchar5  VARCHAR2(128),
  rkl     INTEGER not null,
  cgzt    CHAR(1) not null,
  rkje    FLOAT not null,
  rkdh    VARCHAR2(32) not null,
  ypph    VARCHAR2(32) not null,
  ypyxq   FLOAT not null,
  rktime  TIMESTAMP(6) default SYSDATE not null
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
alter table YYCGDRK2019
  add constraint PK_YYCGDRK2019 primary key (ID)
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
alter table YYCGDRK2019
  add constraint UNI_YYCGDRK2019 unique (YYCGDID, YPXXID)
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


-- 需求：医院查询待入库信息列表
-- 分析：
-- 约束：
-- 1. 医院只查询本医院采购信息
-- 2. 药品的采购状态为“已发货”
select
  YYCGDMX.ID                                                             yycgdmxid,
  USERYY.ID                                                              useryyid,
  USERYY.MC                                                              useryymc,
  YPXX.ID,
  YPXX.BM,
  YPXX.MC,
  YPXX.JX,
  YPXX.GG,
  YPXX.ZHXS,
  YPXX.SCQYMC,
  YPXX.SPMC,
  YPXX.ZBJG,
  YPXX.JYZT,
  (select DICTINFO.INFO
   from DICTINFO
   where DICTINFO.TYPECODE = '003' and DICTINFO.DICTCODE = YPXX.JYZT)    jyztmc,
  YYCGDMX.JYJG,
  YYCGDMX.CGL,
  YYCGDMX.CGJE,
  YYCGDMX.CGZT,
  (select DICTINFO.INFO
   from DICTINFO
   where DICTINFO.TYPECODE = '011' and DICTINFO.DICTCODE = YYCGDMX.CGZT) cgztmc,
  USERGYS.ID                                                             usergysid,
  USERGYS.MC
from YYCGDMX2019 YYCGDMX, YYCGD2019 YYCGD, USERYY, YPXX, USERGYS
where YYCGDMX.YYCGDID = YYCGD.ID
      and YYCGD.USERYYID = USERYY.ID
      and YYCGDMX.YPXXID = YPXX.ID
      and YYCGDMX.USERGYSID = USERGYS.ID
      and YYCGD.USERYYID = 'c9396c43-067e-11e3-8a3c-0019d2ce5116'
      and YYCGDMX.CGZT = '2'