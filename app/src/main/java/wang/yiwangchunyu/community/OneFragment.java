package wang.yiwangchunyu.community;

/**
 * Created by Administrator on 2018/3/8.
 */

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.mob.wrappers.AnalySDKWrapper;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGABannerUtil;

import wang.yiwangchunyu.community.dataStructures.LikeInfo;
import wang.yiwangchunyu.community.dataStructures.ReceivedLikeResponse;
import wang.yiwangchunyu.community.dataStructures.TasksResponse;
import wang.yiwangchunyu.community.dataStructures.TasksShowOnIndex;
import wang.yiwangchunyu.community.discovery.Adpter_three;
import wang.yiwangchunyu.community.fuwu.baoxiu;
import wang.yiwangchunyu.community.recycleview_two.DividerItemDecoration;
import wang.yiwangchunyu.community.recycleview_two.MyRecyclerViewAdapter;
import wang.yiwangchunyu.community.recycleview_two.MyRecyclerViewOnclickInterface;
import wang.yiwangchunyu.community.test.Network;
import wang.yiwangchunyu.community.users.UserBaseInfo;
import wang.yiwangchunyu.community.webService.HttpResponeCallBack;
import wang.yiwangchunyu.community.webService.RequestApiData;
import wang.yiwangchunyu.community.webService.RequestManager;

import static wang.yiwangchunyu.community.constant.UrlConstance.KEY_GET_PUBLISH_INFO;
import static wang.yiwangchunyu.community.constant.UrlConstance.KEY_LIKELIST_BY_UID;
import static wang.yiwangchunyu.community.constant.UrlConstance.KEY_MY_PUBLSH_BY_STATUS;

public class OneFragment extends Fragment implements View.OnClickListener {

    LinearLayout tongzhi;
    LinearLayout daijiao;
    LinearLayout yuyue;
    LinearLayout weixiu;
    private  View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_two, container, false);
        init();
        return view;
    }

    public void init(){
        tongzhi = (LinearLayout)view.findViewById(R.id.tongzhi);
        tongzhi.setOnClickListener(this);
        daijiao = (LinearLayout)view.findViewById(R.id.daijiao);
        daijiao.setOnClickListener(this);
        yuyue = (LinearLayout)view.findViewById(R.id.yuyue);
        yuyue.setOnClickListener(this);
        weixiu = (LinearLayout)view.findViewById(R.id.weixiu);
        weixiu.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case(R.id.tongzhi):
                Toast.makeText(getActivity(),"暂未上线，敬请期待！",Toast.LENGTH_SHORT).show();
                break;
                //Intent intent_One_tongzhi = new Intent(getActivity(),);
            case(R.id.weixiu):
                repair();
                break;
            case(R.id.daijiao):
                Toast.makeText(getActivity(),"暂未上线，敬请期待！",Toast.LENGTH_SHORT).show();
                break;
            case(R.id.yuyue):
                Toast.makeText(getActivity(),"暂未上线，敬请期待！",Toast.LENGTH_SHORT).show();
                break;
                //

        }

    }
    public void repair(){
        Intent intent = new Intent(getActivity(),baoxiu.class) ;    //切换Login Activity至User Activity
        startActivity(intent);
    }



}