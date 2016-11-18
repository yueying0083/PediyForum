package cn.yueying0083.pediyforum.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by luoj@huoli.com on 2016/11/18.
 */

public class LoginResponse implements java.io.Serializable {

    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
