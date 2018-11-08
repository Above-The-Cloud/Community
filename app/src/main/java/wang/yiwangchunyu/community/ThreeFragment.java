package wang.yiwangchunyu.community;

/**
 * Created by Administrator on 2018/3/8.
 * Edited by Robin on 2018/10/30
 */

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import wang.yiwangchunyu.community.discovery.Adpter_three;
import wang.yiwangchunyu.community.discovery.Shangjia;
import android.support.v4.widget.SwipeRefreshLayout;



public class ThreeFragment extends Fragment{


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

    private String address_lafu = "普陀区金沙江路1759号圣诺亚大厦2楼202(近伯士路圣诺亚皇冠假日大酒店)";
    private String bonus_lafu = "100元晚餐卷一张，可叠加，送餐上门配送费10元";
    private String content_lafu = "企业创始人陈松陶携手上海知名主持人丁晓峰打造的具有地道成都风味的火锅潮人地标\n包含露天位、景观位，wifi、沙发、包间齐全，另有贴心的宝宝椅；支持刷卡、现金、网上支付等多种支付方式";
    private String phone_lafu = "02132587787";

    private String address_yucahng = "普陀区祁连山南路398号(近同普路)";
    private String bonus_yuchang = "30元浴场门票一张\n双十一特惠，儿童洗浴立减50\n晚间(八点之后)入馆立减五十";
    private String content_yuchang = "面积达12000平米可容纳多达1400人的日式风格浴场\n提供免费停车，含有吸烟区和无烟区\n提供wifi、免费充电区域\n支持现金、手机支付";
    private String phone_yuchang = "02162310660";

    private String address_kanye = "黄浦区淮海东路68号(光明中学旁)";
    private String bonus_kanye = "100元火锅券一张，含五花牛肉一份，小羊肉一份，虾滑一份，鱼丸一份，素菜若干\n2-3人餐满200打5.9折\n4-5人餐满400打7折";
    private String content_kanye = "荣获全国十佳时尚火锅、成都十大必吃火锅多项大奖，致力于美味研制，满足最刁嘴人群的口味，是美味火锅的完美代言词\n提供wifi、宝宝椅，含有景观位、沙发位\n支持银行卡、现金、网上支付等多种支付方式";
    private String phone_kanye = "02163463637";

    private String address_meijia = "长宁区汇川路99号新时空国际商务广场28楼2813室";
    private String bonus_meijia = "价值150元美甲体验券一张，不可叠加，新顾客限用，对于钻石会员可上门服务\n担任手部美甲套餐仅售58元(原价158)\n单人足部美甲套餐仅售78(原价188)\n";
    private String content_meijia = "挑选产品都是选择健康环保的材料，采用日式嫁接睫毛，保证无伤无害\n承诺无隐形消费、一对一服务、提供免费茶水小食、免费wifi、仅接受预约";
    private String phone_meijia = "15901784298";

    private String address_mangrenanmo = "白兰路137弄1号绿洲广场A座906室";
    private String bonus_mangrenanmo = "刮痧体验券一张，可上门服务\n中式推拿(60分钟)+刮痧/拔罐仅售118(原价158)\n中式足疗单人立减20元券\n半身针灸立减10元劵";
    private String content_mangrenanmo = "环境舒适，技术专业，提供高速免费wifi";
    private String phone_mangrenanmo = "02132125266";

    private String address_tianla = "普陀区中山北路3300号月星环球港4楼L4059号商铺";
    private String bonus_tianla = "50元抢购100元代金券(全场可用)\n全场满300立减70，会员用户再减50\n周末虾滑、毛肚、藕片均半价优惠";
    private String content_tianla = "天辣已经创立了8年，以做健康川菜、为人民服务、旨在创建绿色川菜之宗\n含有包间、卡座、沙发、景观位、露天位，有停车位，提供宝宝椅、免费wifi";
    private String phone_tianla = "02162077117";

    private String address_caichang = "长宁区遵义路760号新遵义菜市场23号摊位";
    private String bonus_caichang = "三日份菜量，可送上门\n新鲜蔬菜，满20立减3元\n每次购买送2个土豆";
    private String content_caichang = "保证新鲜绿色蔬菜，新鲜现宰肉，保证不含隔日食物";
    private String phone_caichang = "13651727086";

