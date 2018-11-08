package wang.yiwangchunyu.community.webService;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Objects;

import wang.yiwangchunyu.community.constant.UrlConstance;
import wang.yiwangchunyu.community.dataStructures.ChangeStatusResponse;
import wang.yiwangchunyu.community.dataStructures.MyTasksResponse;
import wang.yiwangchunyu.community.dataStructures.ReceivedLikeResponse;
import wang.yiwangchunyu.community.dataStructures.TaskPublishingInfo;
import wang.yiwangchunyu.community.dataStructures.TasksResponse;
import wang.yiwangchunyu.community.users.UserBaseInfo;
import wang.yiwangchunyu.community.utils.MD5Util;
 
/*
 * 网络接口
 */

/**
 * @author Administrator
 *
 */
public class RequestApiData {
	private static RequestApiData instance = null;
	private HttpResponeCallBack mCallBack = null;

	//创建接口对象
	public static RequestApiData getInstance() {
		if (instance == null) {
			instance = new RequestApiData();
		}
		return instance;
	}

	/**
	 * 4.6注册用户接口
	 * @param nickname  昵称
	 * @param userid
	 * @param password 密码
	 * @param clazz  数据返回的解析对象
	 * @param callback 回调
	 * 特别要注意参数位置不能变要根据文档来
	 * 请求方式：POST
	 */
	public void getRegistData(String userid, String nickname
			,String password, String userEmail, String userAddress, Class<UserBaseInfo> clazz,
		   HttpResponeCallBack callback) {
		 mCallBack = callback;
		 //这是每一个接口的唯一标示
		 String tagUrl = UrlConstance.KEY_REGIST_INFO;//注册接口
		 //将注册的信息保存在map中（须和服务器端一致）
		 HashMap<String, String> parameter = new HashMap<String, String>();
		 parameter.put("user_name", nickname);
		 parameter.put("user_id",userid);
		 parameter.put("user_password",password);
		parameter.put("user_address",userAddress);
		parameter.put("user_email", userEmail);
		//拼接参数信息，昵称，邮箱，密码，公钥，并用md5进行加密
        StringBuilder builder = new StringBuilder();
        builder.append(nickname);
        builder.append(userid);
        builder.append(password);
		builder.append(userEmail);
		builder.append(userAddress);
        builder.append(UrlConstance.PUBLIC_KEY);

        parameter.put(UrlConstance.ACCESSTOKEN_KEY, MD5Util.getMD5Str(builder.toString()));

		 //请求数据接口
		 RequestManager.post(UrlConstance.APP_URL,tagUrl, parameter, clazz, callback);

	}


