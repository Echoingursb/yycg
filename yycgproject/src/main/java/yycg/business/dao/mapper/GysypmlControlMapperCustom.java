package yycg.business.dao.mapper;

import yycg.business.pojo.po.Gysypml;
import yycg.business.pojo.vo.GysypmlControlCustom;
import yycg.business.pojo.vo.GysypmlQueryVo;

import java.util.List;

/**
 * @ClassName GysypmlControlMapperCustom
 * @Description: 自定义供货商药品目录控制mapper
 * @Author echo
 * @Date 2019/10/16
 * @Version V1.0
 **/
public interface GysypmlControlMapperCustom {
    /**
     * 监管单位查询供货商药品目录信息
     *
     * @param gysypmlQueryVo 查询条件
     * @return 监管单位查询供货商药品目录信息
     * @throws Exception 如果监管单位查询供货商药品目录信息失败，抛出异常
     */
    List<GysypmlControlCustom> findGysypmlControlList(GysypmlQueryVo gysypmlQueryVo) throws Exception;

    /**
     * 监管单位查询供货商药品目录信息总数
     *
     * @param gysypmlQueryVo 查询条件
     * @return 监管单位查询供货商药品目录信息总数
     * @throws Exception 如果监管单位查询供货商药品目录信息总数失败，抛出异常
     */
    int findGysypmlControlCount(GysypmlQueryVo gysypmlQueryVo) throws Exception;

}
