package yycg.business.dao.mapper;

import yycg.business.pojo.vo.GysypmlCustom;
import yycg.business.pojo.vo.GysypmlQueryVo;

import java.util.List;

/**
 * @ClassName GysypmlMapperCustom
 * @Description: 自定义供货商药品目录mapper
 * @Author echo
 * @Date 2019/10/14
 * @Version V1.0
 **/
public interface GysypmlMapperCustom {
    /**
     * 供货商药品目录查询
     *
     * @param gysypmlQueryVo 自定义查询条件
     * @return 查询结果
     * @throws Exception 供货商药品目录查询失败的话，抛出异常
     */
    List<GysypmlCustom> findGysypmlList(GysypmlQueryVo gysypmlQueryVo) throws Exception;

    /**
     * 供货商药品目录查询总数
     *
     * @param gysypmlQueryVo 自定义查询条件
     * @return 供货商药品目录查询总数
     * @throws Exception 供货商药品目录总数查询失败，抛出异常
     */
    int findGysypmlCount(GysypmlQueryVo gysypmlQueryVo) throws Exception;

    /**
     * 供货商药品添加目录查询
     *
     * @param gysypmlQueryVo 自定义查询条件
     * @return 查询结果
     * @throws Exception 供货商药品目录添加查询失败的话，抛出异常
     */
    List<GysypmlCustom> findAddGysypmlList(GysypmlQueryVo gysypmlQueryVo) throws Exception;

    /**
     * 供货商药品目录添加查询总数
     *
     * @param gysypmlQueryVo 自定义查询条件
     * @return 供货商药品目录查询总数
     * @throws Exception 供货商药品目录添加总数查询失败，抛出异常
     */
    int findAddGysypmlCount(GysypmlQueryVo gysypmlQueryVo) throws Exception;
}
