package com.zw.service.boot.start;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.zw.service.springcontext.SpringContextUtil;
import com.zw.service.start.IStart;
import com.zw.service.start.StartAnno;
import com.zw.service.task.ITask;
import com.zw.service.task.proxy.TaskProxy;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * <strong>Title : <br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年03月08日<br>
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
@Component
@StartAnno(description = "定时任务监听")
public class TaskStart implements IStart {


    @Override
    public void start() throws Exception {
        Map<String, ITask> task = SpringContextUtil.getApplicationContext().getBeansOfType(ITask.class);

        for (String beanName : task.keySet()) {
            ITask iTask = task.get(beanName);
            if (iTask.isElasticJob()) {
                CoordinatorRegistryCenter registryCenter = (CoordinatorRegistryCenter) SpringContextUtil.getApplicationContext().getBean("elasticJobRegCenter");
                new JobScheduler(registryCenter, createJobConfiguration(task.get(beanName), beanName)).init();
            } else {

            }
        }
    }

    private LiteJobConfiguration createJobConfiguration(ITask task, String beanName) {
        JobCoreConfiguration coreConfig = JobCoreConfiguration.newBuilder(beanName, task.getCron(), task.getShardingTotalCount()).build();
        SimpleJobConfiguration simpleJobConfig = new SimpleJobConfiguration(coreConfig, TaskProxy.class.getName());
        LiteJobConfiguration result = LiteJobConfiguration.newBuilder(simpleJobConfig).overwrite(true).build();
        return result;
    }

}
