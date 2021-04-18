package com.example.lfm.controller;

import com.example.lfm.entity.ActPrint;
import com.example.lfm.entity.File;
import com.example.lfm.service.ActPrintService;
import com.example.lfm.utils.FastDFSUtils;
import com.example.lfm.utils.ReturnMessageUtil;
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
    @PostMapping("/orderList/{studentId}/{status}")
    public ReturnMessage<Object> orderList(@PathVariable() Long studentId, @PathVariable() String status) {
        return printService.orderList(studentId, status);
    }

    @ApiOperation("查看具体订单 ")
    @PostMapping("/SelectByKey/{printId}")
    public ReturnMessage<Object> SelectByKey(@PathVariable() Long printId) {
        return printService.SelectByKey(printId);
    }

    /**
     * 获取订单信息
     */
    @ApiOperation("手机支付 ")
    @GetMapping("/getOrderInfo")
    public ReturnMessage<Object> getOrderInfo(Long printId) {
        return printService.getOrderInfo(printId);
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
            File file1 = fastDFSUtils.upload(file);
            return ReturnMessageUtil.sucess(file1);
        }
        return ReturnMessageUtil.error(0,"订单不存在");
    }

    /**
     * 上传文件接口
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
