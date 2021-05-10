package com.example.lfm.service.Impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.example.lfm.config.AlipayConfig;
import com.example.lfm.dao.SysStudentMapper;
import com.example.lfm.dao.SysUserMapper;
import com.example.lfm.dao.WashingMapper;
import com.example.lfm.entity.*;
import com.example.lfm.service.WashService;
import com.example.lfm.utils.JwtTokenUtils;
import com.example.lfm.utils.ReturnMessage;
import com.example.lfm.utils.ReturnMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

@Service
public class WashServiceImpl implements WashService {
    @Autowired
    private WashingMapper washingMapper;
    @Autowired
    private SysStudentMapper studentMapper;
    @Autowired
    private SysUserMapper userMapper;
    @Override
    public ReturnMessage<Object> newWash(Washing washing, HttpServletRequest request) {
        String token = request.getHeader("x-auth-token");
        if(token==null){
            return ReturnMessageUtil.error(0, "获取token失败");
        }
        Long studentId= JwtTokenUtils.getStudentId(token);
        washing.setStudentId(studentId);
        if(StringUtils.isEmpty(washing.getAddressId())|| StringUtils.isEmpty(washing.getClothesNumber())|| StringUtils.isEmpty(washing.getAddressId())|| StringUtils.isEmpty(washing.getClothesType())|| StringUtils.isEmpty(washing.getWashType())){
            return ReturnMessageUtil.error(0, "必填项不可为空！");
        }
        washing.setCreateTime(new Date());
        washing.setStatus("0");
        washing.setDelFlag("0");
        //价格随便给的算法算的
        Double fee=0.0;
        if(washing.getClothesType().equals("0")){
            fee=fee+4;
        }else if (washing.getClothesType().equals("1")){
            fee=fee+6;
        }else if (washing.getClothesType().equals("2")){
            fee=fee+10;
        }else if (washing.getClothesType().equals("3")){
            fee=fee+8;
        }
        if(washing.getWashType().equals("0")){
            fee=fee+5;
        }else if(washing.getWashType().equals("1")){
            fee=fee+2;
        }else if(washing.getWashType().equals("2")){
            fee=fee+4;
        }else if(washing.getWashType().equals("3")){
            fee=fee+6;
        }else if(washing.getWashType().equals("4")){
            fee=fee+3;
        }
        fee=fee*washing.getClothesNumber();
        washing.setFee(fee);
        if(washingMapper.insert(washing)==1){
            return ReturnMessageUtil.sucess(washing);
        }
        return ReturnMessageUtil.error(0, "新建失败！");
    }

    @Override
    public ReturnMessage<Object> orderList(String status, HttpServletRequest request) {
        String token = request.getHeader("x-auth-token");
        if(token==null){
            return ReturnMessageUtil.error(0, "获取token失败");
        }
        Long studentId= JwtTokenUtils.getStudentId(token);
        if(StringUtils.isEmpty(studentId)||StringUtils.isEmpty(StringUtils.isEmpty(studentMapper.selectByPrimaryKey(studentId)))){
            return ReturnMessageUtil.error(0, "学生不存在！");
        }
        //当状态码为9时默认查询全部订单
        if(status.equals("9")){
            if(StringUtils.isEmpty(washingMapper.selectByStudentId(studentId,"0"))){
                return ReturnMessageUtil.error(0, "暂无订单信息！");
            }
            return ReturnMessageUtil.sucess(washingMapper.selectByStudentId(studentId,"0"));
        }
        /**
         * 0下单 1支付 2接单 3上门取件 4洗衣 5派送 6收货 7取消
         */
        if(status.equals("0")||status.equals("1")||status.equals("2")||status.equals("3")||status.equals("4")
                ||status.equals("5")||status.equals("6")||status.equals("7")){
            if(StringUtils.isEmpty(washingMapper.selectBySIdStatus(studentId,status,"0"))){
                return ReturnMessageUtil.error(0, "暂无订单信息！");
            }
            return ReturnMessageUtil.sucess(washingMapper.selectBySIdStatus(studentId,status,"0"));
        }
        return ReturnMessageUtil.error(0, "错误信息！");
    }

    @Override
    public ReturnMessage<Object> getOrderInfo(Long washingId) {
        Washing washing = washingMapper.selectByPrimaryKey(washingId);
        if (StringUtils.isEmpty(washingId) || StringUtils.isEmpty(washing)) {
            return ReturnMessageUtil.error(0, "订单不存在！");
        }
        //接单人
        SysUser takeuser = userMapper.selectByPrimaryKey(washing.getUserTakeId());
        //接衣服员
        SysUser fetchuser=userMapper.selectByPrimaryKey(washing.getUserFetchId());
        //洗衣员
        SysUser washinguser=userMapper.selectByPrimaryKey(washing.getUserWashingId());
        //派送员
        SysUser delivery=userMapper.selectByPrimaryKey(washing.getUserDeliveryId());
        WashingDetail washingDetail=new WashingDetail();
        washingDetail.setDelivery(delivery);
        washingDetail.setFetchuser(fetchuser);
        washingDetail.setTakeuser(takeuser);
        washingDetail.setWashing(washing);
        washingDetail.setWashinguser(washinguser);
            return ReturnMessageUtil.sucess(washingDetail);
    }

