package yycg.business.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import yycg.base.dao.mapper.UserjdMapper;
import yycg.base.dao.mapper.UseryyMapper;
import yycg.base.pojo.po.Userjd;
import yycg.base.pojo.po.Useryy;
import yycg.base.process.context.Config;
import yycg.base.process.result.ResultUtil;
import yycg.base.service.SystemConfigService;
import yycg.business.dao.mapper.*;
import yycg.business.pojo.po.*;
import yycg.business.pojo.vo.YycgdCustom;
import yycg.business.pojo.vo.YycgdQueryVo;
import yycg.business.pojo.vo.YycgdmxCustom;
import yycg.business.pojo.vo.YycgdrkCustom;
import yycg.business.service.CgdService;
import yycg.util.MyUtil;
import yycg.util.UUIDBuild;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName CgdServiceImpl
 * @Description: 采购单ServiceImpl
 * @Author echo
 * @Date 2019/10/18
 * @Version V1.0
 **/
public class CgdServiceImpl implements CgdService {
    @Autowired
    private YycgdMapper yycgdMapper;
    @Autowired
    private YycgdMapperCustom yycgdMapperCustom;
    @Autowired
    private UseryyMapper useryyMapper;
    @Autowired
    private YpxxMapper ypxxMapper;
    @Autowired
    private YycgdmxMapper yycgdmxMapper;
    @Autowired
    private UserjdMapper userjdMapper;
    @Autowired
    private YycgdrkMapper yycgdrkMapper;
    @Autowired
    private YybusinessMapper yybusinessMapper;
    @Autowired
    private SystemConfigService systemConfigService;

    /**
     * 创建采购单基本信息
     *
     * @param useryyid    医院id
     * @param year        年份
     * @param yycgdCustom 插入的采购单信息
     * @return 采购单编号
     * @throws Exception 如果创建采购单基本信息，则抛出异常
     */
    @Override
    public String insertYycgd(String useryyid, String year, YycgdCustom yycgdCustom) throws Exception {
        String bm = yycgdMapperCustom.getYycgdBm(year); // 采购单编号
        yycgdCustom.setId(bm);
        yycgdCustom.setBm(bm);
        yycgdCustom.setUseryyid(useryyid);
        yycgdCustom.setCjtime(new Date());
        yycgdCustom.setZt("1");
        yycgdCustom.setBusinessyear(year);
        yycgdMapper.insert(yycgdCustom);
        return bm;
    }

    /**
     * 通过采购单编号查询采购单
     *
     * @param id 采购单编号
     * @return YycgdCustom采购单
     * @throws Exception 如果通过采购单编号查询采购单失败，则抛出异常
     */
    @Override
    public YycgdCustom findYycgdById(String id) throws Exception {
        String businessyear = id.substring(0, 4);
        YycgdExample yycgdExample = new YycgdExample();
        YycgdExample.Criteria criteria = yycgdExample.createCriteria();
        criteria.andIdEqualTo(id);
        yycgdExample.setBusinessyear(businessyear);
        List<Yycgd> list = yycgdMapper.selectByExample(yycgdExample);
        YycgdCustom yycgdCustom = new YycgdCustom();
        Yycgd yycgd = null;
        if (list != null && list.size() == 1) {
            yycgd = list.get(0);
            BeanUtils.copyProperties(yycgd, yycgdCustom);
        } else {
            ResultUtil.createFail(Config.MESSAGE, 501, null);
        }
        String zt = Objects.requireNonNull(yycgd).getZt();
        String ztmc = systemConfigService.findDictinfoByDictcode("010", zt).getInfo();
        yycgdCustom.setYycgdztmc(ztmc);
        return yycgdCustom;
    }

