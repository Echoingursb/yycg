package yycg.base.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import yycg.base.dao.mapper.*;
import yycg.base.pojo.po.*;
import yycg.base.pojo.vo.ActiveUser;
import yycg.base.process.context.Config;
import yycg.base.process.result.ResultInfoException;
import yycg.base.process.result.ResultUtil;
import yycg.base.service.UserService;
import yycg.base.pojo.vo.SysuserCustom;
import yycg.base.pojo.vo.SysuserQueryVo;
import yycg.util.MD5;
import yycg.util.UUIDBuild;

import java.util.List;

public class UserServiceImpl implements UserService {

    @Autowired
    private SysuserMapper sysuserMapper;

    @Autowired
    private UserjdMapper userjdMapper;


    @Autowired
    private UseryyMapper useryyMapper;

    @Autowired
    private UsergysMapper usergysMapper;

    @Autowired
    private SysuserMapperCustom sysuserMapperCustom;

    /**
     * 校验用户信息
     *
     * @param userid 用户账号
     * @param pwd    用户密码
     * @return 用户身份信息
     * @throws Exception 校验用户信息失败抛出异常
     */
    @Override
    public ActiveUser checkUserInfo(String userid, String pwd) throws Exception {
        // 校验用户是否存在
        Sysuser sysuser = findSysuserByUserid(userid);
        if (sysuser == null)
            ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 101, null));
        // 校验密码是否正确
        String pwd_db = sysuser.getPwd();
        String pwd_page = new MD5().getMD5ofStr(pwd);
        if (!pwd_page.equalsIgnoreCase(pwd_db))
            ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 114, null));

        // 构建用户身份信息
        ActiveUser activeUser = new ActiveUser();
        String groupid = sysuser.getGroupid();
        String sysid = sysuser.getSysid();
        activeUser.setUserid(sysuser.getUserid());
        activeUser.setUsername(sysuser.getUsername());
        activeUser.setGroupid(groupid);
        activeUser.setSysid(sysid);
        String sysmc = getSysuserMcById(groupid, sysid);
        activeUser.setSysmc(sysmc);
        return activeUser;
    }

    /**
     * 查询用户列表
     *
     * @param sysuserQueryVo 用户查询条件
     * @return 用户列表
     * @throws Exception 查询用户列表异常
     */
    @Override
    public List<SysuserCustom> findSysuserList(SysuserQueryVo sysuserQueryVo) throws Exception {
        return sysuserMapperCustom.findSysuserList(sysuserQueryVo);
    }

    /**
     * 查询用户列表总数
     *
     * @param sysuserQueryVo 用户查询条件
     * @return 用户列表总数
     * @throws Exception 查询用户列表总数异常
     */
    @Override
    public int findSysuserCount(SysuserQueryVo sysuserQueryVo) throws Exception {
        return sysuserMapperCustom.findSysuserCount(sysuserQueryVo);
    }

    /**
     * 添加用户信息
     *
     * @param sysuserCustom 自定义用户扩展类
     * @throws Exception 添加用户信息异常
     */
    @Override
    public void insertSysuser(SysuserCustom sysuserCustom) throws Exception {
        // 参数校验
        // 通用参数校验，比如非空校验，长度校验
        //...

        // 业务参数校验，比如唯一性校验
        // 用户唯一性校验
        Sysuser sysuser = findSysuserByUserid(sysuserCustom.getUserid());
        if (sysuser != null) { // 帐号重复
            // ResultInfo resultInfo = new ResultInfo();
            // resultInfo.setType(ResultInfo.TYPE_RESULT_FAIL);
            // String message = ResourcesUtil.getValue("resources.messages", "213");
            // resultInfo.setMessage(message);
            // ResultInfo resultInfo = ResultUtil.createFail("resources.messages", 213, null);
            ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 213, null));
            // throw new ResultInfoException(resultInfo);
        }
        String groupid = sysuserCustom.getGroupid();
        String sysusermc = sysuserCustom.getSysusermc();
        // 根据用户类型，单位名称必须存在对应的单位表
        String sysid = getSysuserIdByMc(groupid, sysusermc);
        // 设置主键
        sysuserCustom.setId(UUIDBuild.getUUID());
        sysuserCustom.setSysid(sysid);
        sysuserCustom.setPwd(new MD5().getMD5ofStr(sysuserCustom.getPwd()));
        sysuserMapper.insert(sysuserCustom);
    }

    /**
     * 根据单位名称查询对应sysid
     *
     * @return 单位id
     * @throws ResultInfoException 单位名称输入错误抛出异常
     */
    private String getSysuserIdByMc(String groupid, String sysusermc) throws ResultInfoException {
        String sysid = null;
        switch (groupid) {
            case "1":
            case "2":
                Userjd userJdByMc = findUserJdByMc(sysusermc);
                handleMcException(userJdByMc == null);
                sysid = userJdByMc != null ? userJdByMc.getId() : null;
                break;
            case "3":
                Useryy userYyByMc = findUserYyByMc(sysusermc);
                handleMcException(userYyByMc == null);
                sysid = userYyByMc != null ? userYyByMc.getId() : null;
                break;
            case "4":
                Usergys userGysByMc = findUserGysByMc(sysusermc);
                handleMcException(userGysByMc == null);
                sysid = userGysByMc != null ? userGysByMc.getId() : null;
                break;
        }
        return sysid;
    }

    /**
     * 根据单位名称查询对应sysid
     *
     * @return 单位名称
     * @throws ResultInfoException 单位名称输入错误抛出异常
     */
    private String getSysuserMcById(String groupid, String sysid) throws ResultInfoException {
        String sysmc = null;
        switch (groupid) {
            case "1":
            case "2":
                Userjd userJdByMc = userjdMapper.selectByPrimaryKey(sysid);
                handleMcException(userJdByMc == null);
                sysmc = userJdByMc != null ? userJdByMc.getMc() : null;
                break;
            case "3":
                Useryy userYyByMc = useryyMapper.selectByPrimaryKey(sysid);
                handleMcException(userYyByMc == null);
                sysmc = userYyByMc != null ? userYyByMc.getMc() : null;
                break;
            case "4":
                Usergys userGysByMc = usergysMapper.selectByPrimaryKey(sysid);
                handleMcException(userGysByMc == null);
                sysmc = userGysByMc != null ? userGysByMc.getMc() : null;
                break;
        }
        return sysmc;
    }

    /**
     * 删除用户
     *
     * @param id 删除用户的id
     * @throws Exception 删除用户失败抛出异常
     */
    @Override
    public void deleteSysuser(String id) throws Exception {
        Sysuser sysuser = sysuserMapper.selectByPrimaryKey(id);
        if (sysuser == null) {
            ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 212, null));
        }
        sysuserMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改用户
     * TODO
     *
     * @param id            修改用户的id
     * @param sysuserCustom 修改用户的信息
     * @throws Exception 修改用户失败抛出异常
     */
    @Override
    public void updateSysuser(String id, SysuserCustom sysuserCustom) throws Exception {
        //  1. 校验要修改的帐号不能使用数据库中已有的帐号
        //修改用户账号不允许暂用别人的账号
        //如果判断账号修改了
        //页面提交的账号可能是用户修改的账号
        String userid_page = sysuserCustom.getUserid(); // 页面提交的帐号
        Sysuser sysuser = null;
        if (id != null && !id.equals("")) {
            sysuser = sysuserMapper.selectByPrimaryKey(id);
        }
        if (sysuser == null) {
            ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 215, null));
        }

        String sysuserid = sysuser.getUserid(); // 用户原始帐号
        if (!userid_page.trim().equals(sysuserid.trim())) {
            Sysuser sysuserByUseridPage = findSysuserByUserid(userid_page);
            if (sysuserByUseridPage != null) {
                ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 213, null));
            }
        }

        // 2. 页面提交的单位名称必须存在对应的单位表中
        String groupid = sysuserCustom.getGroupid();
        String sysusermc = sysuserCustom.getSysusermc();
        String sysid = getSysuserIdByMc(groupid, sysusermc);

        // 3. 修改密码
        String pwd_page = sysuserCustom.getPwd();
        String pwd_md5 = null;
        if (pwd_page != null && !pwd_page.equals("")) {
            pwd_md5 = new MD5().getMD5ofStr(pwd_page);
        }

        // 更新用户 调用updateByPrimaryKey之前，要先查询用户
        Sysuser sysuser_update = sysuserMapper.selectByPrimaryKey(id);
        sysuser_update.setUserid(sysuserCustom.getUserid());
        sysuser_update.setUsername(sysuserCustom.getUsername());
        if (pwd_md5 != null)
            sysuser_update.setPwd(pwd_md5);
        sysuser_update.setGroupid(sysuserCustom.getGroupid());
        sysuser_update.setSysid(sysid);
        sysuser_update.setUserstate(sysuserCustom.getUserstate());

        sysuserMapper.updateByPrimaryKey(sysuser_update);
    }


    private void handleMcException(boolean b) throws ResultInfoException {
        if (b) {
            // throw new Exception("单位名称输入错误");
            // 使用系统自定义异常
            // ResultInfo resultInfo = new ResultInfo();
            // resultInfo.setType(ResultInfo.TYPE_RESULT_FAIL);
            // resultInfo.setMessage("单位名称输入错误");
            // throw new ResultInfoException(resultInfo);
            ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 217, null));
        }
    }

    /**
     * 根据监督单位名称查询监督单位信息
     *
     * @param mc 单位名称
     * @return 如果存在名称对应的单位，返回该单位，否则返回null
     */
    public Userjd findUserJdByMc(String mc) {
        UserjdExample userjdExample = new UserjdExample();
        UserjdExample.Criteria criteria = userjdExample.createCriteria();
        criteria.andMcEqualTo(mc);
        List<Userjd> userjdList = userjdMapper.selectByExample(userjdExample);
        if (userjdList != null && userjdList.size() == 1) {
            return userjdList.get(0);
        } else {
            return null;
        }
    }

    /**
     * 根据医院单位名称查询医院单位信息
     *
     * @param mc 单位名称
     * @return 如果存在名称对应的单位，返回该单位，否则返回null
     */
    public Useryy findUserYyByMc(String mc) {
        UseryyExample useryyExample = new UseryyExample();
        UseryyExample.Criteria criteria = useryyExample.createCriteria();
        criteria.andMcEqualTo(mc);
        List<Useryy> useryyList = useryyMapper.selectByExample(useryyExample);
        if (useryyList != null && useryyList.size() == 1) {
            return useryyList.get(0);
        } else {
            return null;
        }
    }

    /**
     * 根据供应商单位名称查询供应商单位信息
     *
     * @param mc 单位名称
     * @return 如果存在名称对应的单位，返回该单位，否则返回null
     */
    public Usergys findUserGysByMc(String mc) {
        UsergysExample usergysExample = new UsergysExample();
        UsergysExample.Criteria criteria = usergysExample.createCriteria();
        criteria.andMcEqualTo(mc);
        List<Usergys> usergysList = usergysMapper.selectByExample(usergysExample);
        if (usergysList != null && usergysList.size() == 1) {
            return usergysList.get(0);
        } else {
            return null;
        }
    }

    /**
     * 根据用户账号userid查询用户
     *
     * @param userid 用户账号
     * @return 如果存在userid对应的用户，返回该用户，否则返回null
     */
    public Sysuser findSysuserByUserid(String userid) {
        SysuserExample sysuserExample = new SysuserExample();
        SysuserExample.Criteria criteria = sysuserExample.createCriteria();
        criteria.andUseridEqualTo(userid);
        List<Sysuser> sysuserList = sysuserMapper.selectByExample(sysuserExample);
        if (sysuserList != null && sysuserList.size() == 1) {
            return sysuserList.get(0);
        } else {
            return null;
        }
    }

    /**
     * 根据用户账号userid查询用户
     *
     * @param userid 用户账号
     * @return 如果存在userid对应的用户，返回该用户，否则返回null
     * @throws Exception 如果查询失败抛出异常
     */
    @Override
    public SysuserCustom findSysuserById(String userid) throws Exception {
        // 从数据库查询用户信息
        Sysuser sysuser = sysuserMapper.selectByPrimaryKey(userid);
        String groupid = sysuser.getGroupid();
        String sysid = sysuser.getSysid();
        String sysmc = getSysuserMcById(groupid, sysid);

        SysuserCustom sysuserCustom = new SysuserCustom();
        BeanUtils.copyProperties(sysuser, sysuserCustom);
        sysuserCustom.setSysusermc(sysmc);
        return sysuserCustom;
    }


}
