package com.wyb.mp.qq.bean.result;

import com.wyb.mp.qq.util.json.QqGsonBuilder;

/**
 * @author Kunzite
 */
public class QqUserInfo {

    private String ret;
    private String msg;
    private String nickname;
    private String figureUrl;
    private String figureUrl_1;
    private String figureUrl_2;
    private String figureUrlQq_1;
    private String figureUrlQq_2;
    private String gender;

    public static QqUserInfo fromJson(String json) {
        return QqGsonBuilder.create().fromJson(json, QqUserInfo.class);
    }

    @Override
    public String toString() {
        return QqGsonBuilder.create().toJson(this);
    }


    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFigureUrl() {
        return figureUrl;
    }

    public void setFigureUrl(String figureUrl) {
        this.figureUrl = figureUrl;
    }

    public String getFigureUrl_1() {
        return figureUrl_1;
    }

    public void setFigureUrl_1(String figureUrl_1) {
        this.figureUrl_1 = figureUrl_1;
    }

    public String getFigureUrl_2() {
        return figureUrl_2;
    }

    public void setFigureUrl_2(String figureUrl_2) {
        this.figureUrl_2 = figureUrl_2;
    }

    public String getFigureUrlQq_1() {
        return figureUrlQq_1;
    }

    public void setFigureUrlQq_1(String figureUrlQq_1) {
        this.figureUrlQq_1 = figureUrlQq_1;
    }

    public String getFigureUrlQq_2() {
        return figureUrlQq_2;
    }

    public void setFigureUrlQq_2(String figureUrlQq_2) {
        this.figureUrlQq_2 = figureUrlQq_2;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
