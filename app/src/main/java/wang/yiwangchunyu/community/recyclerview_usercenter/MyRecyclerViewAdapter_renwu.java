package wang.yiwangchunyu.community.recyclerview_usercenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import wang.yiwangchunyu.community.R;
import wang.yiwangchunyu.community.dataStructures.TasksShowOnIndex;
import wang.yiwangchunyu.community.dataStructures.TasksShowOnUsercenter;
import wang.yiwangchunyu.community.recycleview_two.MyRecyclerViewAdapter;
import wang.yiwangchunyu.community.recycleview_two.MyRecyclerViewOnclickInterface;

/**
 * Created by xinyu jiang on 2018/11/8.
 */

public class MyRecyclerViewAdapter_renwu extends RecyclerView.Adapter<MyRecyclerViewAdapter_renwu.MyViewHolder> {


    private Context context;
    private ArrayList<TasksShowOnUsercenter> mDatas;
    MyRecyclerViewAdapter_renwu.MyViewHolder holder = null;//viewholder,可以提高recycleview的性能

    private MyRecyclerViewOnclickInterface mOnItemClickLitener;

    public void setOnItemClickLitener(MyRecyclerViewOnclickInterface mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public MyRecyclerViewAdapter_renwu(Context context, ArrayList<TasksShowOnUsercenter> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public MyRecyclerViewAdapter_renwu.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (headView!=null && viewType==TYPE_HEADER) return new MyViewHolder(headView);

        holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.recyclerview_item, parent, false));

        return holder;
    }

    @Override
    public void onBindViewHolder(final MyRecyclerViewAdapter_renwu.MyViewHolder holder, int position) {
        //此方法内可以对布局中的控件进行操作
        if (getItemViewType(position)==TYPE_HEADER) return;
        final int pos=getRealPosition(holder);
        TasksShowOnUsercenter tasksShowOnUsercenter = mDatas.get(pos);
        try {
            holder.title.setText(new String(tasksShowOnUsercenter.getTitle().getBytes("ISO-8859-1"),"UTF-8"));
            holder.headTitle.setText(new String(tasksShowOnUsercenter.getTitle().getBytes("ISO-8859-1"),"UTF-8"));
            holder.content.setText(new String(tasksShowOnUsercenter.getContent().getBytes("ISO-8859-1"),"UTF-8"));
            holder.releaser_name.setText(new String(tasksShowOnUsercenter.getUser_id().getBytes("ISO-8859-1"),"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //holder.img.setImageBitmap(tasksShowOnIndex.getImagesUrl());
        holder.commision.setText(String.valueOf(tasksShowOnUsercenter.getPrice()));
        holder.time.setText(tasksShowOnUsercenter.getCtime().substring(0,16));
        holder.address.setText("华东师范大学中北小区");
            //.imageView.setImageBitmap(getBitmapFromURL(tasksShowOnIndex.getImagesUrl().get(0)));

//        Glide.with(context).load(mDatas.get(position).getIm()).into(holder.img);

            // 如果设置了回调，则设置点击事件
            if (mOnItemClickLitener != null) {
                //点击监听
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getLayoutPosition();
                        mOnItemClickLitener.onItemClick(v, pos);
                    }
                });

                //长按监听
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int pos = holder.getLayoutPosition();
                        mOnItemClickLitener.onItemLongClick(v, pos);
                        //返回true可以让长按事件被消耗，避免出发点击事件
                        return true;
                    }
                });
            }

    }

    @Override
    public int getItemCount() {
        //headView也算一个Item
        return headView==null? mDatas.size():mDatas.size()+1;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;//标题
        ImageView img;//显示的图片
        TextView headTitle;//头部标题
        TextView commision;//报酬
        TextView address;//发布地址
        TextView releaser_name;//发布者Id
        TextView content;//内容
        TextView time;//发布时间
        ImageView imageView;//发布时的图片


        public MyViewHolder(View view) {
            super(view);
            title= (TextView) itemView.findViewById(R.id.item_title);
            img= (ImageView) itemView.findViewById(R.id.item_image);
            headTitle= (TextView) itemView.findViewById(R.id.item_headtitle);
            commision= (TextView) itemView.findViewById(R.id.commision);
            address = (TextView) itemView.findViewById(R.id.address);
            releaser_name= (TextView) itemView.findViewById(R.id.releaser_name);
            content = (TextView) itemView.findViewById(R.id.content);
            time = (TextView) itemView.findViewById(R.id.data);
            imageView = (ImageView)itemView.findViewById(R.id.picture);
        }
    }

    public static final int TYPE_HEADER = 0;//显示headvuiew
    public static final int TYPE_NORMAL = 1;//显示普通的item
    private View headView;//这家伙就是Banner


    public View getHeadView(){
        return headView;
    }

    @Override
    public int getItemViewType(int position) {
        if (headView==null)
            return TYPE_NORMAL;
        if (position==0)
            return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    private int getRealPosition(RecyclerView.ViewHolder holder) {
        int position=holder.getLayoutPosition();
        return headView==null? position:position-1;
    }

}