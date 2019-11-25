package yycg.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import yycg.base.process.context.Config;
import yycg.base.process.result.ResultUtil;
import yycg.base.service.SystemConfigService;
import yycg.business.dao.mapper.*;
import yycg.business.pojo.po.*;
import yycg.business.pojo.vo.GysypmlControlCustom;
import yycg.business.pojo.vo.GysypmlCustom;
import yycg.business.pojo.vo.GysypmlQueryVo;
import yycg.business.pojo.vo.YpxxCustom;
import yycg.business.service.YpmlService;
import yycg.util.UUIDBuild;

import java.util.List;

/**
 * @ClassName YpmlServiceImpl
 * @Description: 供货商药品目录Service接口实现类
 * @Author echo
 * @Date 2019/10/14
 * @Version V1.0
 **/
public class YpmlServiceImpl implements YpmlService {
    @Autowired
    private GysypmlMapperCustom gysypmlMapperCustom;
    @Autowired
    private GysypmlMapper gysypmlMapper;
    @Autowired
    private YpxxMapper ypxxMapper;
    @Autowired
    private GysypmlControlMapper gysypmlControlMapper;
    @Autowired
    private SystemConfigService systemConfigService;

    @Autowired
    private GysypmlControlMapperCustom gysypmlControlMapperCustom;

    /**
     * 供货商药品目录查询
     *
     * @param usergysId      药品供货商id
     * @param gysypmlQueryVo 自定义查询条件
     * @return 查询结果
     * @throws Exception 供货商药品目录查询失败的，抛出异常
     */
    @Override
    public List<GysypmlCustom> findGysypmlList(String usergysId, GysypmlQueryVo gysypmlQueryVo) throws Exception {
        gysypmlQueryVo = gysypmlQueryVo != null ? gysypmlQueryVo : new GysypmlQueryVo();
        GysypmlCustom gysypmlCustom = gysypmlQueryVo.getGysypmlCustom();
        if (gysypmlCustom == null) {
            gysypmlCustom = new GysypmlCustom();
        }
        gysypmlCustom.setUsergysid(usergysId);
        gysypmlQueryVo.setGysypmlCustom(gysypmlCustom);
        return gysypmlMapperCustom.findGysypmlList(gysypmlQueryVo);
    }

    /**
     * 供货商药品目录查询总数
     *
     * @param usergysId      药品供货商id
     * @param gysypmlQueryVo 自定义查询条件
     * @return 供货商药品目录查询总数
     * @throws Exception 供货商药品目录总数查询失败，抛出异常
     */
    @Override
    public int findGysypmlCount(String usergysId, GysypmlQueryVo gysypmlQueryVo) throws Exception {
        gysypmlQueryVo = gysypmlQueryVo != null ? gysypmlQueryVo : new GysypmlQueryVo();
        GysypmlCustom gysypmlCustom = gysypmlQueryVo.getGysypmlCustom();
        if (gysypmlCustom == null) {
            gysypmlCustom = new GysypmlCustom();
        }
        gysypmlCustom.setUsergysid(usergysId);
        gysypmlQueryVo.setGysypmlCustom(gysypmlCustom);
        return gysypmlMapperCustom.findGysypmlCount(gysypmlQueryVo);
    }

    /**
     * 供货商药品添加目录查询
     *
     * @param usergysId      供货商id
     * @param gysypmlQueryVo 自定义查询条件
     * @return 查询结果
     * @throws Exception 供货商药品目录添加查询失败的话，抛出异常
     */
    @Override
    public List<GysypmlCustom> findAddGysypmlList(String usergysId, GysypmlQueryVo gysypmlQueryVo) throws Exception {
        gysypmlQueryVo = gysypmlQueryVo != null ? gysypmlQueryVo : new GysypmlQueryVo();
        GysypmlCustom gysypmlCustom = gysypmlQueryVo.getGysypmlCustom();
        if (gysypmlCustom == null) {
            gysypmlCustom = new GysypmlCustom();
        }
        gysypmlCustom.setUsergysid(usergysId);

        gysypmlQueryVo.setGysypmlCustom(gysypmlCustom);
        return gysypmlMapperCustom.findAddGysypmlList(gysypmlQueryVo);
    }

