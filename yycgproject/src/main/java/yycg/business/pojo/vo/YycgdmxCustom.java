package yycg.business.pojo.vo;

import java.util.Date;

/**
 * @ClassName YycgdmxCustom
 * @Description: 医院采购单明细扩展类
 * @Author echo
 * @Date 2019/10/19
 * @Version V1.0
 **/
public class YycgdmxCustom extends YpxxCustom {
    private String gysypmlid;
    private String usergysid;
    private String ypxxid;
    private String usergysmc;
    private String yycgdmxid;
    private String control;
    private String controlmc;
    private String yycgdid;
    private String yycgdbm;
    private String yycgdmc;
    private Date cjtime;
    private String useryyid;
    private String useryymc;
    private Float jyjg;
    private Integer cgl;
    private Float cgje;
    private String cgzt;
    private String cgztmc;
    private String mc;


    public String getYpxxid() {
        return ypxxid;
    }

    public void setYpxxid(String ypxxid) {
        this.ypxxid = ypxxid;
    }

    public String getGysypmlid() {
        return gysypmlid;
    }

    public void setGysypmlid(String gysypmlid) {
        this.gysypmlid = gysypmlid;
    }

    public String getUsergysmc() {
        return usergysmc;
    }

    public void setUsergysmc(String usergysmc) {
        this.usergysmc = usergysmc;
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

    public String getControlmc() {
        return controlmc;
    }

    public void setControlmc(String controlmc) {
        this.controlmc = controlmc;
    }

    public String getYycgdmxid() {
        return yycgdmxid;
    }

    public void setYycgdmxid(String yycgdmxid) {
        this.yycgdmxid = yycgdmxid;
    }

    public String getYycgdid() {
        return yycgdid;
    }

    public void setYycgdid(String yycgdid) {
        this.yycgdid = yycgdid;
    }

    public String getUseryyid() {
        return useryyid;
    }

    public void setUseryyid(String useryyid) {
        this.useryyid = useryyid;
    }

    public String getUseryymc() {
        return useryymc;
    }

    public void setUseryymc(String useryymc) {
        this.useryymc = useryymc;
    }

    public Float getJyjg() {
        return jyjg;
    }

    public void setJyjg(Float jyjg) {
        this.jyjg = jyjg;
    }

    public Integer getCgl() {
        return cgl;
    }

    public void setCgl(Integer cgl) {
        this.cgl = cgl;
    }

    public Float getCgje() {
        return cgje;
    }

    public void setCgje(Float cgje) {
        this.cgje = cgje;
    }

    public String getCgzt() {
        return cgzt;
    }

    public void setCgzt(String cgzt) {
        this.cgzt = cgzt;
    }

    public String getCgztmc() {
        return cgztmc;
    }

    public void setCgztmc(String cgztmc) {
        this.cgztmc = cgztmc;
    }

    public String getUsergysid() {
        return usergysid;
    }

    public void setUsergysid(String usergysid) {
        this.usergysid = usergysid;
    }

    @Override
    public String getMc() {
        return mc;
    }

    @Override
    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getYycgdmc() {
        return yycgdmc;
    }

    public void setYycgdmc(String yycgdmc) {
        this.yycgdmc = yycgdmc;
    }

        public Date getCjtime() {
        return cjtime;
    }

    public void setCjtime(Date cjtime) {
        this.cjtime = cjtime;
    }

    public String getYycgdbm() {
        return yycgdbm;
    }

    public void setYycgdbm(String yycgdbm) {
        this.yycgdbm = yycgdbm;
    }
}
