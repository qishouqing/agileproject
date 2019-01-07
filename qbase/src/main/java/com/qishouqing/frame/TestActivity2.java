package com.qishouqing.frame;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.loader.GlideImageLoader;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.util.PhotoUtils;
import com.lzy.imagepicker.view.CropImageView;
import com.qishouqing.frame.base.BaseActivity;
import com.qishouqing.frame.bean.EventBusBean;
import com.qishouqing.frame.utils.LogUtils;
import com.qishouqing.frame.utils.file.FileStaticUtils;
import com.qishouqing.frame.utils.toast.CustomToast;
import com.qishouqing.frame.utils.toast.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class TestActivity2 extends BaseActivity {
    public static final int REQUEST_CODE_SELECT = 100;


    private List<String> timeList0 = new ArrayList<>();
    private List<String> timeList1 = new ArrayList<>();
    private List<String> timeList2 = new ArrayList<>();

    private long exitTime = 0;

    private boolean flag;

    private TextView tv_test;

    private int maxImgCount = 3;

    private ArrayList<ImageItem> selImageList = new ArrayList<>();//当前选择的所有图片


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        bindViews();


        tv_test = findViewById(R.id.tv_test);
        tv_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ToastUtil.showLong("ddddddd");

                //打开选择,本次允许选择的数量
                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                Intent intent1 = new Intent(TestActivity2.this, ImageGridActivity.class);
                /* 如果需要进入选择的时候显示已经选中的图片，
                 * 详情请查看ImagePickerActivity
                 * */
//                                intent1.putExtra(ImageGridActivity.EXTRAS_IMAGES,images);
                startActivityForResult(intent1, REQUEST_CODE_SELECT);


            }
        });

        initImagepicker();

        timeList0.add("A");
        timeList0.add("B");
        timeList0.add("C");

        timeList1.add("1");
        timeList1.add("2");
        timeList1.add("3");

        timeList2.add("`");
        timeList2.add(".");
        timeList2.add("/");


        JzvdStd jzvdStd = findViewById(R.id.videoplayer);
        jzvdStd.setUp("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4"
                , "饺子闭眼睛", Jzvd.SCREEN_WINDOW_NORMAL);
        jzvdStd.thumbImageView.setImageURI(Uri.parse("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640"));


//        showShare();
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle("测试");
        // titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url在微信、微博，Facebook等平台中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网使用
        oks.setComment("我是测试评论文本");
        // 启动分享GUI
        oks.show(this);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 3000) {
                CustomToast.makeText(TestActivity2.this, "再次点击,将退出APP", Toast.LENGTH_LONG).show();
                ToastUtil.showShort("再次点击,将退出APP");

                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }


    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }


    @OnClick(R.id.text)
    public void onViewClicked() {
        LogUtils.d("qqqqqqqqqqqqqqq");
        if (flag) {
            hideLoading();
        } else {
            showLoading();
        }
        flag = !flag;
    }

    private void initImagepicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(false);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                List<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                selImageList.addAll(images);

                if (images.size() > 0) {
                    new CompressPicTask().execute(images.get(0).path);
                }
            }


        }
    }


    private class CompressPicTask extends AsyncTask<String, Void, Integer> {
        String compressPath;

        @Override
        protected Integer doInBackground(String... params) {
            try {
                String path = params[0];
                String saveDir = FileStaticUtils.getTempFileDir(TestActivity2.this);
                compressPath = PhotoUtils.compressPictureWithSaveDir(path,
                        100, 100,
                        100, saveDir, TestActivity2.this);


                EventBus.getDefault().post(new EventBusBean("compressPath", compressPath));


                return 0;
            } catch (IOException e) {
                return 1;
            }
        }

        @Override
        protected void onPreExecute() {
            LogUtils.d("正在压缩图片");
        }

        @Override
        protected void onPostExecute(Integer result) {
            switch (result) {
                case 0:
                    break;
                case 1:
                    LogUtils.d("图片压缩失败");
                    break;
            }
        }
    }

}
