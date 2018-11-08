package wang.yiwangchunyu.community.discovery;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

import wang.yiwangchunyu.community.R;



public class ShangjiaInfo extends AppCompatActivity {
    private Shangjia shangjiaInfo;

    TextView title;

    TextView price;

    TextView content;

    ImageView img;

    TextView address;

    TextView rating;

    RatingBar ratingBar;

    TextView bonus;

    Button appointment;

    Button home;

    ImageView call;

    String titleS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shangjia_info);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        shangjiaInfo = (Shangjia) intent.getSerializableExtra("ShangjiaInfo");//使用Serializable序列化自定义对象来实现传递，简单但是效率不高，待修改


        ImageView but = (ImageView) findViewById(R.id.picture);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ShangjiaInfo.this, "hello", Toast.LENGTH_SHORT).show();
            }
        });

        title = (TextView) findViewById(R.id.title);
        titleS = shangjiaInfo.getTitle();
        title.setText(shangjiaInfo.getTitle());

        address = (TextView) findViewById(R.id.address);
        address.setText(shangjiaInfo.getAddress());

        content = (TextView) findViewById(R.id.content);
        content.setText(shangjiaInfo.getContent());

        rating = (TextView) findViewById(R.id.rating);
        rating.setText(shangjiaInfo.getRating()+"分");

        price = (TextView) findViewById(R.id.average_cost);
        price.setText("￥" + shangjiaInfo.getPrice() + "/人");

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setRating((float) shangjiaInfo.getRating());

        img = (ImageView) findViewById(R.id.picture);
        img.setImageResource(shangjiaInfo.getBackground());

        bonus = (TextView) findViewById(R.id.bonus);
        bonus.setText(shangjiaInfo.getBonus());

        appointment = (Button) findViewById(R.id.appointment);
        appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShangjiaInfo.this, Appointment.class);
                intent.putExtra("title", titleS);
                startActivity(intent);
            }
        });

        home = (Button) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShangjiaInfo.this, Tohome.class);
                intent.putExtra("title", titleS);
                startActivity(intent);
            }
        });

        final String phone_num = shangjiaInfo.getPhoneNum();
        call = (ImageView) findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone_num.trim()));
                startActivity(intent);//调用上面这个intent实现拨号（只是进入拨号界面）
            }
        });


    }
}
