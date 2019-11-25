package yycg.business.pojo.vo;

import yycg.base.pojo.vo.PageQuery;

import java.util.List;

/**
 * @ClassName GysypmlQueryVo
 * @Description: TODO
 * @Author echo
 * @Date 2019/10/14
 * @Version V1.0
 **/
public class GysypmlQueryVo {
    private GysypmlCustom gysypmlCustom;
    private GysypmlControlCustom gysypmlControlCustom;
    private PageQuery pageQuery;
    private YpxxCustom ypxxCustom;
    private List<YpxxCustom> ypxxCustoms; // 页面批量添加数据
    private List<GysypmlCustom> gysypmlCustoms; // 页面批量提交数据
    private List<GysypmlControlCustom> gysypmlControlCustoms; // 页面批量提交数据


    public GysypmlCustom getGysypmlCustom() {
        return gysypmlCustom;
    }

    public void setGysypmlCustom(GysypmlCustom gysypmlCustom) {
        this.gysypmlCustom = gysypmlCustom;
    }

    public GysypmlControlCustom getGysypmlControlCustom() {
        return gysypmlControlCustom;
    }

    public void setGysypmlControlCustom(GysypmlControlCustom gysypmlControlCustom) {
        this.gysypmlControlCustom = gysypmlControlCustom;
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

    public List<YpxxCustom> getYpxxCustoms() {
        return ypxxCustoms;
    }

    public void setYpxxCustoms(List<YpxxCustom> ypxxCustoms) {
        this.ypxxCustoms = ypxxCustoms;
    }

    public List<GysypmlCustom> getGysypmlCustoms() {
        return gysypmlCustoms;
    }

    public void setGysypmlCustoms(List<GysypmlCustom> gysypmlCustoms) {
        this.gysypmlCustoms = gysypmlCustoms;
    }

    public List<GysypmlControlCustom> getGysypmlControlCustoms() {
        return gysypmlControlCustoms;
    }

    public void setGysypmlControlCustoms(List<GysypmlControlCustom> gysypmlControlCustoms) {
        this.gysypmlControlCustoms = gysypmlControlCustoms;
    }
}
