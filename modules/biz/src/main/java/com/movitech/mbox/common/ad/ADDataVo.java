package com.movitech.mbox.common.ad;

/**
 * Created by gorden on 2017/8/8.
 * 对接佳兆业
 */
public class ADDataVo {
    /**
     * name 用户名,mail 电子邮箱,sAMAccountName 域账号，手机号
     * */
    private  String departDesc,name,mail,sAMAccountName,telephoneNumber;

    public String getDepartDesc() {
        return departDesc;
    }

    public void setDepartDesc(String departDesc) {
        this.departDesc = departDesc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getsAMAccountName() {
        return sAMAccountName;
    }

    public void setsAMAccountName(String sAMAccountName) {
        this.sAMAccountName = sAMAccountName;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }
}
