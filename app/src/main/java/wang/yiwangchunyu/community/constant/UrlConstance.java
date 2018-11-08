package wang.yiwangchunyu.community.constant;

/**
 * @author
 * 处理网络的参数常量类
 */
public class UrlConstance {
	 
	 public static final String ACCESSTOKEN_KEY ="community";

	   
   //签约公钥，即客户端与服务器协商订的一个公钥
   public static final String PUBLIC_KEY ="abovethecloud";
   public static final String APP_URL = "http://139.196.141.24:8080/WebCommunity/";
   
   //4.6注册用户接口
   public static final String KEY_REGIST_INFO ="Register";
   
   //4.8登录用户接口
   public static final String KEY_LOGIN_INFO ="Login";

   //获取用户基本信息
   public static final String KEY_USER_BASE_INFO ="GetUserInfo";

   //上传图片
   public static final String KEY_IMAGE_UPLOADER = "Imageupload";

   //上传发布信息
   public static  final String KEY_PUBLISH_INFO = "PublishInfo";

   //获取发布信息
   public static  final String KEY_GET_PUBLISH_INFO = "GetTasksInfo";

   //改变状态信息
   public static final String KEY_CHANGE_STATUS = "task/accept.php";

   //获取我的任务的状态信息
   public static final String KEY_MY_PUBLSH_BY_STATUS = "task/getMyPublish.php";

   //获取我的任务的点赞信息
   public static final String KEY_LIKELIST_BY_UID = "task/getLikeListByUid.php";

   public static final String APP_URL2 = "http://community.yiwangchunyu.wang:8088/";


}
