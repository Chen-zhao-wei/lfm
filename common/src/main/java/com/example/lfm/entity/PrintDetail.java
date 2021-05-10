package com.example.lfm.entity;

public class PrintDetail {
    private ActPrint print;
    private SysUser printuser;
    private SysUser deliveruser;

    public ActPrint getPrint() {
        return print;
    }

    public void setPrint(ActPrint print) {
        this.print = print;
    }

    public SysUser getPrintuser() {
        return printuser;
    }

    public void setPrintuser(SysUser printuser) {
        this.printuser = printuser;
    }

    public SysUser getDeliveruser() {
        return deliveruser;
    }

    public void setDeliveruser(SysUser deliveruser) {
        this.deliveruser = deliveruser;
    }
}
