package yycg.base.pojo.vo;

import yycg.base.pojo.po.Sysuser;

/**
 * @ClassName SysuserCustom
 * @Description: 自定义用户扩展类
 * @Author echo
 * @Date 2019/07/01
 * @Version V1.0
 **/
public class SysuserCustom extends Sysuser {
    private String sysusermc; // 所属单位名称
    private String groupname; // 用户类型名称

    public String getSysusermc() {
        return sysusermc;
    }

    public void setSysusermc(String sysusermc) {
        this.sysusermc = sysusermc;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }
}
