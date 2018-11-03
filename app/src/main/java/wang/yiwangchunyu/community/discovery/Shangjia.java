package wang.yiwangchunyu.community.discovery;

import java.io.Serializable;

/**
 * Created by XinyuJiang on 2018/3/31.
 */

public class Shangjia implements Serializable {
    private int price;

    private String title;

    private String content;

    private int background;

    private double rating;

    private String address;

    private String bonus;

    private double distance;

    private String phoneNum;

    public Shangjia(int price, String title, String address, String content, int background, double rating, String bonus, double distance, String phoneNum){
        this.price = price;
        this.title = title;
        this.address = address;
        this.content = content;
        this.background = background;
        this.rating = rating;
        this.bonus = bonus;
        this.distance = distance;
        this.phoneNum = phoneNum;
    }

    public void setPrice(int price){
        this.price = price;
    }

    public int getPrice(){
        return price;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public void setContent(String content){
        this.content = content;
    }

    public String getContent(){
        return content;
    }

    public int getBackground() {return background; }

    public void setBackground(int background) {this.background = background; }

    public double getRating() {return rating;  }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    public double getDistance() { return distance; }

    public void setDistance(float distance) { this.distance = distance; }

    public String getPhoneNum() { return phoneNum; }

    public void setPhoneNum(String phoneNum) { this.phoneNum = phoneNum; }
}
