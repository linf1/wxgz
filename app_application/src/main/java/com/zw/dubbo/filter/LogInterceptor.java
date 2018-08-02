package com.zw.dubbo.filter;

import com.alibaba.dubbo.rpc.*;
import com.base.util.TraceLoggerUtil;

/**
 * <strong>Title : <br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年02月20日<br>
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
public class LogInterceptor implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        StringBuilder sb = new StringBuilder();
        String msg = getLogMsg();
        try {
            sb.append("开始请求:");
            sb.append(msg);
            TraceLoggerUtil.info(sb.toString());
            Result result = invoker.invoke(invocation);
            return result;
        } finally {
            sb = new StringBuilder();
            sb.append("请求完成:");
            sb.append(msg);
            TraceLoggerUtil.info(sb.toString());
        }

    }

    private String getLogMsg() {
        StringBuilder sb = new StringBuilder();
        sb.append("\r\n");
        sb.append("remoteHost----->");
        sb.append(RpcContext.getContext().getRemoteAddressString());
        sb.append("\r\n");
        sb.append("target----->");
        sb.append(RpcContext.getContext().getUrl().getPath());
        sb.append(".");
        sb.append(RpcContext.getContext().getMethodName());
       /* sb.append("\r\n");*/
        return sb.toString();
    }
}
