package wang.yiwangchunyu.community;

/**
 * Created by yiwangchunyu on 2018/2/11.
 */

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


public class LoginService {
    private static final String TAG = "LoginService";
    public static String loginByPost(String username,String password){
        try {
            Log.w(TAG, "loginByPost: begin");
            String path = "http://139.196.141.24:8080/WebCommunity/WebCommunity/Login";
            URL url = new URL(path);
            HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("POST");
            String data = "username="+URLEncoder.encode(username)+"&password="
                    +URLEncoder.encode(password);
            System.out.println(data);
            conn.setRequestProperty("Content=Type", "application/x-wwww-form-urlencoded");
            conn.setRequestProperty("Content-length", data.length()+"");
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            os.write(data.getBytes());
            int code = conn.getResponseCode();
            System.out.println(code);
            if (code == 200) {
                InputStream is = conn.getInputStream();
                String text = StreamTools.streamToString(is);
                return text;
            }else {
                return code + "";
            }

        } catch (MalformedURLException e) {

            e.printStackTrace();
            System.out.println("111111");
        } catch (ProtocolException e) {
            System.out.println("222222");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("33333");
            e.printStackTrace();
        }
        return null;
    }
    public static String httpUrlConnectionPost(String username, String password) {
        try {
            //创建URL对象
            URL url = new URL("http://139.196.141.24:8080/WebCommunity/WebCommunity/Login");
            //返回一个URLConnection对象，它表示到URL所引用的远程对象的连接
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            //在这里设置一些属性，详细见UrlConnection文档，HttpURLConnection是UrlConnection的子类
            //设置连接超时为5秒
            httpURLConnection.setConnectTimeout(5000);
            //设定请求方式(默认为get)
            httpURLConnection.setRequestMethod("POST");
            // 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在
            // http正文内，因此需要设为true, 默认情况下是false;
            httpURLConnection.setDoOutput(true);
            // 设置是否从httpUrlConnection读入，默认情况下是true;
            httpURLConnection.setDoInput(true);
            // Post 请求不能使用缓存
            httpURLConnection.setUseCaches(false);


            //这边开始设置请求头
            // 设定传送的内容类型是可序列化的java对象(如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
            httpURLConnection.setRequestProperty("Content-type", "application/x-java-serialized-object");
            //方法setRequestProperty(String key, String value)设置一般请求属性。
            // 连接，从上述url.openConnection()至此的配置必须要在connect之前完成，
            //httpURLConnection.connect();

            //这边设置请内容
            //getOutputStream()里默认就有connect（）了，可以不用写上面的连接
            //接下来我们设置post的请求参数，可以是JSON数据，也可以是普通的数据类型
            OutputStream outputStream = httpURLConnection.getOutputStream();
            /**
             * JSON数据的请求
             * outputStream.write(stringJson.getBytes(), 0, stringJson.getBytes().length);
             * outputStream.close();
             * **/
            /**
             * 字符串数据的请求
             *
             *
             DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
             String content = "username=" + username  + "&password=" + password;
             Log.d(TAG, "httpUrlConnectionPost: " + "\n" + "username=" + username  + "&password=" + password + "\n");
             dataOutputStream.writeBytes(content);
             dataOutputStream.flush();
             dataOutputStream.close();
             * **/
            ObjectOutputStream objOutputStrm = new ObjectOutputStream(outputStream);
            Map namepassMap = new HashMap();
            namepassMap.put("username", username);
            namepassMap.put("password", password);
            objOutputStrm.writeObject(namepassMap);
            objOutputStrm.flush();
            objOutputStrm.close();

            int code = httpURLConnection.getResponseCode();
            Log.d(TAG, "\n" + "ResponseCode: " + code);

            if(code==200){
                //读取返回的数据
                //返回打开连接读取的输入流，输入流转化为StringBuffer类型，这一套流程要记住，常用
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String line = null;
                StringBuffer stringBuffer = new StringBuffer();
                while ((line = bufferedReader.readLine()) != null) {
                    //转化为UTF-8的编码格式
                    line = new String(line.getBytes("UTF-8"));
                    stringBuffer.append(line);
                }
                Log.d("POST请求返回的数据: ", stringBuffer.toString());
                bufferedReader.close();
                httpURLConnection.disconnect();
                return stringBuffer.toString();
            }
            else return String.valueOf(code);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}