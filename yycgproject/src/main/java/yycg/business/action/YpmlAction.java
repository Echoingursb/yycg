package yycg.business.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import yycg.base.pojo.vo.ActiveUser;
import yycg.base.pojo.vo.PageQuery;
import yycg.base.process.context.Config;
import yycg.base.process.result.*;
import yycg.base.service.SystemConfigService;
import yycg.business.pojo.vo.GysypmlControlCustom;
import yycg.business.pojo.vo.GysypmlCustom;
import yycg.business.pojo.vo.GysypmlQueryVo;
import yycg.business.pojo.vo.YpxxCustom;
import yycg.business.service.YpmlService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName YpmlAction
 * @Description: 药品目录
 * @Author echo
 * @Date 2019/10/14
 * @Version V1.0
 **/
@RequestMapping("/ypml")
@Controller
public class YpmlAction {
    @Autowired
    private YpmlService ypmlService;
    @Autowired
    private SystemConfigService systemConfigService;

    /**
     * 跳转供货商药品目录维护页面
     *
     * @param model 模型数据
     * @return 供货商药品目录维护页面
     * @throws Exception 如果跳转供货商药品目录维护页面，则抛出异常
     */
    @RequestMapping("/querygysypml")
    public String querygysypml(Model model) throws Exception {
        List yplblist = systemConfigService.findDictinfoByType("001");
        model.addAttribute("yplblist", yplblist);
        List jyztlist = systemConfigService.findDictinfoByType("003");
        model.addAttribute("jyztlist", jyztlist);
        List zlcclist = systemConfigService.findDictinfoByType("004");
        model.addAttribute("zlcclist", zlcclist);
        List controllist = systemConfigService.findDictinfoByType("008");
        model.addAttribute("controllist", controllist);
        return "business/ypml/querygysypml";
    }


    /**
     * 查询供货商药品目录结果
     *
     * @param session        session
     * @param gysypmlQueryVo 查询条件
     * @param page           当前页
     * @param rows           行数
     * @return DataGridResultInfo 供货商药品目录结果集
     * @throws Exception 如果查询供货商药品目录结果失败，则抛出异常
     */
    @RequestMapping("/querygysypmlResult")
    @ResponseBody
    public DataGridResultInfo querygysypmlResult(HttpSession session, GysypmlQueryVo gysypmlQueryVo, int page, int rows) throws Exception {
        ActiveUser activeUser = (ActiveUser) session.getAttribute(Config.ACTIVEUSER_KEY);
        String usergysId = activeUser.getSysid();
        int count = ypmlService.findGysypmlCount(usergysId, gysypmlQueryVo);
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageParams(count, rows, page);
        gysypmlQueryVo.setPageQuery(pageQuery);
        List<GysypmlCustom> list = ypmlService.findGysypmlList(usergysId, gysypmlQueryVo);
        DataGridResultInfo dataGridResultInfo = new DataGridResultInfo();
        dataGridResultInfo.setTotal(count);
        dataGridResultInfo.setRows(list);
        return dataGridResultInfo;
    }

    /**
     * 跳转供货商药品目录添加页面
     *
     * @param model 模型数据
     * @return 供货商药品目录添加页面
     * @throws Exception 如果跳转供货商药品目录添加页面，则抛出异常
     */
    @RequestMapping("/querygysypmladd")
    public String querygysypmladd(Model model) throws Exception {
        List yplblist = systemConfigService.findDictinfoByType("001");
        model.addAttribute("yplblist", yplblist);
        List jyztlist = systemConfigService.findDictinfoByType("003");
        model.addAttribute("jyztlist", jyztlist);
        List zlcclist = systemConfigService.findDictinfoByType("004");
        model.addAttribute("zlcclist", zlcclist);
        List controllist = systemConfigService.findDictinfoByType("008");
        model.addAttribute("controllist", controllist);
        return "business/ypml/querygysypmladd";
    }


