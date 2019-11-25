package yycg.business.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import yycg.base.pojo.vo.ActiveUser;
import yycg.base.pojo.vo.PageQuery;
import yycg.base.process.context.Config;
import yycg.base.process.result.DataGridResultInfo;
import yycg.base.process.result.ResultInfo;
import yycg.base.process.result.SubmitResultInfo;
import yycg.business.pojo.vo.YybusinessCustom;
import yycg.business.pojo.vo.YybusinessQueryVo;
import yycg.business.service.YybusinessService;
import yycg.util.MyUtil;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @ClassName TjAction
 * @Description: TODO
 * @Author echo
 * @Date 2019/10/28
 * @Version V1.0
 **/
@Controller
@RequestMapping("/statistics")
public class StatisticsAction {
    @Autowired
    private YybusinessService yybusinessService;

    /**
     * 跳转交易明细查询页面
     *
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/businesslist")
    public String businesslist(Model model) throws Exception {
        String year = MyUtil.get_YYYY(MyUtil.getDate());
        model.addAttribute("year", year);
        return "business/statistics/businesslist";
    }

    @RequestMapping("/businesslistResult")
    @ResponseBody
    public DataGridResultInfo businesslistResult(
            String year,
            HttpSession session,
            YybusinessQueryVo yybusinessQueryVo,
            int rows,
            int page
    ) throws Exception {
        ActiveUser activeUser = (ActiveUser) session.getAttribute(Config.ACTIVEUSER_KEY);
        String sysid = activeUser.getSysid();
        String groupid = activeUser.getGroupid();
        int count = yybusinessService.findYyBusinessCount(year, sysid, groupid, yybusinessQueryVo);
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageParams(count, rows, page);
        List<YybusinessCustom> list = yybusinessService.findYyBusinessList(year, sysid, groupid, yybusinessQueryVo);
        DataGridResultInfo dataGridResultInfo = new DataGridResultInfo();
        dataGridResultInfo.setTotal(count);
        dataGridResultInfo.setRows(list);
        return dataGridResultInfo;
    }

    /**
     * 跳转按药品统计页面
     *
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/businessypxxsum")
    public String businessypxxsum(Model model) throws Exception {
        String year = MyUtil.get_YYYY(MyUtil.getDate());
        model.addAttribute("year", year);
        return "business/statistics/sumbyypxx";
    }


    @RequestMapping("/sumbyypxxResult")
    @ResponseBody
    public DataGridResultInfo sumbyypxxResult(
            String year,
            HttpSession session,
            YybusinessQueryVo yybusinessQueryVo,
            int rows,
            int page
    ) throws Exception {
        ActiveUser activeUser = (ActiveUser) session.getAttribute(Config.ACTIVEUSER_KEY);
        String sysid = activeUser.getSysid();
        String groupid = activeUser.getGroupid();
        int count = yybusinessService.findYyBusinessGroupByYpxxCount(year, sysid, groupid, yybusinessQueryVo);
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageParams(count, rows, page);
        List<YybusinessCustom> list = yybusinessService.findYyBusinessGroupByYpxxList(year, sysid, groupid, yybusinessQueryVo);
        DataGridResultInfo dataGridResultInfo = new DataGridResultInfo();
        dataGridResultInfo.setTotal(count);
        dataGridResultInfo.setRows(list);
        return dataGridResultInfo;
    }

    /**
     * 跳转按地区统计页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/groupbyarea")
    public String groupbyarea(Model model) throws Exception {
        String year = MyUtil.get_YYYY(MyUtil.getDate());
        model.addAttribute("year", year);
        return "business/statistics/groupbyarea";
    }


    @RequestMapping("/groupbyareaResult")
    @ResponseBody
    public List<YybusinessCustom> groupbyareaResult(
            String year,
            HttpSession session,
            YybusinessQueryVo yybusinessQueryVo
    ) throws Exception {
        ActiveUser activeUser = (ActiveUser) session.getAttribute(Config.ACTIVEUSER_KEY);
        String sysid = activeUser.getSysid();
        String groupid = activeUser.getGroupid();
        if (year == null)
            year = MyUtil.get_YYYY(MyUtil.getDate());
        return yybusinessService.findYyBusinessGroupByAreaList(year, sysid, groupid, yybusinessQueryVo);
    }
}
