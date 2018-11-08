package wang.yiwangchunyu.community.dataStructures;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by xinyu jiang on 2018/11/8.
 */

public class TasksShowOnUsercenter implements Serializable {
    private String publish_id;
    private String user_id;
    private String restriction;
    private String category;
    private String title;
    private String content;
    private int viewed;
    private int liked;
    private String ctime;
    private String mtime;
    private String status;
    private String submission_time;
    private String price;

    //publish_id
    public void setPublish_id(String publish_id){this.publish_id = publish_id;}
    public String getPublish_id(){return this.publish_id;}

    //user_id
    public void setUser_id(String user_id){this.user_id = user_id;}
    public String getUser_id(){return this.user_id;}

    //restriction
    public String getRestriction() {
        return restriction;
    }
    public void setRestriction(String restriction) {
        this.restriction = restriction;
    }

    //category
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    //title
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    //content
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    //viewed
    public int getViewed() {
        return viewed;
    }
    public void setViewed(int viewed) {
        this.viewed = viewed;
    }

    //liked
    public int getLiked() {
        return liked;
    }
    public void setLiked(int liked) {
        this.liked = liked;
    }

    //ctime
    public String getCtime() {
        return ctime;
    }
    public void setCtime(String time) {
        this.ctime = ctime;
    }

    //ptime
    public String getMtime() {
        return mtime;
    }
    public void setMtime(String mtime) {
        this.mtime = mtime;
    }

    //status
    public String getStatus(){return status;}
    public void setStatus(String status){this.status = status;}

    //submission_time
    public String getSubmission_time(){return submission_time;}
    public void setSubmission_time(String submission_time){this.submission_time = submission_time;}

    //price
    public String getPrice(){return price;}
    public void setPrice(String price){this.price = price;}













}
