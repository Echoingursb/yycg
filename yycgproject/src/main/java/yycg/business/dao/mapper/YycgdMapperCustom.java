package yycg.business.dao.mapper;

import yycg.business.pojo.vo.YycgdCustom;
import yycg.business.pojo.vo.YycgdQueryVo;
import yycg.business.pojo.vo.YycgdmxCustom;

import java.util.List;

/**
 * @ClassName YycgdMapperCustom
 * @Description: TODO
 * @Author echo
 * @Date 2019/10/18
 * @Version V1.0
 **/
public interface YycgdMapperCustom {
    /**
     * 生成采购单编号
     *
     * @param year 年份
     * @return 采购单编号bm
     * @throws Exception 如果生成采购单编号错误，抛出异常
     */
    String getYycgdBm(String year) throws Exception;

    /**
     * 统计符合条件的采购单药品明细的采购量、采购金额
     *
     * @param yycgdQueryVo 查询条件
     * @return 采购单药品明细的采购量、采购金额
     * @throws Exception 如果统计符合条件的采购单药品明细的采购量、采购金额失败，则抛出异常
     */
    List<YycgdmxCustom> selectYycgdmxListSum(YycgdQueryVo yycgdQueryVo) throws Exception;

    /**
     * 医院采购单明细列表查询
     *
     * @param yycgdQueryVo 查询条件
     * @return 医院采购单明细列表
     * @throws Exception 如果医院采购单明细列表查询失败，则抛出异常
     */
    List<YycgdmxCustom> selectYycgdmxList(YycgdQueryVo yycgdQueryVo) throws Exception;

    /**
     * 医院采购单明细列表查询总数
     *
     * @param yycgdQueryVo 查询条件
     * @return 医院采购单明细列表总数
     * @throws Exception 如果医院采购单明细列表查询总数失败，则抛出异常
     */
    int selectYycgdmxCount(YycgdQueryVo yycgdQueryVo) throws Exception;

    /**
     * 采购单明细添加查询
     *
     * @param yycgdQueryVo 查询条件
     * @return 采购单明细添加查询列表
     * @throws Exception 如果采购单明细添加查询失败，则抛出异常
     */
    List<YycgdmxCustom> selectAddYycgdmxList(YycgdQueryVo yycgdQueryVo) throws Exception;

    /**
     * 采购单明细添加查询总数
     *
     * @param yycgdQueryVo 查询条件
     * @return 采购单明细添加查询总数
     * @throws Exception 如果采购单明细添加查询总数失败，则抛出异常
     */
    int selectAddYycgdmxCount(YycgdQueryVo yycgdQueryVo) throws Exception;

    /**
     * 查询采购单列表
     *
     * @param yycgdQueryVo 查询条件
     * @return 查询采购单列表
     * @throws Exception 如果查询采购单列表失败，则抛出异常
     */
    List<YycgdCustom> selectYycgdList(YycgdQueryVo yycgdQueryVo) throws Exception;

    /**
     * 查询采购单列表总数
     *
     * @param yycgdQueryVo 查询条件
     * @return 查询采购单列表总数
     * @throws Exception 如果查询采购单列表总数失败，则抛出异常
     */
    int selectYycgdCount(YycgdQueryVo yycgdQueryVo) throws Exception;

}
