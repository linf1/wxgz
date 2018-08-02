package com.zw.miaofuspd.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.base.util.AppRouterSettings;
import com.base.util.StringUtils;
import net.minidev.json.JSONObject;

import com.jwt.Jwt;
import com.jwt.TokenState;
import org.apache.zookeeper.Login;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * token校验过滤器，所有的API接口请求都要经过该过滤器(除了登陆接口)
 * @author 陈清玉 form https://github.com/bigmeow/JWT
 *
 */
public class CheckTokenFilter implements Filter {
	private  final Logger LOGGER = LoggerFactory.getLogger(CheckTokenFilter.class);
	@Override
	public void doFilter(ServletRequest argo, ServletResponse arg1,
			FilterChain chain ) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest) argo;
		HttpServletResponse response=(HttpServletResponse) arg1;
		String cxt = request.getContextPath();
		LOGGER.info("----------请求路径{}---------",request.getRequestURI());
		String[] nonPath  ={"/login", "/sms", "/activity", "/credit/getCreditApiInfo"};
		for (String non : nonPath) {
			LOGGER.info("-------------不验证路径{}---------------",non);
			if(request.getRequestURI().contains(non)){
				//登陆接口不校验token，直接放行
				chain.doFilter(request, response);
				return;
			}
		}
		//其他API接口一律校验token
		LOGGER.info("开始校验token");
		//从请求头中获取token
		String token = request.getHeader("token");
		if(StringUtils.isEmpty(token)){
			token = request.getParameter("token");
		}
		Map<String, Object> resultMap=Jwt.validToken(token);
		TokenState state=TokenState.getTokenState((String)resultMap.get("state"));
		switch (state) {
		case VALID:
			//取出payload中数据,放入到request作用域中
			request.setAttribute("data", resultMap.get("data"));
			//放行
			chain.doFilter(request, response);
			break;
		case EXPIRED:
		case INVALID:
			LOGGER.info("无效token");
			//token过期或者无效，则输出错误信息返回给ajax
			JSONObject outputMSg=new JSONObject();
			outputMSg.put("retCode", "no_token");
			outputMSg.put("retMsg", "您的token不合法或者过期了，请重新登陆");
			output(outputMSg.toJSONString(), response);
			break;
		default:

		}
		
		
	}
	
	
	public void output(String jsonStr,HttpServletResponse response) throws IOException{
		response.setContentType("text/html;charset=UTF-8;");
		PrintWriter out = response.getWriter();
//		out.println();
		out.write(jsonStr);
		out.flush();
		out.close();
		
	}
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("token过滤器初始化了");
	}

	@Override
	public void destroy() {
		
	}

}