    private String address_zhou = "普陀区金沙江路1678号B1F";
    private String bonus_zhou = "外卖配送免配送费\n限时领取满30减5优惠卷\n使用美团支付随机立减0-5元";
    private String content_zhou = "专业做粥15年，口味独特而丰富，满足各年龄端人群需求";
    private String phone_zhou = "15221152313";

    private String address_hufu = "普陀区中环绿洲6号楼1705(金沙江路真北路)";
    private String bonus_hufu = "单人痘肌护理，会员提前预约可上门服务\n大气泡毛孔清洁套餐降至108(原价580)\n单人日式自然款美睫套餐，满三次送一次\n";
    private String content_hufu = "成立于2014年慈慧皮肤管理中心先进的韩国皮肤管理、技术与服务的最大限度呈现，专业的韩国技术、产品、仪器相结合以改善各种皮肤问题\n无推销办卡、无隐形消费、不满意免费重做、一对一服务、免费茶水小食、仅接受预约";
    private String phone_hufu = "02162108621";

    private void initShangjia(){
        Shangjia shangjia1 = new Shangjia(120, "辣府", address_lafu, content_lafu, R.mipmap.lafu, 5.0, bonus_lafu, 3.3, phone_lafu);
        shangjiaArrayList.add(shangjia1);
        Shangjia shangjia2 = new Shangjia(102, "极乐汤(金沙江店)", address_yucahng, content_yuchang, R.mipmap.yuchang, 4.0, bonus_yuchang, 4.2, phone_yuchang);
        shangjiaArrayList.add(shangjia2);
        Shangjia shangjia3 = new Shangjia(123, "Kanye秘制火锅", address_kanye, content_kanye, R.mipmap.kanye, 5.0, bonus_kanye, 6.8, phone_kanye);
        shangjiaArrayList.add(shangjia3);
        Shangjia shangjia4 = new Shangjia(164, "恋甲美甲", address_meijia, content_meijia, R.mipmap.meijia, 3.0, bonus_meijia, 1.8, phone_meijia);
        shangjiaArrayList.add(shangjia4);
        Shangjia shangjia5 = new Shangjia(107, "盲人按摩", address_mangrenanmo, content_mangrenanmo, R.mipmap.mangrenanmo, 3.5, bonus_mangrenanmo, 0.5, phone_mangrenanmo);
        shangjiaArrayList.add(shangjia5);
        Shangjia shangjia6 = new Shangjia(103, "天辣", address_tianla, content_tianla, R.mipmap.tianla, 5.0, bonus_tianla, 0.4, phone_tianla);
        shangjiaArrayList.add(shangjia6);
        Shangjia shangjia7 = new Shangjia(23, "菜场周大妈", address_caichang, content_caichang, R.mipmap.caichang, 4.0, bonus_caichang, 2.8, phone_caichang);
        shangjiaArrayList.add(shangjia7);
        Shangjia shangjia8 = new Shangjia(20, "吴家粥铺", address_zhou, content_zhou, R.mipmap.zhou, 4.5, bonus_zhou, 0.9, phone_zhou);
        shangjiaArrayList.add(shangjia8);
        Shangjia shangjia9 = new Shangjia(99, "慈慧美肤中心", address_hufu, content_hufu, R.mipmap.hufu, 4.0, bonus_hufu, 3.1, phone_hufu);
        shangjiaArrayList.add(shangjia9);

//        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
//                android.R.color.holo_orange_light, android.R.color.holo_red_light);
//
//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                new AsyncTask<Void, Void, Void>() {
//
//                    @Override
//                    protected Void doInBackground(Void... voids) {
//                        SystemClock.sleep(2000);
//                        return null;
//                    }
//
//                    @Override
//                    protected void onPostExecute(Void aVoid) {
//                        Toast.makeText(getActivity(), "下拉刷新成功", Toast.LENGTH_SHORT).show();
//                        mSwipeRefreshLayout.setRefreshing(false);
//                    }
//                }.execute();
//            }
//        });

    }

}