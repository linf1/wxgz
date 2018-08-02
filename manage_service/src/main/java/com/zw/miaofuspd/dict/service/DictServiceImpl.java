package com.zw.miaofuspd.dict.service;

import com.base.util.StringUtils;
import com.zw.miaofuspd.facade.dict.service.IDictService;
import com.zw.service.base.AbsServiceBase;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <strong>Title :数据字典工具类接口实现 <br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年02月20日<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) Vbill Co.,Ltd.<br>
 * </strong>
 * <p>
 *
 * @author department:技术开发部 <br>
 *         username:wangmin <br>
 *         email: <br>
 * @version <strong>zw有限公司-运营平台</strong><br>
 *          <br>
 *          <strong>修改历史:</strong><br>
 *          修改人 修改日期 修改描述<br>
 *          -------------------------------------------<br>
 *          <br>
 *          <br>
 */
@Service
public class DictServiceImpl extends AbsServiceBase implements IDictService {
    /**
     * 根据字典大类名称得到字典明细集合
     * @param
     * @return
     * @throws Exception
     */
    @Override
    public List getDetailList(String key) throws Exception {
        String sql ="select `name` as `id`,`name` as `name` from mag_dict_detail where state='1' " +
                "and dict_name='"+key+"'";
        List list = sunbmpDaoSupport.findForList(sql);
        return list;
    }

    /**
     * 根据key和name获取对应的code
     * @param
     * @return
     * @throws Exception
     */
    @Override
    public String getDictCode(String key,String name) throws Exception {
        String sql = "SELECT id,code  from  mag_dict_detail where dict_name = '"+key+"' and name = '"+name+"' and state = '1'";
        Map map = sunbmpDaoSupport.findForMap(sql);
        if(map==null){
            return null;
        }
        return map.get("code").toString();
    }

    /**
     * 根据key和code获取对应的value
     * @param
     * @return
     * @throws Exception
     */
    @Override
    public String getDictInfo(String key,String code) throws Exception {
        String sql = "SELECT name  from  mag_dict_detail where dict_name = '"+key+"' and code = '"+code+
                "' and state = '1'";
        Map map = sunbmpDaoSupport.findForMap(sql);
        if(map==null){
            return null;
        }
        return map.get("name").toString();
    }

    /**
     * 根据key得到value集合
     * @param
     * @return
     * @throws Exception
     */
    @Override
    public List getDictJson(String key ) throws Exception {
        String sql = "select `code` as `key`,`name` as `value` from mag_dict_detail where state='1' and dict_name='"+key+"' order by code asc";
        List list = sunbmpDaoSupport.findForList(sql);
        return list;
    }

    @Override
    public List getDict(String key ) throws Exception {
        String sql = "select `code` as `value`,`name` as `text` from mag_dict_detail where state='1' and dict_name='"+key+"'";
        List list = sunbmpDaoSupport.findForList(sql);
        return list;
    }

    /**
     * 根据条件查询结果
     * @param query
     * @return
     * @throws Exception
     */
    @Override
    public List getDictList(String query) throws Exception {
        String sql = ("select id,dict_id,dict_name,code,name,description,create_time,alter_time,state "
                + "from mag_dict_detail where state='1' ");
        if(StringUtils.isNotBlank(query)){
            sql+=(" and ("+query+")");
        }
        List list = sunbmpDaoSupport.findForList(sql);
        return list;
    }

}
