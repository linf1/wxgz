package com.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

/**
 * <p>Title: TextProperties </p>
 * <p>Description: 读取text.properties</p>
 * <p>Copyright (c) </p>
 * <p>Company: </p>
 *
 * @author 陈清玉
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 * @date 2018年6月6日下午10:20:08
 */
public class TextProperties {
    private static final Logger logger = LoggerFactory.getLogger(TextProperties.class);
	
	private static final String TEXT_PROPERTIES_FILE="properties/text.properties";
	private static final TextProperties _insstance = new TextProperties();
	
	public static TextProperties instance(){
		return _insstance;
	}
	
	private final Properties properties;
	
	private TextProperties(){
		properties = new Properties();
		this.read();
	}

	private void read() {
		try {
			Enumeration<URL> resources = this.getClass().getClassLoader().getResources(TEXT_PROPERTIES_FILE);
			while(resources.hasMoreElements()){
				URL url = resources.nextElement();
				InputStream in = url.openStream();
				InputStreamReader isr = new InputStreamReader(in, "utf-8");
				properties.load(isr);
				isr.close();
				in.close();
				logger.info("load text properties from {}", url.toString());
			}
		} catch (Exception e) {
			logger.warn("TextProperties.read exception", e);
		}
	}
	
	public String get(String key){
		return properties.getProperty(key);
	}

	public static void main(String[] args) {
		System.out.println(TextProperties.instance().get("order.audit"));
	}
}





