package yycg.business.pojo.vo;

import yycg.base.pojo.po.Usergys;
import yycg.base.pojo.po.Useryy;
import yycg.base.pojo.vo.PageQuery;

/**
 * @ClassName YybusinessQueryVo
 * @Description: TODO
 * @Author echo
 * @Date 2019/10/28
 * @Version V1.0
 **/
public class YybusinessQueryVo {
    private YybusinessCustom yybusinessCustom;
    private YpxxCustom ypxxCustom;
    private YycgdCustom yycgdCustom;
    private YycgdmxCustom yycgdmxCustom;
    private Useryy useryy;
    private Usergys usergys;
    private String businessyear;
    private PageQuery pageQuery;

    public YybusinessCustom getYybusinessCustom() {
        return yybusinessCustom;
    }

    public void setYybusinessCustom(YybusinessCustom yybusinessCustom) {
        this.yybusinessCustom = yybusinessCustom;
    }

    public Useryy getUseryy() {
        return useryy;
    }

    public void setUseryy(Useryy useryy) {
        this.useryy = useryy;
    }

    public Usergys getUsergys() {
        return usergys;
    }

    public void setUsergys(Usergys usergys) {
        this.usergys = usergys;
    }

    public String getBusinessyear() {
        return businessyear;
    }

    public void setBusinessyear(String businessyear) {
        this.businessyear = businessyear;
    }

    public YpxxCustom getYpxxCustom() {
        return ypxxCustom;
    }

    public void setYpxxCustom(YpxxCustom ypxxCustom) {
        this.ypxxCustom = ypxxCustom;
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

    public PageQuery getPageQuery() {
        return pageQuery;
    }

    public void setPageQuery(PageQuery pageQuery) {
        this.pageQuery = pageQuery;
    }
}
