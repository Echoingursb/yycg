package yycg.base.service;

import yycg.base.pojo.po.Sysuser;
import yycg.base.pojo.po.Usergys;
import yycg.base.pojo.po.Userjd;
import yycg.base.pojo.po.Useryy;
import yycg.base.pojo.vo.ActiveUser;
import yycg.base.pojo.vo.SysuserCustom;
import yycg.base.pojo.vo.SysuserQueryVo;

import java.util.List;

public interface UserService {

    /**
     * 校验用户信息
     *
     * @param userid 用户账号
     * @param pwd 用户密码
     * @return 用户身份信息
     * @throws Exception 校验用户信息失败抛出异常
     */
    ActiveUser checkUserInfo(String userid, String pwd) throws Exception;

    /**
     * 查询用户列表
     *
     * @param sysuserQueryVo 用户查询条件
     * @return 用户列表
     * @throws Exception 查询用户列表异常
     */
    List<SysuserCustom> findSysuserList(SysuserQueryVo sysuserQueryVo) throws Exception;

    /**
     * 查询用户列表总数
     *
     * @param sysuserQueryVo 用户查询条件
     * @return 用户列表总数
     * @throws Exception 查询用户列表总数异常
     */
    int findSysuserCount(SysuserQueryVo sysuserQueryVo) throws Exception;

    /**
     * 添加用户信息
     *
     * @param sysuserCustom 自定义用户扩展类
     * @throws Exception 添加用户信息失败抛出异常
     */
    void insertSysuser(SysuserCustom sysuserCustom) throws Exception;

    /**
     * 删除用户
     *
     * @param id 删除用户的id
     * @throws Exception 删除用户失败抛出异常
     */
    void deleteSysuser(String id) throws Exception;

    /**
     * 修改用户
     *
     * @param id            修改用户的id
     * @param sysuserCustom 修改用户的信息
     * @throws Exception 修改用户失败抛出异常
     */
    void updateSysuser(String id, SysuserCustom sysuserCustom) throws Exception;

    /**
     * 根据监督单位名称查询监督单位信息
     *
     * @param mc 单位名称
     * @return 如果存在名称对应的单位，返回该单位，否则返回null
     */
    Userjd findUserJdByMc(String mc) throws Exception;

    /**
     * 根据医院单位名称查询医院单位信息
     *
     * @param mc 单位名称
     * @return 如果存在名称对应的单位，返回该单位，否则返回null
     */
    Useryy findUserYyByMc(String mc) throws Exception;

    /**
     * 根据用户账号userid查询用户
     *
     * @param userid 用户账号
     * @return 如果存在userid对应的用户，返回该用户，否则返回null
     */
    Sysuser findSysuserByUserid(String userid) throws Exception;

    /**
     * 根据用户账号userid查询用户
     *
     * @param userid 用户账号
     * @return 如果存在userid对应的用户，返回该用户，否则返回null
     * @throws Exception 如果查询失败抛出异常
     */
    SysuserCustom findSysuserById(String userid) throws Exception;

    /**
     * 根据供应商单位名称查询供应商单位信息
     *
     * @param mc 单位名称
     * @return 如果存在名称对应的单位，返回该单位，否则返回null
     */
    Usergys findUserGysByMc(String mc) throws Exception;
}
