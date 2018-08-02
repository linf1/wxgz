package com.zw.service.aop;

import com.zw.service.exception.DAOException;
import com.zw.service.exception.ServiceException;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
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
public class ServiceAdvice {

    @Pointcut("execution(public * com.zw.*.service..*(..))")
    private void anyMethod() {
    }

    @AfterThrowing(value = "anyMethod()", throwing = "e")
    public void afterThrowing(Throwable e) throws Exception {
        if (!(e instanceof ServiceException) && !(e instanceof DAOException)) {
            throw new ServiceException(e);
        }
    }

    /*@Before(value = "anyMethod()")
    public void before(JoinPoint jp) {
        String msg = "";
        String params = "";
        String clazzName = jp.getTarget().getClass().getName();
        String methodName = jp.getSignature().getName();
        Object[] objects = jp.getArgs();
        for (int i = 0; i < objects.length; i++) {
            Object obj = objects[i];
            if (obj instanceof ServiceDTO) {
                ServiceDTO serviceDTO = (ServiceDTO) obj;
                ThreadLocalHelper.getMap().put("reqId", serviceDTO.getReqId());
                ThreadLocalHelper.getMap().put("userId", serviceDTO.getUserId());
                params += "\r\n参数" + i + ":" + JSON.toJSONString(serviceDTO.getDataBody());
            } else {
                params += "\r\n参数" + i + ":" + obj;
            }
        }
        msg += "开始执行:" + clazzName + "." + methodName;
        TraceLoggerUtil.info(msg + params);
    }

    @AfterReturning(pointcut = "anyMethod()", returning = "result")
    public void after(JoinPoint jp, Object result) {
        String msg = "";
        String clazzName = jp.getTarget().getClass().getName();
        String methodName = jp.getSignature().getName();
        msg += "执行结束:" + clazzName + "." + methodName;
        msg += "\r\n返回结果：" + JSON.toJSONString(result);
        TraceLoggerUtil.info(msg);
        ThreadLocalHelper.getMap().clear();
    }*/
}
