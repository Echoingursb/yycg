-- 需求：按药品分类统计
-- 写法一：
select
  business.BM,
  business.ypxxid,
  business.ypxxmc,
  sum(nvl(business.CGL, 0))  cgl,
  sum(nvl(business.CGJE, 0)) CGJE,
  sum(nvl(business.RKL, 0))  RKL,
  sum(nvl(business.RKJE, 0)) RKJE,
  sum(nvl(business.THL, 0))  THL,
  sum(nvl(business.THJE, 0)) THJE,
  sum(nvl(business.JSL, 0))  JSL,
  sum(nvl(business.JSJE, 0)) JSJE
from (
       select
         YYCGD.ID                                                                  yycgdid,
         YYCGD.BM                                                                  yycgdbm,
         USERYY.ID                                                                 useryyid,
         USERYY.MC                                                                 useryymc,
         USERGYS.ID                                                                usergysid,
         USERGYS.MC                                                                usergysmc,
         YPXX.ID                                                                   ypxxid,
         YPXX.BM,
         YPXX.MC                                                                   ypxxmc,
         YPXX.JX,
         YPXX.GG,
         YPXX.ZHXS,
         YPXX.ZBJG,
         YYBUSINESS.CGL,
         YYBUSINESS.CGJE,
         YYBUSINESS.RKL,
         YYBUSINESS.RKJE,
         YYBUSINESS.THL,
         YYBUSINESS.THJE,
         YYBUSINESS.JSL,
         YYBUSINESS.JSJE,
         YYBUSINESS.CGZT,
         (SELECT DICTINFO.INFO
          FROM DICTINFO
          WHERE DICTINFO.TYPECODE = '011' AND DICTINFO.DICTCODE = YYBUSINESS.CGZT) CGZTMC
       from YYBUSINESS2019 YYBUSINESS, YYCGD2019 YYCGD, USERYY, USERGYS, YPXX
       WHERE
         YYBUSINESS.YYCGDID = YYCGD.ID
         AND YYCGD.USERYYID = USERYY.ID
         AND YYBUSINESS.USERGYSID = USERGYS.ID
         AND YYBUSINESS.YPXXID = YPXX.ID
       --   -- 监管单位查询管理地区内医院采购明细信息
       --   AND USERYY.ID IN
       --       (SELECT USERYY.ID
       --        FROM USERYY
       --        WHERE USERYY.DQ LIKE '1.1%')
       --   -- 医院查询自己的采购明细信息
       --   AND USERYY.ID = ''
       --   -- 供货商查询与本供货商相关的采购明细信息
       --   AND USERGYS.ID = '';
     ) business
-- 按药品分类统计
group by business.ypxxid, business.BM, business.ypxxmc;

-- 写法二：
select
  business.*,
  YPXX.ID ypxxid,
  YPXX.BM,
  YPXX.MC
from (
       select
         business.ypxxid,
         sum(nvl(business.CGL, 0))  cgl,
         sum(nvl(business.CGJE, 0)) CGJE,
         sum(nvl(business.RKL, 0))  RKL,
         sum(nvl(business.RKJE, 0)) RKJE,
         sum(nvl(business.THL, 0))  THL,
         sum(nvl(business.THJE, 0)) THJE,
         sum(nvl(business.JSL, 0))  JSL,
         sum(nvl(business.JSJE, 0)) JSJE
       from (
              select
                YYCGD.ID                                                                  yycgdid,
                YYCGD.BM                                                                  yycgdbm,
                USERYY.ID                                                                 useryyid,
                USERYY.MC                                                                 useryymc,
                USERGYS.ID                                                                usergysid,
                USERGYS.MC                                                                usergysmc,
                YPXX.ID                                                                   ypxxid,
                YPXX.BM,
                YPXX.MC                                                                   ypxxmc,
                YPXX.JX,
                YPXX.GG,
                YPXX.ZHXS,
                YPXX.ZBJG,
                YYBUSINESS.CGL,
                YYBUSINESS.CGJE,
                YYBUSINESS.RKL,
                YYBUSINESS.RKJE,
                YYBUSINESS.THL,
                YYBUSINESS.THJE,
                YYBUSINESS.JSL,
                YYBUSINESS.JSJE,
                YYBUSINESS.CGZT,
                (SELECT DICTINFO.INFO
                 FROM DICTINFO
                 WHERE DICTINFO.TYPECODE = '011' AND DICTINFO.DICTCODE = YYBUSINESS.CGZT) CGZTMC
              from YYBUSINESS2019 YYBUSINESS, YYCGD2019 YYCGD, USERYY, USERGYS, YPXX
              WHERE
                YYBUSINESS.YYCGDID = YYCGD.ID
                AND YYCGD.USERYYID = USERYY.ID
                AND YYBUSINESS.USERGYSID = USERGYS.ID
                AND YYBUSINESS.YPXXID = YPXX.ID
                -- 监管单位查询管理地区内医院采购明细信息
                AND USERYY.ID IN
                    (SELECT USERYY.ID
                     FROM USERYY
                     WHERE USERYY.DQ LIKE '1.1%')
                -- 医院查询自己的采购明细信息
                AND USERYY.ID = ''
                -- 供货商查询与本供货商相关的采购明细信息
                AND USERGYS.ID = ''
            ) business
       -- 按药品分类统计
       group by business.ypxxid) business, YPXX
