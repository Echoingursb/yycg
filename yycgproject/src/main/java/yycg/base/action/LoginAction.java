package yycg.base.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import yycg.base.pojo.vo.ActiveUser;
import yycg.base.process.context.Config;
import yycg.base.process.result.ResultUtil;
import yycg.base.process.result.SubmitResultInfo;
import yycg.base.service.UserService;

import javax.servlet.http.HttpSession;

/**
 * @ClassName LoginAction
 * @Description: TODO
 * @Author echo
 * @Date 2019/10/09
 * @Version V1.0
 **/
@Controller
public class LoginAction {
    @Autowired
    private UserService userService;

    /**
     * 跳转登录页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/login")
    public String login() throws Exception {
        return "base/login";
    }

    /**
     * 用户登录提交
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/loginsubmit")
    @ResponseBody
    public SubmitResultInfo loginsubmit(String userid, String pwd, String validateCode, HttpSession session) throws Exception {
        // 校验验证码
        String validateCode_session = (String) session.getAttribute("validateCode");
        if (validateCode_session != null && !validateCode_session.equalsIgnoreCase(validateCode))
            ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 113, null));
        ActiveUser activeUser = userService.checkUserInfo(userid, pwd);
        // 将用户信息写入session
        session.setAttribute(Config.ACTIVEUSER_KEY, activeUser);
        session.setMaxInactiveInterval(-1);
        return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 107, new Object[]{activeUser.getUsername()}));
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) throws Exception{
        session.invalidate();
        return "redirect:/login.action";
    }
}
