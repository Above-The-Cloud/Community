package wang.yiwangchunyu.community.dataStructures;

import java.util.List;

/**
 * Created by yiwangchunyu on 2018/3/16.
 */

public class TasksResponse {
    private String ret;//请求状态码  0：正常， 1：用户不存在
    private String errcode;//错误码
    private String msg;
    private List<TasksShowOnIndex> data;

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
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

    public List<TasksShowOnIndex> getData() {
        return data;
    }

    public void setData(List<TasksShowOnIndex> data) {
        this.data = data;
    }
}
