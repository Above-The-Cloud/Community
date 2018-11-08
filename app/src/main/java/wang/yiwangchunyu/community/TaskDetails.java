package wang.yiwangchunyu.community;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;

import wang.yiwangchunyu.community.dataStructures.TasksShowOnIndex;
import wang.yiwangchunyu.community.usercenter.CircleImageView;
import wang.yiwangchunyu.community.usercenter.Renwupage;
import wang.yiwangchunyu.community.webService.HttpResponeCallBack;
import wang.yiwangchunyu.community.webService.RequestManager;

import static wang.yiwangchunyu.community.webService.RequestManager.postv2;

//TODO:接受任务时改变任务状态
public class TaskDetails extends AppCompatActivity implements View.OnClickListener{

    String publish_id;
    TextView title;//标题
    ImageView img;//显示的图片
    TextView commision;//报酬
    TextView address;//发布地址
    TextView releaser_name;//发布者Id
    TextView content;//内容
    TextView restriction;//描述
    TextView time;//发布时间
    TextView catoGory;//种类
    ImageView imageView;//发布时的图片
    Button lingqu;
    de.hdodenhof.circleimageview.CircleImageView profile_pic;


    SharedPreferences login_sp;

    TasksShowOnIndex task_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO:用Parcelable实现对象传递
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);
        Intent intent = getIntent();
        task_detail = (TasksShowOnIndex) intent.getSerializableExtra("task_details");//使用Serializable序列化自定义对象来实现传递，简单但是效率不高，待修改

        publish_id = task_detail.getPublishId();
        title= (TextView) findViewById(R.id.title);
        //img= (ImageView) findViewById(R.id.item_image);
        commision= (TextView) findViewById(R.id.commision);
        address = (TextView) findViewById(R.id.address);
        releaser_name= (TextView) findViewById(R.id.releaser_name);
        content = (TextView) findViewById(R.id.content);
        restriction = (TextView) findViewById(R.id.restriction);
        //time = (TextView) findViewById(R.id.data);
        //imageView = (ImageView)findViewById(R.id.picture);
        catoGory = (TextView) findViewById(R.id.category);
        lingqu = (Button) findViewById(R.id.lingqu);
        lingqu.setOnClickListener(this);
        releaser_name.setOnClickListener(this);
        profile_pic = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.profile_pic);
        profile_pic.setOnClickListener(this);

        try {
            title.setText(new String(task_detail.getTitle().getBytes("ISO-8859-1"),"UTF-8"));
            releaser_name.setText(new String(task_detail.getUserName().getBytes("ISO-8859-1"),"UTF-8"));
            content.setText(new String(task_detail.getContent().getBytes("ISO-8859-1"),"UTF-8"));
            restriction.setText(new String(task_detail.getRestriction().getBytes("ISO-8859-1"),"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        commision.setText(Integer.toString(task_detail.getCommission()));
        //catoGory.setText(task_detail.getCategory());

    }
    @Override
    public void onClick(View view){
        switch(view.getId()) {
            case (R.id.lingqu):
            {
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("确认信息");
                dialog.setMessage("您确认要领取这条任务吗");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final String phone_num = task_detail.getUserId();
                        lingqu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                login_sp = getSharedPreferences("userInfo", 0);
                                String name = login_sp.getString("USER_NAME", "");
                                try {
                                    RequestManager.postv2(name ,publish_id,"1");
                                    } catch (JSONException e) {
                                    e.printStackTrace();
                                    }
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone_num.trim()));
                                Toast.makeText(getApplicationContext(),"任务领取成功！",Toast.LENGTH_LONG).show();
                                startActivity(intent);//调用上面这个intent实现拨号（只是进入拨号界面）
                            }
                        });
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                dialog.show();
                break;
            }
            case(R.id.releaser_name):
                userinfo(task_detail.getUserId());
                Log.d("点击的用户用户名为：",task_detail.getUserId());
                break;
            case(R.id.profile_pic):
                userinfo(task_detail.getUserId());
                Log.d("点击的用户用户名为：",task_detail.getUserId());
                break;
        }
    }
    private void userinfo(String userId){
        Intent intent = new Intent(this, Renwupage.class);
        Log.d("TAG4",userId);
        intent.putExtra("userId",userId);
        startActivity(intent);
    }

}
