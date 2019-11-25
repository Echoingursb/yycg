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
import yycg.business.pojo.vo.YycgdCustom;
import yycg.business.pojo.vo.YycgdQueryVo;
import yycg.business.pojo.vo.YycgdmxCustom;
import yycg.business.pojo.vo.YycgdrkCustom;
import yycg.business.service.CgdService;
import yycg.util.MyUtil;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CgdAction
 * @Description: 采购单
 * @Author echo
 * @Date 2019/10/18
 * @Version V1.0
 **/
@Controller
@RequestMapping("/cgd")
public class CgdAction {
    @Autowired
    private CgdService cgdService;
    @Autowired
    private SystemConfigService systemConfigService;

    /**
     * 跳转采购单创建页面
     *
     * @return 采购单创建页面
     * @throws Exception 如果跳转采购单创建页面失败，则抛出异常
     */
    @RequestMapping("/addcgd")
    public String addcgd(Model model, HttpSession session) throws Exception {
        ActiveUser activeUser = (ActiveUser) session.getAttribute(Config.ACTIVEUSER_KEY);
        String sysmc = activeUser.getSysmc();
        String date = MyUtil.getDate();
        String year = MyUtil.get_YYYY(date);
        model.addAttribute("year", year);
        String yycgdmc = sysmc + date + "采购单";
        model.addAttribute("yycgdmc", yycgdmc);
        return "business/cgd/addcgd";
    }

    /**
     * 创建采购单基本信息提交
     *
     * @param yycgdQueryVo 页面传递参数
     * @return
     * @throws Exception
     */
    @RequestMapping("/addcgdsubmit")
    @ResponseBody
    public SubmitResultInfo addcgdsubmit(HttpSession session, String year, YycgdQueryVo yycgdQueryVo) throws Exception {
        ActiveUser activeUser = (ActiveUser) session.getAttribute(Config.ACTIVEUSER_KEY);
        String useryyid = activeUser.getSysid();
        String id = cgdService.insertYycgd(useryyid, year, yycgdQueryVo.getYycgdCustom());
        ResultInfo resultInfo = ResultUtil.createSuccess(Config.MESSAGE, 906, null);
        Map<String, Object> sysdata = resultInfo.getSysdata();
        sysdata.put("yycgdid", id);
        resultInfo.setSysdata(sysdata);
        return ResultUtil.createSubmitResult(resultInfo);
    }

    /**
     * 跳转采购单编辑页面
     *
     * @param model
     * @param id    采购单编号
     * @return
     * @throws Exception
     */
    @RequestMapping("/editcgd")
    public String editcgd(Model model, String id) throws Exception {
        List yplblist = systemConfigService.findDictinfoByType("001");
        model.addAttribute("yplblist", yplblist);
        List jyztlist = systemConfigService.findDictinfoByType("003");
        model.addAttribute("jyztlist", jyztlist);
        List cgztlist = systemConfigService.findDictinfoByType("011");
        model.addAttribute("cgztlist", cgztlist);
        YycgdCustom yycgd = cgdService.findYycgdById(id);
        model.addAttribute("yycgd", yycgd);
        return "business/cgd/editcgd";
    }

    /**
     * 修改采购单提交
     *
     * @param id           采购单id
     * @param yycgdQueryVo 修改信息
     * @return
     * @throws Exception
     */
    @RequestMapping("/editcgdsubmit")
    @ResponseBody
    public SubmitResultInfo editcgdsubmit(String id, YycgdQueryVo yycgdQueryVo) throws Exception {
        cgdService.updateYycgd(id, yycgdQueryVo.getYycgdCustom());
        return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
    }

    /**
     * 采购单明细查询结果集
     *
     * @return 采购单明细查询结果集
     * @throws Exception 如果采购单明细查询结果集失败，则抛出异常
     */
    @RequestMapping("/queryyycgdmxResult")
    @ResponseBody
    public DataGridResultInfo queryyycgdmxResult(String id, YycgdQueryVo yycgdQueryVo, int page, int rows) throws Exception {
        int count = cgdService.selectYycgdmxCountByYycgdid(id, yycgdQueryVo);
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageParams(count, rows, page);
        yycgdQueryVo.setPageQuery(pageQuery);
        List<YycgdmxCustom> list = cgdService.selectYycgdmxListByYycgdid(id, yycgdQueryVo);
        DataGridResultInfo dataGridResultInfo = new DataGridResultInfo();
        dataGridResultInfo.setTotal(count);
        dataGridResultInfo.setRows(list);
        if (count > 0) {
            List<YycgdmxCustom> listSum = cgdService.selectYycgdmxListSum(id, yycgdQueryVo);
            dataGridResultInfo.setFooter(listSum);
        }
        return dataGridResultInfo;
    }

