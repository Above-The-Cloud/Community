package wang.yiwangchunyu.community.discovery;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import wang.yiwangchunyu.community.R;

/**
 * Created by XinyuJiang on 2018/3/30.
 */

public class Adpter_three extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;

    private ArrayList<Shangjia> mDatas = new ArrayList<>();

    private View mHeaderView;

    private OnItemClickListener mListener;

    public Adpter_three(ArrayList<Shangjia> dataList){
        mDatas = dataList;
    }

    public void setOnItemClickListener(OnItemClickListener li) {
        mListener = li;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public void addDatas(ArrayList<Shangjia> datas) {
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mHeaderView != null && viewType == TYPE_HEADER) return new Holder(mHeaderView);
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.shangjia_item_index, parent, false);
        return new Holder(layout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(getItemViewType(position) == TYPE_HEADER) return;

        final int pos = getRealPosition(viewHolder);
        final Shangjia data = mDatas.get(pos);
        if(viewHolder instanceof Holder) {
            ((Holder) viewHolder).title.setText(data.getTitle());
            ((Holder) viewHolder).content.setText(data.getContent());
            ((Holder) viewHolder).price.setText(String.valueOf(data.getPrice()));
            ((Holder) viewHolder).avatar.setImageResource(data.getBackground());

            if(mListener == null) return;
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(pos, data.getTitle());
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
        return mHeaderView == null ? mDatas.size() : mDatas.size() + 1;
    }

    class Holder extends RecyclerView.ViewHolder {

        TextView price;
        TextView title;
        TextView content;
        ImageView avatar;

        public Holder(View itemView) {
            super(itemView);
            if(itemView == mHeaderView) return;
            title = (TextView) itemView.findViewById(R.id.item_title);
            content = (TextView) itemView.findViewById(R.id.item_content);
            price = (TextView) itemView.findViewById(R.id.item_price);
            avatar = (ImageView) itemView.findViewById(R.id.item_image);
        }
    }

    interface OnItemClickListener {
        void onItemClick(int position, String data);
    }
}