where business.ypxxid = YPXX.ID;

-- 需求：按药品分类统计总数
select count(*)
from (
  select
    business.*,
    YPXX.ID ypxxid,
    YPXX.BM,
    YPXX.MC
  from (
         select
           business.ypxxid,
           sum(nvl(business.CGL, 0))  cgl,
           sum(nvl(business.CGJE, 0)) CGJE,
           sum(nvl(business.RKL, 0))  RKL,
           sum(nvl(business.RKJE, 0)) RKJE,
           sum(nvl(business.THL, 0))  THL,
           sum(nvl(business.THJE, 0)) THJE,
           sum(nvl(business.JSL, 0))  JSL,
           sum(nvl(business.JSJE, 0)) JSJE
         from (
                select
                  YYCGD.ID                                                                  yycgdid,
                  YYCGD.BM                                                                  yycgdbm,
                  USERYY.ID                                                                 useryyid,
                  USERYY.MC                                                                 useryymc,
                  USERGYS.ID                                                                usergysid,
                  USERGYS.MC                                                                usergysmc,
                  YPXX.ID                                                                   ypxxid,
                  YPXX.BM,
                  YPXX.MC                                                                   ypxxmc,
                  YPXX.JX,
                  YPXX.GG,
                  YPXX.ZHXS,
                  YPXX.ZBJG,
                  YYBUSINESS.CGL,
                  YYBUSINESS.CGJE,
                  YYBUSINESS.RKL,
                  YYBUSINESS.RKJE,
                  YYBUSINESS.THL,
                  YYBUSINESS.THJE,
                  YYBUSINESS.JSL,
                  YYBUSINESS.JSJE,
                  YYBUSINESS.CGZT,
                  (SELECT DICTINFO.INFO
                   FROM DICTINFO
                   WHERE DICTINFO.TYPECODE = '011' AND DICTINFO.DICTCODE = YYBUSINESS.CGZT) CGZTMC
                from YYBUSINESS2019 YYBUSINESS, YYCGD2019 YYCGD, USERYY, USERGYS, YPXX
                WHERE
                  YYBUSINESS.YYCGDID = YYCGD.ID
                  AND YYCGD.USERYYID = USERYY.ID
                  AND YYBUSINESS.USERGYSID = USERGYS.ID
                  AND YYBUSINESS.YPXXID = YPXX.ID
                  -- 监管单位查询管理地区内医院采购明细信息
                  AND USERYY.ID IN
                      (SELECT USERYY.ID
                       FROM USERYY
                       WHERE USERYY.DQ LIKE '1.1%')
                  -- 医院查询自己的采购明细信息
                  AND USERYY.ID = ''
                  -- 供货商查询与本供货商相关的采购明细信息
                  AND USERGYS.ID = ''
              ) business
         -- 按药品分类统计
         group by business.ypxxid) business, YPXX
  where business.ypxxid = YPXX.ID
);
