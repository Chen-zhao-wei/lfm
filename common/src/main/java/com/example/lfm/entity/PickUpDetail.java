package com.example.lfm.entity;

public class PickUpDetail {
    private PickUp pickUp;
    private SysUser pickUser;

    public PickUp getPickUp() {
        return pickUp;
    }

    public void setPickUp(PickUp pickUp) {
        this.pickUp = pickUp;
    }

    public SysUser getPickUser() {
        return pickUser;
    }

    public void setPickUser(SysUser pickUser) {
        this.pickUser = pickUser;
    }
}