    /**
     * 跳转采购药品添加页面
     *
     * @param model   模型数据
     * @param yycgdid 医院采购单id
     * @return 采购药品添加页面
     * @throws Exception 如果跳转采购药品添加页面失败，则抛出异常
     */
    @RequestMapping("/queryaddyycgdmx")
    public String queryaddyycgdmx(Model model, String yycgdid) throws Exception {
        List yplblist = systemConfigService.findDictinfoByType("001");
        model.addAttribute("yplblist", yplblist);
        List jyztlist = systemConfigService.findDictinfoByType("003");
        model.addAttribute("jyztlist", jyztlist);
        List zlcclist = systemConfigService.findDictinfoByType("004");
        model.addAttribute("zlcclist", zlcclist);
        List controllist = systemConfigService.findDictinfoByType("008");
        model.addAttribute("controllist", controllist);
        model.addAttribute("yycgdid", yycgdid);
        return "business/cgd/queryaddyycgdmx";
    }


    /**
     * 采购单药品添加查询结果集
     *
     * @param session session
     * @param yycgdid 医院采购单id
     * @param page    当前页
     * @param rows    行数
     * @return DataGridResultInfo 采购单药品添加查询结果集
     * @throws Exception 如果采购单药品添加查询结果集失败，则抛出异常
     */
    @RequestMapping("/queryaddyycgdmxResult")
    @ResponseBody
    public DataGridResultInfo queryaddyycgdmxResult(HttpSession session, String yycgdid, YycgdQueryVo yycgdQueryVo, int page, int rows) throws Exception {
        ActiveUser activeUser = (ActiveUser) session.getAttribute(Config.ACTIVEUSER_KEY);
        String useryyid = activeUser.getSysid();
        int count = cgdService.selectAddYycgdmxCount(useryyid, yycgdid, yycgdQueryVo);
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageParams(count, rows, page);
        yycgdQueryVo.setPageQuery(pageQuery);
        List<YycgdmxCustom> list = cgdService.selectAddYycgdmxList(useryyid, yycgdid, yycgdQueryVo);
        DataGridResultInfo dataGridResultInfo = new DataGridResultInfo();
        dataGridResultInfo.setRows(list);
        dataGridResultInfo.setTotal(count);
        return dataGridResultInfo;
    }

