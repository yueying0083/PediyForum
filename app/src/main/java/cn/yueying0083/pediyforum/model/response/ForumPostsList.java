package cn.yueying0083.pediyforum.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import cn.yueying0083.pediyforum.model.ForumPosts;

/**
 * Created by luoj@huoli.com on 2016/11/23.
 */

public class ForumPostsList extends BaseResponseModel implements java.io.Serializable {

    private static final long serialVersionUID = -7412863543585146352L;
    @SerializedName("threadList")
    private List<ForumPosts> postsList;

    @SerializedName("pagenav")
    private int totalPages;

    @SerializedName("time")
    private int time;

    public List<ForumPosts> getPostsList() {
        return postsList;
    }

    public void setPostsList(List<ForumPosts> postsList) {
        this.postsList = postsList;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
