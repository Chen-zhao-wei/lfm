package com.example.lfm.service.Impl;

import com.example.lfm.entity.ActPrint;
import com.example.lfm.entity.DshOrder;
import com.example.lfm.service.ActPrintService;
import com.example.lfm.utils.RedisCache;
import com.example.lfm.utils.SbException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.DelayQueue;

/**
 * 延时队列service
 * @author Administrator
 *
 */
@Service
public class DelayService {
    private boolean start;//判断是否启动队列

    private OnDelayedListener listener;//内部接口监听器

    private DelayQueue<DshOrder> delayQueue = new DelayQueue<DshOrder>(); //队列集合

    private Log log = LogFactory.getLog(DelayService.class);

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ActPrintService actPrintService;

    @Autowired
    private DelayService delayService;
    public static interface OnDelayedListener{
        public void onDelayedArrived(DshOrder order);
    }


    public void start(OnDelayedListener listener) {
        if (start) {
            log.error(">>>>>>>>>>>>DelayService已经在启动状态");
            return;
        }
        log.info(">>>>>>>>>>>>DelayService 启动");
        start = true;
        this.listener = listener;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true) {
                        log.info("*********准备获取延迟队列里面将要执行的队列*******");
                        /* 延时队列会将加入队列中的元素按照过期时间的先后顺序排序，先过期的在队首，该take方法会判断队首
                         * 元素是否过期，如果没过期，会阻塞等待，直到队首元素过期，才会取出来，往下执行逻辑 */
                        DshOrder order = delayQueue.take();
                        log.info(order.getOrderNo());
                        if(order.getType() == 1){   //如果是类型为1，未付款取消订单
                            log.info(order.getOrderNo());
                            if (!order.getOrderNo().isEmpty()){
                                String p = order.getOrderNo().substring(0,1);
                                Long id = Long.parseLong(order.getOrderNo().substring(1));
                                if (p == "R"){
                                    //打印订单
                                    ActPrint print = actPrintService.getActPrintById(id);
                                    if (print.getStatus() == "0"){ // 就代表还未支付
                                        print.setStatus("5"); //设置为取消订单
                                    }
                                }
                            }
                        }

                        if (DelayService.this.listener != null) {
                            DelayService.this.listener.onDelayedArrived(order);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public void add(DshOrder order){
        //写入队列
        delayQueue.put(order);
        //存入redis
        redisCache.setCacheMapValue("ORDER", order.getOrderNo(), order);
        log.info("**************订单号:" + order.getOrderNo() + "被写入订单成功!*************");
    }

    public boolean remove(DshOrder order){
        //从redis中删除
        redisCache.removeCacheMapValue("ORDER", order.getOrderNo());
        log.info("**************订单号:" + order.getOrderNo() + "被删除成功!*************");
        //从队列里面删除
        return delayQueue.remove(order);

    }

    public void remove(String orderNo){
        DshOrder[] array = delayQueue.toArray(new DshOrder[]{});
        if(array == null || array.length <= 0){
            return;
        }
        DshOrder target = null;
        for(DshOrder order : array){
            if(order.getOrderNo().equals(orderNo)){
                target = order;
                break;
            }
        }
        if(target != null){
            this.remove(target);
        }
    }
//    public boolean  alipayReturn(Integer oId){
//        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
//        //设置请求参数
//        AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();
//        Order temp = new Order();
//        temp.setoId(oId);
//        List<OrderVo> orderVos = orderService.queryOrderVoByOthers(temp);
//        //商户订单号，商户网站订单系统中唯一订单号
//        String out_trade_no = "" + oId;
//        //支付宝交易号
//        String trade_no = orderVos.get(0).getoTradeNo();
//        //请二选一设置
//        //需要退款的金额，该金额不能大于订单金额，必填
//        String refund_amount = ""+ orderVos.get(0).getGoodsVo().getgPrice();
//        //退款的原因说明
//        String refund_reason = "";
//        //标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传
//        String out_request_no = "";
////
//        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
//                + "\"trade_no\":\""+ trade_no +"\","
//                + "\"refund_amount\":\""+ refund_amount +"\","
//                + "\"refund_reason\":\""+ refund_reason +"\","
//                + "\"out_request_no\":\""+ out_request_no +"\"}");
//
//        //请求
//        AlipayTradeRefundResponse response;
//        try {
//            response = alipayClient.execute(alipayRequest);
//            if (!response.isSuccess()) {
//                String returnStr = response.getSubMsg();//失败会返回错误信息
//                System.out.println(returnStr);
//            }
//            if(response.isSuccess()){
//                System.out.println("退款请求发送成功");
//            }
//            //判断退款是否成功
//            if(response.getFundChange().equals("Y")){
//                System.out.println("退款成功");
//            }
//            return response.isSuccess();
//        } catch (AlipayApiException e) {
//            System.out.println("11111111111111111111111111111111111111111111111");
//            e.printStackTrace();
//        }
//        return false;
//    }
}
