package com.example.lfm.entity;

import java.io.Serializable;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 订单队列对象
 * @author Administrator
 *
 */
public class DshOrder implements Delayed,Serializable{

    private String orderNo;//订单号

    private long startTime; // 超时时间

    private Integer type;//延迟的类型

    /**
     * 构造方法
     */
    public DshOrder() {}

    public DshOrder(String orderNo, long timeout) {
        this.orderNo = orderNo;
        this.startTime = System.currentTimeMillis() + timeout;
    }

    public DshOrder(String orderNo, long timeout, Integer type) {
        this.orderNo = orderNo;
        this.startTime = System.currentTimeMillis() + timeout;
        this.type = type;
    }

    @Override
    public int compareTo(Delayed other) {
        if (other == this) {
            return 0;
        }
        if (other instanceof DshOrder) {
            DshOrder otherRequest = (DshOrder)other;
            long otherStartTime = otherRequest.getStartTime();
            return (int)(this.startTime - otherStartTime);
        }
        return 0;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(startTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }




    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DshOrder other = (DshOrder) obj;
        if (orderNo.equals(other.getOrderNo()))
            return false;
        if (startTime != other.startTime)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = result * 31 + (int)orderNo.hashCode();
        result = result * 31 + (int)startTime;
        return result;
    }

    @Override
    public String toString() {
        return "DSHOrder [orderNo=" + orderNo + ", startTime=" + startTime + "]";
    }
}