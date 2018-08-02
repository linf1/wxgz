package com.zw.service.boot;

import com.zw.service.start.IStart;
import com.zw.service.start.StartAnno;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;


/**
 * <strong>Title : ServiceBoot<br></strong>
 * <strong>Description : </strong>@启动Dubbo服务用的MainClass@<br>
 * <strong>Create on : 2016年10月27日<br></strong>
 * <p>
 * <strong>Copyright (C) zw.<br></strong>
 * <p>
 *
 * @author department:技术公司-架构设计部
 *         <br>username:zh
 *         <br> email: <br>
 * @version <strong>zw有限公司-运营平台</strong><br>
 *          <br>
 *          <strong>修改历史:</strong><br>
 *          修改人		修改日期		修改描述<br>
 *          -------------------------------------------<br>
 *          <br>
 *          <br>
 */
public class ServiceBoot {
    private static Logger log = Logger.getLogger("trace");
    private final static String SC_PATH = "classpath*:/spring/spring-context.xml";

    public static void main(String[] args) {
        try {
            log.info("系统开始启动" );
            System.out.println("系统开始启动" );
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(SC_PATH);
            context.start();
            Map<String, IStart> startBeanMap = context.getBeansOfType(IStart.class);
            for (String beanName : startBeanMap.keySet()) {
                IStart iStart = startBeanMap.get(beanName);
                StartAnno startAnno = iStart.getClass().getAnnotation(StartAnno.class);
                String desc = startAnno.description();
                log.info("开始启动：" + desc);
                iStart.start();
                log.info("启动结束：" + desc);
            }
            log.info("系统启动成功" );
            System.out.println("系统启动成功");
        } catch (Exception e) {
            log.error("服务启动出现异常，程序即将退出.....", e);
            System.out.println("服务启动出现异常，程序即将退出.....\r\nException------>" + e);
            System.exit(0);
        }
        synchronized (ServiceBoot.class) {
            while (true) {
                try {
                    ServiceBoot.class.wait();
                } catch (InterruptedException e) {
                    log.error("== synchronized error:", e);
                }
            }
        }
    }

}
