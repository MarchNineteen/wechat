package com.wyb.accout.bean.entity.Button;

/**
 * Created by Kunzite on 2016/9/29.
 */
public class ComplexButton extends Button {

    private String name;//菜单名称
    private Button[] sub_button;//子级菜单，因为一个一级菜单可以有多个二级菜单，所以这儿使用二级 菜单类型的集合

    public Button[] getSub_button() {
        return sub_button;
    }

    public void setSub_button(Button[] sub_button) {
        this.sub_button = sub_button;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }


}
