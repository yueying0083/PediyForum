package cn.yueying0083.pediyforum.model;

/**
 * Created by luoj@huoli.com on 2016/11/18.
 */

public class UserModel implements java.io.Serializable {

    private String username;
    private String passwordMD5;
    private String id;
    private String rank;
    private String registeTime;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getRegisteTime() {
        return registeTime;
    }

    public void setRegisteTime(String registeTime) {
        this.registeTime = registeTime;
    }
}
