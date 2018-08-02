package com.zw.service.task.proxy;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.zw.service.springcontext.SpringContextUtil;

/**
 * <strong>Title : <br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年03月09日<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) zw.<br>
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
public class TaskProxy implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {
        String beanId = shardingContext.getJobName();
        SimpleJob task = (SimpleJob) SpringContextUtil.getApplicationContext().getBean(beanId);
        task.execute(shardingContext);
    }
}
