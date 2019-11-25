package yycg.business.service;

import yycg.business.pojo.vo.YybusinessCustom;
import yycg.business.pojo.vo.YybusinessQueryVo;

import java.util.List;

/**
 * @ClassName YybusinessService
 * @Description: TODO
 * @Author echo
 * @Date 2019/10/28
 * @Version V1.0
 **/
public interface YybusinessService {
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
    List<YybusinessCustom> findYyBusinessList(String year, String sysyid, String groupid, YybusinessQueryVo yybusinessQueryVo) throws Exception;

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
    int findYyBusinessCount(String year, String sysyid, String groupid, YybusinessQueryVo yybusinessQueryVo) throws Exception;

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
    List<YybusinessCustom> findYyBusinessGroupByYpxxList(String year, String sysyid, String groupid, YybusinessQueryVo yybusinessQueryVo) throws Exception;

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
    int findYyBusinessGroupByYpxxCount(String year, String sysyid, String groupid, YybusinessQueryVo yybusinessQueryVo) throws Exception;

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
    List<YybusinessCustom> findYyBusinessGroupByAreaList(String year, String sysyid, String groupid, YybusinessQueryVo yybusinessQueryVo) throws Exception;
}
