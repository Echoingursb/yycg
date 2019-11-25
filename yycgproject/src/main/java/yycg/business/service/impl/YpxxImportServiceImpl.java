package yycg.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import yycg.business.dao.mapper.YpxxMapper;
import yycg.business.pojo.po.Ypxx;
import yycg.business.pojo.po.YpxxExample;
import yycg.util.HxlsOptRowsInterface;
import yycg.util.UUIDBuild;

import java.util.List;

/**
 * @ClassName YpxxImportServiceImpl
 * @Description: 药品信息导入处理
 * @Author echo
 * @Date 2019/10/12
 * @Version V1.0
 **/
public class YpxxImportServiceImpl implements HxlsOptRowsInterface {
    @Autowired
    private YpxxMapper ypxxMapper;

    @Override
    public String optRows(int sheetIndex, int curRow, List<String> rowlist) throws Exception {
        try {
            String mc = rowlist.get(0); // 通用名
            String jx = rowlist.get(1); // 剂型
            String gg = rowlist.get(2); // 规格
            String zhxs = rowlist.get(3); // 转换系数
            String zbjg = rowlist.get(4); // 中标价
            String scqymc = rowlist.get(5); // 生产企业名称
            String spmc = rowlist.get(6); // 商品名
            String jyzt = rowlist.get(7); // 药品交易状态
            // 药品通用名、剂型、规格、转换系数、药品商品名、药品生产企业唯一约束
            YpxxExample ypxxExample = new YpxxExample();
            YpxxExample.Criteria criteria = ypxxExample.createCriteria();
            criteria.andMcEqualTo(mc);
            criteria.andJxEqualTo(jx);
            criteria.andGgEqualTo(gg);
            criteria.andZhxsEqualTo(zhxs);
            criteria.andSpmcEqualTo(spmc);
            criteria.andScqymcEqualTo(scqymc);
            List<Ypxx> ypxxList = ypxxMapper.selectByExample(ypxxExample);
            if (ypxxList.size() > 0)
                return "药品信息存在重复记录";
            if (jyzt == null || (!jyzt.equals("1") && !jyzt.equals("2"))) {
                return "交易状态输入不对，请输入1或2(1：正常,2：暂停)";
            }
            Ypxx ypxx = new Ypxx();
            ypxx.setId(UUIDBuild.getUUID());
            ypxx.setMc(mc);
            ypxx.setJx(jx);
            ypxx.setGg(gg);
            ypxx.setZhxs(zhxs);
            ypxx.setZbjg(Float.parseFloat(zbjg));
            ypxx.setScqymc(scqymc);
            ypxx.setSpmc(spmc);
            ypxx.setJyzt(jyzt);
            ypxxMapper.insert(ypxx);
        } catch (Exception e) {
            e.printStackTrace();
            return "导入失败";
        }
        return "success";
    }
}
