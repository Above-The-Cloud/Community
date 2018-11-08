package wang.yiwangchunyu.community.fuwu;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import wang.yiwangchunyu.community.R;
import wang.yiwangchunyu.community.recycleview_two.MyRecyclerViewOnclickInterface;

/**
 * Created by xinyu jiang on 2018/11/8.
 */

public class baoxiu extends Activity implements View.OnClickListener{
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        // Inflate the layout for this fragment
        setContentView(R.layout.baoxiu);
        Button baoxiu = (Button) findViewById(R.id.activity_baoxiu);
        baoxiu.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_baoxiu:
                Toast.makeText(this, "报修成功", Toast.LENGTH_SHORT).show();
                finish();
        }
    }
//    private DatePicker findDatePicker(ViewGroup group) {
//        if (group != null) {
//            for (int i = 0, j = group.getChildCount(); i < j; i++) {
//                View child = group.getChildAt(i);
//                if (child instanceof DatePicker) {
//                    return (DatePicker) child;
//                } else if (child instanceof ViewGroup) {
//                    DatePicker result = findDatePicker((ViewGroup) child);
//                    if (result != null)
//                        return result;
//                }
//            }
//        }
//        return null;
//    }

}
