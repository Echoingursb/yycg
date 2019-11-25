package yycg.business.service;

import yycg.business.pojo.po.Yycgdmx;
import yycg.business.pojo.vo.YycgdCustom;
import yycg.business.pojo.vo.YycgdQueryVo;
import yycg.business.pojo.vo.YycgdmxCustom;
import yycg.business.pojo.vo.YycgdrkCustom;

import java.util.List;

/**
 * @ClassName CgdService
 * @Description: 采购单Service
 * @Author echo
 * @Date 2019/10/18
 * @Version V1.0
 **/
public interface CgdService {
    /**
     * 创建采购单基本信息
     *
     * @param useryyid    医院id
     * @param year        年份
     * @param yycgdCustom 插入的采购单信息
     * @return 采购单编号
     * @throws Exception 如果创建采购单基本信息，则抛出异常
     */
    String insertYycgd(String useryyid, String year, YycgdCustom yycgdCustom) throws Exception;

    /**
     * 通过采购单编号查询采购单
     *
     * @param id 采购单编号
     * @return YycgdCustom采购单
     * @throws Exception 如果通过采购单编号查询采购单失败，则抛出异常
     */
    YycgdCustom findYycgdById(String id) throws Exception;

    /**
     * 根据id修改采购单信息
     *
     * @param id          采购单id(和采购单编号相同)
     * @param yycgdCustom 修改信息
     * @throws Exception 如果根据id修改采购单信息失败， 则抛出异常
     */
    void updateYycgd(String id, YycgdCustom yycgdCustom) throws Exception;

    /**
     * 统计符合条件的采购单药品明细的采购量、采购金额
     *
     * @param yycgdid      采购单id
     * @param yycgdQueryVo 查询条件
     * @return 采购单药品明细的采购量、采购金额
     * @throws Exception 如果统计符合条件的采购单药品明细的采购量、采购金额失败，则抛出异常
     */
    List<YycgdmxCustom> selectYycgdmxListSum(String yycgdid, YycgdQueryVo yycgdQueryVo) throws Exception;

    /**
     * 医院采购单明细列表查询
     *
     * @param yycgdid      医院采购单id
     * @param yycgdQueryVo 查询条件
     * @return 医院采购单明细列表
     * @throws Exception 如果医院采购单明细列表查询失败，则抛出异常
     */
    List<YycgdmxCustom> selectYycgdmxListByYycgdid(String yycgdid, YycgdQueryVo yycgdQueryVo) throws Exception;

    /**
     * 医院采购单明细列表查询总数
     *
     * @param yycgdid      医院采购单id
     * @param yycgdQueryVo 查询条件
     * @return 医院采购单明细列表总数
     * @throws Exception 如果医院采购单明细列表查询总数失败，则抛出异常
     */
    int selectYycgdmxCountByYycgdid(String yycgdid, YycgdQueryVo yycgdQueryVo) throws Exception;

    /**
     * 采购单明细添加查询
     *
     * @param useryyid     医院id
     * @param yycgdid      医院采购单id
     * @param yycgdQueryVo 查询条件
     * @return 采购单明细添加查询列表
     * @throws Exception 如果采购单明细添加查询失败，则抛出异常
     */
    List<YycgdmxCustom> selectAddYycgdmxList(String useryyid, String yycgdid, YycgdQueryVo yycgdQueryVo) throws Exception;

    /**
     * 采购单明细添加查询总数
     *
     * @param useryyid     医院id
     * @param yycgdid      医院采购单id
     * @param yycgdQueryVo 查询条件
     * @return 采购单明细添加查询总数
     * @throws Exception 如果采购单明细添加查询总数失败，则抛出异常
     */
    int selectAddYycgdmxCount(String useryyid, String yycgdid, YycgdQueryVo yycgdQueryVo) throws Exception;

    /**
     * 采购单药品明细添加
     *
     * @param yycgdid   医院采购单id
     * @param ypxxid    药品信息id
     * @param usergysid 供货商id
     * @throws Exception 如果采购单药品明细添加失败，则抛出异常
     */
    void insertYycgdmx(String yycgdid, String ypxxid, String usergysid) throws Exception;

    /**
     * 根据医院采购单id和药品信息id查询医药采购单明细
     *
     * @param yycgdid 医院采购单id
     * @param ypxxid  药品信息id
     * @return 医药采购单明细
     * @throws Exception 如果根据供货商id和药品信息id查询医药采购单明细失败，则抛出异常
     */
    Yycgdmx findYycgdmxByYycgdidAndYpxxid(String yycgdid, String ypxxid) throws Exception;

