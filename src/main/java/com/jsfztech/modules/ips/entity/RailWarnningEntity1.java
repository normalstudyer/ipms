package com.jsfztech.modules.ips.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2018/10/16.
 */
public class RailWarnningEntity1 implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Integer id;
    //围栏名称
    private String rail_name;
    //人员编号
    private String target_name;
    //触发时间
    private Date trigger_date;
    //1:进入，2：离开
    private Integer trigger_type;
    //信息描述
    private String info;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTrigger_date() {
        return trigger_date;
    }

    public void setTrigger_date(Date trigger_date) {
        this.trigger_date = trigger_date;
    }

    public Integer getTrigger_type() {
        return trigger_type;
    }

    public void setTrigger_type(Integer trigger_type) {
        this.trigger_type = trigger_type;
    }

    public String getTarget_name() {
        return target_name;

    }

    public void setTarget_name(String target_name) {
        this.target_name = target_name;
    }

    public String getRail_name() {

        return rail_name;
    }

    public void setRail_name(String rail_name) {
        this.rail_name = rail_name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
