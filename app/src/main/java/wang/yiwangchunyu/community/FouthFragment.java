package wang.yiwangchunyu.community;

/**
 * Created by Administrator on 2018/3/8.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import wang.yiwangchunyu.community.recycleview_two.MyRecyclerViewOnclickInterface;
import wang.yiwangchunyu.community.usercenter.Likepage;
import wang.yiwangchunyu.community.usercenter.Renwupage;
import wang.yiwangchunyu.community.usercenter.item_view;
import wang.yiwangchunyu.community.users.UserBaseInfo;
import wang.yiwangchunyu.community.users.UserPreference;

public class FouthFragment extends Fragment{

    private View view;
    private TextView userName;
    private String userId;
    private SharedPreferences login_sp;
    private item_view huozan;
    private item_view renwu;
    private item_view qianbao;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //UserPreference.read("user_name")
        view = inflater.inflate(R.layout.fragment_fouth, container, false);
        login_sp = getActivity().getSharedPreferences("userInfo", 0);
        userName = (TextView)view.findViewById(R.id.user_name);
        userId = login_sp.getString("USER_NAME", "12345678910");
        userName.setText(userId);
        huozan = (item_view)view.findViewById(R.id.huozan);
        huozan.setOnClickListener(mListener);
        renwu = (item_view)view.findViewById(R.id.renwu);
        renwu.setOnClickListener(mListener);
        qianbao = (item_view)view.findViewById(R.id.qianbao);
        qianbao.setOnClickListener(mListener);
        Log.d("TAG4","注册点击事件");
        return view;
    }

    View.OnClickListener mListener = new View.OnClickListener() {                  //不同按钮按下的监听事件选择
        public void onClick(View v) {
            Log.d("TAG4","点击事件");
            switch (v.getId()) {
                case R.id.huozan:
                    Toast.makeText(view.getContext() ,"暂未上线,敬请期待",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.renwu:
                    renwu();
                    break;
                case R.id.qianbao:
                    Toast.makeText(view.getContext() ,"暂未上线,敬请期待",Toast.LENGTH_SHORT).show();
                    break;
//                case R.id.login_btn_login:
//                    login();
//                    break;

            }
        }
    };
    private void like(){
        Intent intent = new Intent(this.getActivity(), Likepage.class);
        startActivity(intent);
    }

    private void renwu(){
        Intent intent = new Intent(this.getActivity(), Renwupage.class);
        Log.d("TAG4",userId);
        intent.putExtra("userId",userId);
        startActivity(intent);
    }

}
