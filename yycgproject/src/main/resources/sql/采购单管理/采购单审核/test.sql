-- 需求：医院提交采购单，由监管单位进行采购单审核，由卫生院及卫生局进行审核。
-- 卫生局可以审核所有医院创建的采购单，卫生院只审核本辖区医院创建的采购单。
select
  USERYY.ID                                                          useryyid,
  USERYY.MC                                                          useryymc,
  YYCGD.ID,
  YYCGD.BM,
  YYCGD.MC,
  YYCGD.CJTIME,
  YYCGD.XGTIME,
  YYCGD.TJTIME,
  YYCGD.SHTIME,
  YYCGD.SHYJ,
  YYCGD.ZT,
  (select DICTINFO.INFO
   from DICTINFO
   where DICTINFO.TYPECODE = '010' and DICTINFO.DICTCODE = YYCGD.ZT) yycgdztmc
from YYCGD2019 YYCGD, USERYY
where
  YYCGD.USERYYID = USERYY.ID
  and YYCGD.ID = '2019100199'
  and YYCGD.ZT = '2'
  and USERYY.ID in (
    select USERYY.ID
    from USERYY
    where USERYY.DQ like '1.1.%'
  );

-- 卫生院只审核本辖区医院创建的采购单
select *
from USERJD
where USERJD.ID = 'c890f6ee-067e-11e3-8a3c-0019d2ce5116';
select *
from USERYY
where USERYY.DQ like '1.13.%';

UPDATE SYSUSER
SET SYSUSER.PWD = '202cb962ac59075b964b07152d234b70';