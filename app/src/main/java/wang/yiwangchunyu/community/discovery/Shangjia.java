package wang.yiwangchunyu.community.discovery;

/**
 * Created by XinyuJiang on 2018/3/31.
 */

public class Shangjia {
    private int price;

    private String title;

    private String content;

    private int background;

    public Shangjia(int price, String title, String content, int background){
        this.price = price;
        this.title = title;
        this.content = content;
        this.background = background;
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

    public void setBackground(int background) {this.background = background;  }
}
