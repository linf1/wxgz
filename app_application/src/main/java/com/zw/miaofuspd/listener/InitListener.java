package com.zw.miaofuspd.listener;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/4 0004.
 */
public class InitListener extends ContextLoaderListener {
    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);
        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
        //获取bean
       // loginService = (ILoginService) applicationContext.getBean("loginServiceImpl");
        ServletContext servletContext = event.getServletContext();
        try {
            Map tokenMap = new HashMap();
            //List<Map> list = loginService.getAccessToken();
            List<Map> list = new ArrayList<>();
            if(list != null && list.size()>0){
                for(Map map : list){
                    String token = map.get("token")+"";
                    if(StringUtils.isNotEmpty(token)){
                        tokenMap.put(token,map);
                    }
                }
            }
            servletContext.setAttribute("token",tokenMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        //得到LoadInfo对象
       /* manage.memory.LoadInfo load=this.loadInfo(servletContext);
        //调用LoadInfo的init()方法
        load.init();*/
    }
}
