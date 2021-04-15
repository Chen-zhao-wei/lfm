package com.example.lfm.service.Impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.example.lfm.dao.ActPrintMapper;
import com.example.lfm.dao.SysStudentMapper;
import com.example.lfm.entity.ActPrint;
import com.example.lfm.service.ActPrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.example.lfm.utils.ReturnMessage;
import com.example.lfm.utils.ReturnMessageUtil;

@Service
public class ActPrintServiceImpl implements ActPrintService {
    @Autowired
    private ActPrintMapper printMapper;
    @Autowired
    private SysStudentMapper studentMapper;
    /**
     * APP_ID 应用id
     */
    public final static String APP_ID = "2021000117636920";

    /**
     * 应用私钥
     */
    public final static String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCCdUq47DN4JKSVm9wf8t9uL/kKDJKjQpMMkx7UWtADnKj5WPZojpIRJ6Nn4yqonST19tQZys+N9KiEqTvKea9Aa+1nqVPY5ZxTnA+fAOelU8qkkaqu5M7Fc85hn9QO4e2MB6IEsKefxOOwPJvCNcBWqhhtHA4I5CDcnRQcvTGXaaFikSvl4HsnMYuN7ric/2V3JYglmVM9JGRxm0g8q7Oduf3HeXAbyimgBnqT0Zd0KDjT1VogFl+2LwGSd2rjAbsHsT4I++YOi7wD27rkbpJelWQZ82ZRNHAOlJVGsj7r3auM6iowtpfmPyUUFUfhEJ12CqF+klNJS0e6zLH7C5UXAgMBAAECggEAL0z7xMTPfJY8nooea+8Rl9AJCd25JTy0OHD3UqOCnGymz5Gz9gELcPDgTICPLQedKRlPmfJxAtVcbI03wpgQNNltAJLiddf6sE37U/luBAG3jafLLRKE7g6pG7hpmOPNz2HNGso6XLF0pKe1CduXW1Tc+mKbrBma2KKTckAexPjfaRPZdSXX4YQxgpl10YtC6saQnOyZP5simctkVHuCEOG0HA3zXooczllTFtVjuGPfnCAgSrxE6NYYXI+CQjflXyWj7ZemUV/Kwj5LuwXrPNBRFwkwsA1fd9JpJZMHtamOs8ERRRwoEub2jWXHYmoxqfA7TezhE8Pzl27nKHZxQQKBgQDW43c3UI6T6SpkifZnJh6vkbOY2Fut8keOoRJEucxLChaJrjB5Ya1nbNGcPP0/Fjtw+dYZG1iZm1QqdtaaMIWTsQ5rfRFxfniuZMsfhsLIVvvVQasmYHTmFYg3rEtViywa/wvKXi8cbAlVU1EaVvbUAoCF0sjeUPPZcC9PrHPBhwKBgQCbarRP+ZXwClX7Wkxj4FEz1o/Iseq7HgptLBwtrhkE8GwhtKNnHGTRn2oH8drjtrl6LLKFRSkq/2SkW48+3rglG5udOdkL2tdjW0RBXxNMVo2JKdWqbxPhDY/23f448X9VrHJDDiJqA0153NwsPprENscuMyStF9UJBvOsZHez8QKBgH+3S7i1iTgBwa9li+w8bMp+d1alaL/Wmo77zmbIYtJKmaCUxbyuwCgTNfV9Dir7pLJ92rCrXxpUhk6CuQOFSS7BcceS2tYwi7tirpL7PjNh92UFFZrGg1PogkBMh628/KbC5RgOqENlNpre3Pohq6vQHODsqXPwey3Fjmj1HwtrAoGALjUaaRC+uICKMdIWFjfSTMRFOZlUPc/fr7fGpPo3LBXDQH4xeu2hOAXag4Qv3TYtjRqO9Tqr08HdDVS/kSSswTOlH6jcVD77fOncsEbxOOge6qIKicK+uVHL9Mzp0cpTXoiH7FHef4B933Z/65OtdhsHO0AvbltAlo9/kUjJojECgYEAigYPU/WXkANmPm7ObZHONj9delHcWRC7JNF/VtgYl/Gxohd/uNnJsEbXq5xwtFKqKlWsLWi/1PvS7XdlV5RwItAEJoVA6/h/ZXr890Z7AHDAbSsbbeWD2gMOQGeOdwwpJIkWcpYKncjYO5r7TRZPYbC62FUWivIWEQ7UKuRn/7Q=";
    /**
     * 编码
     */
    public final static String CHARSET = "UTF-8";

