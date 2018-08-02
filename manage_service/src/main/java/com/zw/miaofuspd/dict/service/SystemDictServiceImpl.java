package com.zw.miaofuspd.dict.service;


import com.zw.miaofuspd.facade.dict.service.ISystemDictService;
import com.zw.service.base.AbsServiceBase;
import net.sf.ehcache.util.PropertyUtil;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * <strong>Title : <br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年02月20日<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) Vbill Co.,Ltd.<br>
 * </strong>
 * <p>
 *
 * @author department:技术开发部 <br>
 *         username:Win7 <br>
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
public class SystemDictServiceImpl extends AbsServiceBase implements ISystemDictService {
    @Override
    public String getInfo(String dict_kind,String dict_key) throws Exception {
//        String sql = "select dict_info from iss_dict where dict_kind='"+dict_kind+"' and dict_key='" +
//                dict_key+"'";
//        Map map = tamcDaoSupport.findForMap(sql);
//        if(map==null){
            return null;
//        }
//        return map.get("dict_info").toString();

    }

    @Override
    public List getJsonDict(String dict_kind) throws Exception {
        String sql = "select dict_info,dict_key from iss_dict where dict_kind='"+dict_kind+"'";
        /*List list = tamcDaoSupport.findForList(sql);
        return list;*/
        return null;
    }


    public String getInfo(String key)throws Exception{
        //将properties文件加载到输入字节流中
        InputStream is = PropertyUtil.class.getClassLoader().getResourceAsStream("properties/config.properties");
        //创建一个Properties容器
        Properties prop = new Properties();
        //从流中加载properties文件信息
        prop.load(is);
        return (String)prop.get(key);
    }

    public static void main(String[] args) {
        SystemDictServiceImpl systemDictService = new SystemDictServiceImpl();
        try{
            System.out.println(systemDictService.getInfo(""));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