    /**
     * 根据id修改采购单信息
     *
     * @param id          采购单id(和采购单编号相同)
     * @param yycgdCustom 修改信息
     * @throws Exception 如果根据id修改采购单信息失败， 则抛出异常
     */
    @Override
    public void updateYycgd(String id, YycgdCustom yycgdCustom) throws Exception {
        String year = id.substring(0, 4);
        YycgdCustom yycgdCustom_old = findYycgdById(id);
        yycgdCustom_old.setMc(yycgdCustom.getMc());
        yycgdCustom_old.setLxr(yycgdCustom.getLxr());
        yycgdCustom_old.setLxdh(yycgdCustom.getLxdh());
        yycgdCustom_old.setBz(yycgdCustom.getBz());
        yycgdCustom_old.setBusinessyear(year);
        yycgdMapper.updateByPrimaryKey(yycgdCustom_old);
    }

    /**
     * 统计符合条件的采购单药品明细的采购量、采购金额
     *
     * @param yycgdid      采购单id
     * @param yycgdQueryVo 查询条件
     * @return 采购单药品明细的采购量、采购金额
     * @throws Exception 如果统计符合条件的采购单药品明细的采购量、采购金额失败，则抛出异常
     */
    @Override
    public List<YycgdmxCustom> selectYycgdmxListSum(String yycgdid, YycgdQueryVo yycgdQueryVo) throws Exception {
        yycgdQueryVo = yycgdQueryVo != null ? yycgdQueryVo : new YycgdQueryVo();
        YycgdmxCustom yycgdmxCustom = yycgdQueryVo.getYycgdmxCustom();
        yycgdmxCustom = yycgdmxCustom != null ? yycgdmxCustom : new YycgdmxCustom();
        yycgdmxCustom.setYycgdid(yycgdid);
        yycgdQueryVo.setYycgdmxCustom(yycgdmxCustom);
        String businessyear = yycgdid.substring(0, 4);
        yycgdQueryVo.setBusinessyear(businessyear);
        return yycgdMapperCustom.selectYycgdmxListSum(yycgdQueryVo);
    }


    /**
     * 医院采购单明细列表查询
     *
     * @param yycgdid      医院采购单id
     * @param yycgdQueryVo 查询条件
     * @return 医院采购单明细列表
     * @throws Exception 如果医院采购单明细列表查询失败，则抛出异常
     */
    @Override
    public List<YycgdmxCustom> selectYycgdmxListByYycgdid(String yycgdid, YycgdQueryVo yycgdQueryVo) throws Exception {
        yycgdQueryVo = yycgdQueryVo != null ? yycgdQueryVo : new YycgdQueryVo();
        String year = yycgdid.substring(0, 4);
        YycgdmxCustom yycgdmxCustom = yycgdQueryVo.getYycgdmxCustom();
        if (yycgdmxCustom == null)
            yycgdmxCustom = new YycgdmxCustom();
        yycgdmxCustom.setYycgdid(yycgdid);
        yycgdQueryVo.setYycgdmxCustom(yycgdmxCustom);
        yycgdQueryVo.setBusinessyear(year);
        return yycgdMapperCustom.selectYycgdmxList(yycgdQueryVo);
    }

    /**
     * 医院采购单明细列表查询总数
     *
     * @param yycgdid      医院采购单id
     * @param yycgdQueryVo 查询条件
     * @return 医院采购单明细列表总数
     * @throws Exception 如果医院采购单明细列表查询总数失败，则抛出异常
     */
    @Override
    public int selectYycgdmxCountByYycgdid(String yycgdid, YycgdQueryVo yycgdQueryVo) throws Exception {
        yycgdQueryVo = yycgdQueryVo != null ? yycgdQueryVo : new YycgdQueryVo();
        String year = yycgdid.substring(0, 4);
        YycgdmxCustom yycgdmxCustom = yycgdQueryVo.getYycgdmxCustom();
        if (yycgdmxCustom == null)
            yycgdmxCustom = new YycgdmxCustom();
        yycgdmxCustom.setYycgdid(yycgdid);
        yycgdQueryVo.setYycgdmxCustom(yycgdmxCustom);
        yycgdQueryVo.setBusinessyear(year);
        return yycgdMapperCustom.selectYycgdmxCount(yycgdQueryVo);
    }

