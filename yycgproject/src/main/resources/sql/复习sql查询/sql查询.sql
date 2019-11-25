-------------------------------------------------------------------
-- 查询所有表
SELECT
  a.OWNER,
  a.TABLE_NAME
FROM ALL_TABLES a
WHERE OWNER = 'YYCG';

SELECT * FROM ALL_TABLES;
-------------------------------------------------------------------

-- select dbms_metadata.get_ddl('TABLE','TABLE_NAME') from dual;
-- SELECT DBMS_METADATA.GET_DDL('TABLE', 'DICTINFO') FROM DUAL;

--------------------------------------------------------------------
-- 多表查询内连接
SELECT
  DICTINFO.*,
  DICTTYPE.*
FROM DICTINFO, DICTTYPE
WHERE DICTINFO.TYPECODE = DICTTYPE.TYPECODE;

-- 需求：查询系统用户类型和名称(用户名称在DICTINFO的TYPECODE字段中)
-- 主表：SYSUSER
-- 关联表：DICTINFO
-- 查询系统用户类型
SELECT
  SYSUSER.USERID,
  SYSUSER.GROUPID
FROM SYSUSER;

-- 查询名称
SELECT
  DICTINFO.ID,
  DICTINFO.DICTCODE,
  DICTINFO.INFO
FROM DICTINFO
WHERE TYPECODE = 's01';
-- 两张表如果有外键关系可以使用内连接，因为通过内连接每一条只能返回单条记录
-- 只要在关联查询表中查询出一条记录，就可以使用内连接
-- 如果从关联查询表中得到多条记录有重复记录，
-- 使用内连接查询出现重复记录，大部分情况下说明SQL错误
-- 在少部分情况下，确定没有逻辑错误，可以使用DISTINCT去除重复记录
-- SQL1
SELECT
  SYSUSER.USERID,
  SYSUSER.GROUPID,
  temp.INFO
FROM
  SYSUSER,
  (SELECT
     DICTINFO.ID,
     DICTINFO.DICTCODE,
     DICTINFO.INFO
   FROM DICTINFO
   WHERE TYPECODE = 's01') temp
WHERE
  SYSUSER.GROUPID = temp.DICTCODE;
-- SQL2
SELECT
  SYSUSER.USERID,
  SYSUSER.GROUPID,
  temp.INFO
FROM
  SYSUSER,
  (SELECT
     DICTINFO.ID,
     DICTINFO.DICTCODE,
     DICTINFO.INFO,
     DICTINFO.TYPECODE
   FROM DICTINFO) temp
WHERE
  SYSUSER.GROUPID = temp.DICTCODE
  AND
  temp.TYPECODE = 's01';
------------------------------------------------------
-- 多表查询外连接
-- 需求：查询系统用户表中对应的医院单位的名称(如果该用户是医院用户则显示医院用户的名称)
-- 主表：SYSUSER
-- 关联表：USERYY

-- 只有部分记录可以从关联查询表查到，主查询表要想显示所有记录，只能和关联表通过外连接
-- 显示全部数据是主查询表，显示部分数据是关联查询表
-- SELECT * FROM SYSUSER;
SELECT
  SYSUSER.*,
  USERYY.MC
FROM SYSUSER
  LEFT JOIN USERYY ON SYSUSER.SYSID = USERYY.ID;
-- 如果上边的语句使用内连接，查询的是主表SYSUSER和关联表USERYY的交集，即系统用户在医院单位中存在记录
SELECT
  SYSUSER.*,
  USERYY.MC
FROM SYSUSER, USERYY
WHERE SYSUSER.SYSID = USERYY.ID;
-- 使用右外连接，实现左外连接同样效果
SELECT
  SYSUSER.*,
  USERYY.MC
FROM
  USERYY
  RIGHT JOIN SYSUSER ON SYSUSER.SYSID = USERYY.ID;
------------------------------------------------------
-- 子查询
-- 需求：查询系统用户表，关联查询单位名称
-- 主表：SYSUSER
-- 关联表：USERYY
-- 子查询的关联表一般通过主键进行关联查询
-- 在主表做子查询，主表记录会全部显示
SELECT
  SYSUSER.*,
  (SELECT USERYY.MC
   FROM USERYY
   WHERE USERYY.ID = SYSUSER.SYSID) USERYYMC
FROM SYSUSER;

-- 需求：查询系统用户表，关联查询单位名称，关联查询用户类型名称
-- 主表：SYSUSER
-- 关联表：USERYY
-- 关联表：DICTINFO
-- 子查询的关联表一般通过主键进行关联查询，绝对没有问题；
-- 也可以不通过主键进行关联查询，但是子查询关联查询的结果集只能有一条记录
-- 在主表做子查询，主表记录会全部显示
-- DICTCODE和TYPECODE唯一决定一条记录
SELECT
  SYSUSER.*,
  (SELECT USERYY.MC
   FROM USERYY
   WHERE USERYY.ID = SYSUSER.SYSID)                                         USERYYMC,
  (SELECT DICTINFO.INFO
   FROM DICTINFO
   WHERE DICTINFO.DICTCODE = SYSUSER.GROUPID AND DICTINFO.TYPECODE = 's01') USERFROUPMC
FROM SYSUSER;
-----------------------------------------------------------------------------
-- 嵌套表查询
-- 嵌套的层级有限制
-- 将嵌套的结果当成一个表（生成的表绝对不能出现重复字段！！！）
SELECT *
FROM (
  SELECT
    SYSUSER.*,
    (SELECT USERYY.MC
     FROM USERYY
     WHERE USERYY.ID = SYSUSER.SYSID)                                         USERYYMC,
    (SELECT DICTINFO.INFO
     FROM DICTINFO
     WHERE DICTINFO.DICTCODE = SYSUSER.GROUPID AND DICTINFO.TYPECODE = 's01') USERFROUPMC
  FROM SYSUSER
);