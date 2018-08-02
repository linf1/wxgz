package com.zw.web.base.aop;

import com.base.util.TraceLoggerUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * <strong>Title : 辅助进件提交接口<br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年02月16日<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) Vbill Co.,Ltd.<br>
 * </strong>
 * <p>
 *
 * @author department:技术开发部 <br>
 *         username:zh-pc <br>
 *         email: <br>
 * @version <strong>zw有限公司-运营平台</strong><br>
 *          <br>
 *          <strong>修改历史:</strong><br>
 *          修改人 修改日期 修改描述<br>
 *          -------------------------------------------<br>
 *          <br>
 *          <br>
 */
@Component
@Aspect
public class ControllerAdvice {

    @Pointcut("execution(public * com.zw.*.controller..*(..))")
    private void anyMethod() {
    }

    @Before(value = "anyMethod()")
    public void before(JoinPoint jp) {
        String msg = "";
        String clazzName = jp.getTarget().getClass().getName();
        String methodName = jp.getSignature().getName();

        msg += "开始执行:" + clazzName + "." + methodName;
        TraceLoggerUtil.info(msg);
    }

    @AfterReturning(pointcut = "anyMethod()", returning = "result")
    public void after(JoinPoint jp, Object result) {
        String msg = "";
        String clazzName = jp.getTarget().getClass().getName();
        String methodName = jp.getSignature().getName();
        msg += "执行结束:" + clazzName + "." + methodName;
        TraceLoggerUtil.info(msg);
    }
}
