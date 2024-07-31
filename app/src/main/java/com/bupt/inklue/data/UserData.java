package com.bupt.inklue.data;

//用户数据
public class UserData {

    private String account;//账号
    private String password;//密码

    public UserData(String user_name, String user_password) {
        this.account = user_name;
        this.password = user_password;
    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public void setAccount(String user_name) {
        this.account = user_name;
    }

    public void setPassword(String user_password) {
        this.password = user_password;
    }
}
