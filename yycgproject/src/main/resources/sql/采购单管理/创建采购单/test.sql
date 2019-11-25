-- 使用oracle序列号生成采购单号
select '2014' || YYCGDBM2014.nextval bm
from dual;
-- 需求：医院采购单明细列表查询
-- 分析：
-- 主查询表：医院采购单明细表(YYCGDMX2019)
-- 关联查询表：医院信息表(USERYY) 采购单表(YYCGD2019) 药品信息表(YPXX) 供货商表(USERGYS)

select
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
      and YYCGDMX.USERGYSID = USERGYS.ID;
--       and YYCGDMX.YYCGDID = '2019100100';

-- 需求：采购单明细添加查询
-- 分析：
-- 约束条件：1. 只查询医院本区域供货商的药品目录
--          2. 将采购单中已存在的药品目录过滤掉
select
  GYSYPML.ID                                                                        gysypmlid,
  GYSYPML.USERGYSID,
  USERGYS.MC                                                                        usergysmc,
  -- 供货企业
  GYSYPML_CONTROL.CONTROL,
  (select DICTINFO.INFO
   from DICTINFO
   where DICTINFO.TYPECODE = '008' and DICTINFO.DICTCODE = GYSYPML_CONTROL.CONTROL) controlmc,
  -- 供货状态
  GYSYPML_CONTROL.ADVICE,
  GYSYPML.YPXXID,
  YPXX.ID,
  YPXX.BM,
  -- 流水号
  YPXX.MC,
  -- 通用名
  YPXX.JX,
  -- 剂型
  YPXX.GG,
  -- 规格
  YPXX.ZHXS,
  -- 转换系数
  YPXX.SCQYMC,
  -- 生产企业
  YPXX.SPMC,
  -- 商品名称
  YPXX.ZBJG,
  -- 中标价
  YPXX.JYZT                                                                         zyzt,
  (select DICTINFO.INFO
   from DICTINFO
   where DICTINFO.TYPECODE = '003' and DICTINFO.DICTCODE = YPXX.JYZT)               jyztmc --交易状态
from GYSYPML, USERGYS, GYSYPML_CONTROL, YPXX
where GYSYPML.USERGYSID = USERGYS.ID
      and GYSYPML.YPXXID = YPXX.ID
      and GYSYPML.USERGYSID = GYSYPML_CONTROL.USERGYSID
      and GYSYPML.YPXXID = GYSYPML_CONTROL.YPXXID
      and GYSYPML.USERGYSID in (
  select USERGYSAREA.USERGYSID
  from USERGYSAREA
  where '1.13.12.' like USERGYSAREA.AREAID || '%');
--       and GYSYPML.YPXXID not in (
--   select YYCGDMX.YPXXID
--   from YYCGDMX2019 YYCGDMX
--   where YYCGDMX.YYCGDID = '');

-- 需求：采购单提交，根据采购单号更新采购单，更新为采购单状态(存储数据字典：2：已提交未审核)

-- 需求：统计符合条件的采购单药品明细的采购量、采购金额
select
  SUM(NVL(YYCGDMX.CGL, 0)) cgl,
  SUM(NVL(YYCGDMX.CGJE, 0)) cgje
from YYCGDMX2019 YYCGDMX, YYCGD2019 YYCGD, USERYY, YPXX, USERGYS
where YYCGDMX.YYCGDID = YYCGD.ID
      and YYCGD.USERYYID = USERYY.ID
      and YYCGDMX.YPXXID = YPXX.ID
      and YYCGDMX.USERGYSID = USERGYS.ID
      and YYCGDMX.YYCGDID = '2019100199';
