package com.zw.web.base.interceptor;

import com.base.util.TraceLoggerUtil;
import com.zw.web.base.vo.ResultVO;
import com.zw.web.base.vo.VOConst;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class AppExceptionHandler implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response, Object object, Exception exception) {
        TraceLoggerUtil.error("控制器层出现异常", exception);
       /* if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request
                .getHeader("X-Requested-With") != null && request
                .getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
            return new ModelAndView("comm/error");
        } else {// JSON格式返回*/
            try (PrintWriter writer = response.getWriter()) {
                response.setHeader("Content-type", "text/html;charset=UTF-8");
                response.setCharacterEncoding("UTF-8");
                // 返回客户端系统异常
                ResultVO resultVO = new ResultVO();
                resultVO.setRetCode(VOConst.EXCEPTION);
                resultVO.setRetMsg("系统或网络异常,请稍候重试！");
                writer.write(resultVO.toString());
                writer.flush();
            } catch (IOException e) {
                TraceLoggerUtil.error("", e);
            }
            return null;

//        }
    }
}
