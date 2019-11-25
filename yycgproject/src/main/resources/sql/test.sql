-- 查询对象的schema
-- SELECT * FROM dba_objects;
-- SELECT * FROM dba_objects WHERE OBJECT_TYPE = 'TABLE' AND OBJECT_NAME= 'SYSUSER';

SELECT
  USERNAME,
  SYSID
FROM SYSUSER;

SELECT *
FROM ALL_TABLES
WHERE OWNER = 'YYCG';

SELECT *
FROM USERAREA;

SELECT *
FROM USERJD;

SELECT *
FROM USERYY;

SELECT *
FROM USERYY yy
WHERE yy.DQ LIKE '1.1.%';

-- 分页
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

     (
       SELECT *
       FROM SYSUSER
     )

     SYSUSER
   WHERE ROWNUM <= 30) page_1
WHERE page_1.page_num >= 20;

-- SELECT page_1.*
-- FROM
--   (SELECT
--      SYSUSER.*,
--      ROWNUM page_num
--    FROM
--
--      (
--        SELECT
--          SYSUSER.ID,
--          SYSUSER.SYSID,
--          SYSUSER.USERNAME,
--          SYSUSER.GROUPID,
--          SYSUSER.USERSTATE,
--          DECODE(SYSUSER.GROUPID,
--                 '1', (SELECT USERJD.MC
--                       FROM USERJD
--                       WHERE USERJD.ID = SYSUSER.SYSID),
--                 '2', (SELECT USERJD.MC
--                       FROM USERJD
--                       WHERE USERJD.ID = SYSUSER.SYSID),
--                 '3', (SELECT )
--          )
--        FROM SYSUSER
--      )
--
--      SYSUSER
--    WHERE ROWNUM <= 30) page_1
-- WHERE page_1.page_num >= 20;