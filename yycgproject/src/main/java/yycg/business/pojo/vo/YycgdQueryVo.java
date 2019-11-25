package yycg.business.pojo.vo;

import yycg.base.pojo.po.Useryy;
import yycg.base.pojo.vo.PageQuery;

import java.util.List;

/**
 * @ClassName YycgdQueryVo
 * @Description: 医院采购单查询类
 * @Author echo
 * @Date 2019/10/18
 * @Version V1.0
 **/
public class YycgdQueryVo {
    private YycgdrkCustom yycgdrkCustom;
    private YycgdCustom yycgdCustom;
    private YycgdmxCustom yycgdmxCustom;
    private String businessyear;
    private YpxxCustom ypxxCustom;
    private GysypmlCustom gysypmlCustom;
    private Useryy useryy;
    private List<YycgdmxCustom> yycgdmxCustoms;
    private List<YycgdCustom> yycgdCustoms;
    private List<YycgdrkCustom> yycgdrkCustoms;
    private PageQuery pageQuery;

    public List<YycgdmxCustom> getYycgdmxCustoms() {
        return yycgdmxCustoms;
    }

    public void setYycgdmxCustoms(List<YycgdmxCustom> yycgdmxCustoms) {
        this.yycgdmxCustoms = yycgdmxCustoms;
    }

    public YycgdCustom getYycgdCustom() {
        return yycgdCustom;
    }

    public void setYycgdCustom(YycgdCustom yycgdCustom) {
        this.yycgdCustom = yycgdCustom;
    }

    public YycgdmxCustom getYycgdmxCustom() {
        return yycgdmxCustom;
    }

    public void setYycgdmxCustom(YycgdmxCustom yycgdmxCustom) {
        this.yycgdmxCustom = yycgdmxCustom;
    }

    public String getBusinessyear() {
        return businessyear;
    }

    public void setBusinessyear(String businessyear) {
        this.businessyear = businessyear;
    }

    public PageQuery getPageQuery() {
        return pageQuery;
    }

    public void setPageQuery(PageQuery pageQuery) {
        this.pageQuery = pageQuery;
    }

    public YpxxCustom getYpxxCustom() {
        return ypxxCustom;
    }

    public void setYpxxCustom(YpxxCustom ypxxCustom) {
        this.ypxxCustom = ypxxCustom;
    }

    public Useryy getUseryy() {
        return useryy;
    }

    public void setUseryy(Useryy useryy) {
        this.useryy = useryy;
    }

    public GysypmlCustom getGysypmlCustom() {
        return gysypmlCustom;
    }

    public void setGysypmlCustom(GysypmlCustom gysypmlCustom) {
        this.gysypmlCustom = gysypmlCustom;
    }

    public List<YycgdCustom> getYycgdCustoms() {
        return yycgdCustoms;
    }

    public void setYycgdCustoms(List<YycgdCustom> yycgdCustoms) {
        this.yycgdCustoms = yycgdCustoms;
    }

    public YycgdrkCustom getYycgdrkCustom() {
        return yycgdrkCustom;
    }

    public void setYycgdrkCustom(YycgdrkCustom yycgdrkCustom) {
        this.yycgdrkCustom = yycgdrkCustom;
    }

    public List<YycgdrkCustom> getYycgdrkCustoms() {
        return yycgdrkCustoms;
    }

    public void setYycgdrkCustoms(List<YycgdrkCustom> yycgdrkCustoms) {
        this.yycgdrkCustoms = yycgdrkCustoms;
    }
}
