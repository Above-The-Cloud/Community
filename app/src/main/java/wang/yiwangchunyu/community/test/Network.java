package wang.yiwangchunyu.community.test;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.util.logging.Logger;

import static com.android.volley.Request.*;

/**
 * Created by xinyu jiang on 2018/11/7.
 */

public class Network {
    public void post() throws JSONException {
        //final JSONObject jsonObj = new JSONObject();
        //long uid = 1891805390;
        //jsonObj.put("uid", uid);
        //jsonObj.put("uid", new BigInteger("18918053907").toString());
        //jsonObj.put("status", 0);
        JSONObject jsonObj = new JSONObject("{\"uid\":18918053907, \"status\":0}");

        JsonObjectRequest jr = new JsonObjectRequest(Method.POST, "http://community.yiwangchunyu.wang:8088/task/getLikeListByUid.php", jsonObj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.i("NetworkTest", jsonObject.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("NetworkTest", volleyError.toString());
            }
        });
    }

}
