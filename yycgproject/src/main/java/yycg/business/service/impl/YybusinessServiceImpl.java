package yycg.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import yycg.base.dao.mapper.UsergysMapper;
import yycg.base.dao.mapper.UserjdMapper;
import yycg.base.dao.mapper.UseryyMapper;
import yycg.base.pojo.po.Usergys;
import yycg.base.pojo.po.Userjd;
import yycg.base.pojo.po.Useryy;
import yycg.business.dao.mapper.YybusinessMapperCustom;
import yycg.business.pojo.vo.YybusinessCustom;
import yycg.business.pojo.vo.YybusinessQueryVo;
import yycg.business.service.YybusinessService;

import java.util.List;

/**
 * @ClassName YybusinessServiceImpl
 * @Description: TODO
 * @Author echo
 * @Date 2019/10/28
 * @Version V1.0
 **/
public class YybusinessServiceImpl implements YybusinessService {
    @Autowired
    private YybusinessMapperCustom yybusinessMapperCustom;
    @Autowired
    private UserjdMapper userjdMapper;
    @Autowired
    private UseryyMapper useryyMapper;
    @Autowired
    private UsergysMapper usergysMapper;

    private void findYyBusinessByGroupid(String sysyid, String groupid, YybusinessQueryVo yybusinessQueryVo) {
        switch (groupid) {
            case "1":
            case "2": {
                // 监管单位查询管理地区内医院采购明细信息
                Userjd userjd = userjdMapper.selectByPrimaryKey(sysyid);
                String dq = userjd.getDq();
                Useryy useryy = yybusinessQueryVo.getUseryy();
                useryy = useryy != null ? useryy : new Useryy();
                useryy.setDq(dq);
                yybusinessQueryVo.setUseryy(useryy);
                break;
            }
            case "3": {
                // 医院查询自己的采购明细信息
                Useryy useryy = yybusinessQueryVo.getUseryy();
                useryy = useryy != null ? useryy : new Useryy();
                useryy.setId(sysyid);
                yybusinessQueryVo.setUseryy(useryy);
                break;
            }
            case "4":
                // 供货商查询与本供货商相关的采购明细信息
                Usergys usergys = yybusinessQueryVo.getUsergys();
                usergys = usergys != null ? usergys : new Usergys();
                usergys.setId(sysyid);
                yybusinessQueryVo.setUsergys(usergys);
                break;
        }
    }

    /**
     * 查询交易明细列表
     *
     * @param year              年份
     * @param sysyid            单位id
     * @param groupid           用户类别
     * @param yybusinessQueryVo 查询条件
     * @return 交易明细列表
     * @throws Exception 如果查询交易明细列表失败，则抛出异常
     */
    @Override
    public List<YybusinessCustom> findYyBusinessList(String year, String sysyid, String groupid, YybusinessQueryVo yybusinessQueryVo) throws Exception {
        yybusinessQueryVo = yybusinessQueryVo != null ? yybusinessQueryVo : new YybusinessQueryVo();
        findYyBusinessByGroupid(sysyid, groupid, yybusinessQueryVo);
        yybusinessQueryVo.setBusinessyear(year);
        return yybusinessMapperCustom.findYyBusinessList(yybusinessQueryVo);
    }

    /**
     * 查询交易明细列表总数
     *
     * @param year              年份
     * @param sysyid            单位id
     * @param groupid           用户类别
     * @param yybusinessQueryVo 查询条件
     * @return 交易明细列表总数
     * @throws Exception 如果查询交易明细列表总数失败，则抛出异常
     */
    @Override
    public int findYyBusinessCount(String year, String sysyid, String groupid, YybusinessQueryVo yybusinessQueryVo) throws Exception {
        yybusinessQueryVo = yybusinessQueryVo != null ? yybusinessQueryVo : new YybusinessQueryVo();
        findYyBusinessByGroupid(sysyid, groupid, yybusinessQueryVo);
        yybusinessQueryVo.setBusinessyear(year);
        return yybusinessMapperCustom.findYyBusinessCount(yybusinessQueryVo);
    }

    /**
     * 按照药品统计列表
     *
     * @param year              年份
     * @param sysyid            单位id
     * @param groupid           用户类别
     * @param yybusinessQueryVo 查询条件
     * @return 药品统计列表
     * @throws Exception 如果查询按照药品统计列表失败，则抛出异常
     */
    @Override
    public List<YybusinessCustom> findYyBusinessGroupByYpxxList(String year, String sysyid, String groupid, YybusinessQueryVo yybusinessQueryVo) throws Exception {
        yybusinessQueryVo = yybusinessQueryVo != null ? yybusinessQueryVo : new YybusinessQueryVo();
        findYyBusinessByGroupid(sysyid, groupid, yybusinessQueryVo);
        yybusinessQueryVo.setBusinessyear(year);
        return yybusinessMapperCustom.findYyBusinessGroupByYpxxList(yybusinessQueryVo);
    }

    /**
     * 按照药品统计列表总数
     *
     * @param year              年份
     * @param sysyid            单位id
     * @param groupid           用户类别
     * @param yybusinessQueryVo 查询条件
     * @return 药品统计列表总数
     * @throws Exception 如果查询按照药品统计列表总数失败，则抛出异常
     */
    @Override
    public int findYyBusinessGroupByYpxxCount(String year, String sysyid, String groupid, YybusinessQueryVo yybusinessQueryVo) throws Exception {
        yybusinessQueryVo = yybusinessQueryVo != null ? yybusinessQueryVo : new YybusinessQueryVo();
        findYyBusinessByGroupid(sysyid, groupid, yybusinessQueryVo);
        yybusinessQueryVo.setBusinessyear(year);
        return yybusinessMapperCustom.findYyBusinessGroupByYpxxCount(yybusinessQueryVo);
    }

    /**
     * 按照区域统计列表
     *
     * @param year              年份
     * @param sysyid            单位id
     * @param groupid           用户类别
     * @param yybusinessQueryVo 查询条件
     * @return 区域统计列表
     * @throws Exception 如果查询按照区域统计列表失败，则抛出异常
     */
    @Override
    public List<YybusinessCustom> findYyBusinessGroupByAreaList(String year, String sysyid, String groupid, YybusinessQueryVo yybusinessQueryVo) throws Exception {
        yybusinessQueryVo = yybusinessQueryVo != null ? yybusinessQueryVo : new YybusinessQueryVo();
        findYyBusinessByGroupid(sysyid, groupid, yybusinessQueryVo);
        yybusinessQueryVo.setBusinessyear(year);
        return yybusinessMapperCustom.findYyBusinessGroupByAreaList(yybusinessQueryVo);
    }
}
