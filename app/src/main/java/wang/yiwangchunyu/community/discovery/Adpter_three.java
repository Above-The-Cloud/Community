package wang.yiwangchunyu.community.discovery;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;

import wang.yiwangchunyu.community.R;

/**
 * Created by XinyuJiang on 2018/3/30.
 */

public class Adpter_three extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;

    private ArrayList<Shangjia> shangjiaArrayList = new ArrayList<>();

    private View mHeaderView;

    // 设置点击事件的接口，利用接口回调，来完成点击事件
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public Adpter_three(ArrayList<Shangjia> dataList){
        shangjiaArrayList = dataList;
    }


    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public void addDatas(ArrayList<Shangjia> datas) {
        shangjiaArrayList.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if(mHeaderView == null) return TYPE_NORMAL;
        if(position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mHeaderView != null && viewType == TYPE_HEADER) return new Holder(mHeaderView);
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.shangjia_item_index, parent, false);
        final Holder holder = new Holder(layout);

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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();
                Shangjia shangjia = shangjiaArrayList.get(pos - 1);
//                Toast.makeText(view.getContext(), "you clicked number" + shangjia.getTitle(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), ShangjiaInfo.class);
                intent.putExtra("ShangjiaInfo", shangjia);
                view.getContext().startActivity(intent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        if(getItemViewType(position) == TYPE_HEADER) return;

        final int pos = getRealPosition(viewHolder);
        final Shangjia data = shangjiaArrayList.get(pos);
        if(viewHolder instanceof Holder) {
            ((Holder) viewHolder).title.setText(data.getTitle());
            ((Holder) viewHolder).content.setText(data.getContent());
            ((Holder) viewHolder).price.setText(String.valueOf(data.getPrice()));
            ((Holder) viewHolder).avatar.setImageResource(data.getBackground());
            ((Holder) viewHolder).distance.setText(String.valueOf(data.getDistance()) + "km");

            if(mOnItemClickLitener == null) return;
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //mOnItemClickLitener.onItemClick(viewHolder.itemView, pos);
                    Toast.makeText(v.getContext(), "hello", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(v.getContext(), ShangjiaInfo.class);
                    intent.putExtra("ShangjiaInfo", data);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? shangjiaArrayList.size() : shangjiaArrayList.size() + 1;
    }

    class Holder extends RecyclerView.ViewHolder {
        View itemView;
        TextView price;
        TextView title;
        TextView content;
        ImageView avatar;
        TextView distance;

        public Holder(View view) {
            super(view);
            itemView = view;
            if(itemView == mHeaderView) return;
            title = (TextView) itemView.findViewById(R.id.item_title);
            content = (TextView) itemView.findViewById(R.id.item_content);
            price = (TextView) itemView.findViewById(R.id.item_price);
            avatar = (ImageView) itemView.findViewById(R.id.item_image);
            distance = (TextView) itemView.findViewById(R.id.item_distance);
        }
    }




}
