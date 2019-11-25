package yycg.business.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import yycg.base.process.context.Config;
import yycg.base.process.result.ResultUtil;
import yycg.base.process.result.SubmitResultInfo;
import yycg.base.service.SystemConfigService;
import yycg.business.pojo.vo.YpxxCustom;
import yycg.business.pojo.vo.YpxxQueryVo;
import yycg.business.service.YpxxService;
import yycg.util.ExcelExportSXSSF;
import yycg.util.HxlsOptRowsInterface;
import yycg.util.HxlsRead;
import yycg.util.UUIDBuild;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName YpxxAction
 * @Description: 药品信息
 * @Author echo
 * @Date 2019/10/10
 * @Version V1.0
 **/
@Controller
@RequestMapping("/ypml")
public class YpxxAction {
    @Autowired
    private YpxxService ypxxService;
    @Autowired
    private SystemConfigService systemConfigService;
    @Autowired
    private HxlsOptRowsInterface ypxxImportService;

    /**
     * 跳转药品目录导出页面
     *
     * @param model
     * @return 药品目录导出页面
     * @throws Exception 跳转药品目录导出页面失败抛出异常
     */
    @RequestMapping("/exportypxx")
    public String exportypxx(Model model) throws Exception {
        List yplblist = systemConfigService.findDictinfoByType("001");
        List jyztlist = systemConfigService.findDictinfoByType("003");
        model.addAttribute("yplblist", yplblist);
        model.addAttribute("jyztlist", jyztlist);
        return "business/ypml/exportypxx";
    }

