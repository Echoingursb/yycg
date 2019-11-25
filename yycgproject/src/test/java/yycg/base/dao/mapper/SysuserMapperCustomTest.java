package yycg.base.dao.mapper;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import yycg.base.pojo.vo.SysuserCustom;
import yycg.base.pojo.vo.SysuserQueryVo;

import java.util.List;

public class SysuserMapperCustomTest {
    private SysuserMapperCustom sysuserMapperCustom;

    @Before
    public void setUp() throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/spring/applicationContext.xml",
                "classpath:/spring/applicationContext-base-dao.xml");
        sysuserMapperCustom = applicationContext.getBean(SysuserMapperCustom.class);
    }

    /**
     * 查询用户列表
     *
     */
    @Test
    public void findSysuserList() throws Exception {
        SysuserQueryVo sysuserQueryVo = new SysuserQueryVo();
        SysuserCustom sysuserCustom = new SysuserCustom();
        // sysuserCustom.setUserid("ad");
        sysuserCustom.setUsername("高村");
        sysuserQueryVo.setSysuserCustom(sysuserCustom);
        List<SysuserCustom> sysuserList = sysuserMapperCustom.findSysuserList(sysuserQueryVo);
        System.out.println(sysuserList);
    }

    @Test
    public void findSysuserCount() throws Exception {
        SysuserQueryVo sysuserQueryVo = new SysuserQueryVo();
        int count = sysuserMapperCustom.findSysuserCount(sysuserQueryVo);
        System.out.println(count);
    }
}