    /**
     * 支付宝公钥
     */
    public final static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgnVKuOwzeCSklZvcH/Lfbi/5CgySo0KTDJMe1FrQA5yo+Vj2aI6SESejZ+MqqJ0k9fbUGcrPjfSohKk7ynmvQGvtZ6lT2OWcU5wPnwDnpVPKpJGqruTOxXPOYZ/UDuHtjAeiBLCnn8TjsDybwjXAVqoYbRwOCOQg3J0UHL0xl2mhYpEr5eB7JzGLje64nP9ldyWIJZlTPSRkcZtIPKuznbn9x3lwG8opoAZ6k9GXdCg409VaIBZfti8Bkndq4wG7B7E+CPvmDou8A9u65G6SXpVkGfNmUTRwDpSVRrI+692rjOoqMLaX5j8lFBVH4RCddgqhfpJTSUtHusyx+wuVFwIDAQAB";
    /**
     * (沙箱)网关
     */
    public final static String GETEWAY_URL = "https://openapi.alipaydev.com/gateway.do";

    /**
     * 格式化
     */
    public final static String FORMAT = "json";

    /**
     * 签名类型
     */
    public final static String SIGN_TYPE = "RSA2";
    @Override
    public ReturnMessage<Object> newprint(ActPrint print) {
        if(StringUtils.isEmpty(print.getStudentId())||StringUtils.isEmpty(print.getFileName())|| StringUtils.isEmpty(print.getFileUrl())|| StringUtils.isEmpty(print.getAddressId())){
            return ReturnMessageUtil.error(0, "必填项不可为空！");
        }
        print.setStatus("0");
        if(printMapper.insert(print)==1){
            return ReturnMessageUtil.sucess();
        }else
        return ReturnMessageUtil.error(0, "下单失败！");
    }


    @Override
    public ReturnMessage<Object> orderList(Long studentId,String status) {
        if(StringUtils.isEmpty(studentId)||StringUtils.isEmpty(StringUtils.isEmpty(studentMapper.selectByPrimaryKey(studentId)))){
            return ReturnMessageUtil.error(0, "学生不存在！");
        }
        //当无状态码时默认查询全部订单
        if(StringUtils.isEmpty(status)||status==null){
            if(StringUtils.isEmpty(printMapper.selectByStudentId(studentId))){
                return ReturnMessageUtil.error(0, "暂无订单信息！");
            }
            return ReturnMessageUtil.sucess(printMapper.selectByStudentId(studentId));
        }
        /**
         * 0:待支付
         * 1：待打印
         * 2：待派送
         * 3.待收货
         * 4.已收货
         */
        if(status.equals("0")||status.equals("1")||status.equals("2")||status.equals("3")||status.equals("4")){
            if(StringUtils.isEmpty(printMapper.selectBySIdStatus(studentId,status))){
                return ReturnMessageUtil.error(0, "暂无订单信息！");
            }
            return ReturnMessageUtil.sucess(printMapper.selectBySIdStatus(studentId,status));
        }
        return ReturnMessageUtil.error(0, "错误信息！");
    }

    @Override
    public ReturnMessage<Object> SelectByKey(Long printId) {
        if(StringUtils.isEmpty(printId)||StringUtils.isEmpty(printMapper.selectByPrimaryKey(printId))){
            return ReturnMessageUtil.error(0, "订单不存在！");
        }
        return ReturnMessageUtil.sucess();
    }

    @Override
    public ReturnMessage<Object> getOrderInfo(Long printId) {
        String outTradeNo=""+printId;
        if(printId==0||StringUtils.isEmpty(printMapper.selectByPrimaryKey(printId))){
            return ReturnMessageUtil.error(0,"打印订单id不能为空");
        }
        ActPrint actPrint=printMapper.selectByPrimaryKey(printId);
        if(StringUtils.isEmpty(actPrint)){
            return ReturnMessageUtil.error(0,"不存在该订单");
        }
        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient(GETEWAY_URL, APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("我是测试数据");//加签过的订单详情
        model.setSubject("打印");
        model.setOutTradeNo(outTradeNo); //交易号 OutTradeNo只能为数字、英文或下划线；此外，OutTradeNo不可以重复，若重复则会出现系统繁忙等错误。
        model.setTimeoutExpress("30m");
        model.setTotalAmount(""+actPrint.getFee());
        model.setProductCode("QUICK_MSECURITY_PAY");
        //将自己想要传递到异步接口的数据，set进去 pass_back_params
        model.setPassbackParams(outTradeNo);
        request.setBizModel(model);
        request.setNotifyUrl("http://3j383810f3.zicp.vip/pay/ali/notif_url");
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            System.out.println("response.getBody()：" + response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
        } catch (
                AlipayApiException e) {
            e.printStackTrace();
            return null;
        }
        actPrint.setStatus("1");//已支付
        printMapper.updateByPrimaryKey(actPrint);
        return ReturnMessageUtil.sucess();
    }
}
