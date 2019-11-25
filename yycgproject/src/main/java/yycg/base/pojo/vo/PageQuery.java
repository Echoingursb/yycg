package yycg.base.pojo.vo;

/**
 * @ClassName PageQuery
 * @Description: 分页查询参数类
 * @Author echo
 * @Date 2019/07/04
 * @Version V1.0
 **/
public class PageQuery {
    private int pageQuery_currPage; // 当前页码
    private int pageQuery_size; // 总页数
    private int pageQuery_count; // 总记录数
    private int pageQuery_pageSize; // 每页显示个数
    private int pageQuery_start; // 开始坐标
    private int pageQuery_end; // 结束坐标

    /**
     * 计算当前页码pageQuery_currPage，开始坐标pageQuery_start，结束坐标pageQuery_end，总页数pageQuery_size
     *
     * @param count    总记录数
     * @param pageSize 每页显示个数
     * @param currPage 当前页码
     */
    public void setPageParams(int count, int pageSize, int currPage) {
        this.pageQuery_count = count;
        this.pageQuery_pageSize = pageSize;
        this.pageQuery_currPage = currPage;
        float size = count / (float) this.pageQuery_pageSize; // 总页数
        if (pageQuery_currPage < 2) {
            pageQuery_currPage = 1;
        } else if (pageQuery_currPage > size) {
            if (size == 0) {
                pageQuery_currPage = 1;
            } else {
                pageQuery_currPage = (int) Math.ceil(size);
            }
            pageQuery_start = (pageQuery_currPage - 1) * this.pageQuery_pageSize + 1;
        } else {
            pageQuery_start = (pageQuery_currPage - 1) * this.pageQuery_pageSize + 1;
        }
        pageQuery_size = (int) Math.ceil(size);
        pageQuery_end = pageQuery_currPage * this.pageQuery_pageSize;
    }

    public int getPageQuery_currPage() {
        return pageQuery_currPage;
    }

    public void setPageQuery_currPage(int pageQuery_currPage) {
        this.pageQuery_currPage = pageQuery_currPage;
    }

    public int getPageQuery_size() {
        return pageQuery_size;
    }

    public void setPageQuery_size(int pageQuery_size) {
        this.pageQuery_size = pageQuery_size;
    }

    public int getPageQuery_count() {
        return pageQuery_count;
    }

    public void setPageQuery_count(int pageQuery_count) {
        this.pageQuery_count = pageQuery_count;
    }

    public int getPageQuery_pageSize() {
        return pageQuery_pageSize;
    }

    public void setPageQuery_pageSize(int pageQuery_pageSize) {
        this.pageQuery_pageSize = pageQuery_pageSize;
    }

    public int getPageQuery_start() {
        return pageQuery_start;
    }

    public void setPageQuery_start(int pageQuery_start) {
        this.pageQuery_start = pageQuery_start;
    }

    public int getPageQuery_end() {
        return pageQuery_end;
    }

    public void setPageQuery_end(int pageQuery_end) {
        this.pageQuery_end = pageQuery_end;
    }
}
