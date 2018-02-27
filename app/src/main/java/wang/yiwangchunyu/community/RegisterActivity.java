package wang.yiwangchunyu.community;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity implements HttpResponeCallBack {

    private EditText loginNick;//用户名
    private EditText phonenumber;//用户手机号
    private EditText password;//注册密码
    private EditText repeatpassword;//重复输入密码
    private EditText identifyingcode;//验证码
    private Button registBtnCode;//注册验证码
    private Button registBtn;//注册
    private CircleImageView circleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
    }

    private void initView() {
        loginNick = (EditText) findViewById(R.id.register_edit_account);
        phonenumber = (EditText) findViewById(R.id.register_edit_phonenumber);
        password = (EditText) findViewById(R.id.register_edit_pwd);
        repeatpassword = (EditText) findViewById(R.id.register_edit_repeat_pwd);
        identifyingcode = (EditText) findViewById(R.id.register_edit_identifying_code);
        registBtn = (Button) findViewById(R.id.register_btn_login);
        registBtnCode = (Button) findViewById(R.id.register_send_identifying_code);
        circleImageView = (CircleImageView) findViewById(R.id.logo);

        registBtnCode.setOnClickListener(new Button.OnClickListener(){//点击发送验证码按钮
            @Override
            public void onClick(View view) {
                String phoneStr = phonenumber.getText().toString();
                if(!TextUtils.isEmpty(phoneStr)){
                    if(Utils.isPhoneNumber(phoneStr)){
                        /*发送验证码
                        *
                        *
                        *
                        *
                        *
                        * */
                    }else {
                        Toast.makeText(RegisterActivity.this, "输入手机号有误", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        registBtn.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //获得用户输入的信息
                String nick = loginNick.getText().toString();
                String phoneStr = phonenumber.getText().toString();
                String passwordStr = password.getText().toString();
                String repeatpasswordStr = repeatpassword.getText().toString();
                String identifyingcodeStr = identifyingcode.getText().toString();
                if (!TextUtils.isEmpty(nick) &&
                        !TextUtils.isEmpty(phoneStr)
                        && !TextUtils.isEmpty(passwordStr) && !TextUtils.isEmpty(identifyingcodeStr)) {
                    if (passwordStr.equals(repeatpasswordStr)) { //验证两次密码是否一致

                        RequestApiData.getInstance().getRegistData(nick, passwordStr, passwordStr,
                                AnalyticalRegistInfo.class, RegisterActivity.this);
                    } else {
                        Toast.makeText(RegisterActivity.this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "输入信息未完全", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onResponeStart(String apiName) {
        // TODO Auto-generated method stub
        Toast.makeText(RegisterActivity.this, "正在请求数据...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoading(String apiName, long count, long current) {
        Toast.makeText(RegisterActivity.this, "Loading...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(String apiName, Object object) {
        // TODO Auto-generated method stub
        //注册接口
        if (UrlConstance.KEY_REGIST_INFO.equals(apiName)) {
            if (object != null && object instanceof AnalyticalRegistInfo) {
                AnalyticalRegistInfo info = (AnalyticalRegistInfo) object;
                String successCode = info.getRet();
                //请求成功
                if (successCode.equals(Constant.KEY_SUCCESS)) {
                    UserBaseInfo baseUser = new UserBaseInfo();
                    baseUser.setEmail(info.getEmail());
                    baseUser.setNickname(info.getNickname());
                    baseUser.setUserhead(info.getUserhead());
                    baseUser.setUserid(String.valueOf(info.getUserid()));
                    //ItLanBaoApplication.getInstance().setBaseUser(baseUser);
                    //UserPreference.save(KeyConstance.IS_USER_ID, String.valueOf(info.getUserid()));
                    //UserPreference.save(KeyConstance.IS_USER_ACCOUNT, info.getEmail());
                    //UserPreference.save(KeyConstance.IS_USER_PASSWORD, password.getText().toString());


                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    RegisterActivity.this.startActivity(intent);

                    Toast.makeText(RegisterActivity.this, "注册成功...", Toast.LENGTH_SHORT).show();

                    RegisterActivity.this.finish();

                } else {
                    Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    @Override
    public void onFailure(String apiName, Throwable t, int errorNo, String strMsg) {
        Toast.makeText(RegisterActivity.this, "Failure", Toast.LENGTH_SHORT).show();
    }
}
