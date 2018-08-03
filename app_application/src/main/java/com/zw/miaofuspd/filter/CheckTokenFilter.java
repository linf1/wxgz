package com.zw.miaofuspd.filter;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wechat.util.SignUtil;

import com.zw.web.base.AbsBaseController;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * token校验过滤器，所有的API接口请求都要经过该过滤器(除了登陆接口)
 * @author 陈清玉 form https://github.com/bigmeow/JWT
 *
 */
public class CheckTokenFilter extends AbsBaseController implements Filter {
	private  final Logger LOGGER = LoggerFactory.getLogger(CheckTokenFilter.class);
	@Override
	public void doFilter(ServletRequest argo, ServletResponse arg1,
			FilterChain chain ) throws IOException {
		HttpServletRequest request=(HttpServletRequest) argo;
		HttpServletResponse response=(HttpServletResponse) arg1;
		LOGGER.info("----------请求路径{}---------",request.getRequestURI());

		LOGGER.info("开始校验签名");
		String signature = request.getParameter("signature");// 微信加密签名
		String timestamp = request.getParameter("timestamp");// 时间戳
		String nonce = request.getParameter("nonce");// 随机数
		String echostr = request.getParameter("echostr");//随机字符串

		LOGGER.info("微信返回参数：{}",signature +","+ timestamp +","+ nonce +","+ echostr);
		if(StringUtils.isBlank(signature) || StringUtils.isBlank(timestamp)
				|| StringUtils.isBlank(nonce)) {
			LOGGER.error("Failed to verify the signature-----null!");
		} else {
			 boolean isPass = SignUtil.checkSignature("eyJ0eXAiOiJKV1QiLCJhbGiiIzI1NiJ9", signature, timestamp, nonce);
			 if(isPass) {
				 LOGGER.info("Connect the weixin server is successful.");

				 //xml解析-------> map
				 Map<String, String> requestMap = xmlToMap(request);
				 // 发送方帐号（open_id）
				 String fromUserName = requestMap.get("FromUserName");
				 // 公众帐号
				 String toUserName = requestMap.get("ToUserName");
				 // 消息类型
				 String msgType = requestMap.get("MsgType");
				 // 消息内容
				 String content = requestMap.get("Content");

				 LOGGER.info("FromUserName is:" + fromUserName + ", ToUserName is:" + toUserName + ", MsgType is:" + msgType);
			 } else {
				 LOGGER.error("Failed to verify the signature!");
			 }
		}
	}
	
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("token过滤器初始化了");
	}

	@Override
	public void destroy() {
		
	}

}
