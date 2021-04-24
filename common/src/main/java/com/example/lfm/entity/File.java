package com.example.lfm.entity;

public class File {
    private String fileName;
    private String fileUrl;
    private int pageNum;

    public File(String fileName, String fileUrl,int pageNum) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.pageNum = pageNum;
    }

    public File() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    @Override
    public String toString() {
        return "File{" +
                "fileName='" + fileName + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                ", pageNum=" + pageNum +
                '}';
    }
}