    /**
     * 供应商药品目录添加查询结果集
     *
     * @param session        session
     * @param gysypmlQueryVo 查询条件
     * @param page           当前页
     * @param rows           行数
     * @return DataGridResultInfo 供应商药品目录添加查询结果集
     * @throws Exception 如果查询供货商药品添加查询结果集失败，则抛出异常
     */
    @RequestMapping("/querygysypmladdResult")
    @ResponseBody
    public DataGridResultInfo querygysypmladdResult(HttpSession session, GysypmlQueryVo gysypmlQueryVo, int page, int rows) throws Exception {
        ActiveUser activeUser = (ActiveUser) session.getAttribute(Config.ACTIVEUSER_KEY);
        String usergysId = activeUser.getSysid();
        gysypmlQueryVo = gysypmlQueryVo != null ? gysypmlQueryVo : new GysypmlQueryVo();
        int count = ypmlService.findAddGysypmlCount(usergysId, gysypmlQueryVo);
        PageQuery pageQuery = new PageQuery();
        gysypmlQueryVo.setPageQuery(pageQuery);
        pageQuery.setPageParams(count, rows, page);
        List<GysypmlCustom> list = ypmlService.findAddGysypmlList(usergysId, gysypmlQueryVo);
        DataGridResultInfo dataGridResultInfo = new DataGridResultInfo();
        dataGridResultInfo.setTotal(count);
        dataGridResultInfo.setRows(list);
        return dataGridResultInfo;
    }

