package wang.yiwangchunyu.community;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ImageView;

public class UsercenterActivity extends AppCompatActivity {
    private ImageView blurImageView;
    private ImageView avatarImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_usercenter);
//        blurImageView = (ImageView) findViewById(R.id.iv_blur);
//        avatarImageView = (ImageView) findViewById(R.id.iv_avatar);
//
//        Glide.with(this).load(R.drawable.head)
//                .bitmapTransform(new BlurTransformation(this, 25), new CenterCrop(this))
//                .into(blurImageView);
//
//        Glide.with(this).load(R.drawable.head)
//                .bitmapTransform(new CropCircleTransformation(this))
//                .into(avatarImageView);
    }
}
