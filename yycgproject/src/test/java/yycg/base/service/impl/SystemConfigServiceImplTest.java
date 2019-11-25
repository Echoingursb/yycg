package yycg.base.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import yycg.base.pojo.po.Basicinfo;
import yycg.base.service.SystemConfigService;

import java.util.List;

public class SystemConfigServiceImplTest {
    private SystemConfigService systemConfigService;
    @Before
    public void setUp() throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "classpath:/spring/applicationContext.xml",
                "classpath:/spring/applicationContext-base-service.xml");
        systemConfigService = applicationContext.getBean(SystemConfigServiceImpl.class);
    }

    @Test
    public void findBasicinfoById() throws Exception{
        Basicinfo basicinfo = systemConfigService.findBasicinfoById("00301");
        System.out.println(systemConfigService);
    }

    @Test
    public void findDictinfoByType() throws Exception{
        List list = systemConfigService.findDictinfoByType("001");
    }

    @Test
    public void test() {
        float a = 4.7f;
        int b = 100;
        System.out.println(Math.round(a * b));

    }
}