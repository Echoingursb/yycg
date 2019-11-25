package yycg.base.process.exception;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import yycg.base.process.result.ResultInfo;
import yycg.base.process.result.ResultInfoException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @ClassName CustomExceptionResolver
 * @Description: 自定义全局异常处理器
 * @Author echo
 * @Date 2019/07/07
 * @Version V1.0
 **/
public class CustomExceptionResolver implements HandlerExceptionResolver {
    //将错误信息解析成json返回到客户端
    private HttpMessageConverter<ResultInfoException> jsonMessageConverter;

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 输出异常信息
        ex.printStackTrace();
        // 转成springmvc底层对象，就是对action方法的封装对象，只有一个方法
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (method == null)
            return null;
        ResponseBody responseBody = AnnotationUtils.findAnnotation(method, ResponseBody.class);
        if (responseBody != null) {
            // 方法返回的是json
            return resolveJsonException(request, response, handler, ex);
        }
        // 方法返回的是jsp页面
        ModelAndView modelAndView = new ModelAndView();
        ResultInfoException resultInfoException = resolveCustomException(ex);
        request.setAttribute("resultInfoException", resultInfoException.getResultInfo());
        modelAndView.addObject("resultInfoException", resultInfoException.getResultInfo());
        modelAndView.setViewName("/base/error");
        return modelAndView;
    }

    /**
     * 解析异常信息
     *
     * @param ex 异常
     * @return 自定义系统异常
     */
    private ResultInfoException resolveCustomException(Exception ex) {
        ResultInfo resultInfo;
        if (ex instanceof ResultInfoException) {
            resultInfo = ((ResultInfoException) ex).getResultInfo();
        } else {
            resultInfo = new ResultInfo();
            resultInfo.setType(ResultInfo.TYPE_RESULT_FAIL);
            resultInfo.setMessage("未知错误！");
        }
        return new ResultInfoException(resultInfo);
    }

    /**
     * 将异常信息转json输出到页面
     *
     * @param request  当前http请求
     * @param response 当前http响应
     * @param handler  the executed handler, or {@code null} if none chosen at the
     *                 time of the exception (for example, if multipart resolution failed)
     * @param ex       在处理程序期间抛出的异常
     * @return 转发到对应的ModelAndView
     */
    private ModelAndView resolveJsonException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ResultInfoException resultInfoException = resolveCustomException(ex);
        HttpOutputMessage httpOutputMessage = new ServletServerHttpResponse(response);
        try {
            jsonMessageConverter.write(resultInfoException, MediaType.APPLICATION_JSON, httpOutputMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ModelAndView();
    }

    public HttpMessageConverter<ResultInfoException> getJsonMessageConverter() {
        return jsonMessageConverter;
    }

    public void setJsonMessageConverter(HttpMessageConverter<ResultInfoException> jsonMessageConverter) {
        this.jsonMessageConverter = jsonMessageConverter;
    }
}
