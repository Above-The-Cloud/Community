package wang.yiwangchunyu.community;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import wang.yiwangchunyu.community.dataStructures.LikeInfo;
import wang.yiwangchunyu.community.usercenter.Likepage;
import wang.yiwangchunyu.community.usercenter.item_view;

public class UsercenterActivity extends AppCompatActivity {
    private ImageView blurImageView;
    private ImageView avatarImageView;
    private item_view huozan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.fragment_fouth);
        huozan = (item_view)findViewById(R.id.huozan);
        huozan.setOnClickListener(mListener);
        Log.d("TAG4","注册点击事件");


    }
    OnClickListener mListener = new OnClickListener() {                  //不同按钮按下的监听事件选择
        public void onClick(View v) {
            Log.d("TAG4","点击事件");
            switch (v.getId()) {
                case R.id.huozan:
                    like();
                    break;

//                case R.id.login_btn_login:
//                    login();
//                    break;

            }
        }
    };

    private void like(){
        Intent intent = new Intent(UsercenterActivity.this, Likepage.class);
        startActivity(intent);
    }
}
