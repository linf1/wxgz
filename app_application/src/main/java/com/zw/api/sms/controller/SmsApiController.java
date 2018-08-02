package com.zw.api.sms.controller;

import com.api.model.common.BYXResponse;
import com.api.service.sortmsg.IMessageServer;
import com.base.util.AppRouterSettings;
import com.base.util.RandomUtil;
import com.base.util.StringUtils;
import com.api.model.sortmsg.MsgRequest;
import com.google.code.kaptcha.Producer;
import com.zw.app.util.AppConstant;
import com.zw.web.base.vo.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 短信接口控制器
 * @author 陈清玉
 */
@RestController
@RequestMapping(AppRouterSettings.VERSION + "/sms")
public class SmsApiController  {

    private final Logger LOGGER = LoggerFactory.getLogger(SmsApiController.class);
    private static final  String CONTENT = "尊敬的用户，您的验证码为:${smsCode}";

    @Autowired
    private IMessageServer messageServer;

    @Autowired
    private Producer captchaProducer;

    /**
     * 短信发送接口
     * @param msgRequest ype 0 手机验证码 1 图片验证码 ，
 *                       phone 手机号，
*                        smsCode 验证码
     * @return
     */
    @PostMapping("/sendMsg")
    public ResultVO sendMsg (MsgRequest msgRequest){
        if(msgRequest == null || StringUtils.isEmpty(msgRequest.getPhone())) {
            return ResultVO.error("参数为空");
        }
        LOGGER.info("发送端信手机号：" + msgRequest.getPhone());
        //生成6位数随机数
        final String smsCode = RandomUtil.createRandomVcode(6);
        Map<String,String> parameters = new HashMap<>(2);
        parameters.put("smsCode",smsCode);
        parameters.put("content",CONTENT);
        msgRequest.setSmsCode(smsCode);
        //设置为短信验证
        msgRequest.setType("0");
        //设置超时时间
        msgRequest.setOverTime(AppConstant.SMS_CODE_PHONE_OVERTIME);
        try {
            final BYXResponse byxResponse = messageServer.sendSms(msgRequest, parameters);
            if (byxResponse != null) {
                if (BYXResponse.resCode.success.getCode().equals(byxResponse.getRes_code())) {
                   // smsService.saveSmsForRedis(msgRequest);
                    LOGGER.info("短信发送成功", byxResponse.toString());
                    return ResultVO.ok(byxResponse.getRes_msg(), null);
                }else{
                    return ResultVO.error(byxResponse.getRes_msg());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ResultVO.error(e.getMessage());
        }
       return ResultVO.error();
    }

    /**
     * 验证验证码
     * @param msgRequest type 0 手机验证码 1 图片验证码 ，
     *                    此字段为老表预留字段 phone 手机号（如果是手机验证码，该入口参数为手机电话，
     *      *                      如果是图片验证码，该入口参数为时间戳），
     *                   smsCode 验证码
     * @return
     */
    @PostMapping("/checkMsg")
        public ResultVO checkMsg(MsgRequest msgRequest){
        try {
            if(msgRequest == null || StringUtils.isEmpty(msgRequest.getSmsCode())) {
                return ResultVO.error("参数为空");
            }
            LOGGER.info("验证手机号：{},验证码：{}" ,msgRequest.getPhone(),msgRequest.getSmsCode().toLowerCase());
            //先查询验证码和用户是否正确
            Map inMap=new HashMap(2);
            inMap.put("tel",msgRequest.getPhone());
            inMap.put("smsCode",msgRequest.getSmsCode().toLowerCase());
            inMap.put("type",msgRequest.getType());
           // final Map resData  = smsService.checkSmsForRedis(inMap);
            final Map resData  = new HashMap();
            if(resData != null){
                Boolean flag = (Boolean) resData.get("flag");
                if(flag ){
                    return   ResultVO.ok("验证成功",null);
                }else{
                    return   ResultVO.error(resData.get("msg").toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVO.error(e.getMessage(),null);
        }
        return ResultVO.error("验证失败");
    }

    /**
     * 图片验证码生成控制器
     * @param response HttpServletResponse
     * @param msgRequest phone 这里的电话是时间戳
     * @throws IOException io异常
     */
    @RequestMapping("captcha.jpg")
    public void captcha(HttpServletResponse response,MsgRequest msgRequest)throws Exception {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        //生成文字验证码
        String text = captchaProducer.createText();
        msgRequest.setSmsCode(text.toLowerCase());
        //设置为图片验证码
        msgRequest.setType("1");
        msgRequest.setOverTime(AppConstant.SMS_CODE_OVERTIME);
        //保存到到redis数据库 便于验证
       // smsService.saveSmsForRedis(msgRequest);
        //生成图片验证码
        BufferedImage image = captchaProducer.createImage(text);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        out.flush();
    }
}
