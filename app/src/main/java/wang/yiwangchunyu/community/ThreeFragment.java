package wang.yiwangchunyu.community;

/**
 * Created by Administrator on 2018/3/8.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import wang.yiwangchunyu.community.discovery.Adpter_three;
import wang.yiwangchunyu.community.discovery.Shangjia;


public class ThreeFragment extends Fragment {

    @BindView(R.id.srl_three)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private Adpter_three mAdapter;

    private ArrayList<Shangjia> shangjiaArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_three, container, false);

        initShangjia();

        RecyclerView mRecyclerview = (RecyclerView) view.findViewById(R.id.id_recyclerview_three);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());

        mRecyclerview.setLayoutManager(layoutManager);

        mRecyclerview.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new Adpter_three(shangjiaArrayList);
        mRecyclerview.setAdapter(mAdapter);
        setHeader(mRecyclerview);

        return view;
    }

    private void setHeader(RecyclerView view) {
        View header = LayoutInflater.from(this.getActivity()).inflate(R.layout.faxian_headview, view, false);
        mAdapter.setHeaderView(header);
    }

    private void initShangjia(){
        Shangjia shangjia1 = new Shangjia(80, "辣府","100元晚餐卷一张，可叠加，送餐上门配送费10元");
        shangjiaArrayList.add(shangjia1);
        Shangjia shangjia2 = new Shangjia(20, "金沙江浴场","30元浴场门票一张");
        shangjiaArrayList.add(shangjia2);
        Shangjia shangjia3 = new Shangjia(66, "Kanye秘制火锅","100元火锅卷一张，含五花牛肉一份，小羊肉一份，虾滑一份，鱼丸一份，素菜若干");
        shangjiaArrayList.add(shangjia3);
        Shangjia shangjia4 = new Shangjia(100, "丽丽美甲","价值150元美甲体验券一张，不可叠加，新顾客限用，可上门服务");
        shangjiaArrayList.add(shangjia4);
        Shangjia shangjia5 = new Shangjia(99, "盲人按摩","刮痧体验券一张，可上门服务");
        shangjiaArrayList.add(shangjia5);
        Shangjia shangjia6 = new Shangjia(80, "辣府","100元晚餐卷一张，可叠加，送餐上门配送费10元");
        shangjiaArrayList.add(shangjia6);
        Shangjia shangjia7 = new Shangjia(70, "菜场周大妈","三日份菜量，可送上门");
        shangjiaArrayList.add(shangjia7);
        Shangjia shangjia8 = new Shangjia(20, "吴家粥铺","30元代金券");
        shangjiaArrayList.add(shangjia8);
        Shangjia shangjia9 = new Shangjia(99, "肤泉","单人痘肌护理，可上门服务");
        shangjiaArrayList.add(shangjia9);

    }

}