package wang.yiwangchunyu.community;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import wang.yiwangchunyu.community.Task.Bimp;
import wang.yiwangchunyu.community.Task.FileUtils;
import wang.yiwangchunyu.community.Task.TestPicActivity;
import wang.yiwangchunyu.community.dataStructures.TaskPublishingInfo;

import static android.app.Activity.RESULT_OK;

/**
 * Created by XinyuJiang on 2018/3/11.
 */

public class FabuFragment extends Fragment{

    private TaskPublishingInfo taskPublishingInfo;//任务实例
    private GridView noScrollgridview;
    private GridAdapter adapter;
    private TextView activity_selectimg_send;
    private String localTempImgDir = "tempDir";
    private File f;

    private EditText item_titile;
    private EditText item_restriction;
    private EditText item_content;
    private EditText item_commission;
    private Button Release;
    private ImageView add_photo;
    private ImageView picture;
    private View view;
    private Uri imageUri;
    public static final int TAKE_PHOTO = 1;

    /*private String title;
    private String content;
    private int commission;
    private String restriction;
    private int viewed;
    private int liked;
    private Date publish_time;
*/


    public FabuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fabu, container, false);
        taskPublishingInfo = new TaskPublishingInfo();
        Init();

        return view;
    }

    public void Init(){

        item_titile = (EditText) view.findViewById(R.id.item_title);
        item_restriction = (EditText) view.findViewById(R.id.item_restriction);
        item_content = (EditText) view.findViewById(R.id.item_content);
        item_commission = (EditText) view.findViewById(R.id.item_commision);
        Release = (Button) view.findViewById(R.id.activity_selectimg_send);
        add_photo = (ImageView) view.findViewById(R.id.add_photo);
        picture = (ImageView) view.findViewById(R.id.picture);

        add_photo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
// 一个自定义的布局，作为显示的内容
                new PopupWindows(getContext());

            }
        });
        Release.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view) {
                //传递数据
                taskPublishingInfo.setTitle(item_titile.getText().toString());
                taskPublishingInfo.setRestriction(item_restriction.getText().toString());
                taskPublishingInfo.setContent(item_content.getText().toString());
                //taskPublishingInfo.setCommission(Integer.parseInt(item_commission.getText().toString()));
                Toast.makeText(getContext(),"任务发布成功！",Toast.LENGTH_LONG).show();
            }
        });
    }

    public class PopupWindows extends PopupWindow {

        public PopupWindows(Context mContext) {

            View view = View
                    .inflate(mContext, R.layout.item_popupwindows, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.fade_ins));
            LinearLayout ll_popup = (LinearLayout) view
                    .findViewById(R.id.ll_popup);
            ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.push_bottom_in_2));

            setWidth(LayoutParams.FILL_PARENT);
            setHeight(LayoutParams.FILL_PARENT);
            setBackgroundDrawable(new BitmapDrawable());
            setFocusable(true);
            setOutsideTouchable(true);
            setContentView(view);
            showAsDropDown(view);
            update();

            Button bt1 = (Button) view
                    .findViewById(R.id.item_popupwindows_camera);
            Button bt2 = (Button) view
                    .findViewById(R.id.item_popupwindows_Photo);
            Button bt3 = (Button) view
                    .findViewById(R.id.item_popupwindows_cancel);
            bt1.setOnClickListener(new OnClickListener() {//拍照
                public void onClick(View v) {
                    photo();
                    dismiss();
                }
            });
            bt2.setOnClickListener(new OnClickListener() {//相册
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(),
                            TestPicActivity.class);
                    startActivity(intent);
                    dismiss();
                }
            });
            bt3.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });

        }
    }

        /*public void showpopupWindows(Context mContext) {

            //Toast.makeText(view.getContext(),"测试测试测试",Toast.LENGTH_LONG).show();

            View view = LayoutInflater.from(mContext).inflate(
                    R.layout.item_popupwindows, null);

            Button bt1 = (Button) view
                    .findViewById(R.id.item_popupwindows_camera);
            Button bt2 = (Button) view
                    .findViewById(R.id.item_popupwindows_Photo);
            Button bt3 = (Button) view
                    .findViewById(R.id.item_popupwindows_cancel);
            bt1.setOnClickListener(new OnClickListener() {//拍照
                public void onClick(View v) {
                    photo();
                    //dismiss();
                }
            });
            bt2.setOnClickListener(new OnClickListener() {//相册
                public void onClick(View v) {
                    //Intent intent = new Intent(getActivity(),
                    //        TestPicActivity.class);
                    //startActivity(intent);
                    //dismiss();
                }
            });
            bt3.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    //dismiss();
                }
            });
            final PopupWindow popupWindow = new PopupWindow(view,
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);

            popupWindow.setTouchable(true);

            popupWindow.setTouchInterceptor(new OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    Log.i("mengdd", "onTouch : ");

                    return false;
                    // 这里如果返回true的话，touch事件将被拦截
                    // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
                }
            });
            // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
            // 我觉得这里是API的一个bug
            //popupWindow.setBackgroundDrawable(new BitmapDrawable());

            // 设置好参数之后再show
            popupWindow.showAsDropDown(view);
        }*/


    /*public void Init() {
        noScrollgridview = (GridView) getActivity().findViewById(R.id.noScrollgridview);
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new GridAdapter(this.getActivity());
        adapter.update();
        noScrollgridview.setAdapter(adapter);
        noScrollgridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                if (arg2 == Bimp.bmp.size()) {
                    new PopupWindows(getActivity(), noScrollgridview);
                } else {
                    Intent intent = new Intent(getActivity(),
                            PhotoActivity.class);
                    intent.putExtra("ID", arg2);
                    startActivity(intent);
                }
            }
        });
        activity_selectimg_send = (TextView) getActivity().findViewById(R.id.activity_selectimg_send);
        activity_selectimg_send.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                List<String> list = new ArrayList<String>();
                for (int i = 0; i < Bimp.drr.size(); i++) {
                    String Str = Bimp.drr.get(i).substring(
                            Bimp.drr.get(i).lastIndexOf("/") + 1,
                            Bimp.drr.get(i).lastIndexOf("."));
                    list.add(FileUtils.SDPATH+Str+".JPEG");
                    File fii=new File(list.get(i).toString());
                    Log.d("Pu", "str=="+fii.getName());
                    Log.d("Pu", "str=="+list.get(i).toString());
                }
                // 高清的压缩图片全部就在  list 路径里面了
                // 高清的压缩过的 bmp 对象  都在 Bim.bmp里面
                // 完成上传服务器后 .........
                FileUtils.deleteDir();
            }
        });
    }*/

    public class GridAdapter extends BaseAdapter {
        private LayoutInflater inflater; // 视图容器
        private int selectedPosition = -1;// 选中的位置
        private boolean shape;

        public boolean isShape() {
            return shape;
        }

        public void setShape(boolean shape) {
            this.shape = shape;
        }

        public GridAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public void update() {
            loading();
        }

        public int getCount() {
            return (Bimp.bmp.size() + 1);
        }

        public Object getItem(int arg0) {

            return null;
        }

        public long getItemId(int arg0) {

            return 0;
        }

        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        public int getSelectedPosition() {
            return selectedPosition;
        }

        /**
         *
         * ListView Item设置
         */
        public View getView(int position, View convertView, ViewGroup parent) {
            final int coord = position;
            ViewHolder holder = null;
            if (convertView == null) {

                convertView = inflater.inflate(R.layout.item_published_grida,
                        parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView
                        .findViewById(R.id.item_grida_image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position == Bimp.bmp.size()) {
                holder.image.setImageBitmap(BitmapFactory.decodeResource(
                        getResources(), R.drawable.icon_addpic_unfocused));
                if (position == 9) {
                    holder.image.setVisibility(View.GONE);
                }
            } else {
                holder.image.setImageBitmap(Bimp.bmp.get(position));
            }

            return convertView;
        }

        public class ViewHolder {
            public ImageView image;
        }

        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        adapter.notifyDataSetChanged();
                        break;
                }
                super.handleMessage(msg);
            }
        };

        public void loading() {
            new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        if (Bimp.max == Bimp.drr.size()) {
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                            break;
                        } else {
                            try {
                                String path = Bimp.drr.get(Bimp.max);
                                System.out.println(path);
                                Bitmap bm = Bimp.revitionImageSize(path);
                                Bimp.bmp.add(bm);
                                String newStr = path.substring(
                                        path.lastIndexOf("/") + 1,
                                        path.lastIndexOf("."));
                                FileUtils.saveBitmap(bm, "" + newStr);
                                Bimp.max += 1;
                                Message message = new Message();
                                message.what = 1;
                                handler.sendMessage(message);
                            } catch (IOException e) {

                                e.printStackTrace();
                            }
                        }
                    }
                }
            }).start();
        }
    }

    public String getString(String s) {
        String path = null;
        if (s == null)
            return "";
        for (int i = s.length() - 1; i > 0; i++) {
            s.charAt(i);
        }
        return path;
    }

    /*protected void onRestart() {
        adapter.update();
        super.onRestart();
    }*/

    /*public class PopupWindows extends PopupWindow {

        public PopupWindows(Context mContext, View parent) {

            View view = View
                    .inflate(mContext, R.layout.item_popupwindows, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.fade_ins));
            LinearLayout ll_popup = (LinearLayout) view
                    .findViewById(R.id.ll_popup);
            ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.push_bottom_in_2));

            setWidth(LayoutParams.FILL_PARENT);
            setHeight(LayoutParams.FILL_PARENT);
            setBackgroundDrawable(new BitmapDrawable());
            setFocusable(true);
            setOutsideTouchable(true);
            setContentView(view);
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            update();

            Button bt1 = (Button) view
                    .findViewById(R.id.item_popupwindows_camera);
            Button bt2 = (Button) view
                    .findViewById(R.id.item_popupwindows_Photo);
            Button bt3 = (Button) view
                    .findViewById(R.id.item_popupwindows_cancel);
            bt1.setOnClickListener(new View.OnClickListener() {//拍照
                public void onClick(View v) {
                    photo();
                    dismiss();
                }
            });
            bt2.setOnClickListener(new View.OnClickListener() {//相册
                public void onClick(View v) {
                    Intent intent = new Intent(this,
                            TestPicActivity.class);
                    startActivity(intent);
                    dismiss();
                }
            });
            bt3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });

        }
    }*/

    private static final int TAKE_PICTURE = 0x000000;
    private String path = "";

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TAKE_PHOTO:
                if(resultCode == RESULT_OK){
                    try{
                        Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(imageUri));
                        picture.setImageBitmap(bitmap);
                    }catch(FileNotFoundException e){
                        Toast.makeText(this.getActivity(), "拍照不成功", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            default:
                break;
        }
    }

    public void photo() {//拍照
//		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//		File file = new File(Environment.getExternalStorageDirectory()
//				+ "/myimage/", String.valueOf(System.currentTimeMillis())
//				+ ".jpg");
//		path = file.getPath();
//		Uri imageUri = Uri.fromFile(file);
//		openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//		startActivityForResult(openCameraIntent, TAKE_PICTURE);

        File dir = new File(getActivity().getExternalCacheDir(),"output_image.jpg");
        try{
            if(dir.exists()){
                dir.delete();
            }
            dir.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }

        if(Build.VERSION.SDK_INT >= 24){
            imageUri = FileProvider.getUriForFile(getContext(),"wang.yiwangchunyu.community.fileprovider",dir);
        }else{
            imageUri = Uri.fromFile(dir);
        }
        //启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);



        /*String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            try {
                File dir = new File(Environment.getExternalStorageDirectory() + "/" + localTempImgDir);
                if (!dir.exists())
                    dir.mkdirs();
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                f = new File(dir, new Date().getTime() + ".jpg");// localTempImgDir和localTempImageFileName是自己定义的名字
                Uri u = Uri.fromFile(f);
                intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
                startActivityForResult(intent, TAKE_PICTURE);
            } catch (Exception e) {
                Toast.makeText(this.getActivity(), "没有找到储存目录", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this.getActivity(), "没有储存卡", Toast.LENGTH_LONG).show();
        }*/
    }

    /*protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                if (Bimp.drr.size() < 9 && resultCode == -1) {

                    // 拍照
//				File f = new File(Environment.getExternalStorageDirectory() + "/" + localTempImgDir + "/" + localTempImgFileName);
                    try {
                        Uri u = Uri.parse(android.provider.MediaStore.Images.Media.insertImage(getContentResolver(), f.getAbsolutePath(), null, null));
                        path = f.getPath();
//					String URL = getRealFilePath(this, u);
//					Intent intent = new Intent(MyHeadPicture.this, ClipImageActivity.class);
//					intent.putExtra("bitmapURL", URL);
//					startActivity(intent);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    Bimp.drr.add(path);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), Bimp.drr.size()+"", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }*/

}