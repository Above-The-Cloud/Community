package wang.yiwangchunyu.community.dataStructures;

import java.util.List;

/**
 * Created by xinyu jiang on 2018/11/8.
 */

public class ChangeStatusResponse {
    private String code;//请求状态码  0：正常， 1：用户不存在
    private String msg;
    private List data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }
}
