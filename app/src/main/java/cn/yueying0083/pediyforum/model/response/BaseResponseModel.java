package cn.yueying0083.pediyforum.model.response;

/**
 * Created by luoj@huoli.com on 2016/11/23.
 */

public class BaseResponseModel {

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