    /**
     * 批量添加药品信息提交
     *
     * @param indexs         接收页面选中的行序号
     * @param gysypmlQueryVo 页面提交的添加数据保存在ypxxCustoms中
     * @return SubmitResultInfo 系统提交结果封装类
     * @throws Exception 如果批量添加药品信息提交失败，则抛出异常
     */
    @RequestMapping("/gysypmladdsubmit")
    @ResponseBody
    public SubmitResultInfo gysypmladdsubmit(int[] indexs, GysypmlQueryVo gysypmlQueryVo, HttpSession session) throws Exception {
        ActiveUser activeUser = (ActiveUser) session.getAttribute(Config.ACTIVEUSER_KEY);// 供货商id从session中取
        String usergysid = activeUser.getSysid();
        List<YpxxCustom> ypxxCustoms = gysypmlQueryVo.getYpxxCustoms();
        // 处理数据的总数
        int count = indexs.length;
        // 处理成功的数量
        int count_success = 0;
        // 处理失败的数量
        int count_failure = 0;
        // 处理失败原因
        List<ResultInfo> failure_msgs = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            YpxxCustom ypxxCustom = ypxxCustoms.get(indexs[i]);
            ResultInfo resultInfo = null;
            try {
                ypmlService.insertGysypml(usergysid, ypxxCustom.getId());
            } catch (Exception e) {
                e.printStackTrace();
                if (e instanceof ResultInfoException)
                    resultInfo = ((ResultInfoException) e).getResultInfo();
                else
                    // 构造未知错误异常
                    resultInfo = ResultUtil.createFail(Config.MESSAGE, 900, null);
            }
            if (resultInfo == null)
                // 说明插入成功
                count_success++;
            else {
                // 说明插入失败
                count_failure++;
                failure_msgs.add(resultInfo);
            }
        }
        return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 907, new Object[]{
                count_success, count_failure
        }), failure_msgs);
    }

    /**
     * 批量删除药品信息提交
     *
     * @param indexs         接收页面选中的行序号
     * @param gysypmlQueryVo 页面提交的添加数据保存在gysypmlCustoms中
     * @param session        session
     * @return SubmitResultInfo 系统提交结果封装类
     * @throws Exception 如果批量删除药品信息提交失败，则抛出异常
     */
    @RequestMapping("/deletegysypmlsubmit")
    @ResponseBody
    public SubmitResultInfo deletegysypmlsubmit(int[] indexs, GysypmlQueryVo gysypmlQueryVo, HttpSession session) throws Exception {
        ActiveUser activeUser = (ActiveUser) session.getAttribute(Config.ACTIVEUSER_KEY);
        String usergysid = activeUser.getSysid();
        List<GysypmlCustom> gysypmlCustoms = gysypmlQueryVo.getGysypmlCustoms();
        // 处理数据的总数
        int count = indexs.length;
        // 处理成功的数量
        int count_success = 0;
        // 处理失败的数量
        int count_failure = 0;
        List<ResultInfo> failure_msgs = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            GysypmlCustom gysypmlCustom = gysypmlCustoms.get(indexs[i]);
            ResultInfo resultInfo = null;
            try {
                ypmlService.deleteGysypmlByUsergysidAndYpxxid(usergysid, gysypmlCustom.getYpxxid());
            } catch (Exception e) {
                e.printStackTrace();
                if (e instanceof ResultInfoException) {
                    resultInfo = ((ResultInfoException) e).getResultInfo();
                } else {
                    resultInfo = ResultUtil.createFail(Config.MESSAGE, 900, null);
                }
            }
            if (resultInfo == null) {
                count_success++;
            } else {
                count_failure++;
                failure_msgs.add(resultInfo);
            }
        }
        return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 907, new Object[]{
                count_success, count_failure
        }), failure_msgs);
    }


    /**
     * 跳转供货目录控制页面
     *
     * @param model 模型数据
     * @return 供货目录控制页面
     * @throws Exception 如果跳转供货目录控制页面失败，则抛出异常
     */
    @RequestMapping("/querygysypmlcontrol")
    public String querygysypmlcontrol(Model model) throws Exception {
        List yplblist = systemConfigService.findDictinfoByType("001");
        model.addAttribute("yplblist", yplblist);
        List jyztlist = systemConfigService.findDictinfoByType("003");
        model.addAttribute("jyztlist", jyztlist);
        List controllist = systemConfigService.findDictinfoByType("008");
        model.addAttribute("controllist", controllist);
        return "business/ypml/querygysypmlcontrol";
    }

    /**
     * 查询供货商控制目录结果集
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/querygysypmlcontrolRresult")
    @ResponseBody
    public DataGridResultInfo querygysypmlcontrolRresult(GysypmlQueryVo gysypmlQueryVo, int page, int rows) throws Exception {
        // 列表总数
        int count = ypmlService.findGysypmlControlCount(gysypmlQueryVo);
        // 分页参数
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageParams(count, rows, page);
        gysypmlQueryVo.setPageQuery(pageQuery);

        // 分页查询列表
        List<GysypmlControlCustom> list = ypmlService.findGysypmlControlList(gysypmlQueryVo);
        DataGridResultInfo dataGridResultInfo = new DataGridResultInfo();
        dataGridResultInfo.setRows(list);
        dataGridResultInfo.setTotal(count);
        return dataGridResultInfo;
    }

    /**
     * 批量更新药品目录控制信息
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/gysypmlcontrolsubmit")
    @ResponseBody
    public SubmitResultInfo gysypmlcontrolsubmit(int[] indexs, GysypmlQueryVo gysypmlQueryVo) throws Exception {
        List<GysypmlControlCustom> gysypmlControlCustoms = gysypmlQueryVo.getGysypmlControlCustoms();
        // 处理数据的总数
        int count = indexs.length;
        // 处理成功的数量
        int count_success = 0;
        // 处理失败的数量
        int count_failures = 0;
        List<ResultInfo> failure_msgs = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            GysypmlControlCustom gysypmlControlCustom = gysypmlControlCustoms.get(indexs[i]);
            String usergysid = gysypmlControlCustom.getUsergysid();
            String ypxxid = gysypmlControlCustom.getYpxxid();
            String control = gysypmlControlCustom.getControl();
            String advice = gysypmlControlCustom.getAdvice();
            ResultInfo resultInfo = null;
            try {
                ypmlService.updateGysypmlControlByUsergysidAndYpxxid(usergysid, ypxxid, control, advice);
            } catch (Exception e) {
                e.printStackTrace();
                if (e instanceof ResultInfoException) {
                    resultInfo = ((ResultInfoException) e).getResultInfo();
                } else {
                    resultInfo = ResultUtil.createFail(Config.MESSAGE, 900, null);
                }
            }
            if (resultInfo == null) {
                count_success++;
            } else {
                count_failures++;
                failure_msgs.add(resultInfo);
            }
        }
        return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 907, new Object[]{
                count_success, count_failures
        }), failure_msgs);
    }
}
