package yycg.base.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import yycg.base.process.context.Config;
import yycg.base.process.result.DataGridResultInfo;
import yycg.base.process.result.ResultUtil;
import yycg.base.process.result.SubmitResultInfo;
import yycg.base.service.SystemConfigService;
import yycg.base.service.UserService;
import yycg.base.pojo.vo.PageQuery;
import yycg.base.pojo.vo.SysuserCustom;
import yycg.base.pojo.vo.SysuserQueryVo;

import java.util.List;

/**
 * @ClassName UserAction
 * @Description: 系统用户管理
 * @Author echo
 * @Date 2019/07/02
 * @Version V1.0
 **/
@Controller
@RequestMapping("/user")
public class UserAction {
    @Autowired
    UserService userService;
    @Autowired
    SystemConfigService systemConfigService;

    /**
     * 跳转用户查询页面
     *
     * @return 用户查询页面
     * @throws Exception 跳转用户查询页面失败
     */
    @RequestMapping("/queryuser")
    public String queryuser(Model model) throws Exception {
        List groupList = systemConfigService.findDictinfoByType("s01");
        model.addAttribute("groupList", groupList);
        return "/base/user/queryuser";
    }

    /**
     * 跳转用户查询结果页面
     *
     * @param sysuserQueryVo 自定义用户包装类
     * @param page           当前页码
     * @param rows           每页显示个数
     * @return 用户查询结果页面
     * @throws Exception 跳转用户查询结果页面异常
     */
    @RequestMapping("/queryuserresult")
    @ResponseBody
    public DataGridResultInfo queryuserresult(SysuserQueryVo sysuserQueryVo, int page, int rows) throws Exception {
        sysuserQueryVo = sysuserQueryVo != null ? sysuserQueryVo : new SysuserQueryVo();
        int count = userService.findSysuserCount(sysuserQueryVo);
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageParams(count, rows, page);
        sysuserQueryVo.setPageQuery(pageQuery);
        List<SysuserCustom> sysuserCustomList = userService.findSysuserList(sysuserQueryVo);
        DataGridResultInfo dataGridResultInfo = new DataGridResultInfo();
        dataGridResultInfo.setTotal(count);
        dataGridResultInfo.setRows(sysuserCustomList);
        return dataGridResultInfo;
    }

    /**
     * 跳转用户添加页面
     *
     * @return 用户添加页面
     * @throws Exception 跳转用户添加页面失败
     */
    @RequestMapping("/addsysuser")
    public String addsysuser() throws Exception {
        return "base/user/addsysuser";
    }

    /**
     * 用户添加提交
     *
     * @param sysuserQueryVo 自定义用户包装类
     * @return Map 操作信息
     * @throws Exception 用户添加提交失败
     */
    @RequestMapping("/addsysusersubmit")
    @ResponseBody
    public SubmitResultInfo addsysusersubmit(SysuserQueryVo sysuserQueryVo) throws Exception {
        // ResultInfo resultInfo = new ResultInfo();
        // resultInfo.setType(ResultInfo.TYPE_RESULT_SUCCESS);
        // resultInfo.setMessage("操作成功！");
        userService.insertSysuser(sysuserQueryVo.getSysuserCustom());
        // return new SubmitResultInfo(resultInfo);
        return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
    }

    /**
     * 删除用户
     *
     * @param id 删除用户的id
     * @throws Exception 删除用户失败抛出异常
     */
    @RequestMapping("/deletsysuser")
    @ResponseBody
    public SubmitResultInfo deletsysuser(String id) throws Exception {
        userService.deleteSysuser(id);
        return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
    }

    /**
     * 跳转用户修改页面
     *
     * @param model 填充导入模型数据
     * @param id    修改用户的id
     * @return 用户修改页面
     * @throws Exception 跳转用户修改页面失败抛出异常
     */
    @RequestMapping("/editsysuser")
    public String editsysyuser(Model model, String id) throws Exception {
        // 通过id取出用户信息，传向页面
        SysuserCustom sysuserCustom = userService.findSysuserById(id);
        model.addAttribute("sysuserCustom", sysuserCustom);
        return "base/user/editsysuser";
    }

    /**
     * 修改用户提交
     *
     * @param id             修改用户原始id
     * @param sysuserQueryVo 自定义用户包装类
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/editsysusersubmit", method = RequestMethod.POST)
    @ResponseBody
    public SubmitResultInfo editsysusersubmit(String id, SysuserQueryVo sysuserQueryVo) throws Exception {
        SysuserCustom sysuserCustom = sysuserQueryVo.getSysuserCustom();
        userService.updateSysuser(id, sysuserCustom);
        return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
    }


}
