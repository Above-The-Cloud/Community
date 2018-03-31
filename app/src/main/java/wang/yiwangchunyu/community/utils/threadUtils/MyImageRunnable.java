package wang.yiwangchunyu.community.utils.threadUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by yiwangchunyu on 2018/3/29.
 */

public class MyImageRunnable implements Runnable {

    String imageUrl;
    ImageView imageView;

    public MyImageRunnable(String imageUrl, ImageView imageView) {
        this.imageUrl = imageUrl;
        this.imageView = imageView;
    }

    @Override
    public void run() {
        try {
            URL httpUrl = new URL(imageUrl);
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setConnectTimeout(6000);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            InputStream in = conn.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            imageView.setImageBitmap(bitmap);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
