package com.zw.miaofuspd.api.result;

import com.api.model.result.ApiResult;
import com.api.service.result.IApiResultServer;
import com.base.util.BeanHump;
import com.base.util.BeanMapperUtil;
import com.base.util.StringUtils;
import com.zw.service.base.AbsServiceBase;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 接口结果服务实现
 * @author 陈清玉
 */
@Service(IApiResultServer.BEAN_KEY)
public class ApiResultServerImpl extends AbsServiceBase implements IApiResultServer {
    @Override
    public int insertApiResult(ApiResult result) throws Exception {
        StringBuilder sql = new StringBuilder("INSERT INTO zw_api_result");
        sql.append(" (id,code,message,source_child_name,source_child_code,source_code,source_name,real_name,identity_code,user_mobile,user_name,only_key,result_data,api_return_id,state)");
        sql.append(" VALUES(");
        sql.append("'").append(result.getId()).append("',");
        sql.append("'").append(result.getCode()).append("',");
        sql.append("'").append(result.getMessage()).append("',");
        sql.append("'").append(result.getSourceChildName()).append("',");
        sql.append("'").append(result.getSourceChildCode()).append("',");
        sql.append("'").append(result.getSourceCode()).append("',");
        sql.append("'").append(result.getSourceName()).append("',");
        sql.append("'").append(result.getRealName()).append("',");
        sql.append("'").append(result.getIdentityCode()).append("',");
        sql.append("'").append(result.getUserMobile()).append("',");
        sql.append("'").append(result.getUserName()).append("',");
        sql.append("'").append(result.getOnlyKey()).append("',");
        sql.append("'").append(result.getResultData()).append("',");
        sql.append("'").append(result.getApiReturnId()).append("',");
        sql.append("'").append(result.getState()).append("'");
        sql.append(")");
        return sunbmpDaoSupport.executeSql(sql.toString());
    }

    @Override
    public List<Map> selectApiResult(ApiResult result) throws Exception {
        StringBuilder sql = new StringBuilder("SELECT * FROM zw_api_result WHERE 1=1 ");
        final Map<String, Object> objectMap = BeanMapperUtil.converMap(result);
        for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
            if(entry.getValue() != null &&  !"serialVersionUID".equals(entry.getKey())){
                sql.append("AND ").append(BeanHump.camelToUnderline(entry.getKey())).append("='").append(entry.getValue()).append("' ");
            }
        }
        if("".equals(result.getResultData())){
            sql.append("AND ").append("result_data != '").append("'");
        }

        return sunbmpDaoSupport.findForList(sql.toString());
    }

    @Override
    public Boolean validateData(ApiResult result) throws Exception {
        StringBuilder sql = new StringBuilder("SELECT count(id) FROM zw_api_result WHERE 1=1 ");
        final Map<String, Object> objectMap = BeanMapperUtil.converMap(result);
        for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
            if(entry.getValue() != null  &&  !"serialVersionUID".equals(entry.getKey())){
                sql.append("AND ").append(BeanHump.camelToUnderline(entry.getKey())).append("='").append(entry.getValue()).append("' ");
            }
        }
       return sunbmpDaoSupport.getCount(sql.toString()) > 0;
    }

    @Override
    public int deleteApiResult(ApiResult result) throws Exception {
        StringBuilder sql = new StringBuilder("delete from zw_api_result where 1 = 1 ");
        final Map<String, Object> objectMap = BeanMapperUtil.converMap(result);
        for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
            if(entry.getValue() != null &&  !"serialVersionUID".equals(entry.getKey())){
                sql.append("AND ").append(BeanHump.camelToUnderline(entry.getKey())).append("='").append(entry.getValue()).append("' ");
            }
        }

        return sunbmpDaoSupport.executeSql(sql.toString());
    }


    @Override
    public Boolean updateByOnlyKey(ApiResult result) throws Exception {
        StringBuilder sql = new StringBuilder("update zw_api_result  set ");
        if(result.getCode() != null){
            sql.append(" code = '").append(result.getCode()).append("', ");
        }
        if(result.getMessage() != null){
            sql.append(" message = '").append(result.getMessage()).append("', ");
        }
        if(result.getResultData() != null){
            sql.append(" result_data = '").append(result.getResultData()).append("', ");
        }
        if(result.getApiReturnId() != null){
            sql.append(" api_return_id = '").append(result.getApiReturnId()).append("', ");
        }
        if(result.getState() != null){
            sql.append(" state = '").append(result.getState()).append("', ");
        }
        if(result.getSourceChildCode() != null){
            sql.append(" source_child_code = '").append(result.getSourceChildCode()).append("', ");
        }
        if(result.getSourceChildName() != null){
            sql.append(" source_child_name = '").append(result.getSourceChildName()).append("', ");
        }
        String  sqlStr = sql.toString();
        sql  = new StringBuilder(sqlStr.substring(0,sqlStr.lastIndexOf(",")));
        sql.append("where only_key = '").append(result.getOnlyKey()).append("'");
        if(StringUtils.isNotBlank(result.getSourceCode())){
            sql.append(" and source_code = '").append(result.getSourceCode()).append("' ");
        }
        return sunbmpDaoSupport.executeSql(sql.toString()) > 0;
    }
}
