package wang.yiwangchunyu.community;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import wang.yiwangchunyu.community.constant.Constant;
import wang.yiwangchunyu.community.constant.KeyConstance;
import wang.yiwangchunyu.community.constant.UrlConstance;
import wang.yiwangchunyu.community.users.UserBaseInfo;
import wang.yiwangchunyu.community.users.UserPreference;
import wang.yiwangchunyu.community.utils.Utils;
import wang.yiwangchunyu.community.webService.AnalyticalRegistInfo;
import wang.yiwangchunyu.community.webService.HttpResponeCallBack;
import wang.yiwangchunyu.community.webService.registerService.MobSMS;

public class RegisterActivity extends Activity implements HttpResponeCallBack {

    private EditText loginNick;//用户名
    private EditText phonenumber;//用户手机号
    private EditText identifyingcode;//验证码
    private Button registBtnCode;//注册验证码
    private Button registBtn;//注册
    private CircleImageView circleImageView;
    private MobSMS mobSMS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mobSMS = new MobSMS();
        initView();
    }

    private void initView() {
        loginNick = (EditText) findViewById(R.id.register_edit_account);
        phonenumber = (EditText) findViewById(R.id.register_edit_phonenumber);
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
                        // TODO 请获取国家信息

                        mobSMS.sendCode("86", phoneStr);

                    }else {
                        Toast.makeText(RegisterActivity.this, "输入手机号有误", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        registBtn.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                //
                //获得用户输入的信息
                String nick = loginNick.getText().toString();
                String phoneStr = phonenumber.getText().toString();
                String identifyingcodeStr = identifyingcode.getText().toString();
                if (!TextUtils.isEmpty(nick)  && !TextUtils.isEmpty(identifyingcodeStr)) {

                        //TODO 此处表单验证不完善，防止代码注入

                        //TODO 缺少国家信息
                        mobSMS.submitCode("86", phoneStr, identifyingcodeStr);

                        mobSMS.onDestroy();
                        //RequestApiData.getInstance().getRegistData(nick, passwordStr, passwordStr,
                        //        AnalyticalRegistInfo.class, RegisterActivity.this);


                } else {
                    Toast.makeText(RegisterActivity.this, "输入信息未完全", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onResponeStart(String apiName) {
        //
        Toast.makeText(RegisterActivity.this, "正在请求数据...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoading(String apiName, long count, long current) {
        Toast.makeText(RegisterActivity.this, "Loading...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(String apiName, Object object) {
        //
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
                    ItLanBaoApplication.getInstance().setBaseUser(baseUser);
                    UserPreference.save(KeyConstance.IS_USER_ID, String.valueOf(info.getUserid()));
                    UserPreference.save(KeyConstance.IS_USER_ACCOUNT, info.getEmail());
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
