package wang.yiwangchunyu.community;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import wang.yiwangchunyu.community.dataStructures.TasksShowOnIndex;

public class TaskDetails extends AppCompatActivity {

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

    TasksShowOnIndex task_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO:用Parcelable实现对象传递
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);
        Intent intent = getIntent();
        task_detail = (TasksShowOnIndex) intent.getSerializableExtra("task_details");//使用Serializable序列化自定义对象来实现传递，简单但是效率不高，待修改

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

        title.setText(task_detail.getTitle());
        commision.setText(Integer.toString(task_detail.getCommission()));
        releaser_name.setText(task_detail.getUserName());
        content.setText(task_detail.getContent());
        restriction.setText(task_detail.getRestriction());
        //catoGory.setText(task_detail.getCategory());

    }
}