	/**
	 * 4.8登录用户接口
	 * @param user_id
	 * @param password 密码
	 * @param clazz 数据返回的解析对象
	 * @param callback 回调
	 * 特别要注意参数位置不能变要根据文档来
	 * 请求方式：POST
	 */
	public void getLoginData(String user_id ,String password,
			Class<UserBaseInfo> clazz,
		   HttpResponeCallBack callback) {
		 mCallBack = callback;
		 //这是每一个接口的唯一标示
		 String tagUrl = UrlConstance.KEY_LOGIN_INFO;//登录接口
		 HashMap<String, String> parameter = new HashMap<String, String>();
		 parameter.put("user_id", user_id);
		 parameter.put("user_password", password);

			//拼接参数信息，邮箱，密码，公钥，并用md5进行加密
			StringBuilder builder = new StringBuilder();
			builder.append(user_id);
			builder.append(password);
			builder.append(UrlConstance.PUBLIC_KEY);

		 parameter.put(UrlConstance.ACCESSTOKEN_KEY,MD5Util.getMD5Str(builder.toString()));

		 //请求数据接口
		 RequestManager.post(UrlConstance.APP_URL,tagUrl, parameter, clazz, callback);

	}
	public void getUserInfo(String userid, Class<UserBaseInfo> clazz,
						  HttpResponeCallBack callback){
		mCallBack = callback;
		//这是每一个接口的唯一标示
		String tagUrl = UrlConstance.KEY_USER_BASE_INFO;//登录接口
		HashMap<String, String> parameter = new HashMap<String, String>();
		parameter.put("userid", userid);

		//拼接参数信息，userid，公钥，并用md5进行加密
		StringBuilder builder = new StringBuilder();
		builder.append(userid);
		builder.append(UrlConstance.PUBLIC_KEY);

		parameter.put(UrlConstance.ACCESSTOKEN_KEY,MD5Util.getMD5Str(builder.toString()));

		//请求数据接口
		RequestManager.post(UrlConstance.APP_URL, tagUrl, parameter, clazz, callback);
	}
	public void getPublishTaskInfo(TaskPublishingInfo task, Class<TasksResponse> clazz, HttpResponeCallBack callback){
		mCallBack = callback;
		//这是每一个接口的唯一标示
		String tagUrl = UrlConstance.KEY_PUBLISH_INFO;//登录接口
		HashMap<String, String> parameter = new HashMap<String, String>();
		parameter.put("user_id", task.getUserId());
		parameter.put("title", task.getTitle());
		parameter.put("restriction", task.getRestriction());
		parameter.put("content", task.getContent());
		parameter.put("category",task.getCategory());
		parameter.put("commission", String.valueOf(task.getCommission()));
		//拼接参数信息，userid，公钥，并用md5进行加密
		StringBuilder builder = new StringBuilder();
		builder.append(task.getUserId());
		builder.append(UrlConstance.PUBLIC_KEY);

		parameter.put(UrlConstance.ACCESSTOKEN_KEY,MD5Util.getMD5Str(builder.toString()));

		//请求数据接口
		RequestManager.post(UrlConstance.APP_URL, tagUrl, parameter, clazz, callback);


	}
	public void getPublishTaskInfoFromServer(Class<TasksResponse> clazz, HttpResponeCallBack callback) {
		mCallBack = callback;
		//这是每一个接口的唯一标示
		String tagUrl = UrlConstance.KEY_GET_PUBLISH_INFO;
		HashMap<String, String> parameter = new HashMap<String, String>();
		StringBuilder builder = new StringBuilder();
		builder.append(UrlConstance.PUBLIC_KEY);
		parameter.put(UrlConstance.ACCESSTOKEN_KEY,MD5Util.getMD5Str(builder.toString()));

		//请求数据接口
		RequestManager.post(UrlConstance.APP_URL, tagUrl, parameter, clazz, callback);
	}

	//获得我发布的任务的状态信息
	public void getPublishStatusFromServer(String userid, String status, Class<MyTasksResponse> clazz, HttpResponeCallBack callback) {
		mCallBack = callback;
		//这是每一个接口的唯一标示
		String tagUrl = UrlConstance.KEY_MY_PUBLSH_BY_STATUS;
		HashMap<String, String> parameter = new HashMap<String, String>();
		parameter.put("uid",userid);
		parameter.put("status", status);


		//请求数据接口
		RequestManager.post(UrlConstance.APP_URL2, tagUrl, parameter, clazz, callback);
	}

	//更改任务的状态
	public void changeTaskStatus(String userid, String publish_id, String status, Class<ChangeStatusResponse> clazz, HttpResponeCallBack callback){
		mCallBack = callback;
		//这是每一个接口的唯一标示
		String tagUrl = UrlConstance.KEY_CHANGE_STATUS;
		HashMap<String, String> parameter = new HashMap<String, String>();
		parameter.put("uid",userid);
		parameter.put("publish_id",publish_id);
		parameter.put("status", status);


		//请求数据接口
		RequestManager.post(UrlConstance.APP_URL2, tagUrl, parameter, clazz, callback);
	}

	//获得我发布的任务的状态信息
	public void getLikeListByUid(String userid, Class<ReceivedLikeResponse> clazz, HttpResponeCallBack callback) throws JSONException {
		mCallBack = callback;
		//这是每一个接口的唯一标示
		String tagUrl = UrlConstance.KEY_LIKELIST_BY_UID;
		HashMap<String, String> parameter = new HashMap<String, String>();
		parameter.put("uid",userid);

		//请求数据接口
		RequestManager.post(UrlConstance.APP_URL2, tagUrl, parameter, clazz, callback);
	}

}
