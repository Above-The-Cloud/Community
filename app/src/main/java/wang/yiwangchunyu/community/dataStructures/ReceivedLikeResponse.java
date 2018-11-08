package wang.yiwangchunyu.community.dataStructures;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xinyu jiang on 2018/11/5.
 */

public class ReceivedLikeResponse {
    private String code;//请求状态码  0：正常， 1：用户不存在
    private String errcode;//错误码
    private String msg;
    private List<LikeInfo> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<LikeInfo> getData() {
        return data;
    }

    public void setData(List<LikeInfo> data) {
        this.data = data;
    }

}