    /**
     * 根据医院采购单id和药品信息id修改采购量
     *
     * @param yycgdid 医院采购单id
     * @param ypxxid  药品信息id
     * @param cgl     采购量
     * @throws Exception 如果根据医院采购单id和药品信息id修改采购量，则抛出异常
     */
    void updateYycgdmx(String yycgdid, String ypxxid, Integer cgl) throws Exception;

    /**
     * 查询采购单列表
     *
     * @param useryyid     医院Id
     * @param year         年份
     * @param yycgdQueryVo 查询条件
     * @return 查询采购单列表
     * @throws Exception 如果查询采购单列表失败，则抛出异常
     */
    List<YycgdCustom> selectYycgdList(String useryyid, String year, YycgdQueryVo yycgdQueryVo) throws Exception;

    /**
     * 查询采购单列表总数
     *
     * @param useryyid     医院Id
     * @param year         年份
     * @param yycgdQueryVo 查询条件
     * @return 查询采购单列表总数
     * @throws Exception 如果查询采购单列表总数失败，则抛出异常
     */
    int selectYycgdCount(String useryyid, String year, YycgdQueryVo yycgdQueryVo) throws Exception;

    /**
     * 采购单提交，根据采购单号更新采购单，更新为采购单状态(存储数据字典：2：已提交未审核)
     *
     * @param yycgdid 医药采购单id
     * @throws Exception 如果根据采购单号更新采购单更新失败，则抛出异常
     */
    void saveYycgdSubmit(String yycgdid) throws Exception;

    /**
     * 查询审核采购单列表
     *
     * @param userjdid     监督单位id
     * @param year         年份
     * @param yycgdQueryVo 查询条件
     * @return 审核采购单列表
     * @throws Exception 如果查询审核采购单列表失败，则抛出异常
     */
    List<YycgdCustom> findYycgdCheckList(String userjdid, String year, YycgdQueryVo yycgdQueryVo) throws Exception;

    /**
     * 查询审核采购单列表总数
     *
     * @param userjdid     监督单位id
     * @param year         年份
     * @param yycgdQueryVo 查询条件
     * @return 审核采购单列表总数
     * @throws Exception 如果查询审核采购单列表总数失败，则抛出异常
     */
    int findYycgdCheckCount(String userjdid, String year, YycgdQueryVo yycgdQueryVo) throws Exception;

    /**
     * 采购单审核
     *
     * @param yycgdid     采购单id
     * @param yycgdCustom 审核信息(审核结果和意见)
     * @throws Exception 如果采购单审核失败，则抛出异常
     */
    void saveYycgdCheckStatus(String yycgdid, YycgdCustom yycgdCustom) throws Exception;

    /**
     * 查询采购单受理列表
     *
     * @param usergysid    供货商id
     * @param year         年份
     * @param yycgdQueryVo 查询条件
     * @return 采购单受理列表
     * @throws Exception 如果查询采购单受理列表失败，则抛出异常
     */
    List<YycgdmxCustom> findDiposeYycgdList(String usergysid, String year, YycgdQueryVo yycgdQueryVo) throws Exception;

    /**
     * 查询采购单受理列表总数
     *
     * @param usergysid    供货商id
     * @param year         年份
     * @param yycgdQueryVo 查询条件
     * @return 采购单受理列表总数
     * @throws Exception 如果查询采购单受理列表总数失败，则抛出异常
     */
    int findDiposeYycgdCount(String usergysid, String year, YycgdQueryVo yycgdQueryVo) throws Exception;


    /**
     * 确认发货
     *
     * @param yycgdid 采购单id
     * @param ypxxid  药品信息id
     * @throws Exception 如果确认发货失败，则抛出异常
     */
    void saveSendStatus(String yycgdid, String ypxxid) throws Exception;

    /**
     * 查询待入库列表
     *
     * @param useryyid     医院id
     * @param year         年份
     * @param yycgdQueryVo 查询条件
     * @return 待入库列表
     * @throws Exception 如果查询待入库列表失败，则抛出异常
     */
    List<YycgdmxCustom> findYycgdReceiveList(String useryyid, String year, YycgdQueryVo yycgdQueryVo) throws Exception;

    /**
     * 查询待入库列表
     *
     * @param useryyid     医院id
     * @param year         年份
     * @param yycgdQueryVo 查询条件
     * @return 待入库列表
     * @throws Exception 如果查询待入库列表失败，则抛出异常
     */
    int findYycgdReceiveCount(String useryyid, String year, YycgdQueryVo yycgdQueryVo) throws Exception;

    /**
     * 药品入库提交
     *
     * @param yycgdid       采购单id
     * @param ypxxxid       药品id
     * @param yycgdrkCustom
     * @throws Exception 如果药品入库提交失败，则抛出异常
     */
    void saveYycgdrk(String yycgdid, String ypxxxid, YycgdrkCustom yycgdrkCustom) throws Exception;
}
