package yycg.business.dao.mapper;

import yycg.business.pojo.vo.YybusinessCustom;
import yycg.business.pojo.vo.YybusinessQueryVo;

import java.util.List;

public interface YybusinessMapperCustom {
    /**
     * 查询交易明细列表
     *
     * @param yybusinessQueryVo 查询条件
     * @return 交易明细列表
     * @throws Exception 如果查询交易明细列表失败，则抛出异常
     */
    List<YybusinessCustom> findYyBusinessList(YybusinessQueryVo yybusinessQueryVo) throws Exception;

    /**
     * 查询交易明细列表总数
     *
     * @param yybusinessQueryVo 查询条件
     * @return 交易明细列表总数
     * @throws Exception 如果查询交易明细列表总数失败，则抛出异常
     */
    int findYyBusinessCount(YybusinessQueryVo yybusinessQueryVo) throws Exception;

    /**
     * 按照药品统计列表
     *
     * @param yybusinessQueryVo 查询条件
     * @return 药品统计列表
     * @throws Exception 如果查询按照药品统计列表失败，则抛出异常
     */
    List<YybusinessCustom> findYyBusinessGroupByYpxxList(YybusinessQueryVo yybusinessQueryVo) throws Exception;

    /**
     * 按照药品统计列表总数
     *
     * @param yybusinessQueryVo 查询条件
     * @return 药品统计列表总数
     * @throws Exception 如果查询按照药品统计列表总数失败，则抛出异常
     */
    int findYyBusinessGroupByYpxxCount(YybusinessQueryVo yybusinessQueryVo) throws Exception;


    /**
     * 按照区域统计列表
     *
     * @param yybusinessQueryVo 查询条件
     * @return 区域统计列表
     * @throws Exception 如果查询按照区域统计列表失败，则抛出异常
     */
    List<YybusinessCustom> findYyBusinessGroupByAreaList(YybusinessQueryVo yybusinessQueryVo) throws Exception;
}