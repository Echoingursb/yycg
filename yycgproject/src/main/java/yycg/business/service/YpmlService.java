package yycg.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import yycg.business.dao.mapper.GysypmlMapperCustom;
import yycg.business.pojo.po.Gysypml;
import yycg.business.pojo.po.GysypmlControl;
import yycg.business.pojo.vo.GysypmlControlCustom;
import yycg.business.pojo.vo.GysypmlCustom;
import yycg.business.pojo.vo.GysypmlQueryVo;

import java.util.List;

/**
 * @ClassName YpmlService
 * @Description: 供货商药品目录Service接口
 * @Author echo
 * @Date 2019/10/14
 * @Version V1.0
 **/
public interface YpmlService {
    /**
     * 供货商药品目录查询
     *
     * @param usergysId      供货商id
     * @param gysypmlQueryVo 自定义查询条件
     * @return 查询结果
     * @throws Exception 供货商药品目录查询失败的话，抛出异常
     */
    List<GysypmlCustom> findGysypmlList(String usergysId, GysypmlQueryVo gysypmlQueryVo) throws Exception;

    /**
     * 供货商药品目录查询总数
     *
     * @param usergysId      供货商id
     * @param gysypmlQueryVo 自定义查询条件
     * @return 供货商药品目录查询总数
     * @throws Exception 供货商药品目录总数查询失败，抛出异常
     */
    int findGysypmlCount(String usergysId, GysypmlQueryVo gysypmlQueryVo) throws Exception;

    /**
     * 供货商药品添加目录查询
     *
     * @param usergysId      供货商id
     * @param gysypmlQueryVo 自定义查询条件
     * @return 查询结果
     * @throws Exception 供货商药品目录添加查询失败的话，抛出异常
     */
    List<GysypmlCustom> findAddGysypmlList(String usergysId, GysypmlQueryVo gysypmlQueryVo) throws Exception;

    /**
     * 供货商药品目录添加查询总数
     *
     * @param usergysId      供货商id
     * @param gysypmlQueryVo 自定义查询条件
     * @return 供货商药品目录查询总数
     * @throws Exception 供货商药品目录添加总数查询失败，抛出异常
     */
    int findAddGysypmlCount(String usergysId, GysypmlQueryVo gysypmlQueryVo) throws Exception;

    /**
     * 向供货商药品目录添加一条记录
     *
     * @param usergysid 供货商id
     * @param ypxxid    药品信息id
     * @throws Exception 如果向供货商药品目录添加一条记录失败，抛出异常
     */
    void insertGysypml(String usergysid, String ypxxid) throws Exception;

    /**
     * 根据供货商id和药品信息id查询供货商药品目录控制表
     *
     * @param usergysid 供货商id
     * @param ypxxid    药品信息id
     * @return 如果查询到一条记录返回记录，否则返回null
     */
    GysypmlControl findGysypmlControlByUsergysidAndYpxxid(String usergysid, String ypxxid) throws Exception;

    /**
     * 根据供货商id和药品信息id查询供货商药品目录表
     *
     * @param usergysid 供货商id
     * @param ypxxid    药品信息id
     * @return 如果查询到一条记录返回记录，否则返回null
     */
    Gysypml findGysypmlByUsergysidAndYpxxid(String usergysid, String ypxxid) throws Exception;

    /**
     * 根据供货商id和药品信息id查询删除供货商药品目录表
     *
     * @param usergysid 供货商id
     * @param ypxxid    药品信息id
     * @throws Exception 如果删除失败抛出异常
     */
    void deleteGysypmlByUsergysidAndYpxxid(String usergysid, String ypxxid) throws Exception;

    /**
     * 监管单位查询供货商药品目录信息
     *
     * @param gysypmlQueryVo 查询条件
     * @return 监管单位查询供货商药品目录信息
     * @throws Exception 如果监管单位查询供货商药品目录信息失败，抛出异常
     */
    List<GysypmlControlCustom> findGysypmlControlList( GysypmlQueryVo gysypmlQueryVo) throws Exception;

    /**
     * 监管单位查询供货商药品目录信息总数
     *
     * @param gysypmlQueryVo 查询条件
     * @return 监管单位查询供货商药品目录信息总数
     * @throws Exception 如果监管单位查询供货商药品目录信息总数失败，抛出异常
     */
    int findGysypmlControlCount(GysypmlQueryVo gysypmlQueryVo) throws Exception;

    /**
     * 监督单位根据供货商id和药品信息id更新供货状态和审核意见
     *
     * @throws Exception 如果监督单位更新供货状态和审核意见失败，抛出异常
     */
    void updateGysypmlControlByUsergysidAndYpxxid(String usergysid, String ypxxid, String control, String advice) throws Exception;

}