    /**
     * 采购单明细添加查询
     *
     * @param useryyid     医院id
     * @param yycgdid      医院采购单id
     * @param yycgdQueryVo 查询条件
     * @return 采购单明细添加查询列表
     * @throws Exception 如果采购单明细添加查询失败，则抛出异常
     */
    @Override
    public List<YycgdmxCustom> selectAddYycgdmxList(String useryyid, String yycgdid, YycgdQueryVo yycgdQueryVo) throws Exception {
        yycgdQueryVo = yycgdQueryVo != null ? yycgdQueryVo : new YycgdQueryVo();
        // 设置医院地区
        Useryy useryy = useryyMapper.selectByPrimaryKey(useryyid);
        String dq = useryy.getDq();
        Useryy useryy1 = yycgdQueryVo.getUseryy();
        if (useryy1 == null)
            useryy1 = new Useryy();
        useryy1.setDq(dq);
        yycgdQueryVo.setUseryy(useryy1);
        // 设置年份
        String businessyear = yycgdid.substring(0, 4);
        yycgdQueryVo.setBusinessyear(businessyear);
        // 设置医院采购单id
        YycgdCustom yycgdCustom = yycgdQueryVo.getYycgdCustom();
        if (yycgdCustom == null)
            yycgdCustom = new YycgdCustom();
        yycgdCustom.setId(yycgdid);
        yycgdQueryVo.setYycgdCustom(yycgdCustom);
        return yycgdMapperCustom.selectAddYycgdmxList(yycgdQueryVo);
    }

    /**
     * 采购单明细添加查询总数
     *
     * @param useryyid     医院id
     * @param yycgdid      医院采购单id
     * @param yycgdQueryVo 查询条件
     * @return 采购单明细添加查询总数
     * @throws Exception 如果采购单明细添加查询总数失败，则抛出异常
     */
    @Override
    public int selectAddYycgdmxCount(String useryyid, String yycgdid, YycgdQueryVo yycgdQueryVo) throws Exception {
        yycgdQueryVo = yycgdQueryVo != null ? yycgdQueryVo : new YycgdQueryVo();
        // 设置医院地区
        Useryy useryy = useryyMapper.selectByPrimaryKey(useryyid);
        String dq = useryy.getDq();
        Useryy useryy1 = yycgdQueryVo.getUseryy();
        if (useryy1 == null)
            useryy1 = new Useryy();
        useryy1.setDq(dq);
        yycgdQueryVo.setUseryy(useryy1);
        // 设置年份
        String businessyear = yycgdid.substring(0, 4);
        yycgdQueryVo.setBusinessyear(businessyear);
        // 设置医院采购单id
        YycgdCustom yycgdCustom = yycgdQueryVo.getYycgdCustom();
        if (yycgdCustom == null)
            yycgdCustom = new YycgdCustom();
        yycgdCustom.setId(yycgdid);
        yycgdQueryVo.setYycgdCustom(yycgdCustom);
        return yycgdMapperCustom.selectAddYycgdmxCount(yycgdQueryVo);
    }

