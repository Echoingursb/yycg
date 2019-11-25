package yycg.base.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FirstAction {
    /**
     * 跳转首页
     *
     * @return 首页
     * @throws Exception 跳转首页失败
     */
    @RequestMapping("/first")
    public String first() throws Exception {
        return "base/first";
    }

    /**
     * 跳转欢迎页
     *
     * @return 欢迎页
     * @throws Exception 跳转欢迎页失败
     */
    @RequestMapping("/welcome")
    public String welcome() throws Exception {
        return "base/welcome";
    }
}
