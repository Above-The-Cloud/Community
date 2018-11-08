package wang.yiwangchunyu.community.usercenter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import wang.yiwangchunyu.community.MainActivity;
import wang.yiwangchunyu.community.R;
import wang.yiwangchunyu.community.dataStructures.LikeInfo;
import wang.yiwangchunyu.community.dataStructures.ReceivedLikeResponse;
import wang.yiwangchunyu.community.message.Adpter_like;
import wang.yiwangchunyu.community.recycleview_two.DividerItemDecoration;
import wang.yiwangchunyu.community.recycleview_two.MyRecyclerViewOnclickInterface;
import wang.yiwangchunyu.community.webService.HttpResponeCallBack;
import wang.yiwangchunyu.community.webService.RequestApiData;
import wang.yiwangchunyu.community.webService.RequestManager;

import static wang.yiwangchunyu.community.constant.UrlConstance.KEY_LIKELIST_BY_UID;

/**
 * Created by xinyu jiang on 2018/11/7.
 */

public class Likepage extends Activity implements HttpResponeCallBack,MyRecyclerViewOnclickInterface{

    @BindView(R.id.recyclerview_like)//绑定RecycyleView
            RecyclerView mRecyclerview;

    @BindView(R.id.srl_like)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private Adpter_like mAdapter;
    private SharedPreferences login_sp;

    private ArrayList<LikeInfo> likeArr;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    //private ArrayList<TasksShowOnIndex> tasksArr;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout for this fragment
        setContentView(R.layout.fragment_likelist);


//        System.out.print("aaaaaaaaaaaa");
        Log.d("TAG", "加载第二个页面的布局管理器");
        //getStatusInfo();

//        try {
//            RequestManager.postv2();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        //View view = inflater.inflate(R.layout.fragment_fouth, container, false);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    //从服务器获取所有用户发布的信息，onsuccess
    public void getStatusInfo() throws JSONException {

        Log.d("TAG", "从服务器获取用户点赞信息 ");
        login_sp = getSharedPreferences("userInfo", 0);
        String name = login_sp.getString("USER_NAME", "");
        RequestApiData.getInstance().getLikeListByUid(name, ReceivedLikeResponse.class, this);
        //RequestApiData.getInstance().getPublishStatusFromServer(name,"0",UserBaseInfo.class,this);
    }

    private void setHeader(RecyclerView view) {
        View header = LayoutInflater.from(this).inflate(R.layout.message_headview, view, false);
        mAdapter.setHeaderView(header);
    }

    private void showstatus(ArrayList<LikeInfo> dataList) {
        for (int i = 0; i < dataList.size(); i++)
            Log.d("TIME", dataList.get(i).getTime());
        //initTasks(dataList);
        Log.d("TAG", "加载第二个页面的适配器");


        mAdapter = new Adpter_like(this, dataList);
        //设置布局管理器
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        Log.d("TAG", "加载第二个页面的Layoutmanager");
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
                        //Toast.makeText(this, "下拉刷新成功", Toast.LENGTH_SHORT).show();
                        try {
                            getStatusInfo();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }.execute();
            }
        });

        View header = LayoutInflater.from(this).inflate(R.layout.message_headview, mRecyclerview, false);
        Log.d("Header", header.toString());
        mAdapter.setHeaderView(header);

    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "onItemClick", Toast.LENGTH_SHORT).show();
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
        Log.w("TAG", "成功回调点赞信息");
        Log.w("TAG", apiName);
        Log.w("TAG2", KEY_LIKELIST_BY_UID);
        if (apiName.equals(KEY_LIKELIST_BY_UID)) {
            Log.d("TAG", "加加载点赞信息成功！");
            ReceivedLikeResponse hr = (ReceivedLikeResponse) object;
            likeArr = (ArrayList<LikeInfo>) hr.getData();
            Log.d("TAG信息成功！3", likeArr.toString());
            showstatus(likeArr);
            Log.d("TAG2", likeArr.toString());
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
                .setName("Likepage Page") // TODO: Define a title for the content shown.
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
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
