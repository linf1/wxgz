package com.zw.miaofuspd.listener;

import com.zw.service.start.IStart;
import com.zw.service.start.StartAnno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@StartAnno(description = "更新用户状态")
public class InitListener implements IStart {
	@Autowired
	private InitService initService;
	@Override
	public void start() throws Exception {
		//initService.init();
	}
}
