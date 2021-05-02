package com.example.lfm.service.Impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.example.lfm.config.AlipayConfig;
import com.example.lfm.dao.ActTaskMapper;
import com.example.lfm.dao.SysStudentMapper;
import com.example.lfm.dao.SysUserMapper;
import com.example.lfm.entity.*;
import com.example.lfm.service.TaskService;
import com.example.lfm.utils.JwtTokenUtils;
import com.example.lfm.utils.ReturnMessage;
import com.example.lfm.utils.ReturnMessageUtil;
import javafx.concurrent.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private ActTaskMapper taskMapper;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private SysStudentMapper studentMapper;
    @Override
    public ReturnMessage<Object> newTask(ActTask task, HttpServletRequest request) {
        String token = request.getHeader("x-auth-token");
        if(token==null){
            return ReturnMessageUtil.error(0, "获取token失败");
        }
        Long studentId= JwtTokenUtils.getStudentId(token);
        task.setStudentSendId(studentId);
        if(StringUtils.isEmpty(task.getAddressId())|| StringUtils.isEmpty(task.getFee())|| StringUtils.isEmpty(task.getTaskDescribe())){
            return ReturnMessageUtil.error(0, "必填项不可为空！");
        }
        task.setCreateTime(new Date());
        task.setStatus("0");
        task.setDelFlag("0");
        if(taskMapper.insert(task)==1){
            return  ReturnMessageUtil.sucess();
        }
        return ReturnMessageUtil.error(0,"发布失败！");
    }

    @Override
    public ReturnMessage<Object> publicorderList(String status, HttpServletRequest request) {
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
            if(StringUtils.isEmpty(taskMapper.selectBySendId(studentId,"0"))){
                return ReturnMessageUtil.error(0, "暂无订单信息！");
            }
            return ReturnMessageUtil.sucess(taskMapper.selectBySendId(studentId,"0"));
        }
        /**
         * 0下单 1审核 2 接单 3完成 4支付 5取消
         */
        if(status.equals("0")||status.equals("1")||status.equals("2")||status.equals("3")||status.equals("4")||status.equals("5")){
            if(StringUtils.isEmpty(taskMapper.selectBySIdStatus(studentId,status,"0"))){
                return ReturnMessageUtil.error(0, "暂无订单信息！");
            }
            return ReturnMessageUtil.sucess(taskMapper.selectBySIdStatus(studentId,status,"0"));
        }
        return ReturnMessageUtil.error(0, "错误信息！");
    }

    @Override
    public ReturnMessage<Object> takeTaskList(String status, HttpServletRequest request) {
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
            if(StringUtils.isEmpty(taskMapper.selectByTakeTaskId(studentId,"0"))){
                return ReturnMessageUtil.error(0, "暂无订单信息！");
            }
            return ReturnMessageUtil.sucess(taskMapper.selectByTakeTaskId(studentId,"0"));
        }
        /**
         * 0下单 1审核 2 接单 3完成 4支付 5取消
         */
        if(status.equals("0")||status.equals("1")||status.equals("2")||status.equals("3")||status.equals("4")||status.equals("5")){
            if(StringUtils.isEmpty(taskMapper.selectByTIdStatus(studentId,status,"0"))){
                return ReturnMessageUtil.error(0, "暂无订单信息！");
            }
            return ReturnMessageUtil.sucess(taskMapper.selectByTIdStatus(studentId,status,"0"));
        }
        return ReturnMessageUtil.error(0, "错误信息！");
    }

    @Override
    public ReturnMessage<Object> getOrderInfo(Long taskId) {
        ActTask task = taskMapper.selectByPrimaryKey(taskId);
        if (StringUtils.isEmpty(task) || StringUtils.isEmpty(task)) {
            return ReturnMessageUtil.error(0, "订单不存在！");
        }
        //接单人
        SysStudent takeuser = studentMapper.selectByPrimaryKey(task.getStudentRealizeId());
        //审核员
        SysUser checkuser=userMapper.selectByPrimaryKey(task.getUserCheckId());
        String status = task.getStatus();
        /**
         * 0下单 1审核 2 接单 3完成 4支付 5取消
         */
        if (status.equals("0") || status.equals("5") ) {
            return ReturnMessageUtil.sucess(task);
        } else if (status.equals("1") ) {
            return ReturnMessageUtil.sucess(task + "" + checkuser);
        }else if(status.equals("2")|| status.equals("3")|| status.equals("4") ){
            return ReturnMessageUtil.sucess(task + "" + takeuser+""+checkuser);
        }
        return ReturnMessageUtil.error(0,"错误信息");
    }

    @Override
    public ReturnMessage<Object> pay(Long taskId) {
        String outTradeNo="W"+taskId;
        if(taskId==0||StringUtils.isEmpty(taskMapper.selectByPrimaryKey(taskId))){
            return ReturnMessageUtil.error(0,"打印订单id不能为空");
        }
        ActTask task=taskMapper.selectByPrimaryKey(taskId);
        if(StringUtils.isEmpty(task)){
            return ReturnMessageUtil.error(0,"不存在该订单");
        }
        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.GETEWAY_URL, AlipayConfig.APP_ID, AlipayConfig.APP_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGN_TYPE);
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        //       model.setBody();//加签过的订单详情
        //       model.setSubject();
        model.setOutTradeNo(outTradeNo); //交易号 OutTradeNo只能为数字、英文或下划线；此外，OutTradeNo不可以重复，若重复则会出现系统繁忙等错误。
        model.setTimeoutExpress("30m");
        model.setTotalAmount(""+task.getFee());
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
    public ReturnMessage<Object> canceltaskOrder(Long taskId) throws AlipayApiException {
        if(taskId==0||StringUtils.isEmpty(taskMapper.selectByPrimaryKey(taskId))){
            return ReturnMessageUtil.error(0,"打印订单id不能为空");
        }
        ActTask task=taskMapper.selectByPrimaryKey(taskId);
        if(StringUtils.isEmpty(task)){
            return ReturnMessageUtil.error(0,"不存在该订单");
        }
        if(task.getStatus().equals("0")||task.getStatus().equals("1")){
            if(task.getStatus().equals("1")){
                //如果已支付就执行退款操作
                refund(taskId);
            }
            task.setStatus("5");//取消订单
            task.setCancelTime(new Date());//取消时间
            taskMapper.updateByPrimaryKey(task);
            return ReturnMessageUtil.sucess();
        }
        return ReturnMessageUtil.error(0,"不能取消该订单");
    }

    @Override
    public ReturnMessage<Object> confirm(Long taskId) {
        ActTask task=taskMapper.selectByPrimaryKey(taskId);
        if(taskId==0||StringUtils.isEmpty(task)){
            return ReturnMessageUtil.error(0,"不存在该订单");
        }
        if(task.getStatus().equals("2")){
            task.setStatus("3");
            taskMapper.updateByPrimaryKey(task);
            return ReturnMessageUtil.sucess();
        }
        return ReturnMessageUtil.error(0,"收货失败！");
    }

    @Override
    public ReturnMessage<Object> takeOrder(Long taskId, HttpServletRequest request) {
        String token = request.getHeader("x-auth-token");
        if(token==null){
            return ReturnMessageUtil.error(0, "获取token失败");
        }
        Long studentId= JwtTokenUtils.getStudentId(token);
        if(StringUtils.isEmpty(studentId)||StringUtils.isEmpty(StringUtils.isEmpty(studentMapper.selectByPrimaryKey(studentId)))){
            return ReturnMessageUtil.error(0, "学生不存在！");
        }
        ActTask task=taskMapper.selectByPrimaryKey(taskId);
        if(taskId==0||StringUtils.isEmpty(task)){
            return ReturnMessageUtil.error(0,"不存在该订单");
        }
        if(task.getStatus().equals("1")){
            task.setStatus("2");
            task.setStudentRealizeId(studentId);
            taskMapper.updateByPrimaryKey(task);
            return ReturnMessageUtil.sucess();
        }
        return ReturnMessageUtil.error(0,"接单失败！！");
    }

    @Override
    public ActTask getTaskById(Long taskId) {
        return taskMapper.selectByPrimaryKey(taskId);
    }

    @Override
    public ReturnMessage<Object> updateByPrimaryKey(ActTask task) {
        return ReturnMessageUtil.sucess(taskMapper.updateByPrimaryKey(task));
    }

    private ReturnMessage<Object> refund(Long taskId) throws AlipayApiException {
        //response.setContentType("text/html;charset=utf-8");
        //PrintWriter out = response.getWriter();
        //获得初始化的AlipayClient
        String outTradeNo="R"+taskId;
        if(taskId==0||StringUtils.isEmpty(taskMapper.selectByPrimaryKey(taskId))){
            return ReturnMessageUtil.error(0,"打印订单id不能为空");
        }
        ActTask task=taskMapper.selectByPrimaryKey(taskId);
        if(StringUtils.isEmpty(task)){
            return ReturnMessageUtil.error(0,"不存在该订单");
        }
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.GETEWAY_URL, AlipayConfig.APP_ID, AlipayConfig.APP_PRIVATE_KEY, "json", AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGN_TYPE);
        //设置请求参数
        AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();
        //商户订单号，必填
        String out_trade_no = new String(outTradeNo);
        //需要退款的金额，该金额不能大于订单金额，必填
        String refund_amount = new String(task.getFee()+"");
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
