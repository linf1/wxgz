package com.zw.service.dubbo.filter;

import com.alibaba.dubbo.rpc.*;
import com.alibaba.fastjson.JSON;
import com.base.util.TraceLoggerUtil;
import com.zw.service.exception.DAOException;

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
public class LogProviderInterceptor implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        StringBuilder sb = new StringBuilder();
        String msg = getLogMsg();
        Result result = null;
        try {
            sb.append("开始执行:");
            sb.append(msg);
            sb.append("\r\n");
            sb.append("arguments:");
            //sb.append("\r\n");
            int index = 0;
            for (Object o : RpcContext.getContext().getArguments()) {
                sb.append("\r\n");
                sb.append("     argu");
                sb.append(index);
                sb.append("----->");
                sb.append(JSON.toJSONString(o));
                index++;
            }
            TraceLoggerUtil.info(sb.toString());
            result = invoker.invoke(invocation);
            if (result.hasException()) {
                Throwable exception = result.getException();
                if (!(exception instanceof DAOException)) {
                    sb = new StringBuilder();
                    sb.append("执行出现异常:");
                    sb.append(msg);
                    TraceLoggerUtil.error(sb.toString(), exception);
                }
            }
            return result;
        } finally {
            sb = new StringBuilder();
            sb.append("执行完成:");
            sb.append(msg);
            sb.append("\r\n");
            if (result != null) {
                sb.append("result");
                sb.append("----->");
                sb.append(JSON.toJSONString(result.getValue()));
            }
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
        return sb.toString();
    }
}
