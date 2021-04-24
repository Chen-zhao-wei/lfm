package com.example.lfm.controller;

import com.alipay.api.AlipayApiException;
import com.example.lfm.entity.ActPrint;
import com.example.lfm.entity.File;
import com.example.lfm.service.ActPrintService;
import com.example.lfm.utils.FastDFSUtils;
import com.example.lfm.utils.ReturnMessageUtil;
import com.example.lfm.utils.SbException;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.lfm.utils.ReturnMessage;
import org.springframework.web.multipart.MultipartFile;
import sun.management.resources.agent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/print")
@Api(value = "打印控制器")
public class PrintController {
    @Autowired
    private ActPrintService printService;

    @Autowired
    private FastDFSUtils fastDFSUtils;

    @ApiOperation("新增打印")
    @PostMapping("/newprint")
    public ReturnMessage<Object> newprint(@RequestBody ActPrint print) {
        return printService.newprint(print);
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
        // 付款金额
        String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "GBK");
        // 支付宝交易号
        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "GBK");
        // 交易说明
        String cus = new String(request.getParameter("body").getBytes("ISO-8859-1"), "GBK");
        // 交易状态
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "GBK");
        if (trade_status.equals("TRADE_SUCCESS")) {//支付成功商家操作

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
