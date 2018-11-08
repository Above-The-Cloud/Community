package wang.yiwangchunyu.community.dataStructures;

/**
 * Created by xinyu jiang on 2018/11/4.
 */

public class LikeInfo {
    private String id;
    private String uid;
    private String object_id;
    private String type;
    private TaskPublishingInfo publish_info;
    private String time;

    public String getId(){return id;}
    public void setId(String id){this.id = id;}

    public String getUid(){return uid;}
    public void setUid(String uid){this.uid = uid;}

    public String getObject_id(){return object_id;}
    public void setObject_id(String object_id){this.object_id = object_id;}

    public String getType(){return type;}
    public void setType(String type){this.type = type;}

    public TaskPublishingInfo getPublish_info(){return publish_info;}
    public void setPublish_info(TaskPublishingInfo publish_info){this.publish_info = publish_info;}

    public String getTime(){return time;}
    public void setTime(String date){this.time = time;}

}
