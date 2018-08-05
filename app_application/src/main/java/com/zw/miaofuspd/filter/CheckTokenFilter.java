package com.zw.miaofuspd.filter;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.api.service.wechat.IAuthApiService;
import com.api.model.wechat.AuthResult;
import com.constants.ApiConstants;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * token校验过滤器，所有的API接口请求都要经过该过滤器(除了登陆接口)
 * @author 陈清玉 form https://github.com/bigmeow/JWT
 *
 */
@Component
public class CheckTokenFilter implements Filter {
	private  final Logger LOGGER = LoggerFactory.getLogger(CheckTokenFilter.class);
	@Resource
	private IAuthApiService authApiService;

	@Override
	public void doFilter(ServletRequest argo, ServletResponse arg1,
			FilterChain chain ) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest) argo;
		HttpServletResponse response=(HttpServletResponse) arg1;
		LOGGER.info("----------请求路径{}---------",request.getRequestURI());

		LOGGER.info("开始校验签名");
		String signature = request.getParameter("signature");// 微信加密签名(非必须)
        String msgSignature = request.getParameter("msg_signature");// 微信加密签名
        String timestamp = request.getParameter("timestamp");// 时间戳
        String nonce = request.getParameter("nonce");// 随机数
        String echostr = request.getParameter("echostr");//随机字符串

        LOGGER.info("微信返回参数：{}",signature +","+msgSignature+","+ timestamp +","+ nonce +","+ echostr);
        if((StringUtils.isBlank(msgSignature) && StringUtils.isBlank(signature)) || StringUtils.isBlank(timestamp)
				|| StringUtils.isBlank(nonce)) {
            LOGGER.error("Failed to verify the signature-----null!");
        } else {
            if(StringUtils.isNotBlank(echostr)){
				LOGGER.info("开始验证微信接入验签:{}" + echostr);
				AuthResult result = authApiService.validConnectWechat(signature,timestamp, nonce, echostr);
				if(ApiConstants.STATUS_SIGNATURE_SUCCESS.equals(result.getCode())) {
					LOGGER.info("验签成功，开始接入微信。。。。。。。。。。。。。");
					outWrite(echostr, response);
				} else {
					LOGGER.info("微信接入，验签失败：{}"+ result.getMsg());
				}
			} else {
				LOGGER.info("开始提取request中xml数据包:{..................}");
				Map<String, String> requestMap = xmlToMap(request);
				if(!CollectionUtils.isEmpty(requestMap)) {
					if(requestMap.containsKey("Encrypt")) {
						LOGGER.info("数据提取成功,开始验证签名。。。。。。。。。。。");
						AuthResult result = authApiService.validSignature(msgSignature,timestamp, nonce, requestMap);
						if(ApiConstants.STATUS_SIGNATURE_SUCCESS.equals(result.getCode())) {
							LOGGER.info("验签成功，开始执行下一步。。。。。。。。。。。。。");
							//跳转到指定的controller
							request.setAttribute("encrypt", requestMap.get("Encrypt"));
							request.getRequestDispatcher(result.getMsg()).forward(request, response);
						} else {
							LOGGER.info("微信消息，验签失败：{}"+ result.getMsg());
						}
					} else if(requestMap.containsKey("msg")) {
						LOGGER.info("数据提取失败:{}"+ requestMap.get("msg"));
					} else {
						LOGGER.info("数据提取失败未知异常，请联系管理员。");
					}
				}
			}
		}
	}
	
	
	@Override
	public void init(FilterConfig arg0) {
		System.out.println("token过滤器初始化了");
	}

	@Override
	public void destroy() {
		
	}

	/**
	 * xml转换为map
	 * @param request
	 * @return map 错误信息或者提取的map
	 */
	@SuppressWarnings("unchecked")
	private static Map<String, String> xmlToMap(HttpServletRequest request){
		Map<String, String> map = new HashMap<>();
		SAXReader reader = new SAXReader();
		try {
			InputStream ins = request.getInputStream();
			try {
				Document doc = reader.read(ins);
				Element root = doc.getRootElement();
				List<Element> list = root.elements();
				for (Element e : list) {
					map.put(e.getName(), e.getText());
				}
			} catch (DocumentException e1) {
				map.put("msg", Arrays.toString(e1.getStackTrace()));
			}finally{
				assert ins != null;
				try {
					ins.close();
				} catch (IOException e) {
					map.put("msg", Arrays.toString(e.getStackTrace()));
				}
			}
		} catch (IOException e1) {
			map.put("msg", Arrays.toString(e1.getStackTrace()));
			return map;
		}
		return map;
	}

	public void outWrite(String jsonStr,HttpServletResponse response) throws IOException{
		response.setContentType("text/html;charset=UTF-8;");
		PrintWriter out = response.getWriter();
		out.write(jsonStr);
		out.flush();
		out.close();

	}

}
