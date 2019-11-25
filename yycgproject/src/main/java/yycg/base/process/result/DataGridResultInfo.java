package yycg.base.process.result;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName DataGridResultInfo
 * @Description: 数据查询列表结果
 * @Author echo
 * @Date 2019/07/02
 * @Version V1.0
 **/
public class DataGridResultInfo {
    private ResultInfo resultInfo; // 操作结果信息
    private int total; // 总条数
    private List rows = new ArrayList(); // 结果集
    private List footer = new ArrayList(); // 总计告诉footer

    public DataGridResultInfo() {
    }

    public DataGridResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    public List getFooter() {
        return footer;
    }

    public void setFooter(List footer) {
        this.footer = footer;
    }
}
