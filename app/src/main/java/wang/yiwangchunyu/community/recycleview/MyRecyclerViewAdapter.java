package wang.yiwangchunyu.community.recycleview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import wang.yiwangchunyu.community.R;

/**
 * Created by XinyuJiang on 2018/3/8.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {


    private Context context;
    private ArrayList<Recycler_Item> mDatas;
    MyViewHolder holder = null;//viewholder,可以提高recycleview的性能

    private MyRecyclerViewOnclickInterface mOnItemClickLitener;

    public void setOnItemClickLitener(MyRecyclerViewOnclickInterface mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public MyRecyclerViewAdapter(Context context, ArrayList<Recycler_Item> mDatas) {
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

        holder.title.setText(mDatas.get(position).getTitle());

        Glide.with(context).load(mDatas.get(position).getImgurl()).into(holder.img);

        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            //点击监听
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

            //长按监听
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
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


        public MyViewHolder(View view) {
            super(view);
            title= (TextView) itemView.findViewById(R.id.item_title);
            img= (ImageView) itemView.findViewById(R.id.item_image);
            headTitle= (TextView) itemView.findViewById(R.id.item_headtitle);

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