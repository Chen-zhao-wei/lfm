package com.example.lfm.service.Impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.example.lfm.config.AlipayConfig;
import com.example.lfm.dao.ActPrintMapper;
import com.example.lfm.dao.SysDictDataMapper;
import com.example.lfm.dao.SysStudentMapper;
import com.example.lfm.dao.SysUserMapper;
import com.example.lfm.entity.*;
import com.example.lfm.service.ActPrintService;
import com.example.lfm.utils.JwtTokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.example.lfm.utils.ReturnMessage;
import com.example.lfm.utils.ReturnMessageUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ActPrintServiceImpl implements ActPrintService {
    private static Logger logger = LoggerFactory.getLogger(ActPrintServiceImpl.class);
    @Autowired
    private ActPrintMapper printMapper;
    @Autowired
    private SysStudentMapper studentMapper;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private SysDictDataMapper sysDictDataMapper;
    @Autowired
    private DelayService delayService;

    @Override
    public ReturnMessage<Object> newprint(ActPrint print, HttpServletRequest request) {
        String token = request.getHeader("x-auth-token");
        if(token==null){
            return ReturnMessageUtil.error(0, "获取token失败");
        }
        Long studentId= JwtTokenUtils.getStudentId(token);
        print.setStudentId(studentId);
        if(StringUtils.isEmpty(print.getFileName())|| StringUtils.isEmpty(print.getFileUrl())|| StringUtils.isEmpty(print.getAddressId())){
            return ReturnMessageUtil.error(0, "必填项不可为空！");
        }
        print.setStatus("0");
        // 计算价格
        // 单面2毛一张，双页 * 1.5
        // 如果是B4 * 2
        // 如果是彩打 * 2
        // 封胶加5块
        // 封皮加2块
        double fee = 0;
        fee = print.getPageNum();
        fee = fee * print.getPrintNumber();
        // 单双面打印标志
        List<SysDictData> bothsideTypes =  sysDictDataMapper.selectDictDataByType("sys_bothside_type");
        if (StringUtils.isEmpty(print.getBothSideFlag())){
            ReturnMessageUtil.error(0, "单双面标志不可为空！");
        }
        fee = fee * 0.2;
        for (SysDictData sysDictData:bothsideTypes) {
            //双页打印
            if ("双页".equals(sysDictData.getDictLabel()) && print.getBothSideFlag().equals(sysDictData.getDictValue())){
                fee = fee * 1.5;
            }
        }
        // 纸张大小
        List<SysDictData> paperTypes =  sysDictDataMapper.selectDictDataByType("sys_paper_type");
        if (StringUtils.isEmpty(print.getPaperSize())){
            ReturnMessageUtil.error(0, "纸张大小不可为空！");
        }
        for (SysDictData sysDictData:paperTypes) {
            //B4打印
            if ("B4".equals(sysDictData.getDictLabel()) && print.getPaperSize().equals(sysDictData.getDictValue())){
                fee = fee * 2;
            }
        }
        // 黑白/彩打
        List<SysDictData> colorTypes =  sysDictDataMapper.selectDictDataByType("sys_color_type");
        if (StringUtils.isEmpty(print.getColorFlag())){
            ReturnMessageUtil.error(0, "彩印标志不可为空！");
        }
        for (SysDictData sysDictData:colorTypes) {
            //B4打印
            if ("彩印".equals(sysDictData.getDictLabel()) && print.getColorFlag().equals(sysDictData.getDictValue())){
                fee = fee * 2;
            }
        }
        //封胶
        List<SysDictData> sealingTypes =  sysDictDataMapper.selectDictDataByType("sys_sealing_type");
        if (StringUtils.isEmpty(print.getSealingFlag())){
            ReturnMessageUtil.error(0, "封胶标志不可为空！");
        }
        for (SysDictData sysDictData:sealingTypes) {
            //B4打印
            if ("封胶".equals(sysDictData.getDictLabel()) && print.getSealingFlag().equals(sysDictData.getDictValue())){
                fee = fee + 5;
            }
        }
        //封皮
        List<SysDictData> coverFlags =  sysDictDataMapper.selectDictDataByType("sys_cover_flag");
        if (StringUtils.isEmpty(print.getCoverFlag())){
            ReturnMessageUtil.error(0, "封面标志不可为空！");
        }
        if (print.getCoverFlag() == "0" && StringUtils.isEmpty(print.getCoverColor())){
            ReturnMessageUtil.error(0, "封面颜色不可为空！");
        }
        for (SysDictData sysDictData:coverFlags) {
            //B4打印
            if ("封面".equals(sysDictData.getDictLabel()) && print.getBothSideFlag().equals(sysDictData.getDictValue())){
                fee = fee + 2;
            }
        }
        print.setFee(fee);
        print.setCreateTime(new Date());
        if(printMapper.insert(print)==1){
            // 添加成功后，创建一个1小时之内没有付款就自动取消订单的定时器
            DshOrder dshOrder = new DshOrder("R"+print.getPrintId(),30 * 60 * 1000,1);
            delayService.add(dshOrder);
            return ReturnMessageUtil.sucess(print);
        }
        return ReturnMessageUtil.error(0, "下单失败！");
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
        //当无状态码时默认查询全部订单
        if(status.equals("9")){
            if(StringUtils.isEmpty(printMapper.selectByStudentId(studentId,"0"))){
                return ReturnMessageUtil.error(0, "暂无订单信息！");
            }
            return ReturnMessageUtil.sucess(printMapper.selectByStudentId(studentId,"0"));
        }
        /**
         * 0:待支付
         * 1：待打印
         * 2：待派送
         * 3.待收货
         * 4.已收货
         */
        if(status.equals("0")||status.equals("1")||status.equals("2")||status.equals("3")||status.equals("4")){
            if(StringUtils.isEmpty(printMapper.selectBySIdStatus(studentId,status,"0"))){
                return ReturnMessageUtil.error(0, "暂无订单信息！");
            }
            return ReturnMessageUtil.sucess(printMapper.selectBySIdStatus(studentId,status,"0"));
        }
        return ReturnMessageUtil.error(0, "错误信息！");
    }



    @Override
    public ReturnMessage<Object> getOrderinfo(Long printId) {
        ActPrint print=printMapper.selectByPrimaryKey(printId);
        if(StringUtils.isEmpty(printId)||StringUtils.isEmpty(print)){
            return ReturnMessageUtil.error(0, "订单不存在！");
        }
        //打印员
        SysUser printuser=userMapper.selectByPrimaryKey(print.getUserPrintId());
        //派送员
        SysUser deliveruser=userMapper.selectByPrimaryKey(print.getUserDeliveryId());
        String status=print.getStatus();
        if(status.equals("1")||status.equals("0")){
            logger.info("打印订单未接单=======================================");
            return ReturnMessageUtil.sucess(print);
        } else if(status.equals("3")){
            logger.info("打印订单已接单");
            return ReturnMessageUtil.sucess(print+""+printuser);
        }else if (status.equals("4")){
            logger.info("打印订单已派送");
            return ReturnMessageUtil.sucess(print+""+printuser+""+deliveruser);
        }
        return ReturnMessageUtil.error(0,"查询失败");
    }

    @Override
    public ActPrint getActPrintById(Long printId) {
        return printMapper.selectByPrimaryKey(printId);
    }

    @Override
    public ReturnMessage<Object> pay(Long printId) {
        String outTradeNo="R"+printId;
        if(printId==0||StringUtils.isEmpty(printMapper.selectByPrimaryKey(printId))){
            return ReturnMessageUtil.error(0,"打印订单id不能为空");
        }
        ActPrint actPrint=printMapper.selectByPrimaryKey(printId);
        if(StringUtils.isEmpty(actPrint)){
            return ReturnMessageUtil.error(0,"不存在该订单");
        }
        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.GETEWAY_URL, AlipayConfig.APP_ID, AlipayConfig.APP_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGN_TYPE);
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(actPrint.getFileName());//加签过的订单详情
        model.setSubject(actPrint.getFileName());
        model.setOutTradeNo(outTradeNo); //交易号 OutTradeNo只能为数字、英文或下划线；此外，OutTradeNo不可以重复，若重复则会出现系统繁忙等错误。
        model.setTimeoutExpress("30m");
        model.setTotalAmount(""+actPrint.getFee());
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

    //退款
    @Override
    public ReturnMessage<Object> refund(Long printId) throws IOException, AlipayApiException {
        //response.setContentType("text/html;charset=utf-8");
        //PrintWriter out = response.getWriter();
        //获得初始化的AlipayClient
        String outTradeNo="R"+printId;
        if(printId==0||StringUtils.isEmpty(printMapper.selectByPrimaryKey(printId))){
            return ReturnMessageUtil.error(0,"打印订单id不能为空");
        }
        ActPrint actPrint=printMapper.selectByPrimaryKey(printId);
        if(StringUtils.isEmpty(actPrint)){
            return ReturnMessageUtil.error(0,"不存在该订单");
        }
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.GETEWAY_URL, AlipayConfig.APP_ID, AlipayConfig.APP_PRIVATE_KEY, "json", AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGN_TYPE);
        //设置请求参数
        AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();
        //商户订单号，必填
        String out_trade_no = new String(outTradeNo);
        //需要退款的金额，该金额不能大于订单金额，必填
        String refund_amount = new String(actPrint.getFee()+"");
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

    @Override
    public ReturnMessage<Object> cancelorder(Long printId)throws IOException, AlipayApiException {
        if(printId==0||StringUtils.isEmpty(printMapper.selectByPrimaryKey(printId))){
            return ReturnMessageUtil.error(0,"打印订单id不能为空");
        }
        ActPrint actPrint=printMapper.selectByPrimaryKey(printId);
        if(StringUtils.isEmpty(actPrint)){
            return ReturnMessageUtil.error(0,"不存在该订单");
        }
        if(actPrint.getStatus().equals("0")||actPrint.getStatus().equals("1")){
            if(actPrint.getStatus().equals("1")){
                //如果已支付就执行退款操作
                refund(printId);
            }
            actPrint.setStatus("5");//取消订单
            actPrint.setCancelTime(new Date());//取消时间
            printMapper.updateByPrimaryKey(actPrint);
            return ReturnMessageUtil.sucess();
        }
        return ReturnMessageUtil.error(0,"不能取消该订单");
    }

    @Override
    public ReturnMessage<Object> updateByPrimaryKey(ActPrint actPrint){
        return ReturnMessageUtil.sucess(printMapper.updateByPrimaryKey(actPrint));
    }

    @Override
    public ReturnMessage<Object> confirm(Long printId) {
        ActPrint print=printMapper.selectByPrimaryKey(printId);
        if(printId==0||StringUtils.isEmpty(print)){
            return ReturnMessageUtil.error(0,"不存在该订单");
        }
        if(print.getStatus().equals("3")){
            print.setStatus("4");
            printMapper.updateByPrimaryKey(print);
            return ReturnMessageUtil.sucess();
        }
        return ReturnMessageUtil.error(0,"收货失败！");

    }
}
