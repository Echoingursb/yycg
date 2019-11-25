-- 需求：查询采购单列表
select
  USERYY.ID                                                          useryyid,
  USERYY.MC                                                          useryymc,
  YYCGD.ID,
  YYCGD.BM,
  YYCGD.MC,
  YYCGD.CJTIME,
  YYCGD.XGTIME,
  YYCGD.TJTIME,
  YYCGD.ZT,
  (select DICTINFO.INFO
   from DICTINFO
   where DICTINFO.TYPECODE = '010' and DICTINFO.DICTCODE = YYCGD.ZT) yycgdztmc
from YYCGD2019 YYCGD, USERYY
where
  YYCGD.USERYYID = USERYY.ID
  and YYCGD.ID = '2019100199';

select COUNT(*)
from YYCGD2019 YYCGD, USERYY
where YYCGD.USERYYID = USERYY.ID
      and USERYY.ID = 'c9396c43-067e-11e3-8a3c-0019d2ce5116'
