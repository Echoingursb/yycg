package yycg.base.dao.mapper;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import yycg.base.pojo.po.Sysuser;
import yycg.base.pojo.po.SysuserExample;
import yycg.util.UUIDBuild;

import java.util.Calendar;
import java.util.List;

public class SysuserMapperTest {
    private SysuserMapper sysuserMapper;

    @Before
    public void setUp() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml",
                "classpath:spring/applicationContext-base-dao.xml");
        sysuserMapper = applicationContext.getBean(SysuserMapper.class);
    }

    @Test
    public void selectByPrimaryKey() {
        Sysuser sysuser = sysuserMapper.selectByPrimaryKey("001");
        System.out.println(sysuser);
    }

    @Test
    public void deleteByPrimaryKey() {

    }

    @Test
    public void insert() {
        Sysuser sysuser = new Sysuser();
        Calendar calendar = Calendar.getInstance();
        // 2014-04-15 19:04:14.000000
        calendar.set(2014, Calendar.APRIL, 15, 20, 5, 14);
        sysuser.setId(UUIDBuild.getUUID());
        sysuser.setUserid("test002");
        sysuser.setUsername("测试002");
        sysuser.setGroupid("3");
        sysuser.setPwd("96e79218965eb72c92a549dd5a330112");
        sysuser.setUserstate("1");
        sysuser.setCreatetime(calendar.getTime());
        sysuser.setSysid("am017c53-067e-11e3-8a3c-0019d2ce5120");
        int rows = sysuserMapper.insert(sysuser);
        System.out.println(rows);
    }

    /**
     * 自定义查询条件查询
     */
    @Test
    public void selectByExample() {
        SysuserExample sysuserExample = new SysuserExample();
        SysuserExample.Criteria criteria = sysuserExample.createCriteria();
        criteria.andUsernameEqualTo("测试alpha");
        criteria.andGroupidEqualTo("3");
        List<Sysuser> sysuserList = sysuserMapper.selectByExample(sysuserExample);
        System.out.println(sysuserList);
    }

    @Test
    public void updateByPrimaryKeySelective() {
        // sysuserMapper.updateByPrimaryKeySelective();
    }

    @Test
    public void updateByPrimaryKey() {
        Sysuser sysuser = sysuserMapper.selectByPrimaryKey("001");
        sysuser.setId(UUIDBuild.getUUID());
        int rows = sysuserMapper.updateByPrimaryKey(sysuser);
        System.out.println(rows);
    }
}