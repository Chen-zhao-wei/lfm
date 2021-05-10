package com.example.lfm.controller;

import com.alipay.api.AlipayApiException;
import com.example.lfm.entity.*;
import com.example.lfm.service.ActPrintService;
import com.example.lfm.service.Impl.DelayService;
import com.example.lfm.service.PickUpService;
import com.example.lfm.service.TaskService;
import com.example.lfm.service.WashService;
import com.example.lfm.utils.*;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import io.lettuce.core.resource.Delay;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.management.resources.agent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.Date;

@RestController
@RequestMapping("/print")
@Api(value = "打印控制器")
public class PrintController {
    @Autowired
    private ActPrintService printService;
    @Autowired
    private DelayService delayService;
    @Autowired
    private FastDFSUtils fastDFSUtils;
    @Autowired
    private PickUpService pickUpService;
    @Autowired
    private WashService washService;
    @Autowired
    private TaskService taskService;

    @ApiOperation("新增打印")
    @PostMapping("/newprint")
    public ReturnMessage<Object> newprint(@RequestBody ActPrint print, HttpServletRequest request) {
        return printService.newprint(print,request);
    }

    @ApiOperation("查看个人打印订单 ")
    @PostMapping("/orderList/{status}")
    public ReturnMessage<Object> orderList( @PathVariable() String status,HttpServletRequest request) {
        return printService.orderList(status,request);
    }

    /**
     * 获取订单信息
     */
    @ApiOperation("查看具体订单 ")
    @PostMapping("/getinfo/{printId}")
    public ReturnMessage<Object> getinfo(@PathVariable() Long printId) {
        return printService.getOrderinfo(printId);
    }

    @ApiOperation("手机支付 ")
    @GetMapping("/pay")
    public ReturnMessage<Object> pay(Long printId) {
        return printService.pay(printId);
    }


    @ApiOperation("取消订单 ")
    @GetMapping("/cancelPrintOrder")
    public ReturnMessage<Object> cancelPrintOrder(Long printId) throws IOException, AlipayApiException {
        return printService.cancelorder(printId);
    }

