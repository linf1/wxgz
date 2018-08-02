package com.zw.service.base;

import com.base.util.ThreadLocalHelper;
import com.zw.service.common.dao.IDaoSupport;
import com.zw.service.dto.UserInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


/**
 * <strong>Title : 基类<br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年02月16日<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) Vbill Co.,Ltd.<br>
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
public class AbsServiceBase {

    @Autowired
    @Qualifier("sunbmpDaoSupport")
    protected IDaoSupport sunbmpDaoSupport;

//    @Autowired
//    @Qualifier("tamcDaoSupport")
//    protected IDaoSupport tamcDaoSupport;

    protected UserInfoDTO getUserInfo() {
        return (UserInfoDTO) ThreadLocalHelper.getMap().get(ThreadLocalHelper.USER_INFO_DTO);
    }
}
