-- 普通配置项存储：
-- 普通配置项名称存储在DICTINFO表中info字段
-- 普通配置项对应的类型id存储在DICTINFO表中TYPECODE
select *
from DICTINFO d
where d.TYPECODE = '001';

select *
from DICTINFO d
where d.TYPECODE = 's01';

SELECT *
FROM (
       SELECT
         SYSUSER.ID,
         SYSUSER.USERID,
         SYSUSER.USERNAME,
         SYSUSER.GROUPID,
         SYSUSER.USERSTATE,
         (SELECT INFO
          FROM DICTINFO
          WHERE DICTINFO.DICTCODE = SYSUSER.GROUPID AND DICTINFO.TYPECODE = 's01') groupname,
         SYSUSER.SYSID,
         DECODE(SYSUSER.GROUPID,
                '1', (SELECT USERJD.MC
                      FROM USERJD
                      WHERE USERJD.ID = SYSUSER.SYSID),
                '2', (SELECT USERJD.MC
                      FROM USERJD
                      WHERE USERJD.ID = SYSUSER.SYSID),
                '3', (SELECT USERYY.MC
                      FROM USERYY
                      WHERE USERYY.ID = SYSUSER.SYSID),
                '4', (SELECT USERGYS.MC
                      FROM USERGYS
                      WHERE USERGYS.ID = SYSUSER.SYSID)
         )                                                                         SYSUSERMC
       FROM SYSUSER) SYSUSER;