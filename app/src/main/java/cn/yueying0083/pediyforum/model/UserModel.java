package cn.yueying0083.pediyforum.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by luoj@huoli.com on 2016/11/18.
 */

public class UserModel implements java.io.Serializable {

    private static final long serialVersionUID = 7440096839758111545L;

    @SerializedName("username")
    private String username;// 用户名
    @SerializedName("passwordMD5")
    private String passwordMD5;// 本地保存密码MD5
    @SerializedName("userid")
    private String id;// 用户ID
    @SerializedName("usertitle")
    private String rank;// 用户头衔
    @SerializedName("posts")
    private String posts;// 发帖数
    @SerializedName("languageid")
    private String languageid;// 用户浏览语音
    @SerializedName("money")
    private String money;// 用户金钱数
    @SerializedName("goodnees")
    private String goodnees;// 精华数
    @SerializedName("avatar")
    private String avatar;// 是否有头像，0无头像，1有头象
    @SerializedName("avatardateline")
    private String avatardateline;// 头像上传日期时间戳，如无头像返回0

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

    public String getPosts() {
        return posts;
    }

    public void setPosts(String posts) {
        this.posts = posts;
    }

    public String getLanguageid() {
        return languageid;
    }

    public void setLanguageid(String languageid) {
        this.languageid = languageid;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getGoodnees() {
        return goodnees;
    }

    public void setGoodnees(String goodnees) {
        this.goodnees = goodnees;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatardateline() {
        return avatardateline;
    }

    public void setAvatardateline(String avatardateline) {
        this.avatardateline = avatardateline;
    }
}