    /**
     * 供货商药品目录添加查询总数
     *
     * @param usergysId      供货商id
     * @param gysypmlQueryVo 自定义查询条件
     * @return 供货商药品目录查询总数
     * @throws Exception 供货商药品目录添加总数查询失败，抛出异常
     */
    @Override
    public int findAddGysypmlCount(String usergysId, GysypmlQueryVo gysypmlQueryVo) throws Exception {
        gysypmlQueryVo = gysypmlQueryVo != null ? gysypmlQueryVo : new GysypmlQueryVo();
        GysypmlCustom gysypmlCustom = gysypmlQueryVo.getGysypmlCustom();
        if (gysypmlCustom == null) {
            gysypmlCustom = new GysypmlCustom();
        }
        gysypmlCustom.setUsergysid(usergysId);

        gysypmlQueryVo.setGysypmlCustom(gysypmlCustom);
        return gysypmlMapperCustom.findAddGysypmlCount(gysypmlQueryVo);
    }

    /**
     * 向供货商药品目录添加一条记录
     *
     * @param usergysid 供货商id
     * @param ypxxid    药品信息id
     * @throws Exception 如果向供货商药品目录添加一条记录失败，抛出异常
     */
    @Override
    public void insertGysypml(String usergysid, String ypxxid) throws Exception {
        // 前置条件
        // 1. 只允许添加供货商药品目录中没有的药品
        // 2. 药品的交易状态为暂停不允许添加
        // 后置条件
        // 1. 向供货商药品目录表gysypml插入一条记录
        // 2. 同时向供货商药品目录控制表gysypml_control插入一条记录(如果在供货商药品目录控制表中有该记录插入，没有则不插入)

        // 1. 只允许添加供货商药品目录中没有的药品
        Gysypml gysypml = findGysypmlByUsergysidAndYpxxid(usergysid, ypxxid);
        if (gysypml != null)
            ResultUtil.createSubmitResult(ResultUtil.createFail(Config.MESSAGE, 401, null));
        // 2. 药品的交易状态为暂停不允许添加
        Ypxx ypxx = ypxxMapper.selectByPrimaryKey(ypxxid);
        if (ypxx == null)
            ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 316, null));
        else {
            String jyzt = ypxx.getJyzt();
            if (jyzt.equals("2")) {
                ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 403, new Object[]{
                        ypxx.getBm(), ypxx.getMc()
                })); // 使用ResultUtil.throwExcepion(ResultInfo resultInfo)抛出异常
                // ResultUtil.createFail(Config.MESSAGE, 403, new Object[]{
                //         ypxx.getBm(), ypxx.getMc()
                // }); // 使用ResultUtil.throwExcepion(ResultInfo resultInfo)抛出异常

            }
        }

        // 1. 向供货商药品目录表gysypml插入一条记录
        Gysypml gysypml_insert = new Gysypml();
        gysypml_insert.setId(UUIDBuild.getUUID());
        gysypml_insert.setUsergysid(usergysid);
        gysypml_insert.setYpxxid(ypxxid);
        gysypmlMapper.insert(gysypml_insert);
        // 2. 同时向供货商药品目录控制表gysypml_control插入一条记录(如果在供货商药品目录控制表中没有该记录插入，有则不插入)
        // TODO BUG
        //
        GysypmlControl gysypmlControl = findGysypmlControlByUsergysidAndYpxxid(usergysid, ypxxid);
        String control = systemConfigService.findBasicinfoById("00101").getValue();
        if (gysypmlControl == null) {
            GysypmlControl gysypmlControl_insert = new GysypmlControl();
            gysypmlControl_insert.setId(UUIDBuild.getUUID());
            gysypmlControl_insert.setUsergysid(usergysid);
            gysypmlControl_insert.setYpxxid(ypxxid);
            gysypmlControl_insert.setControl(control); // 默认控制状态为1
            gysypmlControlMapper.insert(gysypmlControl_insert);
        }

    }

    /**
     * 根据供货商id和药品信息id查询供货商药品目录控制表
     *
     * @param usergysid 供货商id
     * @param ypxxid    药品信息id
     * @return 如果查询到一条记录返回记录，否则返回null
     */
    public GysypmlControl findGysypmlControlByUsergysidAndYpxxid(String usergysid, String ypxxid) {
        GysypmlControlExample gysypmlControlExample = new GysypmlControlExample();
        GysypmlControlExample.Criteria criteria = gysypmlControlExample.createCriteria();
        criteria.andUsergysidEqualTo(usergysid);
        criteria.andYpxxidEqualTo(ypxxid);
        List<GysypmlControl> list = gysypmlControlMapper.selectByExample(gysypmlControlExample);
        if (list != null && list.size() == 1)
            return list.get(0);
        else
            return null;
    }


    /**
     * 根据供货商id和药品信息id查询供货商药品目录表
     *
     * @param usergysid 供货商id
     * @param ypxxid    药品信息id
     * @return 如果查询到一条记录返回记录，否则返回null
     */
    public Gysypml findGysypmlByUsergysidAndYpxxid(String usergysid, String ypxxid) {
        GysypmlExample gysypmlExample = new GysypmlExample();
        GysypmlExample.Criteria criteria = gysypmlExample.createCriteria();
        criteria.andUsergysidEqualTo(usergysid);
        criteria.andYpxxidEqualTo(ypxxid);
        List<Gysypml> list = gysypmlMapper.selectByExample(gysypmlExample);
        if (list != null && list.size() == 1)
            return list.get(0);
        else
            return null;
    }

    /**
     * 根据供货商id和药品信息id查询供货商药品目录表
     *
     * @param usergysid 供货商id
     * @param ypxxid    药品信息id
     * @throws Exception 如果删除失败抛出异常
     */
    @Override
    public void deleteGysypmlByUsergysidAndYpxxid(String usergysid, String ypxxid) throws Exception {
        GysypmlExample gysypmlExample = new GysypmlExample();
        GysypmlExample.Criteria criteria = gysypmlExample.createCriteria();
        criteria.andUsergysidEqualTo(usergysid);
        criteria.andYpxxidEqualTo(ypxxid);
        List<Gysypml> list = gysypmlMapper.selectByExample(gysypmlExample);
        Ypxx ypxx = ypxxMapper.selectByPrimaryKey(ypxxid);
        Gysypml gysypml;
        if (list != null && list.size() == 1) {
            gysypml = list.get(0);
            gysypmlMapper.deleteByPrimaryKey(gysypml.getId());
        } else
            ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 405, new Object[]{
                    ypxx.getBm(), ypxx.getMc()
            }));
    }

    /**
     * 监管单位查询供货商药品目录信息
     *
     * @param gysypmlQueryVo 查询条件
     * @return 监管单位查询供货商药品目录信息
     * @throws Exception 如果监管单位查询供货商药品目录信息失败，抛出异常
     */
    @Override
    public List<GysypmlControlCustom> findGysypmlControlList(GysypmlQueryVo gysypmlQueryVo) throws Exception {
        gysypmlQueryVo = gysypmlQueryVo != null ? gysypmlQueryVo : new GysypmlQueryVo();
        GysypmlControlCustom gysypmlControlCustom = gysypmlQueryVo.getGysypmlControlCustom();
        if (gysypmlControlCustom == null)
            gysypmlControlCustom = new GysypmlControlCustom();
        gysypmlQueryVo.setGysypmlControlCustom(gysypmlControlCustom);
        return gysypmlControlMapperCustom.findGysypmlControlList(gysypmlQueryVo);

    }

    /**
     * 监管单位查询供货商药品目录信息总数
     *
     * @param gysypmlQueryVo 查询条件
     * @return 监管单位查询供货商药品目录信息总数
     * @throws Exception 如果监管单位查询供货商药品目录信息总数失败，抛出异常
     */
    @Override
    public int findGysypmlControlCount(GysypmlQueryVo gysypmlQueryVo) throws Exception {
        gysypmlQueryVo = gysypmlQueryVo != null ? gysypmlQueryVo : new GysypmlQueryVo();
        GysypmlControlCustom gysypmlControlCustom = gysypmlQueryVo.getGysypmlControlCustom();
        if (gysypmlControlCustom == null)
            gysypmlControlCustom = new GysypmlControlCustom();
        gysypmlQueryVo.setGysypmlControlCustom(gysypmlControlCustom);
        return gysypmlControlMapperCustom.findGysypmlControlCount(gysypmlQueryVo);
    }

    /**
     * 监督单位根据供货商id和药品信息id更新供货状态和审核意见
     *
     * @throws Exception 如果监督单位更新供货状态和审核意见失败，抛出异常
     */
    @Override
    public void updateGysypmlControlByUsergysidAndYpxxid(String usergysid, String ypxxid, String control, String advice) throws Exception {
        GysypmlControlExample gysypmlControlExample = new GysypmlControlExample();
        GysypmlControlExample.Criteria criteria = gysypmlControlExample.createCriteria();
        criteria.andUsergysidEqualTo(usergysid);
        criteria.andYpxxidEqualTo(ypxxid);
        List<GysypmlControl> list = gysypmlControlMapper.selectByExample(gysypmlControlExample);
        if (list != null && list.size() == 1) {
            GysypmlControl gysypmlControl = list.get(0);
            if (control != null && (control.equals("1") || control.equals("2"))) {
                gysypmlControl.setControl(control);
            }
            gysypmlControl.setAdvice(advice);
            gysypmlControlMapper.updateByExample(gysypmlControl, gysypmlControlExample);
        }
    }
}
