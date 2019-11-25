package yycg.base.pojo.vo;

/**
 * @ClassName SysuserQueryVo
 * @Description: 查询用户包装类
 * @Author Administrator
 * @Date 2019/7/1
 * @Version V1.0
 **/
public class SysuserQueryVo {
    private SysuserCustom sysuserCustom; // 自定义用户扩展
    private PageQuery pageQuery; //  分页查询参数

    public SysuserCustom getSysuserCustom() {
        return sysuserCustom;
    }

    public void setSysuserCustom(SysuserCustom sysuserCustom) {
        this.sysuserCustom = sysuserCustom;
    }

    public PageQuery getPageQuery() {
        return pageQuery;
    }

    public void setPageQuery(PageQuery pageQuery) {
        this.pageQuery = pageQuery;
    }
}
