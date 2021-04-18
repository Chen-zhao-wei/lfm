package com.example.lfm.utils;

import com.example.lfm.entity.File;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Component
public class FastDFSUtils {

    //分布式文件系统IP
    public final String fastDFSHost = "47.119.126.86";
    //分布式文件系统端口
    public final String fastDFSPort = "8888";

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

//    @Autowired
//    private TrackerClient trackerClient;

    private final Logger logger = LoggerFactory.getLogger(FastDFSUtils.class);

    /**
     * 文件上传
     */
    public File upload(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();
        long size = file.getSize();
        StorePath storePath = fastFileStorageClient.uploadFile(inputStream,size,fileName.substring(fileName.lastIndexOf(".")+1),null);
        String fileUrl = getResAccessUrl(storePath);
        File file1 = new File(fileName,fileUrl);
        return file1;
    }

    /**
     * 下载文件
     * @param fileUrl
     * @return
     * @throws IOException
     */
    public byte[] downloadFile(String fileUrl) throws IOException {
        String group = fileUrl.substring(0, fileUrl.indexOf("/"));
        String path = fileUrl.substring(fileUrl.indexOf("/") + 1);
        DownloadByteArray downloadByteArray = new DownloadByteArray();
        byte[] bytes = fastFileStorageClient.downloadFile(group, path, downloadByteArray);
        return bytes;
    }

    /**
     * 测试文件删除
     */
    public void deleteFile(){
        fastFileStorageClient.deleteFile("group1","M00/00/00/wKiAjVlpQVyARpQwAADGA0F72jo566.jpg");
    }
    // 封装文件完整URL地址
    private String getResAccessUrl(StorePath storePath) {
        String fileUrl = "http://"+ fastDFSHost+ ":"+fastDFSPort+"/" + storePath.getFullPath();
        return fileUrl;
    }
}