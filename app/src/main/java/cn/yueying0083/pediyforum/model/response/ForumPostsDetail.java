package cn.yueying0083.pediyforum.model.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by luoj@huoli.com on 2016/11/24.
 */

public class ForumPostsDetail extends BaseResponseModel implements Serializable {

    private static final long serialVersionUID = 1423486980439438149L;
    @SerializedName("time")
    private int time;
    @SerializedName("pagenav")
    private int totalPage;
    @SerializedName("postbits")
    private List<Detail> detailList;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<Detail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<Detail> detailList) {
        this.detailList = detailList;
    }

    public static class Detail implements Serializable {
        private static final long serialVersionUID = -7680774730525323605L;
        @SerializedName("postid")
        private int postsId;
        @SerializedName("posttime")
        private String postsTime;
        @SerializedName("postdate")
        private String postsDate;
        @SerializedName("thumbnail")
        private int postsThumbnail;
        @SerializedName("title")
        private String postsTitle;
        @SerializedName("message")
        private String postsContent;

        @SerializedName("username")
        private String authorName;
        @SerializedName("userid")
        private int authorId;
        @SerializedName("avatar")
        private int authorAvatar;
        @SerializedName("avatardateline")
        private int authorAvatarDateLine;

        @SerializedName("thumbnailattachments")
        private List<Attachment> attachmentList;

        public int getPostsId() {
            return postsId;
        }

        public void setPostsId(int postsId) {
            this.postsId = postsId;
        }

        public String getPostsTime() {
            return postsTime;
        }

        public void setPostsTime(String postsTime) {
            this.postsTime = postsTime;
        }

        public String getPostsDate() {
            return postsDate;
        }

        public void setPostsDate(String postsDate) {
            this.postsDate = postsDate;
        }

        public int getPostsThumbnail() {
            return postsThumbnail;
        }

        public void setPostsThumbnail(int postsThumbnail) {
            this.postsThumbnail = postsThumbnail;
        }

        public String getPostsTitle() {
            return postsTitle;
        }

        public void setPostsTitle(String postsTitle) {
            this.postsTitle = postsTitle;
        }

        public String getPostsContent() {
            return postsContent;
        }

        public void setPostsContent(String postsContent) {
            this.postsContent = postsContent;
        }

        public String getAuthorName() {
            return authorName;
        }

        public void setAuthorName(String authorName) {
            this.authorName = authorName;
        }

        public int getAuthorId() {
            return authorId;
        }

        public void setAuthorId(int authorId) {
            this.authorId = authorId;
        }

        public int getAuthorAvatar() {
            return authorAvatar;
        }

        public void setAuthorAvatar(int authorAvatar) {
            this.authorAvatar = authorAvatar;
        }

        public int getAuthorAvatarDateLine() {
            return authorAvatarDateLine;
        }

        public void setAuthorAvatarDateLine(int authorAvatarDateLine) {
            this.authorAvatarDateLine = authorAvatarDateLine;
        }

        public List<Attachment> getAttachmentList() {
            return attachmentList;
        }

        public void setAttachmentList(List<Attachment> attachmentList) {
            this.attachmentList = attachmentList;
        }

        public static class Attachment implements Serializable {
            private static final long serialVersionUID = -7768076029652506734L;

            @SerializedName("attachmentid")
            private int id;
            @SerializedName("filename")
            private String name;
            @SerializedName("filesize")
            private int size;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }
        }

    }
}