    /**
     * 批量采购单药品添加提交
     *
     * @param yycgdid 医院采购单id
     * @param indexs  接收页面选中的行序号
     * @return SubmitResultInfo 系统提交结果封装类
     * @throws Exception 如果批量采购单药品添加提交失败，则抛出异常
     */
    @RequestMapping("/addyycgdmxsubmit")
    @ResponseBody
    public SubmitResultInfo addyycgdmxsubmit(
            String yycgdid,
            YycgdQueryVo yycgdQueryVo,
            int[] indexs
    ) throws Exception {
        List<YycgdmxCustom> yycgdmxCustoms = yycgdQueryVo.getYycgdmxCustoms();
        // 处理数据的总数
        int count = indexs.length;
        // 处理成功的数量
        int count_success = 0;
        // 处理失败的数量
        int count_failures = 0;
        // 处理失败的原因
        List<ResultInfo> failures_msgs = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            YycgdmxCustom yycgdmxCustom = yycgdmxCustoms.get(indexs[i]);
            String ypxxid = yycgdmxCustom.getYpxxid();
            String usergysid = yycgdmxCustom.getUsergysid();
            ResultInfo resultInfo = null;
            try {
                cgdService.insertYycgdmx(yycgdid, ypxxid, usergysid);
            } catch (Exception e) {
                e.printStackTrace();
                if (e instanceof ResultInfoException) {
                    resultInfo = ((ResultInfoException) e).getResultInfo();
                } else {
                    ResultUtil.createFail(Config.MESSAGE, 900, null);
                }
            }
            if (resultInfo == null) {
                count_success++;
            } else {
                count_failures++;
                failures_msgs.add(resultInfo);
            }
        }
        return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 907, new Object[]{
                        count_success, count_failures
                }),
                failures_msgs);
    }

    /**
     * 批量更新采购单药品采购量
     *
     * @param yycgdQueryVo 查询条件
     * @param indexs       接收页面选中的行序号
     * @param id           医院采购单id
     * @return
     * @throws Exception
     */
    @RequestMapping("/savecgl")
    @ResponseBody
    public SubmitResultInfo savecgl(YycgdQueryVo yycgdQueryVo, int[] indexs, String id) throws Exception {
        List<YycgdmxCustom> yycgdmxCustoms = yycgdQueryVo.getYycgdmxCustoms();
        // 处理数据的总数
        int count = indexs.length;
        // 处理成功的数量
        int count_success = 0;
        // 处理失败的数量
        int count_failures = 0;
        // 处理失败的原因
        List<ResultInfo> failures_msgs = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            YycgdmxCustom yycgdmxCustom = yycgdmxCustoms.get(indexs[i]);
            String ypxxid = yycgdmxCustom.getYpxxid();
            Integer cgl = yycgdmxCustom.getCgl();
            ResultInfo resultInfo = null;
            try {
                cgdService.updateYycgdmx(id, ypxxid, cgl);
            } catch (Exception e) {
                e.printStackTrace();
                if (e instanceof ResultInfoException) {
                    resultInfo = ((ResultInfoException) e).getResultInfo();
                } else {
                    ResultUtil.createFail(Config.MESSAGE, 900, null);
                }
            }
            if (resultInfo == null) {
                count_success++;
            } else {
                count_failures++;
                failures_msgs.add(resultInfo);
            }
        }
        return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 907, new Object[]{
                        count_success, count_failures
                }),
                failures_msgs);
    }

    /**
     * 跳转到医院采购单维护页面
     *
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/querycgd")
    public String querycgd(Model model) throws Exception {
        List cgztlist = systemConfigService.findDictinfoByType("010");
        model.addAttribute("cgztlist", cgztlist);
        model.addAttribute("year", MyUtil.get_YYYY(MyUtil.getDate()));
        return "business/cgd/queryYycgd";
    }

    /**
     * 采购单维护列表查询
     *
     * @param session
     * @param year
     * @param yycgdQueryVo
     * @param page
     * @param rows
     * @return
     * @throws Exception
     */
    @RequestMapping("/queryYycgdResult")
    @ResponseBody
    public DataGridResultInfo queryYycgdResult(HttpSession session,
                                               String year,
                                               YycgdQueryVo yycgdQueryVo,
                                               int page,
                                               int rows) throws Exception {
        ActiveUser activeUser = (ActiveUser) session.getAttribute(Config.ACTIVEUSER_KEY);
        String useryyid = activeUser.getSysid();
        year = year.substring(0, 4);
        int count = cgdService.selectYycgdCount(useryyid, year, yycgdQueryVo);
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageParams(count, rows, page);
        yycgdQueryVo.setPageQuery(pageQuery);
        List<YycgdCustom> list = cgdService.selectYycgdList(useryyid, year, yycgdQueryVo);
        DataGridResultInfo dataGridResultInfo = new DataGridResultInfo();
        dataGridResultInfo.setTotal(count);
        dataGridResultInfo.setRows(list);
        return dataGridResultInfo;
    }

    /**
     * 医药采购单提交
     *
     * @param id 采购单id
     * @return
     * @throws Exception
     */
    @RequestMapping("/submitYycgd")
    @ResponseBody
    public SubmitResultInfo submitYycgd(String id) throws Exception {
        cgdService.saveYycgdSubmit(id);
        return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
    }

    /**
     * 跳转采购单审核页面
     *
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/querycheckcgd")
    public String querycheckcgd(Model model) throws Exception {
        model.addAttribute("year", MyUtil.get_YYYY(MyUtil.getDate()));
        return "business/cgd/checkYycgd";
    }

    /**
     * 采购单审核列表查询
     *
     * @param session
     * @param year
     * @param yycgdQueryVo
     * @param page
     * @param rows
     * @return
     * @throws Exception
     */
    @RequestMapping("/checkYycgdResult")
    @ResponseBody
    public DataGridResultInfo checkYycgdResult(HttpSession session,
                                               String year,
                                               YycgdQueryVo yycgdQueryVo,
                                               int page,
                                               int rows) throws Exception {
        ActiveUser activeUser = (ActiveUser) session.getAttribute(Config.ACTIVEUSER_KEY);
        String userjdid = activeUser.getSysid();
        year = year.substring(0, 4);
        int count = cgdService.findYycgdCheckCount(userjdid, year, yycgdQueryVo);
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageParams(count, rows, page);
        yycgdQueryVo.setPageQuery(pageQuery);
        List<YycgdCustom> list = cgdService.findYycgdCheckList(userjdid, year, yycgdQueryVo);
        DataGridResultInfo dataGridResultInfo = new DataGridResultInfo();
        dataGridResultInfo.setTotal(count);
        dataGridResultInfo.setRows(list);
        return dataGridResultInfo;
    }


    /**
     * 批量提交采购单审核结果
     *
     * @param yycgdQueryVo 查询条件
     * @param indexs       接收页面选中的行序号
     * @return
     * @throws Exception
     */
    @RequestMapping("/checkcgdsubmit")
    @ResponseBody
    public SubmitResultInfo checkcgdsubmit(YycgdQueryVo yycgdQueryVo, int[] indexs) throws Exception {
        List<YycgdCustom> yycgdCustoms = yycgdQueryVo.getYycgdCustoms();
        // 处理数据的总数
        int count = indexs.length;
        // 处理成功的数量
        int count_success = 0;
        // 处理失败的数量
        int count_failures = 0;
        // 处理失败的原因
        List<ResultInfo> failures_msgs = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            YycgdCustom yycgdCustom = yycgdCustoms.get(indexs[i]);
            String yycgdid = yycgdCustom.getId();
            ResultInfo resultInfo = null;
            try {
                cgdService.saveYycgdCheckStatus(yycgdid, yycgdCustom);
            } catch (Exception e) {
                e.printStackTrace();
                if (e instanceof ResultInfoException) {
                    resultInfo = ((ResultInfoException) e).getResultInfo();
                } else {
                    ResultUtil.createFail(Config.MESSAGE, 900, null);
                }
            }
            if (resultInfo == null) {
                count_success++;
            } else {
                count_failures++;
                failures_msgs.add(resultInfo);
            }
        }
        return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 907, new Object[]{
                        count_success, count_failures
                }),
                failures_msgs);
    }

    /**
     * 跳转采购单受理页面
     *
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/disposecgd")
    public String disposecgd(Model model) throws Exception {
        model.addAttribute("year", MyUtil.get_YYYY(MyUtil.getDate()));
        return "business/cgd/disposeyycgd";
    }

    /**
     * 采购单受理列表查询
     *
     * @param session
     * @param year
     * @param yycgdQueryVo
     * @param page
     * @param rows
     * @return
     * @throws Exception
     */
    @RequestMapping("/disposeyycgdResult")
    @ResponseBody
    public DataGridResultInfo disposeyycgdResult(HttpSession session,
                                                 String year,
                                                 YycgdQueryVo yycgdQueryVo,
                                                 int page,
                                                 int rows) throws Exception {
        ActiveUser activeUser = (ActiveUser) session.getAttribute(Config.ACTIVEUSER_KEY);
        String usergysid = activeUser.getSysid();
        year = year.substring(0, 4);
        int count = cgdService.findDiposeYycgdCount(usergysid, year, yycgdQueryVo);
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageParams(count, rows, page);
        yycgdQueryVo.setPageQuery(pageQuery);
        List<YycgdmxCustom> list = cgdService.findDiposeYycgdList(usergysid, year, yycgdQueryVo);
        DataGridResultInfo dataGridResultInfo = new DataGridResultInfo();
        dataGridResultInfo.setTotal(count);
        dataGridResultInfo.setRows(list);
        return dataGridResultInfo;
    }

    /**
     * 批量确认发货
     *
     * @param yycgdQueryVo 查询条件
     * @param indexs       接收页面选中的行序号
     * @return
     * @throws Exception
     */
    @RequestMapping("/disposesubmit")
    @ResponseBody
    public SubmitResultInfo disposesubmit(YycgdQueryVo yycgdQueryVo, int[] indexs) throws Exception {
        List<YycgdmxCustom> yycgdmxCustoms = yycgdQueryVo.getYycgdmxCustoms();
        // 处理数据的总数
        int count = indexs.length;
        // 处理成功的数量
        int count_success = 0;
        // 处理失败的数量
        int count_failures = 0;
        // 处理失败的原因
        List<ResultInfo> failures_msgs = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            YycgdmxCustom yycgdmxCustom = yycgdmxCustoms.get(indexs[i]);
            String yycgdid = yycgdmxCustom.getYycgdid();
            String ypxxid = yycgdmxCustom.getYpxxid();
            ResultInfo resultInfo = null;
            try {
                cgdService.saveSendStatus(yycgdid, ypxxid);
            } catch (Exception e) {
                e.printStackTrace();
                if (e instanceof ResultInfoException) {
                    resultInfo = ((ResultInfoException) e).getResultInfo();
                } else {
                    ResultUtil.createFail(Config.MESSAGE, 900, null);
                }
            }
            if (resultInfo == null) {
                count_success++;
            } else {
                count_failures++;
                failures_msgs.add(resultInfo);
            }
        }
        return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 907, new Object[]{
                        count_success, count_failures
                }),
                failures_msgs);
    }

    /**
     * 跳转采购药品入库
     *
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/receivecgd")
    public String receivecgd(Model model) throws Exception {
        model.addAttribute("year", MyUtil.get_YYYY(MyUtil.getDate()));
        return "business/cgd/receiveyycgd";
    }

    /**
     * 采购药品入库列表查询
     *
     * @param session
     * @param year
     * @param yycgdQueryVo
     * @param page
     * @param rows
     * @return
     * @throws Exception
     */
    @RequestMapping("/receiveyycgdResult")
    @ResponseBody
    public DataGridResultInfo receiveyycgdResult(HttpSession session,
                                                 String year,
                                                 YycgdQueryVo yycgdQueryVo,
                                                 int page,
                                                 int rows) throws Exception {
        ActiveUser activeUser = (ActiveUser) session.getAttribute(Config.ACTIVEUSER_KEY);
        String useryyid = activeUser.getSysid();
        year = year.substring(0, 4);
        int count = cgdService.findYycgdReceiveCount(useryyid, year, yycgdQueryVo);
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageParams(count, rows, page);
        yycgdQueryVo.setPageQuery(pageQuery);
        List<YycgdmxCustom> list = cgdService.findYycgdReceiveList(useryyid, year, yycgdQueryVo);
        DataGridResultInfo dataGridResultInfo = new DataGridResultInfo();
        dataGridResultInfo.setTotal(count);
        dataGridResultInfo.setRows(list);
        return dataGridResultInfo;
    }

    /**
     * 采购药品批量提交
     *
     * @param yycgdQueryVo 查询条件
     * @param indexs       接收页面选中的行序号
     * @return
     * @throws Exception
     */
    @RequestMapping("/receivesubmit")
    @ResponseBody
    public SubmitResultInfo receivesubmit(YycgdQueryVo yycgdQueryVo, int[] indexs) throws Exception {
        List<YycgdrkCustom> yycgdrkCustoms = yycgdQueryVo.getYycgdrkCustoms();
        // 处理数据的总数
        int count = indexs.length;
        // 处理成功的数量
        int count_success = 0;
        // 处理失败的数量
        int count_failures = 0;
        // 处理失败的原因
        List<ResultInfo> failures_msgs = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            YycgdrkCustom yycgdrkCustom = yycgdrkCustoms.get(indexs[i]);
            String yycgdid = yycgdrkCustom.getYycgdid();
            String ypxxid = yycgdrkCustom.getYpxxid();
            ResultInfo resultInfo = null;
            try {
                cgdService.saveYycgdrk(yycgdid, ypxxid, yycgdrkCustom);
            } catch (Exception e) {
                e.printStackTrace();
                if (e instanceof ResultInfoException) {
                    resultInfo = ((ResultInfoException) e).getResultInfo();
                } else {
                    ResultUtil.createFail(Config.MESSAGE, 900, null);
                }
            }
            if (resultInfo == null) {
                count_success++;
            } else {
                count_failures++;
                failures_msgs.add(resultInfo);
            }
        }
        return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 907, new Object[]{
                        count_success, count_failures
                }),
                failures_msgs);
    }

}
