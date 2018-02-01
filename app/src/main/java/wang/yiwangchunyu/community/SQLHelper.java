package wang.yiwangchunyu.community;

/**
 * Created by yiwangchunyu on 2018/2/1.
 */

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static android.content.ContentValues.TAG;

public class SQLHelper {
    public static final String url = SQLConfig.URL;
    public static final String name = "com.mysql.jdbc.Driver";
    public static final String user = SQLConfig.USER;
    public static final String password = SQLConfig.PASSWORD;

    public Connection conn = null;
    public PreparedStatement pst = null;

    public SQLHelper(String sql) {
        try {
            Class.forName(name);//指定连接类型
            conn = DriverManager.getConnection(url, user, password);//获取连接
            pst = conn.prepareStatement(sql);//准备执行语句
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "SQLHelper: " + e.getMessage());
        }
    }

    public void close() {
        try {
            this.conn.close();
            this.pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e(TAG, "close: " + e.getMessage());
        }
    }
}
