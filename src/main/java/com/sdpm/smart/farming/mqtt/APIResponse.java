package com.sdpm.smart.farming.mqtt;


/**
 * 响应
 *
 * @author shirukai
 */
public class APIResponse {
    private Boolean success;
    private Integer code;
    private String msg;
    private String contentType;
    private Object data;

    public APIResponse() {
    }

    public APIResponse(Boolean success, Integer code, String msg, String contentType, Object data) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.contentType = contentType;
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "APIResponse{" +
                "success=" + success +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", contentType='" + contentType + '\'' +
                ", data=" + data +
                '}';
    }
}
