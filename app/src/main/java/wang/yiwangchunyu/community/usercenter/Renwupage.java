package wang.yiwangchunyu.community.usercenter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import butterknife.BindView;
import wang.yiwangchunyu.community.MainActivity;
import wang.yiwangchunyu.community.R;
import wang.yiwangchunyu.community.TaskDetails;
import wang.yiwangchunyu.community.dataStructures.LikeInfo;
import wang.yiwangchunyu.community.dataStructures.MyTasksResponse;
import wang.yiwangchunyu.community.dataStructures.ReceivedLikeResponse;
import wang.yiwangchunyu.community.dataStructures.TasksResponse;
import wang.yiwangchunyu.community.dataStructures.TasksShowOnIndex;
import wang.yiwangchunyu.community.dataStructures.TasksShowOnUsercenter;
import wang.yiwangchunyu.community.message.Adpter_like;
import wang.yiwangchunyu.community.recyclerview_usercenter.MyRecyclerViewAdapter_renwu;
import wang.yiwangchunyu.community.recycleview_two.DividerItemDecoration;
import wang.yiwangchunyu.community.recycleview_two.MyRecyclerViewOnclickInterface;
import wang.yiwangchunyu.community.webService.HttpResponeCallBack;
import wang.yiwangchunyu.community.webService.RequestApiData;
import wang.yiwangchunyu.community.webService.RequestManager;

import static wang.yiwangchunyu.community.constant.UrlConstance.KEY_LIKELIST_BY_UID;
import static wang.yiwangchunyu.community.constant.UrlConstance.KEY_MY_PUBLSH_BY_STATUS;

/**
 * Created by xinyu jiang on 2018/11/8.
 */

public class Renwupage extends Activity implements HttpResponeCallBack,MyRecyclerViewOnclickInterface {

    RecyclerView mRecyclerview;

    SwipeRefreshLayout mSwipeRefreshLayout;
    String userId;

    private MyRecyclerViewAdapter_renwu mAdapter;
    private SharedPreferences login_sp;

    private ArrayList<TasksShowOnUsercenter> tasksArr;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    //private ArrayList<TasksShowOnUsercenter> tasksArr;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout for this fragment
        setContentView(R.layout.user_renwulist);
        userId = getIntent().getStringExtra("userId");

//        System.out.print("aaaaaaaaaaaa");
        Log.d("TAG", "加载我发布的任务页面的布局管理器");
        getStatusInfo(userId);
        mRecyclerview = (RecyclerView)findViewById(R.id.recyclerview_renwu);//这里用BIndview的话会报空指针，不知道为什么
        mSwipeRefreshLayout =(SwipeRefreshLayout)findViewById(R.id.srl_renwu);
//        try {
//            RequestManager.postv2();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        //View view = inflater.inflate(R.layout.fragment_fouth, container, false);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    //从服务器获取所有用户发布的信息，onsuccess
    public void getStatusInfo(String userId) {

        Log.d("TAG", "从服务器获取用户发布的任务信息 ");

        //RequestApiData.getInstance().getLikeListByUid(name, ReceivedLikeResponse.class, this);
        RequestApiData.getInstance().getPublishStatusFromServer(userId,"0",MyTasksResponse.class,this);
    }




    private void showstatus(ArrayList<TasksShowOnUsercenter> dataList) {
            Log.d("TAG","加载第一页的任务列表");
            for (int i = 0 ; i < dataList.size(); i ++)
                Log.d("TIME",dataList.get(i).getCtime());
            //initTasks(dataList);
            Log.d("TAG","加载适配器");


            //设置布局管理器
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            mRecyclerview.setLayoutManager(linearLayoutManager);
            mAdapter = new MyRecyclerViewAdapter_renwu(this, dataList);
            //设置adapter
            mRecyclerview.setAdapter(mAdapter);
            //添加分割线
            mRecyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
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
                            //Toast.makeText( "下拉刷新成功", Toast.LENGTH_SHORT).show();
                            getStatusInfo(userId);
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }.execute();
                }
            });

    }

    @Override
    public void onItemClick(View view, int position)  {
        Toast.makeText(this, "onItemClick", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, TaskDetails.class);
        TasksShowOnIndex tasksShowOnIndex = new TasksShowOnIndex();
        try {
            tasksShowOnIndex.setTitle(new String(tasksArr.get(position-1).getTitle().getBytes("ISO-8859-1"),"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        tasksShowOnIndex.setCommission(Integer.parseInt(tasksArr.get(position-1).getPrice()));
        tasksShowOnIndex.setContent(tasksArr.get(position-1).getContent());
        tasksShowOnIndex.setRestriction(tasksArr.get(position-1).getRestriction());
        tasksShowOnIndex.setUserName(tasksArr.get(position-1).getUser_id());


        intent.putExtra("task_details",tasksShowOnIndex);
        startActivity(intent);
        //MainActivity activity = (MainActivity) this;
        //activity.toTaskDetails(likeArr.get(position-1));
    }

    @Override
    public void onItemLongClick(View view, int position) {
        Toast.makeText(this, "onItemLongClick", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onSuccess(String apiName, Object object) {
        Toast.makeText(this, "onSuccess！", Toast.LENGTH_SHORT);
        Log.w("TAG", "成功回调任务信息");
        Log.w("TAG", apiName);
        Log.w("TAG2", KEY_MY_PUBLSH_BY_STATUS);
        if (apiName.equals(KEY_MY_PUBLSH_BY_STATUS)) {
            Log.d("TAG", "加加载任务信息成功！");
            MyTasksResponse hr = (MyTasksResponse) object;
            tasksArr = (ArrayList<TasksShowOnUsercenter>)hr.getData();
            showstatus(tasksArr);
            Log.d("TAG2", tasksArr.toString());
        }
    }

    @Override
    public void onResponeStart(String apiName) {

    }

    @Override
    public void onLoading(String apiName, long count, long current) {

    }

    @Override
    public void onFailure(String apiName, Throwable t, int errorNo, String strMsg) {
        Toast.makeText(this, "Failure！", Toast.LENGTH_SHORT);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Renwupage Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client.connect();
        //AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //AppIndex.AppIndexApi.end(client, getIndexApiAction());
        //client.disconnect();
    }
}
