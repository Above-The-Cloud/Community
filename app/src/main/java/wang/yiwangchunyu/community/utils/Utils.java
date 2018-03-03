package wang.yiwangchunyu.community.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Administrator
 * 工具类
 */
public class Utils {

    public static boolean isEmail(String email){   
    	
        String str="^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
           Pattern p = Pattern.compile(str);     
           Matcher m = p.matcher(email);  
           return m.matches();     
       }

    public static boolean isPhoneNumber(String phoneStr) {
        //TODO: 电话号码检查待实现
        return true;
    }
}
