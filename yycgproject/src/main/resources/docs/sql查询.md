# 多表查询

## 内连接

适用情况：两张表如果有**外键关系**可以使用内连接，因为通过内连接每一条记录只能返回单条记录，使用内连接的可以使用子查询实现。

```sql
-- 内连接 --
-- 下边的dictinfo表中的typecode只能从dicttype表中找到一条记录，因为typecode是外键
select
  DICTINFO.*,
  DICTTYPE.*
from DICTINFO, DICTTYPE
where DICTTYPE.TYPECODE = DICTINFO.TYPECODE;

-- 需求：系统用户类型名称查询，内连接实现
-- 分析：主查询表：系统用户表(SYSUSER) 关联查询表：字典信息表(DICTINFO)
-- 只要在关联表中查询出一条记录就可以使用内连接
-- 如果从关联查询表中得到多表记录有重复记录，使用内连接如果出现重复记录则大部分情况说明查询语句是错误的
-- 在确保sql查询没有逻辑错误可以使用distinct去除重复记录
-- tempTable结果集作为临时表
select
  SYSUSER.USERID,
  SYSUSER.GROUPID,
  tempTable.INFO
from SYSUSER,
  (select
     DICTINFO.DICTCODE,
     DICTINFO.INFO
   from DICTINFO
   where TYPECODE = 's01') tempTable
where SYSUSER.GROUPID = tempTable.DICTCODE;
-- 另外一种方法
select
  SYSUSER.USERID,
  SYSUSER.GROUPID,
  tempTable.INFO
from SYSUSER,
  (select
     DICTINFO.DICTCODE,
     DICTINFO.INFO,
     DICTINFO.TYPECODE
   from DICTINFO) tempTable
where SYSUSER.GROUPID = tempTable.DICTCODE and TYPECODE = 's01';

```

## 外连接

使用情况：只有部分记录可以从关联表中查询到，主查询表想要显示所有记录，只能和关联表通过外连接进行关联查询

**显示全部数据是主查询表，显示部分数据是关联查询表**

**left join 左外连接，left左边是主查询表**

**right join 右外连接，right右边是主查询表**

```sql
-- 外连接 --
-- 需求：查询系统用户表中对应的医院单位名称(如果该用户是医院用户则显示医院名称)
-- 主查询表：系统用户表 关联查询表：医院单位表
-- 左外连接
select
  SYSUSER.USERID,
  SYSUSER.USERNAME,
  USERYY.MC
from SYSUSER
  left outer join USERYY on SYSUSER.SYSID = USERYY.ID;
-- 右外连接
select
  SYSUSER.USERID,
  SYSUSER.USERNAME,
  USERYY.MC
from USERYY
  right outer join SYSUSER on SYSUSER.SYSID = USERYY.ID;-- 外连接 --
-- 需求：查询系统用户表中对应的医院单位名称(如果该用户是医院用户则显示医院名称)
-- 分析：主查询表：系统用户表(SYSUSER) 关联查询表：医院单位表(USERYY)
-- 左外连接
select
  SYSUSER.USERID,
  SYSUSER.USERNAME,
  USERYY.MC
from SYSUSER
  left outer join USERYY on SYSUSER.SYSID = USERYY.ID;
-- 右外连接
select
  SYSUSER.USERID,
  SYSUSER.USERNAME,
  USERYY.MC
from USERYY
  right outer join SYSUSER on SYSUSER.SYSID = USERYY.ID;
```



## 子查询

```sql
-- 子查询 --
-- 需求：查询系统用户表，关联查询单位名称，关联查询用户类型名称
-- 分析：主查询表：系统用户表(SYSUSER) 关联查询表：医院用户表(USERYY)，字典信息表(DICTINFO)
-- 通过关联表的主键进行关联查询没有问题，也可以不通过关联表的主键查询(但要求关联查询的结果集只能有一条记录)
-- 在主表上做子查询，主表记录会全部显示
select
  SYSUSER.*,
  (select USERYY.MC
   from USERYY
   where USERYY.ID = SYSUSER.SYSID)                                         USERMC,
  (select DICTINFO.INFO
   from DICTINFO
   where DICTINFO.DICTCODE = SYSUSER.GROUPID and DICTINFO.TYPECODE = 's01') INFO -- DICTCODE和TYPECODE唯一决定一条记录
from SYSUSER;
```

## 嵌套表查询

```sql
-- 嵌套表查询 --
-- 将嵌套的结果集当成一个表(嵌套子查询中列不能重复)
-- 嵌套表的层级有限制
select *
from (
  select
    SYSUSER.*,
    (select USERYY.MC
     from USERYY
     where USERYY.ID = SYSUSER.SYSID)                                         USERMC,
    (select DICTINFO.INFO
     from DICTINFO
     where DICTINFO.DICTCODE = SYSUSER.GROUPID and DICTINFO.TYPECODE = 's01') INFO -- DICTCODE和TYPECODE唯一决定一条记录
  from SYSUSER
);
```

