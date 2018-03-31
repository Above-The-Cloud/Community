package wang.yiwangchunyu.community;

/**
 * Created by Administrator on 2018/3/8.
 */

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import wang.yiwangchunyu.community.users.UserBaseInfo;
import wang.yiwangchunyu.community.users.UserPreference;

public class FouthFragment extends Fragment {

    private View view;
    private TextView userName;
    private String userId;
    private SharedPreferences login_sp;

    public FouthFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //UserPreference.read("user_name")
        view = inflater.inflate(R.layout.fragment_fouth, container, false);
        login_sp = getActivity().getSharedPreferences("userInfo", 0);
        userName = (TextView)view.findViewById(R.id.user_name);
        userId = login_sp.getString("USER_NAME", "12345678910");
        userName.setText(userId);
        return view;
    }
}
