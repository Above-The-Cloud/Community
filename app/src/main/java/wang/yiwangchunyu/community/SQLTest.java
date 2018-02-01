package wang.yiwangchunyu.community;

/**
 * Created by yiwangchunyu on 2018/2/1.
 */

import android.util.Log;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLTest {
    private static final String TAG = "SQLTest";
    static String sql = null;
    static SQLHelper db1 = null;
    static ResultSet ret = null;

    public SQLTest() {
        sql = "select *from user_info";//SQL语句
        db1 = new SQLHelper(sql);//创建DBHelper对象
        Log.d(TAG, "SQLTest: after new SQLHelper(sql)");
        try {
            ret = db1.pst.executeQuery();//执行语句，得到结果集
            while (ret.next()) {
                String name = ret.getString(1);
                String password = ret.getString(2);
                String time = ret.getString(3);
                Log.d(TAG, name + "\t" + password + "\t" + time );
            }//显示数据
            ret.close();
            db1.close();//关闭连接
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e(TAG, "SQLTest: " + e.getMessage());
        }
    }

}