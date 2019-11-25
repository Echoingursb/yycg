--1. 需求：用户查询
---------------------------------------------------------
---------------------------------------------------------
-- 查询字段：帐号， 名称， 用户类型， 所属单位， 状态
-- 多表查询
-- 主查询表：SYSUSER
-- 关联查询表：USERYY, USERJD, USERGYS
-- SELECT
--   SYSUSER.ID,
--   SYSUSER.USERID,
--   -- 帐号
--   SYSUSER.USERNAME,
--   -- 名称
--   (
--     SELECT DICTINFO.INFO
--     FROM DICTINFO
--     WHERE DICTINFO.DICTCODE = SYSUSER.GROUPID AND TYPECODE = 's01'
--   ) USERGROUP,
--   -- 用户类型
--   (SELECT USERYY.MC
--    FROM USERYY
--    WHERE USERYY.ID = SYSUSER.SYSID
--   )
-- FROM SYSUSER;

-- SQL1 外连接
SELECT *
FROM (
       SELECT
         SYSUSER.ID,
         SYSUSER.USERID,
         SYSUSER.USERNAME,
         SYSUSER.GROUPID,
         NVL(USERJD.MC, NVL(USERYY.MC, USERGYS.MC)) USERMC
       -- USERJD.MC, USERYY.MC, USERGYS.MC
       FROM SYSUSER
         LEFT JOIN USERJD
           ON SYSUSER.SYSID = USERJD.ID
         LEFT JOIN USERYY
           ON SYSUSER.SYSID = USERYY.ID
         LEFT JOIN USERGYS
           ON SYSUSER.SYSID = USERGYS.ID
     ) T
WHERE
  T.USERID LIKE '%%'
  AND
  T.USERNAME LIKE '%%'
  AND
  T.USERMC LIKE '%%'
--   AND
--   T.GROUPID = ''
;
-- SQL2 子查询(DECODE())
SELECT *
FROM (
       SELECT
         SYSUSER.ID,
         SYSUSER.USERID,
         SYSUSER.USERNAME,
         SYSUSER.GROUPID,
         SYSUSER.USERSTATE,
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
                      WHERE USERGYS.ID = SYSUSER.SYSID),
                (SELECT SYSUSER.GROUPID
                 FROM SYSUSER
                 WHERE SYSUSER.GROUPID = '0')
         ) SYSUSERMC
       FROM SYSUSER) SYSUSER
WHERE
  SYSUSER.USERID LIKE '%%'
  AND
  SYSUSER.USERNAME LIKE '%%'
  AND
  SYSUSER.SYSUSERMC LIKE '%%'
--   AND
--   T.GROUPID = ''
;
-- SQL3 子查询(CASE表达式)
SELECT
  SYSUSER.ID,
  SYSUSER.USERID,
  SYSUSER.USERNAME,
  SYSUSER.GROUPID,
  CASE SYSUSER.GROUPID
  WHEN '1'
    THEN (SELECT USERJD.MC
          FROM USERJD
          WHERE USERJD.ID = SYSUSER.SYSID)
  WHEN '2'
    THEN (SELECT USERJD.MC
          FROM USERJD
          WHERE USERJD.ID = SYSUSER.SYSID)
  WHEN '3'
    THEN (SELECT USERYY.MC
          FROM USERYY
          WHERE USERYY.ID = SYSUSER.SYSID)
  WHEN '4'
    THEN (SELECT USERGYS.MC
          FROM USERGYS
          WHERE USERGYS.ID = SYSUSER.SYSID)

  END USERMC
FROM SYSUSER;


SELECT
  SYSUSER.ID,
  SYSUSER.USERID,
  SYSUSER.USERNAME,
  SYSUSER.GROUPID,
  (SELECT DICTINFO.ISENABLE
   FROM DICTINFO
   WHERE DICTINFO.DICTCODE = SYSUSER.GROUPID AND DICTINFO.TYPECODE = '002'
  ) USERSTATE
FROM SYSUSER;
---------------------------------------------------------
SELECT *
FROM (SELECT
        SYSUSER.ID,
        SYSUSER.USERID,
        SYSUSER.USERNAME,
        SYSUSER.GROUPID,
        DECODE(SYSUSER.GROUPID, '1', (SELECT USERJD.MC
                                      FROM USERJD
                                      WHERE USERJD.ID = SYSUSER.SYSID), '2', (SELECT USERJD.MC
                                                                              FROM USERJD
                                                                              WHERE USERJD.ID = SYSUSER.SYSID), '3',
               (SELECT USERYY.MC
                FROM USERYY
                WHERE USERYY.ID = SYSUSER.SYSID), '4', (SELECT USERGYS.MC
                                                        FROM USERGYS
                                                        WHERE USERGYS.ID = SYSUSER.SYSID)) SYSUSERMC
      FROM SYSUSER) SYSUSER
WHERE SYSUSER.USERID LIKE CONCAT(CONCAT('%', 'admin'), '%');
-- concat(concat('%', #{username}), '%')

-- 2. 需求：分页
SELECT SYSUSER.*, ROWNUM
FROM SYSUSER
WHERE ROWNUM BETWEEN 0 AND 15;

SELECT SYSUSER.*
FROM SYSUSER
WHERE ROWNUM = 0;

SELECT
  SYSUSER.*,
  ROWNUM page_num
FROM SYSUSER
WHERE ROWNUM <= 30;

SELECT page_1.*
FROM
  (SELECT
     SYSUSER.*,
     ROWNUM page_num
   FROM SYSUSER
   WHERE ROWNUM <= 30) page_1
WHERE page_1.page_num >= 20;

SELECT page_1.*
FROM
  (SELECT
     SYSUSER.*,
     ROWNUM page_num
   FROM

     (SELECT *
      FROM SYSUSER)

     SYSUSER
   WHERE ROWNUM <= 30) page_1
WHERE page_1.page_num >= 20;

-- oracle分页模版
SELECT page_2.*
FROM
  (SELECT
     page_1.*,
     ROWNUM page_num
   FROM

     (
       --        这里可以填入任意查询sql
       SELECT *
       FROM SYSUSER
     )

     page_1
   WHERE ROWNUM <= 30) page_2
WHERE page_2.page_num >= 20;

-- 最终分页sql
SELECT page_2.*
FROM
  (SELECT
     page_1.*,
     ROWNUM page_num
   FROM

     (SELECT *
      FROM (
             SELECT
               SYSUSER.ID,
               SYSUSER.USERID,
               SYSUSER.USERNAME,
               SYSUSER.GROUPID,
               SYSUSER.USERSTATE,
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
               ) SYSUSERMC
             FROM SYSUSER) SYSUSER)

     page_1
   WHERE ROWNUM <= 30) page_2
WHERE page_2.page_num >= 20;

-- 总记录数
SELECT COUNT(*)
FROM (
       SELECT
         SYSUSER.ID,
         SYSUSER.USERID,
         SYSUSER.USERNAME,
         SYSUSER.GROUPID,
         SYSUSER.USERSTATE,
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
                      WHERE USERGYS.ID = SYSUSER.SYSID),
                (SELECT SYSUSER.GROUPID
                 FROM SYSUSER
                 WHERE SYSUSER.GROUPID = '0')
         ) SYSUSERMC
       FROM SYSUSER) SYSUSER
WHERE
  SYSUSER.USERID LIKE '%%'
  AND
  SYSUSER.USERNAME LIKE '%%'
  AND
  SYSUSER.SYSUSERMC LIKE '%%'
--   AND
--   T.GROUPID = ''
;
