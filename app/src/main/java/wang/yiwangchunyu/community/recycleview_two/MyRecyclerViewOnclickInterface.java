package wang.yiwangchunyu.community.recycleview_two;

/**
 * Created by Administrator on 2018/3/8.
 */

import android.view.View;


public interface MyRecyclerViewOnclickInterface {

    void onItemClick(View view, int position);
    void onItemLongClick(View view, int position);
}