    /**
     * 支付完成回调验证操作
     * @param response，request
     * @throws Exception
     * @return void
     * @author 有梦想一起实现
     */
    @RequestMapping("notify_url")
    public void Notify(HttpServletResponse response, HttpServletRequest request) throws Exception {
        System.out.println("----------------------------notify_url------------------------");
        // 商户订单号
        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "GBK");
        System.out.println("----------------------------notify_url-----out_trade_no-------------------"+out_trade_no);
        // 付款金额
        String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "GBK");
        System.out.println("----------------------------notify_url---total_amount---------------------"+total_amount);
        // 支付宝交易号
        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "GBK");
        System.out.println("----------------------------notify_url-------trade_no-----------------"+trade_no);
        // 交易说明
        String cus = new String(request.getParameter("body").getBytes("ISO-8859-1"), "GBK");
        System.out.println("----------------------------notify_url---------------cus---------"+cus);
        // 交易状态
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "GBK");
        System.out.println("----------------------------notify_url------------------------"+trade_status);
        if (trade_status.equals("TRADE_SUCCESS")) {//支付成功商家操作
            System.out.println("----------------------------notify_url-------------------成功-----");
            if (StringUtils.isNotEmpty(out_trade_no)&& "R".equals(out_trade_no.substring(0,1))){
                Long printId =  Long.parseLong(out_trade_no.substring(1));
                System.out.println("----------------------------notify_url------------printId------------" + printId);
                ActPrint actPrint = printService.getActPrintById(printId);
                System.out.println("----------------------------notify_url------------actPrint------------" + actPrint.getStatus());
                if ("0".equals(actPrint.getStatus())){
                    System.out.println("----------------------------notify_url----------------getStatus--------");
                    actPrint.setStatus("1");
                    actPrint.setPayTime(new Date());
                    printService.updateByPrimaryKey(actPrint);
                    DshOrder dshOrder = new DshOrder("R"+actPrint.getPrintId(),24 * 60 * 60 * 1000,4);
                    delayService.add(dshOrder);
                }
            }
            if (StringUtils.isNotEmpty(out_trade_no)&& "P".equals(out_trade_no.substring(0,1))){
                Long puckUpId =  Long.parseLong(out_trade_no.substring(1));
                System.out.println("----------------------------notify_url------------puckUpId------------" + puckUpId);
                PickUp pickUp = pickUpService.getPickUpById(puckUpId);
                System.out.println("----------------------------notify_url------------PickUp------------" + pickUp.getStatus());
                if ("0".equals(pickUp.getStatus())){
                    System.out.println("----------------------------notify_url----------------getStatus--------");
                    pickUp.setStatus("1");
                    pickUp.setPayTime(new Date());
                    pickUpService.updateByPrimaryKey(pickUp);
                    DshOrder dshOrder = new DshOrder("P"+pickUp.getPickUpId(),24 * 60 * 60 * 1000,4);
                    delayService.add(dshOrder);
                }
            }
            if (StringUtils.isNotEmpty(out_trade_no)&& "T".equals(out_trade_no.substring(0,1))){
                Long taskId =  Long.parseLong(out_trade_no.substring(1));
                System.out.println("----------------------------notify_url------------puckUpId------------" + taskId);
                ActTask task = taskService.getTaskById(taskId);
                System.out.println("----------------------------notify_url------------PickUp------------" + task.getStatus());
                if ("3".equals(task.getStatus())){
                    System.out.println("----------------------------notify_url----------------getStatus--------");
                    task.setStatus("4");
                    task.setPayTime(new Date());
                    taskService.updateByPrimaryKey(task);
                    DshOrder dshOrder = new DshOrder("P"+task.getTaskId(),24 * 60 * 60 * 1000,4);
                    delayService.add(dshOrder);
                }
            }
            if (StringUtils.isNotEmpty(out_trade_no)&& "W".equals(out_trade_no.substring(0,1))){
                Long washId =  Long.parseLong(out_trade_no.substring(1));
                System.out.println("----------------------------notify_url------------washId------------" + washId);
                Washing washing = washService.getwashById(washId);
                System.out.println("----------------------------notify_url------------washing------------" + washing.getStatus());
                if ("0".equals(washing.getStatus())){
                    System.out.println("----------------------------notify_url----------------getStatus--------");
                    washing.setStatus("1");
                    washing.setPayTime(new Date());
                    washService.updateByPrimaryKey(washing);
                    DshOrder dshOrder = new DshOrder("P"+washing.getWashingId(),24 * 60 * 60 * 1000,4);
                    delayService.add(dshOrder);
                }
            }
        }
    }

    /**
     *退款
     */
    @ApiOperation("退款 ")
    @GetMapping("/refund")
    public ReturnMessage<Object> refund(Long printId) throws IOException, AlipayApiException {
        return printService.refund(printId);
    }

    @ApiOperation("确认收货 ")
    @GetMapping("/confirm")
    public ReturnMessage<Object> confirm(Long printId)  {
        return printService.confirm(printId);
    }
    /**
     * 上传文件接口
     */
    @ApiOperation("文件上传 ")
    @PostMapping("/upload")
    public ReturnMessage<Object> avatar(@RequestParam("file") MultipartFile file) throws IOException
    {
        if (!file.isEmpty())
        {
            File file1  = fastDFSUtils.uploadPrint(file);
            return ReturnMessageUtil.sucess(file1);
        }
        return ReturnMessageUtil.error(0,"文件不存在");
    }

    /**
     * 下载文件接口
     */
    @ApiOperation("下载文件 ")
    @GetMapping(value = "/download")
    public void download(@RequestParam("fileName") String fileName,@RequestParam("fileUrl") String fileUrl, HttpServletResponse httpServletResponse,HttpServletRequest httpServletRequest) throws MalformedURLException {
        if(!fileName.isEmpty() && !fileUrl.isEmpty()){
            try {
                byte[] b = fastDFSUtils.downloadFile(fileUrl);
                httpServletResponse.reset();
                httpServletResponse.setContentType("application/x-download");
                String agent = httpServletRequest.getHeader("User-Agent");
                if(agent.toLowerCase().indexOf("firefox")!=-1) {
                    httpServletResponse.addHeader("content-Disposition", "attachment; filename==?UTF-8?B?" + new String(Base64.encodeBase64(fileName.getBytes("UTF-8")))+"?=");
                }else {
                    httpServletResponse.addHeader("content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName,"UTF-8"));
                }
                httpServletResponse.getOutputStream().write(b);
                httpServletResponse.getOutputStream().close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
