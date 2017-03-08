package org.seckill.entity;

import java.util.*;

/**
 * Created by pc on 2017/3/5.
 */
public class SuccessKilled {
    private long seckillid;
    private long userPhone;
    private Date createTime;
    private short state;
    //多对一
    private Seckill seckill;


    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public long getSeckillid() {
        return seckillid;
    }

    public void setSeckillid(long seckillid) {
        this.seckillid = seckillid;
    }

    public long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(long userPhone) {
        this.userPhone = userPhone;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Seckill getSeckill() {
        return seckill;
    }

    public void setSeckill(Seckill seckill) {
        this.seckill = seckill;
    }

    @Override
    public String toString() {
        return "SuccessKilled{" +
                "seckillid=" + seckillid +
                ", userPhone=" + userPhone +
                ", createTime=" + createTime +
                ", state=" + state +
                '}';
    }
}