    /**
     * 采购单药品明细添加
     *
     * @param yycgdid   医院采购单id
     * @param ypxxid    药品信息id
     * @param usergysid 供货商id
     * @throws Exception 如果采购单药品明细添加失败，则抛出异常
     */
    @Override
    public void insertYycgdmx(String yycgdid, String ypxxid, String usergysid) throws Exception {
        Ypxx ypxx = ypxxMapper.selectByPrimaryKey(ypxxid);
        if (ypxx == null)
            ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 509, null));
        Yycgdmx yycgdmx = findYycgdmxByYycgdidAndYpxxid(yycgdid, ypxxid);
        if (yycgdmx != null)
            ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 508, null));
        Yycgdmx yycgdmx1 = new Yycgdmx();
        String businessyear = yycgdid.substring(0, 4);
        yycgdmx1.setBusinessyear(businessyear);
        yycgdmx1.setId(UUIDBuild.getUUID());
        yycgdmx1.setYycgdid(yycgdid);
        yycgdmx1.setYpxxid(ypxxid);
        yycgdmx1.setUsergysid(usergysid);
        yycgdmx1.setZbjg(ypxx.getZbjg());
        yycgdmx1.setJyjg(ypxx.getZbjg());
        yycgdmx1.setCgzt("1"); // 系统默认为1、未确认送货
        yycgdmxMapper.insert(yycgdmx1);
    }

    /**
     * 根据医院采购单id和药品信息id查询医药采购单明细
     *
     * @param yycgdid 医院采购单id
     * @param ypxxid  药品信息id
     * @return 医药采购单明细
     * @throws Exception 如果根据供货商id和药品信息id查询医药采购单明细失败，则抛出异常
     */
    @Override
    public Yycgdmx findYycgdmxByYycgdidAndYpxxid(String yycgdid, String ypxxid) throws Exception {
        YycgdmxExample yycgdmxExample = new YycgdmxExample();
        YycgdmxExample.Criteria criteria = yycgdmxExample.createCriteria();
        criteria.andYycgdidEqualTo(yycgdid);
        criteria.andYpxxidEqualTo(ypxxid);
        // 设置年份
        String businessyear = yycgdid.substring(0, 4);
        yycgdmxExample.setBusinessyear(businessyear);
        List<Yycgdmx> list = yycgdmxMapper.selectByExample(yycgdmxExample);
        if (list != null && list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 根据医院采购单id和药品信息id修改采购量
     *
     * @param yycgdid 医院采购单id
     * @param ypxxid  药品信息id
     * @param cgl     采购量
     * @throws Exception 如果根据医院采购单id和药品信息id修改采购量，则抛出异常
     */
    @Override
    public void updateYycgdmx(String yycgdid, String ypxxid, Integer cgl) throws Exception {
        Yycgdmx yycgdmx = findYycgdmxByYycgdidAndYpxxid(yycgdid, ypxxid);
        if (yycgdmx == null)
            ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 509, null));
        else {
            Float jyjg = yycgdmx.getJyjg();
            Float cgje = null;
            if (cgl == null || cgl <= 0)
                ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 711, null));
            else {
                cgje = jyjg * cgl;
            }
            Yycgdmx yycgdmx_update = new Yycgdmx();
            yycgdmx_update.setId(yycgdmx.getId());
            yycgdmx_update.setCgl(cgl);
            yycgdmx_update.setCgje(cgje);
            // 设置年份
            yycgdmx_update.setBusinessyear(yycgdid.substring(0, 4));
            yycgdmxMapper.updateByPrimaryKeySelective(yycgdmx_update);
        }
    }

    /**
     * 查询采购单列表
     *
     * @param useryyid     医院Id
     * @param year         年份
     * @param yycgdQueryVo 查询条件
     * @return 查询采购单列表
     * @throws Exception 如果查询采购单列表失败，则抛出异常
     */
    @Override
    public List<YycgdCustom> selectYycgdList(String useryyid, String year, YycgdQueryVo yycgdQueryVo) throws Exception {
        yycgdQueryVo = yycgdQueryVo != null ? yycgdQueryVo : new YycgdQueryVo();
        Useryy useryy = yycgdQueryVo.getUseryy();
        if (useryy == null)
            useryy = new Useryy();
        useryy.setId(useryyid);
        yycgdQueryVo.setUseryy(useryy);
        yycgdQueryVo.setBusinessyear(year);
        return yycgdMapperCustom.selectYycgdList(yycgdQueryVo);
    }

    /**
     * 查询采购单列表总数
     *
     * @param useryyid     医院Id
     * @param year         年份
     * @param yycgdQueryVo 查询条件
     * @return 查询采购单列表总数
     * @throws Exception 如果查询采购单列表总数失败，则抛出异常
     */
    @Override
    public int selectYycgdCount(String useryyid, String year, YycgdQueryVo yycgdQueryVo) throws Exception {
        yycgdQueryVo = yycgdQueryVo != null ? yycgdQueryVo : new YycgdQueryVo();
        Useryy useryy = yycgdQueryVo.getUseryy();
        if (useryy == null)
            useryy = new Useryy();
        useryy.setId(useryyid);
        yycgdQueryVo.setUseryy(useryy);
        yycgdQueryVo.setBusinessyear(year);
        return yycgdMapperCustom.selectYycgdCount(yycgdQueryVo);
    }

    /**
     * 采购单提交，根据采购单号更新采购单，更新为采购单状态(存储数据字典：2：已提交未审核)
     *
     * @param yycgdid 医药采购单id
     * @throws Exception 如果根据采购单号更新采购单更新失败，则抛出异常
     */
    @Override
    public void saveYycgdSubmit(String yycgdid) throws Exception {
        // 1. 采购单在未提交或审核不通过时方可执行提交操作
        Yycgd yycgd = findYycgdById(yycgdid);
        if (yycgd == null) {
            ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 501, null));
        } else {
            String zt = yycgd.getZt();
            if (!zt.equals("1") && !zt.equals("4")) {
                ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 502, null));
            }

            // 2. 采购单必须包括采购药品明细方可提交
            List<YycgdmxCustom> yycgdmxCustoms = selectYycgdmxListByYycgdid(yycgdid, null);
            if (yycgdmxCustoms == null || yycgdmxCustoms.size() <= 0)
                ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 504, null));
            else {
                // 3. 采购单的采购药品明细信息必须完整（采购量、采购金额必须指定）
                for (YycgdmxCustom yycgdmxCustom : yycgdmxCustoms) {
                    Integer cgl = yycgdmxCustom.getCgl();
                    if (cgl == null)
                        ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 505, null));
                }
            }
            yycgd.setZt("2");
            yycgd.setTjtime(MyUtil.getNowDate());
            yycgd.setBusinessyear(yycgdid.substring(0, 4));
            yycgdMapper.updateByPrimaryKey(yycgd);
        }


    }

    /**
     * 查询审核采购单列表
     *
     * @param userjdid     监督单位id
     * @param year         年份
     * @param yycgdQueryVo 查询条件
     * @return 审核采购单列表
     * @throws Exception 如果查询审核采购单列表失败，则抛出异常
     */
    @Override
    public List<YycgdCustom> findYycgdCheckList(String userjdid, String year, YycgdQueryVo yycgdQueryVo) throws Exception {
        yycgdQueryVo = yycgdQueryVo != null ? yycgdQueryVo : new YycgdQueryVo();
        YycgdCustom yycgdCustom = yycgdQueryVo.getYycgdCustom();
        if (yycgdCustom == null)
            yycgdCustom = new YycgdCustom();
        yycgdCustom.setZt("2");
        yycgdQueryVo.setYycgdCustom(yycgdCustom);

        Userjd userjd = userjdMapper.selectByPrimaryKey(userjdid);
        if (userjd == null)
            userjd = new Userjd();
        String dq = userjd.getDq();

        Useryy useryy = yycgdQueryVo.getUseryy();
        if (useryy == null)
            useryy = new Useryy();
        useryy.setDq(dq);
        yycgdQueryVo.setUseryy(useryy);
        yycgdQueryVo.setBusinessyear(year);
        return yycgdMapperCustom.selectYycgdList(yycgdQueryVo);
    }

    /**
     * 查询审核采购单列表总数
     *
     * @param userjdid     监督单位id
     * @param year         年份
     * @param yycgdQueryVo 查询条件
     * @return 审核采购单列表总数
     * @throws Exception 如果查询审核采购单列表总数失败，则抛出异常
     */
    @Override
    public int findYycgdCheckCount(String userjdid, String year, YycgdQueryVo yycgdQueryVo) throws Exception {
        yycgdQueryVo = yycgdQueryVo != null ? yycgdQueryVo : new YycgdQueryVo();
        YycgdCustom yycgdCustom = yycgdQueryVo.getYycgdCustom();
        if (yycgdCustom == null)
            yycgdCustom = new YycgdCustom();
        yycgdCustom.setZt("2");
        yycgdQueryVo.setYycgdCustom(yycgdCustom);

        Userjd userjd = userjdMapper.selectByPrimaryKey(userjdid);
        String dq = userjd.getDq();

        Useryy useryy = yycgdQueryVo.getUseryy();
        if (useryy == null)
            useryy = new Useryy();
        useryy.setDq(dq);
        yycgdQueryVo.setUseryy(useryy);
        yycgdQueryVo.setBusinessyear(year);
        return yycgdMapperCustom.selectYycgdCount(yycgdQueryVo);
    }

    /**
     * 采购单审核
     *
     * @param yycgdid     采购单id
     * @param yycgdCustom 审核信息(审核结果和意见)
     * @throws Exception 如果采购单审核失败，则抛出异常
     */
    @Override
    public void saveYycgdCheckStatus(String yycgdid, YycgdCustom yycgdCustom) throws Exception {
        Yycgd yycgd = findYycgdById(yycgdid);
        if (yycgd == null)
            ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 501, null));
        String zt = yycgd.getZt();
        if (!zt.equals("2"))
            ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 514, null));
        String zt1 = yycgdCustom.getZt();
        if (zt1 == null || (!zt1.equals("3") && !zt1.equals("4")))
            ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 513, null));
        String businessyear = yycgdid.substring(0, 4);
        Yycgd yycgd_update = new Yycgd();
        yycgd_update.setId(yycgd.getId());
        yycgd_update.setZt(zt1);
        yycgd_update.setShyj(yycgdCustom.getShyj());
        yycgd_update.setShtime(MyUtil.getNowDate());
        yycgd_update.setBusinessyear(businessyear);
        yycgdMapper.updateByPrimaryKeySelective(yycgd_update);
        // 当采购单审核通过时，将采购单明细记录插入到交易明细表
        if (yycgdCustom.getZt().equals("3")) {
            List<YycgdmxCustom> yycgdmxCustoms = selectYycgdmxListByYycgdid(yycgdid, null);
            for (YycgdmxCustom yycgdmxCustom : yycgdmxCustoms) {
                Yybusiness yybusiness = new Yybusiness();
                yybusiness.setId(UUIDBuild.getUUID());
                yybusiness.setYycgdid(yycgdid);
                yybusiness.setUseryyid(yycgdmxCustom.getUseryyid());
                yybusiness.setYpxxid(yycgdmxCustom.getId());
                yybusiness.setZbjg(yycgdmxCustom.getZbjg());
                yybusiness.setJyjg(yycgdmxCustom.getJyjg());
                yybusiness.setCgl(yycgdmxCustom.getCgl());
                yybusiness.setCgje(yycgdmxCustom.getCgje());
                yybusiness.setCgzt(yycgdmxCustom.getCgzt());
                yybusiness.setUsergysid(yycgdmxCustom.getUsergysid());
                yybusiness.setBusinessyear(businessyear);
                yybusinessMapper.insert(yybusiness);
            }
        }
    }

    /**
     * 查询采购单受理列表
     *
     * @param usergysid    供货商id
     * @param year         年份
     * @param yycgdQueryVo 查询条件
     * @return 采购单受理列表
     * @throws Exception 如果查询采购单受理列表失败，则抛出异常
     */
    @Override
    public List<YycgdmxCustom> findDiposeYycgdList(String usergysid, String year, YycgdQueryVo yycgdQueryVo) throws Exception {
        yycgdQueryVo = yycgdQueryVo != null ? yycgdQueryVo : new YycgdQueryVo();
        // 1. 供货商只允许查询自己供应的采购药品信息
        YycgdmxCustom yycgdmxCustom = yycgdQueryVo.getYycgdmxCustom();
        yycgdmxCustom = yycgdmxCustom != null ? yycgdmxCustom : new YycgdmxCustom();
        yycgdmxCustom.setUsergysid(usergysid);
        // 3. 采购药品明细状态为“未确认送货”
        yycgdmxCustom.setCgzt("1");
        yycgdQueryVo.setYycgdmxCustom(yycgdmxCustom);
        // 2. 采购单为审核通过
        YycgdCustom yycgdCustom = yycgdQueryVo.getYycgdCustom();
        yycgdCustom = yycgdCustom != null ? yycgdCustom : new YycgdCustom();
        yycgdCustom.setZt("3");
        yycgdQueryVo.setYycgdCustom(yycgdCustom);
        yycgdQueryVo.setBusinessyear(year);
        return yycgdMapperCustom.selectYycgdmxList(yycgdQueryVo);
    }

    /**
     * 查询采购单受理列表总数
     *
     * @param usergysid    供货商id
     * @param year         年份
     * @param yycgdQueryVo 查询条件
     * @return 采购单受理列表总数
     * @throws Exception 如果查询采购单受理列表总数失败，则抛出异常
     */
    @Override
    public int findDiposeYycgdCount(String usergysid, String year, YycgdQueryVo yycgdQueryVo) throws Exception {
        yycgdQueryVo = yycgdQueryVo != null ? yycgdQueryVo : new YycgdQueryVo();
        // 1. 供货商只允许查询自己供应的采购药品信息
        YycgdmxCustom yycgdmxCustom = yycgdQueryVo.getYycgdmxCustom();
        yycgdmxCustom = yycgdmxCustom != null ? yycgdmxCustom : new YycgdmxCustom();
        yycgdmxCustom.setUsergysid(usergysid);
        // 3. 采购药品明细状态为“未确认送货”
        yycgdmxCustom.setCgzt("1");
        yycgdQueryVo.setYycgdmxCustom(yycgdmxCustom);
        // 2. 采购单为审核通过
        YycgdCustom yycgdCustom = yycgdQueryVo.getYycgdCustom();
        yycgdCustom = yycgdCustom != null ? yycgdCustom : new YycgdCustom();
        yycgdCustom.setZt("3");
        yycgdQueryVo.setYycgdCustom(yycgdCustom);
        yycgdQueryVo.setBusinessyear(year);
        return yycgdMapperCustom.selectYycgdmxCount(yycgdQueryVo);
    }

    /**
     * 确认发货
     *
     * @param yycgdid 采购单id
     * @param ypxxid  药品信息id
     * @throws Exception 如果确认发货失败，则抛出异常
     */
    @Override
    public void saveSendStatus(String yycgdid, String ypxxid) throws Exception {
        Yycgdmx yycgdmx = findYycgdmxByYycgdidAndYpxxid(yycgdid, ypxxid);
        Ypxx ypxx = ypxxMapper.selectByPrimaryKey(ypxxid);
        String bm = ypxx.getBm();
        String mc = ypxx.getMc();
        if (yycgdmx == null)
            ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 509, null));
        // 校验当前采购药品状态为“未确认送货”，方可执行发货操作
        String cgzt = yycgdmx.getCgzt();
        if (!cgzt.equals("1"))
            ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 550, new Object[]{
                    bm, mc, yycgdid
            }));
        // 根据采购单id、药品id去更新采购药品状态为“已发货”
        yycgdmx.setCgzt("2");
        String businessyear = yycgdid.substring(0, 4);
        yycgdmx.setBusinessyear(businessyear);
        yycgdmxMapper.updateByPrimaryKey(yycgdmx);
    }

    /**
     * 查询待入库列表
     *
     * @param useryyid     医院id
     * @param year         年份
     * @param yycgdQueryVo 查询条件
     * @return 待入库列表
     * @throws Exception 如果查询待入库列表失败，则抛出异常
     */
    @Override
    public List<YycgdmxCustom> findYycgdReceiveList(String useryyid, String year, YycgdQueryVo yycgdQueryVo) throws Exception {
        yycgdQueryVo = yycgdQueryVo != null ? yycgdQueryVo : new YycgdQueryVo();
        // 1. 医院只查询本医院采购信息
        YycgdCustom yycgdCustom = yycgdQueryVo.getYycgdCustom();
        yycgdCustom = yycgdCustom != null ? yycgdCustom : new YycgdCustom();
        yycgdCustom.setUseryyid(useryyid);
        yycgdQueryVo.setYycgdCustom(yycgdCustom);
        // 2. 药品的采购状态为“已发货”
        YycgdmxCustom yycgdmxCustom = yycgdQueryVo.getYycgdmxCustom();
        yycgdmxCustom = yycgdmxCustom != null ? yycgdmxCustom : new YycgdmxCustom();
        yycgdmxCustom.setCgzt("2");
        yycgdQueryVo.setYycgdmxCustom(yycgdmxCustom);
        yycgdQueryVo.setBusinessyear(year);
        return yycgdMapperCustom.selectYycgdmxList(yycgdQueryVo);
    }

    /**
     * 查询待入库列表
     *
     * @param useryyid     医院id
     * @param year         年份
     * @param yycgdQueryVo 查询条件
     * @return 待入库列表
     * @throws Exception 如果查询待入库列表失败，则抛出异常
     */
    @Override
    public int findYycgdReceiveCount(String useryyid, String year, YycgdQueryVo yycgdQueryVo) throws Exception {
        yycgdQueryVo = yycgdQueryVo != null ? yycgdQueryVo : new YycgdQueryVo();
        // 1. 医院只查询本医院采购信息
        YycgdCustom yycgdCustom = yycgdQueryVo.getYycgdCustom();
        yycgdCustom = yycgdCustom != null ? yycgdCustom : new YycgdCustom();
        yycgdCustom.setUseryyid(useryyid);
        yycgdQueryVo.setYycgdCustom(yycgdCustom);
        // 2. 药品的采购状态为“已发货”
        YycgdmxCustom yycgdmxCustom = yycgdQueryVo.getYycgdmxCustom();
        yycgdmxCustom = yycgdmxCustom != null ? yycgdmxCustom : new YycgdmxCustom();
        yycgdmxCustom.setCgzt("2");
        yycgdQueryVo.setYycgdmxCustom(yycgdmxCustom);
        yycgdQueryVo.setBusinessyear(year);
        return yycgdMapperCustom.selectYycgdmxCount(yycgdQueryVo);
    }

    /**
     * 药品入库提交
     *
     * @param yycgdid       采购单id
     * @param ypxxxid       药品id
     * @param yycgdrkCustom
     * @throws Exception 如果药品入库提交失败，则抛出异常
     */
    @Override
    public void saveYycgdrk(String yycgdid, String ypxxxid, YycgdrkCustom yycgdrkCustom) throws Exception {
        String businessyear = yycgdid.substring(0, 4);
        // 采购单药品明细状态为“已发货”，方可入库
        Yycgdmx yycgdmx = findYycgdmxByYycgdidAndYpxxid(yycgdid, ypxxxid);
        if (yycgdmx == null)
            ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 501, null));
        String cgzt = yycgdmx.getCgzt();
        Ypxx ypxx = ypxxMapper.selectByPrimaryKey(ypxxxid);
        String bm = ypxx.getBm();
        String mc = ypxx.getMc();
        if (!cgzt.equals("2"))
            ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 554, new Object[]{
                    bm, mc, yycgdid
            }));
        // 入库量小于等于采购量方可入库
        Integer cgl = yycgdmx.getCgl();
        Integer rkl = yycgdrkCustom.getRkl();
        if (rkl > cgl)
            ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 556, new Object[]{
                    bm, mc, yycgdid
            }));
        yycgdmx.setCgzt("3");
        yycgdmx.setBusinessyear(businessyear);
        yycgdmxMapper.updateByPrimaryKey(yycgdmx);

        Yycgdrk yycgdrk = new Yycgdrk();
        BeanUtils.copyProperties(yycgdrkCustom, yycgdrk);
        yycgdrk.setId(UUIDBuild.getUUID());
        yycgdrk.setYycgdid(yycgdid);
        yycgdrk.setYpxxid(ypxxxid);
        yycgdrk.setRktime(MyUtil.getNowDate());

        Float jyjg = yycgdmx.getJyjg();
        Float rkje = rkl * jyjg;
        yycgdrk.setRkje(rkje);
        yycgdrk.setCgzt("3");
        yycgdrk.setBusinessyear(businessyear);
        yycgdrkMapper.insert(yycgdrk);
    }
}