    /**
     * 提交药品目录导出提交
     *
     * @param ypxxQueryVo
     * @return
     * @throws Exception
     */
    @RequestMapping("/exportYpxxSubmit")
    @ResponseBody
    public SubmitResultInfo exportYpxxSubmit(YpxxQueryVo ypxxQueryVo) throws Exception {
        //导出文件存放的路径，并且是虚拟目录指向的路径
        String filePath = systemConfigService.findBasicinfoById("00301").getValue();
        String fileWebPath = systemConfigService.findBasicinfoById("00302").getValue();
        //导出文件的前缀
        String filePrefix = "ypxx";
        //-1表示关闭自动刷新，手动控制写磁盘的时机，其它数据表示多少数据在内存保存，超过的则写入磁盘
        int flushRows = 100;
        //指导导出数据的title
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("流水号"); // bm
        fieldNames.add("通用名"); // mc
        fieldNames.add("剂型"); // jx
        fieldNames.add("规格"); // gg
        fieldNames.add("转换系数"); // zhxs
        fieldNames.add("生产企业"); // scqymc
        fieldNames.add("商品名称"); // spmc
        fieldNames.add("中标价"); // zbjg
        fieldNames.add("交易状态"); // jyztmc
        //告诉导出类数据list中对象的属性，让ExcelExportSXXSSF通过反射获取对象的值
        List<String> fieldCodes = new ArrayList<String>();
        fieldCodes.add("bm");// 流水号
        fieldCodes.add("mc");// 通用名
        fieldCodes.add("jx");// 剂型
        fieldCodes.add("gg");// 规格
        fieldCodes.add("zhxs");// 转换系数
        fieldCodes.add("scqymc");// 生产企业
        fieldCodes.add("spmc");// 商品名称
        fieldCodes.add("zbjg");// 中标价
        fieldCodes.add("jyztmc");// 交易状态
        //注意：fieldCodes和fieldNames个数必须相同且属性和title顺序一一对应，这样title和内容才一一对应
        //开始导出，执行一些workbook及sheet等对象的初始创建
        ExcelExportSXSSF excelExportSXSSF = ExcelExportSXSSF.start(filePath, fileWebPath, filePrefix, fieldNames, fieldCodes, flushRows);
        //准备导出的数据，将数据存入list，且list中对象的字段名称必须是刚才传入ExcelExportSXXSSF的名称
        List<YpxxCustom> list = ypxxService.findYpxxList(ypxxQueryVo);
        //执行导出
        excelExportSXSSF.writeDatasByObject(list);
        //输出文件，返回下载文件的http地址
        String webpath = excelExportSXSSF.exportFile();
        System.out.println(webpath);
        return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 313, new Object[]{
                list.size(), webpath
        }));
    }

    /**
     * 跳转药品目录导入页面
     *
     * @param model
     * @return 药品目录导入页面
     * @throws Exception 跳转药品目录导入页面失败抛出异常
     */
    @RequestMapping("/importypxx")
    public String importypxx(Model model) throws Exception {
        return "business/ypml/importypxx";
    }

    /**
     * 药品目录导入提交
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/importypxxsubmit")
    @ResponseBody
    public SubmitResultInfo importypxxsubmit(MultipartFile ypxximportfile) throws Exception {
        if (ypxximportfile == null) {
            return ResultUtil.createSubmitResult(ResultUtil.createFail(Config.MESSAGE, 319, null));
        }
        String originalFilename = ypxximportfile.getOriginalFilename();
        long optRows_success;
        long optRows_failure;
        String uploadPath = "E:\\SourceCode\\IdeaProjects\\yycg\\yycgproject\\src\\main\\webapp\\upload";
        String fileName = UUIDBuild.getUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
        File file = new File(uploadPath + File.separator + fileName);
        boolean b = true;
        if (!file.exists()) {
            b = file.mkdirs();
        }
        if (b) {
            ypxximportfile.transferTo(file);
        }
        HxlsRead xls2csv = null;
        try {
            //第一个参数就是导入的文件
            //第二个参数就是导入文件中哪个sheet
            //第三个参数导入接口的实现类对象
            xls2csv = new HxlsRead(file.getAbsolutePath(), 1, ypxxImportService);
            xls2csv.process();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        assert xls2csv != null;
        optRows_success = xls2csv.getOptRows_success();
        optRows_failure = xls2csv.getOptRows_failure();
        // 将错误记录解析生成xlsx
        List<List<String>> failrows = xls2csv.getFailrows();
        List<String> failmsgs = xls2csv.getFailmsgs();
        // List<List<String>> failmsgs = xls2csv.getFailmsgs();
        List<YpxxCustom> list = new ArrayList<YpxxCustom>();
        for (int i = 0; i < failrows.size(); i++) {
            List<String> s = failrows.get(i);
            YpxxCustom ypxxCustom = new YpxxCustom();
            for (int j = 0; j < s.size(); j++) {
                switch (j) {
                    case 0:
                        ypxxCustom.setMc(s.get(j));
                        break;
                    case 1:
                        ypxxCustom.setJx(s.get(j));
                        break;
                    case 2:
                        ypxxCustom.setGg(s.get(j));
                        break;
                    case 3:
                        ypxxCustom.setZhxs(s.get(j));
                        break;
                    case 4:
                        ypxxCustom.setZbjg(Float.parseFloat(s.get(j)));
                        break;
                    case 5:
                        ypxxCustom.setScqymc(s.get(j));
                        break;
                    case 6:
                        ypxxCustom.setSpmc(s.get(j));
                        break;
                    case 7:
                        ypxxCustom.setJyztmc(s.get(j));
                        break;
                }
            }
            list.add(ypxxCustom);
        }
        List<YpxxCustom> list1 = new ArrayList<YpxxCustom>();
        for (int i = 0; i < list.size(); i++) {
            YpxxCustom ypxxCustom = list.get(i);
            for (int j = 0; j < failmsgs.size(); j++) {
                String s = failmsgs.get(j);
                ypxxCustom.setCwyy(s);

                // List<String> list1 = failmsgs.get(j);
                // ypxxCustom.setCwyy(list1);
            }
            list1.add(ypxxCustom);
        }

        List<String> rowtitles = xls2csv.getRowtitle();
        rowtitles.add("错误原因");
        List<String> fieldCodes = new ArrayList<String>();
        fieldCodes.add("mc");// 通用名
        fieldCodes.add("jx");// 剂型
        fieldCodes.add("gg");// 规格
        fieldCodes.add("zhxs");// 转换系数
        fieldCodes.add("zbjg");// 中标价
        fieldCodes.add("scqymc");// 生产企业
        fieldCodes.add("spmc");// 商品名称
        fieldCodes.add("jyztmc");// 交易状态
        fieldCodes.add("cwyy"); // 错误原因
        String filePath = systemConfigService.findBasicinfoById("00303").getValue();
        String fileWebPath = systemConfigService.findBasicinfoById("00304").getValue();
        ExcelExportSXSSF excelExportSXSSF = ExcelExportSXSSF.start(filePath, fileWebPath, "error", rowtitles, fieldCodes, 100);
        excelExportSXSSF.writeDatasByObject(list);
        String webpath = excelExportSXSSF.exportFile();
        System.out.println(webpath);
        int messageCode = 315;
        // 如果没有错误，使用信息代码314(没有错误下载链接)
        if (optRows_failure == 0) {
            messageCode = 314;
        }
        return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, messageCode, new Object[]{
                optRows_success, optRows_failure, webpath
        }));
    }

}
