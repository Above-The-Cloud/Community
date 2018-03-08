package wang.yiwangchunyu.community;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import wang.yiwangchunyu.community.constant.Constant;
import wang.yiwangchunyu.community.constant.KeyConstance;
import wang.yiwangchunyu.community.constant.UrlConstance;
import wang.yiwangchunyu.community.users.UserBaseInfo;
import wang.yiwangchunyu.community.users.UserPreference;
import wang.yiwangchunyu.community.utils.MD5Util;
import wang.yiwangchunyu.community.webService.HttpResponeCallBack;
import wang.yiwangchunyu.community.webService.RequestApiData;

public class NamePwdActivity extends AppCompatActivity implements View.OnClickListener, HttpResponeCallBack {
    private static final String TAG = "NamePwdActivity";
    private EditText nickName;
    private EditText pwd;
    private EditText doublecheckpwd;
    private RadioGroup radioGroup;
    private EditText email;
    private EditText address;
    private Button submit;
    private String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_pwd);
        Intent intent = getIntent();
        user_id = intent.getStringExtra("userid");

        initView();
    }
    private void initView(){
        nickName = (EditText) findViewById(R.id.nickname);
        pwd = (EditText) findViewById(R.id.pwd);
        doublecheckpwd = (EditText) findViewById(R.id.double_check_pwd);
        radioGroup = (RadioGroup) findViewById(R.id.gender);
        email = (EditText) findViewById(R.id.email);
        address = (EditText) findViewById(R.id.address);
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.submit:
                String user_name = nickName.getText().toString();
                String user_password = pwd.getText().toString();
                if(nickName.getText().toString().length()>0){
                    if(pwd.getText().toString().equals(doublecheckpwd.getText().toString())){

                        RequestApiData.getInstance().getRegistData(user_id, user_name, MD5Util.getMD5Str(user_password), UserBaseInfo.class, NamePwdActivity.this);

                        Toast.makeText(this, "提交成功", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this, "两次输入密码不相同", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "昵称不合法", Toast.LENGTH_SHORT).show();
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
        //注册接口
        if (UrlConstance.KEY_REGIST_INFO.equals(apiName)) {
            if (object != null && object instanceof UserBaseInfo) {
                UserBaseInfo info = (UserBaseInfo) object;
                String successCode = info.getRet();
                //请求成功
                if (successCode.equals(Constant.KEY_SUCCESS)) {
                    UserBaseInfo baseUser = new UserBaseInfo();
                    //baseUser.setEmail(info.getEmail());
                    baseUser.setNickname(info.getNickname());
                    //baseUser.setUserhead(info.getUserhead());
                    baseUser.setUserid(String.valueOf(info.getUserid()));
                    //ItLanBaoApplication.getInstance().setBaseUser(baseUser);
                    UserPreference.save(KeyConstance.IS_USER_ID, String.valueOf(info.getUserid()));
                    //UserPreference.save(KeyConstance.IS_USER_ACCOUNT, info.getEmail());
                    UserPreference.save(KeyConstance.IS_USER_PASSWORD, pwd.getText().toString());

                    Log.d(TAG, "onSuccess: startActivity MainActivity");
                    Intent intent = new Intent(NamePwdActivity.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(NamePwdActivity.this, "注册成功...", Toast.LENGTH_SHORT).show();

                    NamePwdActivity.this.finish();

                } else {
                    Toast.makeText(NamePwdActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    @Override
    public void onFailure(String apiName, Throwable t, int errorNo, String strMsg) {

    }
}
