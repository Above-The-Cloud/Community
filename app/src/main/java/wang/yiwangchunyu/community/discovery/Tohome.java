package wang.yiwangchunyu.community.discovery;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import wang.yiwangchunyu.community.R;

public class Tohome extends AppCompatActivity {

    TextView confirm_info;

    TextView title;

    RadioGroup numberRG;
    RadioGroup dateRG;
    RadioGroup timeRG;

    RadioButton today;

    RadioButton date1;
    RadioButton date2;
    RadioButton date3;
    RadioButton date4;

    EditText command;

    Button confirm_but;

    private String selectRadioBtn(){

        RadioButton number = (RadioButton)findViewById(numberRG.getCheckedRadioButtonId());
        RadioButton date = (RadioButton)findViewById(dateRG.getCheckedRadioButtonId());
        RadioButton time = (RadioButton)findViewById(timeRG.getCheckedRadioButtonId());

        confirm_info = (TextView) findViewById(R.id.confirm_info);

        String Text = (number.getText()+",  "+date.getText()+time.getText());

        confirm_info.setText(Text);

        return Text;

    }


    private int calcDay(int day){
        if(day > 30){
            return day%30+100;
        }
        else return day;
    }

    private boolean isValidTime(){
        today = (RadioButton) findViewById(R.id.button1);
        if(today.isChecked()){
            RadioButton time = (RadioButton)findViewById(timeRG.getCheckedRadioButtonId());
            String checkTime = (String) time.getText();
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            String minuteS;
            if(minute >= 10){
                minuteS = minute + "";
            }
            else{
                minuteS = "0" + minute;
            }
            String nowTime;
            if(hour >= 10){
                nowTime = hour + ":" + minuteS;
            }
            else {
                nowTime = "0" + hour + ":" + minuteS;
            }
            //Toast.makeText(Tohome.this, nowTime, Toast.LENGTH_SHORT).show();
            if(nowTime.compareTo(checkTime)>=0){
                //Toast.makeText(Tohome.this, "时间已过，预约失败", Toast.LENGTH_SHORT).show();
                return false;
            }
            else{
                return true;
            }
        }
        else{
            return true;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_home);
        getSupportActionBar().hide();

        title = (TextView)findViewById(R.id.title);
        Intent intent = getIntent();
        title.setText(intent.getStringExtra("title"));

        confirm_but = (Button) findViewById(R.id.confirm_but);
        confirm_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(Tohome.this, ShangjiaInfo.class);
                //时间是合法的，确认信息后跳转回主页面
                if(isValidTime()){
                    //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
                    AlertDialog.Builder builder = new AlertDialog.Builder(Tohome.this);
                    //    设置Title的图标
                    //builder.setIcon(R.drawable.ic_launcher);
                    //    设置Title的内容
                    builder.setTitle("确认");
                    //    设置Content来显示一个信息
                    String appointment_msg = selectRadioBtn();
                    builder.setMessage("确认您的预约上门服务信息: \n" + appointment_msg);
                    //    设置一个PositiveButton
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            startActivity(intent);
                        }
                    });
                    //    设置一个NegativeButton
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            Toast.makeText(Tohome.this, "重新选择预约信息", Toast.LENGTH_SHORT).show();
//                            placeRG.check(R.id.place1);
//                            dateRG.check(R.id.button1);
//                            timeRG.check(R.id.time1);
//                            numberRG.check(R.id.number1);
                        }
                    });
                    //    设置一个NeutralButton
//                    builder.setNeutralButton("忽略", new DialogInterface.OnClickListener()
//                    {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which)
//                        {
//                            Toast.makeText(Tohome.this, "neutral: " + which, Toast.LENGTH_SHORT).show();
//                        }
//                    });
                    //    显示出该对话框
                    builder.show();
                }
                //时间不合法，重新选择
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(Tohome.this);
                    //    设置Title的图标
                    //builder.setIcon(R.drawable.ic_launcher);
                    //    设置Title的内容
                    builder.setTitle("预约上门服务失败");
                    //    设置Content来显示一个信息
                    String appointment_msg = selectRadioBtn();
                    builder.setMessage("\n您预约的时间已逾期");
                    //    设置一个PositiveButton
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
//                            placeRG.check(R.id.place1);
//                            dateRG.check(R.id.button1);
                            timeRG.check(R.id.time1);
//                            numberRG.check(R.id.number1);
                        }
                    });

                    //    显示出该对话框
                    builder.show();
                }
            }
        });

        numberRG = (RadioGroup)findViewById(R.id.numberRG);
        numberRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                selectRadioBtn();
            }
        });

        dateRG = (RadioGroup)findViewById(R.id.dateRG);
        dateRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                selectRadioBtn();
            }
        });

        timeRG = (RadioGroup)findViewById(R.id.timeRG);
        timeRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                selectRadioBtn();
            }
        });



        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        day = calcDay(day);
        if(day > 100){
            month += 1;
            day -= 100;
        }

        date1 = (RadioButton) findViewById(R.id.button4);
        date1.setText(month+"."+calcDay(day+3));
        date2 = (RadioButton) findViewById(R.id.button5);
        date2.setText(month+"."+calcDay(day+4));
        date3 = (RadioButton) findViewById(R.id.button6);
        date3.setText(month+"."+calcDay(day+5));
        date4 = (RadioButton) findViewById(R.id.button7);
        date4.setText(month+"."+calcDay(day+6));


    }

}
