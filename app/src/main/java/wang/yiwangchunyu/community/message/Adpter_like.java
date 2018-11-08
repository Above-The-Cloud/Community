package wang.yiwangchunyu.community.message;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import wang.yiwangchunyu.community.R;
import wang.yiwangchunyu.community.dataStructures.LikeInfo;
import wang.yiwangchunyu.community.recycleview_two.MyRecyclerViewOnclickInterface;


/**
 * Created by xinyu jiang on 2018/11/3.
 */

public class Adpter_like extends RecyclerView.Adapter<Adpter_like.MyViewHolder> {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;

    private Context context;
    private ArrayList<LikeInfo> mDatas = new ArrayList<>();
    MyViewHolder holder = null;//viewholder,可以提高recycleview的性能


    private View mHeaderView;

    // 设置点击事件的接口，利用接口回调，来完成点击事件
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private MyRecyclerViewOnclickInterface mOnItemClickLitener;

    public void setOnItemClickLitener(MyRecyclerViewOnclickInterface mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public Adpter_like(Context context, ArrayList<LikeInfo> mDatas){
        this.context = context;
        this.mDatas = mDatas;
    }


    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public void addDatas(ArrayList<LikeInfo> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if(mHeaderView == null) return TYPE_NORMAL;
        if(position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mHeaderView != null && viewType == TYPE_HEADER) return new Adpter_like.MyViewHolder(mHeaderView);
        View layout = LayoutInflater.from(context).inflate(R.layout.recyclerview_item_msg, parent, false);
        final Adpter_like.MyViewHolder holder = new Adpter_like.MyViewHolder(layout);

//        holder.content.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                int position = holder.getAdapterPosition();
//                Shangjia shangjia = shangjiaArrayList.get(position-1);
//                Toast.makeText(view.getContext(), "you clicked view" + shangjia.getTitle(), Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(view.getContext(), ShangjiaInfo.class);
//                view.getContext().startActivity(intent);
//            }
//        });

        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, int position) {
        if(getItemViewType(position) == TYPE_HEADER) return;

        final int pos = getRealPosition(viewHolder);
        final LikeInfo data = mDatas.get(pos);
        if(viewHolder instanceof Adpter_like.MyViewHolder) {
            (viewHolder).uid.setText(data.getUid());
            (viewHolder).content.setText((data.getPublish_info()).getContent());
            (viewHolder).releaser_name.setText((data.getPublish_info()).getUserId());
            (viewHolder).time.setText(data.getTime());

            if(mOnItemClickLitener == null) return;

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
//            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mOnItemClickLitener.onItemClick(viewHolder.itemView, pos);
//                    Toast.makeText(v.getContext(), "hello", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(v.getContext(), LikeInfo.class);
//                    intent.putExtra("ShangjiaInfo", data);
//                    v.getContext().startActivity(intent);
//                }
//            });
        }
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? mDatas.size() : mDatas.size() + 1;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        TextView uid;
        TextView releaser_name;
        TextView time;
        TextView content;


        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            if(itemView == mHeaderView) return;
            uid = (TextView) itemView.findViewById(R.id.item_uid);
            releaser_name = (TextView) itemView.findViewById(R.id.releaser_name);
            time = (TextView) itemView.findViewById(R.id.item_date);
            content = (TextView) itemView.findViewById(R.id.item_content);

        }
    }




}