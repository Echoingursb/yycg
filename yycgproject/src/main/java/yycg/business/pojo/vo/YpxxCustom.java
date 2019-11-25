package yycg.business.pojo.vo;

import yycg.business.pojo.po.Ypxx;

import java.util.List;

/**
 * @ClassName YpxxCustom
 * @Description: 自定义药品信息扩展类
 * @Author echo
 * @Date 2019/10/10
 * @Version V1.0
 **/
public class YpxxCustom extends Ypxx {
    private String jyztmc; // 交易状态名称
    private String cwyy; // 错误原因
    private String zbjglower; // 低中标价格
    private String zbjgupper; // 高中标价格
    // private List<String> cwyy; // 错误原因

    public String getJyztmc() {
        return jyztmc;
    }

    public void setJyztmc(String jyztmc) {
        this.jyztmc = jyztmc;
    }

    public String getCwyy() {
        return cwyy;
    }

    public void setCwyy(String cwyy) {
        this.cwyy = cwyy;
    }


    // public List<String> getCwyy() {
    //     return cwyy;
    // }
    //
    // public void setCwyy(List<String> cwyy) {
    //     this.cwyy = cwyy;
    // }


    public String getZbjglower() {
        return zbjglower;
    }

    public void setZbjglower(String zbjglower) {
        this.zbjglower = zbjglower;
    }

    public String getZbjgupper() {
        return zbjgupper;
    }

    public void setZbjgupper(String zbjgupper) {
        this.zbjgupper = zbjgupper;
    }
}
