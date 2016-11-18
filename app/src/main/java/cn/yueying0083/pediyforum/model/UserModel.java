package cn.yueying0083.pediyforum.model;

/**
 * Created by luoj@huoli.com on 2016/11/18.
 */

public class UserModel {

    private String username;
    private String passwordMD5;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordMD5() {
        return passwordMD5;
    }

    public void setPasswordMD5(String passwordMD5) {
        this.passwordMD5 = passwordMD5;
    }
}
