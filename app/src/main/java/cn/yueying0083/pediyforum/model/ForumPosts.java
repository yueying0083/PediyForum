package cn.yueying0083.pediyforum.model;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by luoj@huoli.com on 2016/11/23.
 */

public class ForumPosts implements java.io.Serializable {

    private static final long serialVersionUID = 6006696911021271386L;
    @SerializedName("threadid")
    private int id;
    @SerializedName("threadtitle")
    private String title;

    @SerializedName("postuserid")
    private int authorId;
    @SerializedName("postusername")
    private String authorName;
    @SerializedName("avatar")
    private int authorAvatar;
    @SerializedName("avatardateline")
    private int authorAvatarLine;

    @SerializedName("lastpostdate")
    private String lastPostDate;
    @SerializedName("views")
    private int viewCounts;
    @SerializedName("replycount")
    private int replyCounts;

    @SerializedName("globalsticky")
    private int globalSticky;// 是否全局置顶，-1 -> yes， 0 -> no
    @SerializedName("sticky")
    private int sticky;// 是否置顶，1 -> yes， 0 -> no

    @SerializedName("goodnees")
    private int goodness;// 是否精华 0：表示不是精华帖 1：转帖 2：关注 10：优秀 40：精华 80：酷帖 ** 1：收集 10：利器 30 ：独家

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getAuthorAvatar() {
        return authorAvatar;
    }

    public void setAuthorAvatar(int authorAvatar) {
        this.authorAvatar = authorAvatar;
    }

    public int getAuthorAvatarLine() {
        return authorAvatarLine;
    }

    public void setAuthorAvatarLine(int authorAvatarLine) {
        this.authorAvatarLine = authorAvatarLine;
    }

    public String getLastPostDate() {
        return lastPostDate;
    }

    public void setLastPostDate(String lastPostDate) {
        this.lastPostDate = lastPostDate;
    }

    public int getViewCounts() {
        return viewCounts;
    }

    public void setViewCounts(int viewCounts) {
        this.viewCounts = viewCounts;
    }

    public int getReplyCounts() {
        return replyCounts;
    }

    public void setReplyCounts(int replyCounts) {
        this.replyCounts = replyCounts;
    }

    public int getGlobalSticky() {
        return globalSticky;
    }

    public void setGlobalSticky(int globalSticky) {
        this.globalSticky = globalSticky;
    }

    public int getSticky() {
        return sticky;
    }

    public void setSticky(int sticky) {
        this.sticky = sticky;
    }

    public int getGoodness() {
        return goodness;
    }

    public void setGoodness(int goodness) {
        this.goodness = goodness;
    }

    static Map<String, String> goodnessMap;

    static {
        goodnessMap = new HashMap<>();
        goodnessMap.put("10", "优秀");
        goodnessMap.put("40", "精华");
        goodnessMap.put("80", "酷帖");
        goodnessMap.put("30", "独家");
    }

    public String getGoodnessText(String key) {
        return goodnessMap.get(key);
    }
}
