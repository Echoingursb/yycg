-- 需求：交易明细查询
-- 分析：
-- 主查询表：YYCGDMX(医药采购单明细表)
-- 关联查询表：
-- USERYY(医院) YYCGD(采购单) USERGYS(供货商) YPXX(药品信息) YYCGDRK(入库信息) (YYTHD(退货单信息) YYJSD(结算单信息))
SELECT
  YYCGDMX.*,
  YYCGDRK.RKL,
  YYCGDRK.RKJE
FROM
  (
    select
      YYCGDMX.YYCGDID,
      USERYY.ID  useryyid,
      USERYY.MC  useryymc,
      USERGYS.ID usergysid,
      USERGYS.MC usergysmc,
      YPXX.ID    ypxxid,
      YPXX.BM,
      YPXX.MC    ypxxmc,
      YPXX.JX,
      YPXX.GG,
      YPXX.ZHXS,
      YPXX.ZBJG,
      YYCGDMX.CGL,
      YYCGDMX.CGJE
    from YYCGDMX2019 YYCGDMX, YYCGD2019 YYCGD, USERYY, USERGYS, YPXX
    WHERE
      YYCGDMX.YYCGDID = YYCGD.ID
      AND YYCGD.USERYYID = USERYY.ID
      AND YYCGDMX.USERGYSID = USERGYS.ID
      AND YYCGDMX.YPXXID = YPXX.ID
  ) YYCGDMX
  LEFT OUTER JOIN YYCGDRK2019 YYCGDRK ON YYCGDMX.YYCGDID = YYCGDRK.YYCGDID AND YYCGDMX.ypxxid = YYCGDRK.YPXXID;

-- 需求：交易明细查询
-- 分析：
-- 主查询表：YYBUSINESS(交易明细)
-- 关联查询表：
-- USERYY(医院) YYCGD(采购单) USERGYS(供货商) YPXX(药品信息) YYCGDRK(入库信息) (YYTHD(退货单信息) YYJSD(结算单信息))

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
  AND USERGYS.ID = '';