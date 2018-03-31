package wang.yiwangchunyu.community;

/**
 * Created by Administrator on 2018/3/8.
 */

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGABannerUtil;
import wang.yiwangchunyu.community.dataStructures.TasksResponse;
import wang.yiwangchunyu.community.dataStructures.TasksShowOnIndex;
import wang.yiwangchunyu.community.recycleview_two.DividerItemDecoration;
import wang.yiwangchunyu.community.recycleview_two.MyRecyclerViewAdapter;
import wang.yiwangchunyu.community.recycleview_two.MyRecyclerViewOnclickInterface;
import wang.yiwangchunyu.community.webService.HttpResponeCallBack;
import wang.yiwangchunyu.community.webService.RequestApiData;

import static wang.yiwangchunyu.community.constant.UrlConstance.KEY_GET_PUBLISH_INFO;

public class TwoFragment extends Fragment implements MyRecyclerViewOnclickInterface,HttpResponeCallBack {


    @BindView(R.id.id_recyclerview)//绑定RecycyleView
            RecyclerView mRecyclerview;

    @BindView(R.id.srl_one)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private MyRecyclerViewAdapter mAdapter;

    private int otherdate=0;//从今日算起，倒数第几天 eg:昨天 就是1 前天就是 2


    private ArrayList<String> titles;//存放banner中的标题

    private ArrayList<String> images;//存放banner中的图片

    private ArrayList<String> ids;//存放每一项的id

    private ArrayList<TasksShowOnIndex> tasksArr;


    private void initBanner() {



    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_two, container, false);

        Log.d("Header",view.toString());
        ButterKnife.bind(this, view);

        Log.d("TAG","加载布局管理器");
        getTasksInfo();
        return view;
    }


    private void showTasks(ArrayList<TasksShowOnIndex> dataList) {
        for (int i = 0 ; i < dataList.size(); i ++)
            Log.d("TIME",dataList.get(i).getTime());
        //initTasks(dataList);
        Log.d("TAG","加载适配器");


        mAdapter = new MyRecyclerViewAdapter(getActivity(), dataList);
        //设置布局管理器
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        //设置adapter
        mRecyclerview.setAdapter(mAdapter);
        //添加分割线
        mRecyclerview.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mAdapter.setOnItemClickLitener(this);

        //初始化下拉控件颜色
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... voids) {
                        SystemClock.sleep(2000);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        Toast.makeText(getActivity(), "下拉刷新成功", Toast.LENGTH_SHORT).show();
                        getTasksInfo();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }.execute();
            }
        });

        View header = LayoutInflater.from(this.getActivity()).inflate(R.layout.index_headview, mRecyclerview, false);
        Log.d("Header",header.toString());
        BGABanner banner = (BGABanner) header.findViewById(R.id.banner);
        Log.d("Header",banner.toString());
        List<View> views = new ArrayList<>();
        views.add(BGABannerUtil.getItemImageView(getActivity(),R.mipmap.head_1));
        views.add(BGABannerUtil.getItemImageView(getActivity(),R.mipmap.head_2));
        views.add(BGABannerUtil.getItemImageView(getActivity(),R.mipmap.head_3));
        banner.setData(views);
        banner.setDelegate(new BGABanner.Delegate<ImageView, String>() {
            @Override
            public void onBannerItemClick(BGABanner banner, ImageView itemView, String model, int position) {
                Toast.makeText(banner.getContext(), "点击了" + position, Toast.LENGTH_SHORT).show();
                if (position==2){
                    banner.stopAutoPlay();
                }
            }
        });
        mAdapter.setHeadView(header);

    }

    @Override
    public void onItemClick(View view, int position) {
        MainActivity activity = (MainActivity) getActivity();
        activity.toTaskDetails(tasksArr.get(position-1));
    }

    @Override
    public void onItemLongClick(View view, int position) {
        Toast.makeText(getActivity(), "onItemLongClick", Toast.LENGTH_SHORT).show();
    }

    //从服务器获取所有用户发布的信息，onsuccess
    public void getTasksInfo(){

        Log.d("TAG", "从服务器获取用户发布信息 ");
        RequestApiData.getInstance().getPublishTaskInfoFromServer(TasksResponse.class, this);

    }

    @Override
    public void onResponeStart(String apiName) {

    }

    @Override
    public void onLoading(String apiName, long count, long current) {

    }

    @Override
    public void onSuccess(String apiName, Object object) {
        Toast.makeText(getActivity(),"onSuccess！",Toast.LENGTH_SHORT);
        Log.d("TAG","成功回调");
        if(apiName.equals(KEY_GET_PUBLISH_INFO)) {
            TasksResponse hr = (TasksResponse) object;
            tasksArr = (ArrayList<TasksShowOnIndex>)hr.getData();
            showTasks(tasksArr);
        }
    }

    @Override
    public void onFailure(String apiName, Throwable t, int errorNo, String strMsg) {
        Toast.makeText(getActivity(),"Failure！",Toast.LENGTH_SHORT);
    }
}