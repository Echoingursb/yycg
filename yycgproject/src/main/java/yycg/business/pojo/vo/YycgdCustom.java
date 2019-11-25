package yycg.business.pojo.vo;

import yycg.business.pojo.po.Yycgd;

import java.util.Date;

/**
 * @ClassName YycgdCustom
 * @Description: 医院采购单扩展类
 * @Author echo
 * @Date 2019/10/18
 * @Version V1.0
 **/
public class YycgdCustom extends Yycgd {
    private String yycgdztmc; // 采购单状态名称
    private String useryyid;
    private String useryymc;
    private Date cjtime_start;
    private Date cjtime_end;

    public String getUseryymc() {
        return useryymc;
    }

    public void setUseryymc(String useryymc) {
        this.useryymc = useryymc;
    }

    public String getYycgdztmc() {
        return yycgdztmc;
    }

    public void setYycgdztmc(String yycgdztmc) {
        this.yycgdztmc = yycgdztmc;
    }

    public Date getCjtime_start() {
        return cjtime_start;
    }

    public void setCjtime_start(Date cjtime_start) {
        this.cjtime_start = cjtime_start;
    }

    public Date getCjtime_end() {
        return cjtime_end;
    }

    public void setCjtime_end(Date cjtime_end) {
        this.cjtime_end = cjtime_end;
    }

    @Override
    public String getUseryyid() {
        return useryyid;
    }

    @Override
    public void setUseryyid(String useryyid) {
        this.useryyid = useryyid;
    }
}
