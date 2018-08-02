package com.zw.miaofuspd.listener;

import com.zw.service.base.AbsServiceBase;
import org.springframework.stereotype.Service;

/**
 * <strong>Title : <br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年03月13日<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) Vbill Co.,Ltd.<br>
 * </strong>
 * <p>
 *
 * @author department:技术开发部 <br>
 *         username:吕彬 <br>
 *         email: <br>
 * @version <strong>zw有限公司-运营平台</strong><br>
 *          <br>
 *          <strong>修改历史:</strong><br>
 *          修改人 修改日期 修改描述<br>
 *          -------------------------------------------<br>
 *          <br>
 *          <br>
 */
@Service("initService")
public class InitService extends AbsServiceBase {

    public void init()throws Exception {
        String sql = "update app_user set state = '1',registration_id = ''";
        sunbmpDaoSupport.exeSql(sql);
    }
}
