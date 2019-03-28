package com.wyb.accout.bean.entity;


/**
 * 微信用户的信息封装对象
 *
 * @author FE
 * @date 2016年7月20日 下午1:38:40
 */
public class WeixinUserInfoEntity extends WeixinBasicEntity {

    private static final long serialVersionUID = 8339551270088976798L;

    private String openid;        //openid
    private String unionid;    //用户unionid
    private String nickname;   //昵称
    private String sex;        //性别
    private String province;    //用户所在省
    private String city;        //用户所在市
    private String country;    //用户所在区
    private String headimgurl; //用户头像
    private String privilege;  //用户拥有权限

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    @Override
    public String toString() {
        return "WeinXinUserInfo [openid=" + openid + ", unionid=" + unionid
                + ", nickname=" + nickname + ", sex=" + sex + ", province="
                + province + ", city=" + city + ", country=" + country
                + ", headimgurl=" + headimgurl + ", privilege=" + privilege
                + "]";
    }
}
