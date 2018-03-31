package wang.yiwangchunyu.community.discovery;

/**
 * Created by XinyuJiang on 2018/3/31.
 */

public class Shangjia {
    private int price;

    private String title;

    private String content;

    public Shangjia(int price, String title,String content){
        this.price = price;
        this.title = title;
        this.content = content;
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
}
