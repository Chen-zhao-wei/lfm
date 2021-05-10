package com.example.lfm.entity;

public class TaskDetail {
    private ActTask task ;
    private SysStudent takeuser;
    private SysUser checkuser;

    public ActTask getTask() {
        return task;
    }

    public void setTask(ActTask task) {
        this.task = task;
    }

    public SysStudent getTakeuser() {
        return takeuser;
    }

    public void setTakeuser(SysStudent takeuser) {
        this.takeuser = takeuser;
    }

    public SysUser getCheckuser() {
        return checkuser;
    }

    public void setCheckuser(SysUser checkuser) {
        this.checkuser = checkuser;
    }
}
