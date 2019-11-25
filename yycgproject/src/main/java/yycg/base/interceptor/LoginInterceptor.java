package yycg.base.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import yycg.base.pojo.vo.ActiveUser;
import yycg.base.process.context.Config;
import yycg.base.process.result.ResultUtil;
import yycg.util.ResourcesUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @ClassName LoginInterceptor
 * @Author echo
 * @Date 2019/10/09
 * @Version V1.0
 **/
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        ActiveUser activeUser = (ActiveUser) session.getAttribute(Config.ACTIVEUSER_KEY);
        if (activeUser != null)
            return true;
        List<String> openUris = ResourcesUtil.getkeyList(Config.ANONYMOUS_ACTIONS);
        String uri = request.getRequestURI();
        if (uri.contains("first.action")) {
            request.getRequestDispatcher("/WEB-INF/jsp/base/login.jsp").forward(request, response);
        }
        if (uri.contains("logout.action")) {
            response.sendRedirect(request.getContextPath() + "/login.action");
            return false;
        }
        for (String openUri : openUris) {
            if (uri.contains(openUri))
                return true;
        }
        // request.getRequestDispatcher("/WEB-INF/jsp/base/login.jsp").forward(request, response);
        ResultUtil.throwExcepion(ResultUtil.createWarning(Config.MESSAGE, 106, new Object[]{
                request.getContextPath() + "/login.action"
        }));
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
