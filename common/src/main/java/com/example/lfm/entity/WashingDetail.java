package com.example.lfm.entity;

public class WashingDetail {
    private Washing washing ;
    private SysUser takeuser;
    private SysUser fetchuser;
    private SysUser washinguser;
    private SysUser delivery;

    public Washing getWashing() {
        return washing;
    }

    public void setWashing(Washing washing) {
        this.washing = washing;
    }

    public SysUser getTakeuser() {
        return takeuser;
    }

    public void setTakeuser(SysUser takeuser) {
        this.takeuser = takeuser;
    }

    public SysUser getFetchuser() {
        return fetchuser;
    }

    public void setFetchuser(SysUser fetchuser) {
        this.fetchuser = fetchuser;
    }

    public SysUser getWashinguser() {
        return washinguser;
    }

    public void setWashinguser(SysUser washinguser) {
        this.washinguser = washinguser;
    }

    public SysUser getDelivery() {
        return delivery;
    }

    public void setDelivery(SysUser delivery) {
        this.delivery = delivery;
    }
}
