package wang.yiwangchunyu.community;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import wang.yiwangchunyu.community.constant.Constant;
import wang.yiwangchunyu.community.constant.UrlConstance;
import wang.yiwangchunyu.community.users.UserBaseInfo;
import wang.yiwangchunyu.community.utils.MD5Util;
import wang.yiwangchunyu.community.utils.Utils;
import wang.yiwangchunyu.community.webService.HttpResponeCallBack;
import wang.yiwangchunyu.community.webService.RequestApiData;

import static android.content.ContentValues.TAG;

public class LoginActivity extends Activity implements HttpResponeCallBack{                 //登录界面活动

    public int pwdresetFlag=0;
    private EditText mAccount;                        //用户名编辑
    private EditText mPwd;                            //密码编辑
    private Button mRegisterButton;                   //注册按钮
    private Button mLoginButton;                      //登录按钮
    //private Button mCancleButton;                     //注销按钮
    private CheckBox mRememberCheck;
    private ImageView logo_imageView;        //logo图片

    private SharedPreferences login_sp;
    private String userNameValue,passwordValue;

    private View loginView;                           //登录
    private View loginSuccessView;
    private TextView loginSuccessShow;
    private TextView mChangepwdText;
    private UserDataManager mUserDataManager;         //用户数据管理类


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Log.w(TAG, "onCreate: -----------------");
        //通过id找到相应的控件
        mAccount = (EditText) findViewById(R.id.login_edit_account);
        mPwd = (EditText) findViewById(R.id.login_edit_pwd);
        mRegisterButton = (Button) findViewById(R.id.login_btn_register);
        mLoginButton = (Button) findViewById(R.id.login_btn_login);
        //mCancleButton = (Button) findViewById(R.id.login_btn_cancle);
        loginView=findViewById(R.id.login_view);
        loginSuccessView=findViewById(R.id.login_success_view);
        loginSuccessShow=(TextView) findViewById(R.id.login_success_show);

        mChangepwdText = (TextView) findViewById(R.id.login_text_change_pwd);

        mRememberCheck = (CheckBox) findViewById(R.id.Login_Remember);

        login_sp = getSharedPreferences("userInfo", 0);
        String name=login_sp.getString("USER_NAME", "");
        String pwd =login_sp.getString("PASSWORD", "");
        boolean choseRemember =login_sp.getBoolean("mRememberCheck", false);
        boolean choseAutoLogin =login_sp.getBoolean("mAutologinCheck", false);
        //如果上次选了记住密码，那进入登录页面也自动勾选记住密码，并填上用户名和密码
        if(choseRemember){
            mAccount.setText(name);
            mPwd.setText(pwd);
            mRememberCheck.setChecked(true);
        } else if(name != ""){
            mAccount.setText(name);
        }

        mRegisterButton.setOnClickListener(mListener);                      //采用OnClickListener方法设置不同按钮按下之后的监听事件
        mLoginButton.setOnClickListener(mListener);
        //mCancleButton.setOnClickListener(mListener);
        mChangepwdText.setOnClickListener(mListener);

//        ImageView image = (ImageView) findViewById(R.id.logo);             //使用ImageView显示logo
//        image.setImageResource(R.drawable.logo);
//
        if (mUserDataManager == null) {
            mUserDataManager = new UserDataManager(this);
            mUserDataManager.openDataBase();                              //建立本地数据库
        }
    }
    OnClickListener mListener = new OnClickListener() {                  //不同按钮按下的监听事件选择
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.login_btn_register:                            //登录界面的注册按钮
                    register();
                    break;

                case R.id.login_btn_login:                              //登录界面的登录按钮
                    login();
                    break;

            }
        }
    };

    public void login() {                                              //登录按钮监听事件
        if (isUserNameAndPwdValid()) {
            String user_id = mAccount.getText().toString().trim();    //获取当前输入的用户名和密码信息
            String userPwd = mPwd.getText().toString().trim();
            SharedPreferences.Editor editor =login_sp.edit();
            if(mRememberCheck.isChecked()){
                editor.putString("USER_NAME", user_id).putString("PASSWORD", userPwd).putBoolean("mRememberCheck", true);
            } else{
                editor.putBoolean("mRememberCheck", false).putString("USER_NAME", user_id);
            }
            editor.apply();
//            int result=mUserDataManager.findUserByNameAndPwd(userName, userPwd);
            RequestApiData.getInstance().getLoginData(user_id, MD5Util.getMD5Str(userPwd), UserBaseInfo.class, LoginActivity.this);
        }
    }

    public void register() {           //注册
        Intent intent_Login_to_Register = new Intent(LoginActivity.this,GetIdentifingCodeActivity.class) ;    //切换Login Activity至User Activity
        startActivity(intent_Login_to_Register);

    }

    public boolean isUserNameAndPwdValid() {
        if (!Utils.isUserName(mAccount.getText().toString().trim())) {
            Toast.makeText(this, getString(R.string.error_invalid_username),
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Utils.isPassWord(mPwd.getText().toString().trim())) {
            Toast.makeText(this, getString(R.string.error_invalid_pass),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
//        if (mUserDataManager == null) {
//            mUserDataManager = new UserDataManager(this);
//            mUserDataManager.openDataBase();
//        }
        super.onResume();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    protected void onPause() {
//        if (mUserDataManager != null) {
//            mUserDataManager.closeDataBase();
//            mUserDataManager = null;
//        }
        super.onPause();
    }

    @Override
    public void onResponeStart(String apiName) {

    }

    @Override
    public void onLoading(String apiName, long count, long current) {

    }

    @Override
    public void onSuccess(String apiName, Object object) {
        if (UrlConstance.KEY_LOGIN_INFO.equals(apiName)){
            //返回数据
            if (object != null && object instanceof UserBaseInfo) {
                UserBaseInfo info = (UserBaseInfo) object;
                if (info.getRet().equals(Constant.KEY_SUCCESS)) {
                    Intent intent_Login_to_MainActivity = new Intent(LoginActivity.this,MainActivity.class) ;    //切换Login Activity至User Activity
                    startActivity(intent_Login_to_MainActivity);
                    finish();
                    Toast.makeText(this,"登录成功",Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this,"用户名或密码错误",Toast.LENGTH_SHORT).show();

                }
            }
        }
    }

    @Override
    public void onFailure(String apiName, Throwable t, int errorNo, String strMsg) {
            Toast.makeText(this,"网络错误！",Toast.LENGTH_SHORT);
    }
}