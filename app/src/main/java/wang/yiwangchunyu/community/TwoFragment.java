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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;
import wang.yiwangchunyu.community.dataStructures.TasksArrayList;
import wang.yiwangchunyu.community.recycleview.DividerItemDecoration;
import wang.yiwangchunyu.community.recycleview.MyRecyclerViewAdapter;
import wang.yiwangchunyu.community.recycleview.MyRecyclerViewOnclickInterface;
import wang.yiwangchunyu.community.recycleview.Recycler_Item;
import wang.yiwangchunyu.community.webService.HttpResponeCallBack;
import wang.yiwangchunyu.community.webService.RequestApiData;

public class TwoFragment extends Fragment implements MyRecyclerViewOnclickInterface {


    @BindView(R.id.id_recyclerview)//绑定RecycyleView
    RecyclerView mRecyclerview;

    @BindView(R.id.srl_one)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private MyRecyclerViewAdapter mAdapter;
    private ArrayList<Recycler_Item> dataList;


    private int otherdate=0;//从今日算起，倒数第几天 eg:昨天 就是1 前天就是 2

    private RequestQueue mQueue;

    private ArrayList<Recycler_Item> bannerList;//banner控件

    private ArrayList<String> titles;//存放banner中的标题

    private ArrayList<String> images;//存放banner中的图片

    private ArrayList<String> ids;//存放每一项的id

    TasksArrayList tasksArrayList;

    private void initBanner() {
        //初始化banner
        titles=new ArrayList<>();
        ids=new ArrayList<>();
        images=new ArrayList<>();

        bannerList = new ArrayList<>();

        //mQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("http://news-at.zhihu.com/api/4/news/latest", null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    //解析banner中的数据
                    JSONArray topinfos = response.getJSONArray("top_stories");
                    Log.d("TAG", "onResponse: "+topinfos);
                    for (int i = 0; i < topinfos.length(); i++) {
                        JSONObject item = topinfos.getJSONObject(i);
                        Recycler_Item item1 = new Recycler_Item();
                        item1.setImgurl(item.getString("image"));
                        item1.setTitle(item.getString("title"));
                        item1.setId(item.getString("id"));
                        bannerList.add(item1);
                        titles.add(item1.getTitle());
                        images.add(item1.getImgurl());
                        ids.add(item1.getId());
                    }


                    setHeader(mRecyclerview, images, titles, ids);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mQueue.add(jsonObjectRequest);


    }

    private void setHeader(RecyclerView view, ArrayList<String> urls, ArrayList<String> titles, final ArrayList<String> ids) {
        View header = LayoutInflater.from(this.getActivity()).inflate(R.layout.headview, view, false);
        //找到banner所在的布局
        BGABanner banner = (BGABanner) header.findViewById(R.id.banner);
        //绑定banner
        banner.setAdapter(new BGABanner.Adapter<ImageView, String>() {


            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
                Glide.with(TwoFragment.this)
                        .load(model)
                        .centerCrop()
                        .dontAnimate()
                        .into(itemView);
            }
        });
        banner.setDelegate(new BGABanner.Delegate() {
            @Override
            public void onBannerItemClick(BGABanner banner, View itemView, Object model, int position) {
                //此处可设置banner子项的点击事件

            }
        });
        banner.setData(urls, titles);
        mAdapter.setHeadView(header);//向适配器中添加banner
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        ButterKnife.bind(this, view);
        initData();
        initBanner();
        return view;
    }

    private void initData() {
        dataList = new ArrayList<Recycler_Item>();
        getInfoFromNet();

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
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }.execute();
            }
        });
    }

    private void getInfoFromNet(){
        //获取网络数据
        mQueue = Volley.newRequestQueue(this.getActivity());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("http://news-at.zhihu.com/api/4/news/latest", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray list = null;
                    try {
                        list = response.getJSONArray("stories");
                        //获取返回数据的内容

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //开始解析数据
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject item = list.getJSONObject(i);

                        JSONArray images = item.getJSONArray("images");
                        Recycler_Item listItem = new Recycler_Item();
                        //创建list中的每一个对象，并设置数据
                        listItem.setTitle(item.getString("title"));
                        listItem.setImgurl(images.getString(0));
                        //listItem.setDate(getDate());
                        listItem.setId(item.getString("id"));
                        dataList.add(listItem);
                    }
                    mAdapter.notifyDataSetChanged();//通知适配器 刷新数据啦 啊喂
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            //如果遇到异常，在这里通知用户
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(),"碰到了一点问题",Toast.LENGTH_SHORT);
            }
        });
        mQueue.add(jsonObjectRequest);//开始任务
    }

//    private String getDate(){
//        //获取当前需要加载的数据的日期
//        Calendar c = Calendar.getInstance();
//        c.setTime(new Date());
//        c.add(Calendar.DAY_OF_MONTH, -otherdate);//otherdate天前的日子
//
//        String date = new SimpleDateFormat("yyyyMMdd").format(c.getTime());
//        //将日期转化为20170520这样的格式
//        return date;
//
//    }date


    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getActivity(), "onItemShortClick"+dataList.get(position), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(View view, int position) {
        Toast.makeText(getActivity(), "onItemLongClick" + dataList.get(position), Toast.LENGTH_SHORT).show();
    }
    public TasksArrayList getTasksInfo(){

        RequestApiData.getInstance().getPublishTaskInfoFromServer(TasksArrayList.class, new HttpResponeCallBack() {
            @Override
            public void onResponeStart(String apiName) {

            }

            @Override
            public void onLoading(String apiName, long count, long current) {

            }

            @Override
            public void onSuccess(String apiName, Object object) {
                tasksArrayList = (TasksArrayList) object;

            }

            @Override
            public void onFailure(String apiName, Throwable t, int errorNo, String strMsg) {

            }
        });


        return tasksArrayList;
    }
}