-- 需求：按区域统计采购金额(查询出各个镇的名称和各个镇下面所有卫生室的采购金额总和)
-- 思路：将下边的结果集查询出卫生室所属父级区域的名称
select
  areainfo.AREANAME,
  nvl(yybusiness.cgje, 0) cgje
from
  (select
     BSS_SYS_AREA.AREANAME,
     0 cgje
   from BSS_SYS_AREA
   where BSS_SYS_AREA.AREALEVEL = '2') areainfo
  left join (

              select
                yybusiness.AREANAME,
                sum(yybusiness.CGJE) cgje
              from (
                     select
                       yybusiness.*,
                       (select BSS_SYS_AREA.AREANAME
                        from BSS_SYS_AREA
                        where BSS_SYS_AREA.AREAID = yybusiness.PARENTID) AREANAME
                     from (
                            select
                              yybusiness.*,
                              -- 查出卫生室所属区域
                              (select BSS_SYS_AREA.PARENTID
                               from BSS_SYS_AREA
                               where BSS_SYS_AREA.AREAID = yybusiness.useryydq) PARENTID
                            from (
                                   select
                                     yybusiness.useryyid,
                                     yybusiness.CGJE,
                                     (select USERYY.DQ
                                      from USERYY
                                      where USERYY.ID = yybusiness.useryyid) useryydq
                                   from (
                                          select
                                            YYCGD.ID                                                              yycgdid,
                                            YYCGD.BM                                                              yycgdbm,
                                            USERYY.ID                                                             useryyid,
                                            USERYY.MC                                                             useryymc,
                                            USERGYS.ID                                                            usergysid,
                                            USERGYS.MC                                                            usergysmc,
                                            YPXX.ID                                                               ypxxid,
                                            YPXX.BM,
                                            YPXX.MC                                                               ypxxmc,
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
                                             WHERE
                                               DICTINFO.TYPECODE = '011' AND DICTINFO.DICTCODE = YYBUSINESS.CGZT) CGZTMC
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
                                        ) yybusiness) yybusiness) yybusiness) yybusiness
              group by yybusiness.AREANAME) yybusiness on yybusiness.AREANAME = areainfo.AREANAME