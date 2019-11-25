-- 内连接
select *
from GYSYPML, GYSYPML_CONTROL
where GYSYPML.USERGYSID = GYSYPML_CONTROL.USERGYSID
      and GYSYPML.YPXXID = GYSYPML_CONTROL.YPXXID;

-- 子查询
select
  GYSYPML.*,
  (select GYSYPML_CONTROL.CONTROL
   from GYSYPML_CONTROL
   where GYSYPML.YPXXID = GYSYPML_CONTROL.YPXXID and GYSYPML.USERGYSID = GYSYPML_CONTROL.USERGYSID) control
from GYSYPML;

-- 1. 需求：查询供货商药品目录信息
-- 分析：
-- 主查询表：供货商药品目录表(GYSYPML)
-- 关联查询表：供货商药品目录控制表(GYSYPML_CONTROL) 供货商表(USERGYS) 药品信息表(YPXX)
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
      and GYSYPML.USERGYSID = '5197cdd2-08cf-11e3-8a4f-60a44cea4388'; -- 数据范围权限
--       and GYSYPML_CONTROL.CONTROL = '1';

SELECT page_2.*
FROM (SELECT
        page_1.*,
        ROWNUM page_num
      FROM (

             select
               GYSYPML.ID                                                                        gysypmlid,
               GYSYPML.USERGYSID,
               USERGYS.MC                                                                        usergysmc,
               GYSYPML_CONTROL.CONTROL,
               (select DICTINFO.INFO
                from DICTINFO
                where DICTINFO.TYPECODE = '008' and DICTINFO.DICTCODE = GYSYPML_CONTROL.CONTROL) controlmc,
               GYSYPML_CONTROL.ADVICE,
               GYSYPML.YPXXID,
               YPXX.ID,
               YPXX.BM,
               YPXX.MC,
               YPXX.JX,
               YPXX.GG,
               YPXX.ZHXS,
               YPXX.SCQYMC,
               YPXX.SPMC,
               YPXX.ZBJG,
               YPXX.JYZT                                                                         zyzt,
               (select DICTINFO.INFO
                from DICTINFO
                where DICTINFO.TYPECODE = '003' and DICTINFO.DICTCODE = YPXX.JYZT)               jyztmc
             from GYSYPML, USERGYS, GYSYPML_CONTROL, YPXX
             where GYSYPML.USERGYSID = USERGYS.ID and GYSYPML.YPXXID = YPXX.ID and
                   GYSYPML.USERGYSID = GYSYPML_CONTROL.USERGYSID and GYSYPML.YPXXID = GYSYPML_CONTROL.YPXXID and
                   GYSYPML.USERGYSID = '' and GYSYPML_CONTROL.USERGYSID = ''

           ) page_1
      WHERE ROWNUM <= 15) page_2
WHERE page_2.page_num >= 0;

-- 2. 需求：供货商药品目录添加查询，查询药品目录表，查询列表中将供货商药品目录已存在的药品过滤掉

select *
from YPXX
where YPXX.ID NOT IN
      (select GYSYPML.YPXXID -- 某个供货商药品目录结果集
       from GYSYPML
       where GYSYPML.USERGYSID = '5197cdd2-08cf-11e3-8a4f-60a44cea4388');

select
  YPXX.*,
  -- 子查询 关联查询到说明此药品id在供货商药品目录存在
  (select GYSYPML.ID
   from GYSYPML
   where GYSYPML.USERGYSID = '5197cdd2-08cf-11e3-8a4f-60a44cea4388' and YPXX.ID = GYSYPML.YPXXID) gysypmlid
from YPXX;

select
  YPXX.ID,
  YPXX.BM,
  YPXX.MC,
  YPXX.JX,
  YPXX.GG,
  YPXX.ZHXS,
  YPXX.SCQYMC,
  YPXX.SPMC,
  YPXX.ZBJG,
  YPXX.JYZT                                                           zyzt,
  (select DICTINFO.INFO
   from DICTINFO
   where DICTINFO.TYPECODE = '003' and DICTINFO.DICTCODE = YPXX.JYZT) jyztmc
from YPXX
where NOT EXISTS(select GYSYPML.ID
                 from GYSYPML
                 where GYSYPML.USERGYSID = '5197cdd2-08cf-11e3-8a4f-60a44cea4388' and YPXX.ID = GYSYPML.YPXXID);
-- 每个记录查询时，都需要执行exists中的子查询
-- 建议子查询条件根据主键或索引查询，可以提高效率，使用exists比in速度要快

select COUNT(*)
from YPXX
where NOT EXISTS(select GYSYPML.ID
                 from GYSYPML
                 where GYSYPML.USERGYSID = '5197cdd2-08cf-11e3-8a4f-60a44cea4388' and YPXX.ID = GYSYPML.YPXXID);

-- 需求：监管单位查询供货商药品目录信息
-- 分析
-- 主查询表：供货商药品目录控制表
-- 关联查询表：供货商表(USERGYS) 药品信息表(YPXX)
SELECT page_2.*
FROM (SELECT
        page_1.*,
        ROWNUM page_num
      FROM (

             select
               GYSYPML_CONTROL.ID                                                                gysypmlcontrolid,
               GYSYPML_CONTROL.USERGYSID,
               USERGYS.MC                                                                        usergysmc,
               GYSYPML_CONTROL.CONTROL,
               (select DICTINFO.INFO
                from DICTINFO
                where DICTINFO.TYPECODE = '008' and DICTINFO.DICTCODE = GYSYPML_CONTROL.CONTROL) controlmc,
               GYSYPML_CONTROL.ADVICE,
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
                where DICTINFO.TYPECODE = '003' and DICTINFO.DICTCODE = YPXX.JYZT)               jyztmc
             from GYSYPML_CONTROL, USERGYS, YPXX
             where GYSYPML_CONTROL.USERGYSID = USERGYS.ID
                   and GYSYPML_CONTROL.YPXXID = YPXX.ID


           ) page_1
      WHERE ROWNUM <= 15) page_2
WHERE page_2.page_num >= 0;

-- 需求：监督单位更新供货状态和审核意见
update GYSYPML_CONTROL
set GYSYPML_CONTROL.CONTROL = '' and GYSYPML_CONTROL.ADVICE = ''
where GYSYPML_CONTROL.ID = '';
