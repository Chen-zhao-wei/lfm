package com.example.lfm.utils;

import com.aspose.words.Document;
import com.example.lfm.entity.File;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.itextpdf.text.pdf.PdfReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Component
public class FastDFSUtils {

    //分布式文件系统IP
    public final String fastDFSHost = "47.119.126.86";
    //分布式文件系统端口
    public final String fastDFSPort = "8888";

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    private static final List<String> AVATAR_CONTENT_TYPES = Arrays.asList("image/jpeg", "image/png");

    private static final List<String> PRINT_CONTENT_TYPES = Arrays.asList("application/pdf", "application/msword","application/vnd.openxmlformats-officedocument.wordprocessingml.document");
//    @Autowired
//    private TrackerClient trackerClient;

    private final Logger logger = LoggerFactory.getLogger(FastDFSUtils.class);

    /**
     * 头像上传
     */
    public String uploadAvatar(MultipartFile file) throws IOException {
        // 校验文件的类型
        String contentType = file.getContentType();
        if (!AVATAR_CONTENT_TYPES.contains(contentType)){
            // 文件类型不合法，直接返回null
            throw new SbException(0,"文件类型不正确");
        }
        String fileName = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();
        long size = file.getSize();
        StorePath storePath = fastFileStorageClient.uploadFile(inputStream,size,fileName.substring(fileName.lastIndexOf(".")+1),null);
        String fileUrl = getResAccessUrl(storePath);
        return fileUrl;
    }

    /**
     * 文件上传
     */
    public File uploadPrint(MultipartFile file) throws IOException {
        // 校验文件的类型
        String contentType = file.getContentType();
        if (!PRINT_CONTENT_TYPES.contains(contentType)){
            // 文件类型不合法，直接返回null
            throw new SbException(0,"文件类型不正确");
        }
        String fileName = file.getOriginalFilename();
        String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
        int pageNum = 0;
        if( "doc".equals(prefix) || "docx".equals(prefix)){
            Document doc = null;
            try {
                doc = new Document(file.getInputStream());
                pageNum = doc.getPageCount();
            } catch (Exception e) {
                e.printStackTrace();
                throw new SbException(0,"文件上传失败");
            }
        }
        //pdf获取附件总页数
        if( "pdf".equals(prefix)){
            PdfReader pdfReader = new PdfReader(file.getInputStream());
            pageNum = pdfReader.getNumberOfPages();
        }
        InputStream inputStream = file.getInputStream();
        long size = file.getSize();
        StorePath storePath = fastFileStorageClient.uploadFile(inputStream,size,fileName.substring(fileName.lastIndexOf(".")+1),null);
        String fileUrl = getResAccessUrl(storePath);
        File file1 = new File(fileName,fileUrl,pageNum);
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