    @Override
    public ReturnMessage<Object> pay(Long washId) {
        String outTradeNo="W"+washId;
        if(washId==0||StringUtils.isEmpty(washingMapper.selectByPrimaryKey(washId))){
            return ReturnMessageUtil.error(0,"打印订单id不能为空");
        }
        Washing washing=washingMapper.selectByPrimaryKey(washId);
        if(StringUtils.isEmpty(washing)){
            return ReturnMessageUtil.error(0,"不存在该订单");
        }
        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.GETEWAY_URL, AlipayConfig.APP_ID, AlipayConfig.APP_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGN_TYPE);
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("洗衣订单"+outTradeNo);//加签过的订单详情
        model.setSubject("洗衣订单"+outTradeNo);
        model.setOutTradeNo(outTradeNo); //交易号 OutTradeNo只能为数字、英文或下划线；此外，OutTradeNo不可以重复，若重复则会出现系统繁忙等错误。
        model.setTimeoutExpress("30m");
        model.setTotalAmount(""+washing.getFee());
        model.setProductCode("QUICK_MSECURITY_PAY");
        //将自己想要传递到异步接口的数据，set进去 pass_back_params
        model.setPassbackParams(outTradeNo);
        request.setBizModel(model);
        request.setNotifyUrl("http://47.119.126.86:8084/print/notify_url");
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            System.out.println("response.getBody()：" + response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
            return ReturnMessageUtil.sucess(response.getBody());
        } catch (
                AlipayApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ReturnMessage<Object> cancelWashOrder(Long washId) throws AlipayApiException {
        if(washId==0||StringUtils.isEmpty(washingMapper.selectByPrimaryKey(washId))){
            return ReturnMessageUtil.error(0,"打印订单id不能为空");
        }
        Washing washing=washingMapper.selectByPrimaryKey(washId);
        if(StringUtils.isEmpty(washing)){
            return ReturnMessageUtil.error(0,"不存在该订单");
        }
        if(washing.getStatus().equals("0")||washing.getStatus().equals("1")){
            if(washing.getStatus().equals("1")){
                //如果已支付就执行退款操作
                refund(washId);
            }
            washing.setStatus("7");//取消订单
            washing.setCancelTime(new Date());//取消时间
            washingMapper.updateByPrimaryKey(washing);
            return ReturnMessageUtil.sucess();
        }
        return ReturnMessageUtil.error(0,"不能取消该订单");
    }

    @Override
    public ReturnMessage<Object> confirm(Long washingId) {
        Washing washing=washingMapper.selectByPrimaryKey(washingId);
        if(washingId==0||StringUtils.isEmpty(washing)){
            return ReturnMessageUtil.error(0,"不存在该订单");
        }
        if(washing.getStatus().equals("5")){
            washing.setStatus("6");
            washingMapper.updateByPrimaryKey(washing);
            return ReturnMessageUtil.sucess();
        }
        return ReturnMessageUtil.error(0,"收货失败！");
    }

    @Override
    public Washing getwashById(Long washId) {
        return washingMapper.selectByPrimaryKey(washId);
    }

    @Override
    public ReturnMessage<Object> updateByPrimaryKey(Washing washing) {
        return ReturnMessageUtil.sucess(washingMapper.updateByPrimaryKey(washing));
    }

    private ReturnMessage<Object> refund(Long washId) throws AlipayApiException {
        //response.setContentType("text/html;charset=utf-8");
        //PrintWriter out = response.getWriter();
        //获得初始化的AlipayClient
        String outTradeNo="R"+washId;
        if(washId==0||StringUtils.isEmpty(washingMapper.selectByPrimaryKey(washId))){
            return ReturnMessageUtil.error(0,"打印订单id不能为空");
        }
        Washing washing=washingMapper.selectByPrimaryKey(washId);
        if(StringUtils.isEmpty(washing)){
            return ReturnMessageUtil.error(0,"不存在该订单");
        }
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.GETEWAY_URL, AlipayConfig.APP_ID, AlipayConfig.APP_PRIVATE_KEY, "json", AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGN_TYPE);
        //设置请求参数
        AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();
        //商户订单号，必填
        String out_trade_no = new String(outTradeNo);
        //需要退款的金额，该金额不能大于订单金额，必填
        String refund_amount = new String(washing.getFee()+"");
        //标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传
        String out_request_no = new String(UUID.randomUUID().toString());

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"refund_amount\":\""+ refund_amount +"\","
                + "\"out_request_no\":\""+ out_request_no +"\"}");
        //请求
        String result = alipayClient.execute(alipayRequest).getBody();
        //输出
        return ReturnMessageUtil.sucess(result);
    }
}
