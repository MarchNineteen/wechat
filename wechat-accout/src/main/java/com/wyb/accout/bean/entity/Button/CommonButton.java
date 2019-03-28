package com.wyb.accout.bean.entity.Button;

/**
 * Created by Kunzite on 2016/9/29.
 */

/**
 * 表示二级菜单(CLICK类型)
 */
public class CommonButton extends Button {

    private String type;//菜单类型
    private String key;//key值

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
