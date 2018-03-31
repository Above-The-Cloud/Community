package wang.yiwangchunyu.community.recycleview_two;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import wang.yiwangchunyu.community.R;
import wang.yiwangchunyu.community.dataStructures.TasksShowOnIndex;
//import static wang.yiwangchunyu.community.utils.Utils.getBitmapFromURL;

/**
 * Created by XinyuJiang on 2018/3/8.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {


    private Context context;
    private ArrayList<TasksShowOnIndex> mDatas;
    MyViewHolder holder = null;//viewholder,可以提高recycleview的性能

    private MyRecyclerViewOnclickInterface mOnItemClickLitener;

    public void setOnItemClickLitener(MyRecyclerViewOnclickInterface mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public MyRecyclerViewAdapter(Context context, ArrayList<TasksShowOnIndex> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (headView!=null && viewType==TYPE_HEADER) return new MyViewHolder(headView);

        holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.recyclerview_item, parent, false));

        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        //此方法内可以对布局中的控件进行操作
        if (getItemViewType(position)==TYPE_HEADER) return;
        final int pos=getRealPosition(holder);
        TasksShowOnIndex tasksShowOnIndex = mDatas.get(pos);
        holder.title.setText(tasksShowOnIndex.getTitle());
        //holder.img.setImageBitmap(tasksShowOnIndex.getImagesUrl());
        holder.headTitle.setText(tasksShowOnIndex.getTitle());
        holder.commision.setText(String.valueOf(tasksShowOnIndex.getCommission()));
        holder.releaser_name.setText(tasksShowOnIndex.getUserName());
        holder.time.setText(tasksShowOnIndex.getTime().substring(0,16));
        holder.address.setText("华东师范大学中北小区");
        holder.content.setText(tasksShowOnIndex.getContent());
        if (!tasksShowOnIndex.getImagesUrl().isEmpty())
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

    public void setHeadView(View headView){
        this.headView=headView;
        notifyItemInserted(0);
    }

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