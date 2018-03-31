package wang.yiwangchunyu.community.webService.androidAsyncHttp;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.AsyncHttpResponseHandler;

import wang.yiwangchunyu.community.constant.UrlConstance;

/**
 * Created by yiwangchunyu on 2018/3/20.
 */

public class MyCLient{
    private static final String BASE_URL= UrlConstance.APP_URL;

    //创建一个static client
    private static AsyncHttpClient client=new AsyncHttpClient();

    //重写我们所需要的get,post等方法

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHand){
        client.get(BASE_URL+url,params,responseHand);
    }
    public static void post(String url,RequestParams params,AsyncHttpResponseHandler responseHand){
        client.post(BASE_URL+url,params,responseHand);
    }
    public static void getImage(String url,RequestParams params,AsyncHttpResponseHandler responseHand){
        client.post(url,params,responseHand);
    }

}
