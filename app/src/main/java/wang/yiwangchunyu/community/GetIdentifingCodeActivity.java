package wang.yiwangchunyu.community;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mob.MobSDK;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import wang.yiwangchunyu.community.constant.Constant;
import wang.yiwangchunyu.community.constant.UrlConstance;
import wang.yiwangchunyu.community.users.UserBaseInfo;
import wang.yiwangchunyu.community.webService.HttpResponeCallBack;
import wang.yiwangchunyu.community.webService.RequestApiData;

public class GetIdentifingCodeActivity extends Activity implements View.OnClickListener, HttpResponeCallBack {
    private HashMap<Character, ArrayList<String[]>> rawData;
    private EditText etVCode;
    private EditText etVGetcode;
    private HashMap<Character, ArrayList<String[]>> first;   //第一层数组
    ArrayList<String[]> second;   //第二层数组
    String countyList="国家列表：\n";
    private HashMap<String, String> countryRules;
//    private ArrayList<String> titles;
//    private ArrayList<ArrayList<String[]>> countries;
//    private SearchEngine sEngine;

    List<String> country = new ArrayList<>();
    List<String> country1 = new ArrayList<>();
    ArrayList<String[]> list;
    private EventHandler handler;
    private UserBaseInfo userBaseInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_identifing_code);
        userBaseInfo = new UserBaseInfo();

        MobSDK.init(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int hasWritePermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int hasReadPermission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);

            List<String> permissions = new ArrayList<String>();
            if (hasWritePermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            } else {
//              preferencesUtility.setString("storage", "true");
            }

            if (hasReadPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);

            } else {
//              preferencesUtility.setString("storage", "true");
            }

            if (!permissions.isEmpty()) {
//              requestPermissions(permissions.toArray(new String[permissions.size()]), REQUEST_CODE_SOME_FEATURES_PERMISSIONS);

                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE},
                        0);
            }
        }

        rawData = SMSSDK.getGroupedCountryList();
        findViewById(R.id.tv_test1).setOnClickListener(this);
        findViewById(R.id.tv_test_vcode_valiable).setOnClickListener(this);
        etVCode = (EditText) findViewById(R.id.et_v_code);
        etVGetcode = (EditText) findViewById(R.id.et_v_getcode);
        // 创建EventHandler对象


        handler = new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {

                if (result == SMSSDK.RESULT_COMPLETE){
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功


                        Intent intent = new Intent(GetIdentifingCodeActivity.this,RegisterUserInfoActivity.class) ;
                        intent.putExtra("userid", userBaseInfo.getUserid());
                        startActivity(intent);
                        finish();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(GetIdentifingCodeActivity.this,"验证成功",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }else if (event == SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(GetIdentifingCodeActivity.this,"语音验证发送",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                        boolean isSmart = (boolean) data;
                        if (isSmart){
                            Log.i("ssss","isSmart");
                        }else{
                            Log.i("ssss","notSmart");
                        }
                        //获取验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(GetIdentifingCodeActivity.this,"验证码已发送",Toast.LENGTH_SHORT).show();
                            }
                        });



                    }else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                        Log.i("test","test");
//                        initSearchEngine();
//                        search((String)null);
//
//                        for (ArrayList<String[]> test: countries){
//                            for (int j=0;j<test.size();j++){
//                                Log.i("ssss",test.get(j)[0]);//国名
//                                Log.i("ssss",test.get(j)[1]);//区号
//                            }
//                        }

//
                    }
                }else{
                    ((Throwable)data).printStackTrace();
                    Throwable throwable = (Throwable) data;
                    throwable.printStackTrace();
                    Log.i("ssss",throwable.toString());
                    try {
                        JSONObject obj = new JSONObject(throwable.getMessage());
                        final String des = obj.optString("detail");
                        if (!TextUtils.isEmpty(des)){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(GetIdentifingCodeActivity.this,des,Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        };

        SMSSDK.registerEventHandler(handler);

    }

    @Override
    public void onClick(View v) {
        String phone = etVGetcode.getText().toString();

        switch (v.getId()){
//
            case R.id.tv_test1:
                //获取验证码
                if (TextUtils.isEmpty(phone))
                    Toast.makeText(this,"phone can't be null",Toast.LENGTH_SHORT).show();
                //TODO: 表单验证，手机号正则表达式
                //访问数据库， 用户是否已经存在
                RequestApiData.getInstance().getUserInfo(phone, UserBaseInfo.class, GetIdentifingCodeActivity.this);

                //SMSSDK.getVerificationCode("86",phone,null);
//                SMSSDK.getVoiceVerifyCode("86",phone);
                break;


            case R.id.tv_test_vcode_valiable:


                userBaseInfo.setUserid(phone);
                //提交验证码验证
                if (TextUtils.isEmpty(phone))
                    Toast.makeText(this,"phone can't be null",Toast.LENGTH_SHORT).show();

                String number = etVCode.getText().toString();

                if (TextUtils.isEmpty(number))
                    Toast.makeText(this,"phone can't be null",Toast.LENGTH_SHORT).show();

                Log.i("ssss",phone+","+number);
                SMSSDK.submitVerificationCode("86",phone,number);
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        SMSSDK.unregisterAllEventHandler();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 0: {
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {


                        System.out.println("Permissions --> " + "Permission Granted: " + permissions[i]);
                    } else if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        System.out.println("Permissions --> " + "Permission Denied: " + permissions[i]);
                    }
                }
            }
            break;
            default: {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    @Override
    public void onResponeStart(String apiName) {

    }

    @Override
    public void onLoading(String apiName, long count, long current) {

    }

    @Override
    public void onSuccess(String apiName, Object object) {
        if (UrlConstance.KEY_USER_BASE_INFO.equals(apiName)){
                //返回数据
            if (object != null && object instanceof UserBaseInfo) {
                UserBaseInfo info = (UserBaseInfo) object;
                if (info.getRet().equals(Constant.KEY_SUCCESS)) {
                    Toast.makeText(this,"用户已存在",Toast.LENGTH_SHORT).show();
                } else {
                    //发送验证码
                    SMSSDK.getVerificationCode("86", info.getUserid());
                }
            }
        }

    }

    @Override
    public void onFailure(String apiName, Throwable t, int errorNo, String strMsg) {

    }
}
