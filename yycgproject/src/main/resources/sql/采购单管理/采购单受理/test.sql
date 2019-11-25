-- 需求：供货商查询待发货的清单
-- 约束条件
-- 1. 供货商只允许查询自己供应的采购样品信息
-- 2. 采购单为审核通过
-- 3. 采购药品明细状态为‘未确认送货’

select
  YYCGD.ID                                                               yycgdid,
  YYCGD.BM                                                               yycgdbm,
  YYCGD.MC                                                               yycgdmc,
  YYCGD.CJTIME,
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
      and YYCGDMX.USERGYSID = ''
      and YYCGD.ZT = '3'
      and YYCGDMX.CGZT = '1';