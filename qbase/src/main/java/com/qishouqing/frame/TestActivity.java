package com.qishouqing.frame;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lqr.adapter.LQRAdapterForRecyclerView;
import com.lqr.adapter.LQRViewHolderForRecyclerView;
import com.qishouqing.frame.base.BaseFragmentActivity;
import com.qishouqing.frame.base.QApp;
import com.qishouqing.frame.utils.ImageUtils;
import com.qishouqing.frame.utils.LogUtils;
import com.qishouqing.frame.view.BaseRefreshLoadmoreLayout;
import com.qishouqing.frame.view.RefreshLoadmoreLayout;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestActivity extends BaseFragmentActivity {

    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.tv_test)
    TextView tvTest;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.banner2)
    Banner banner2;
    @BindView(R.id.rv_info)
    RecyclerView rv_info;
    @BindView(R.id.swipe_layout)
    RefreshLoadmoreLayout refresh;
    @BindView(R.id.tabhost)
    FragmentTabHost tabhost;


    private LQRAdapterForRecyclerView<String> infoAdapter;
    private List<String> infoData = new ArrayList<>();

    private String TAG = "TEST";

    private NotificationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        bindViews();
        initView();


        LogUtils.D(TAG, QApp.getInstance().getTimestamp() + "");
    }

    private void initView() {

        initBanner();

        initInfo();

        initRefreshLayout();

        initTabhost();


    }


    private void initRefreshLayout() {

        refresh.setOnStartListener(new BaseRefreshLoadmoreLayout.OnStartListener() {
            @Override
            public void onStartRefresh(BaseRefreshLoadmoreLayout xtomRefreshLoadmoreLayout) {
                infoData.add("onStartRefresh");
                infoAdapter.notifyDataSetChangedWrapper();

                refresh.refreshSuccess();
            }

            @Override
            public void onStartLoadmore(BaseRefreshLoadmoreLayout xtomRefreshLoadmoreLayout) {
                infoData.add("onStartLoadmore");
                infoAdapter.notifyDataSetChangedWrapper();

                refresh.loadmoreSuccess();
            }
        });
        // 初始化滑动list，必须放到监听之后
        refresh.setRefreshable(true);
        refresh.setLoadmoreable(true);


    }

    private void initInfo() {
        infoData.add("1");
        infoData.add("2");
        infoData.add("3");


        LinearLayoutManager llm = new LinearLayoutManager(TestActivity.this);
        rv_info.setLayoutManager(llm);

        infoAdapter = new LQRAdapterForRecyclerView<String>(TestActivity.this, infoData, R.layout.item_string) {
            @Override
            public void convert(LQRViewHolderForRecyclerView helper, final String testBean, final int position) {
                helper.setText(R.id.tv_string, testBean);
            }
        };


        rv_info.setAdapter(infoAdapter.getHeaderAndFooterAdapter());
    }

    private void initBanner() {
        List<String> images = new ArrayList<>();
        images.add("http://t2.hddhhn.com/uploads/tu/201610/198/scx30045vxd.jpg");
        images.add("http://t2.hddhhn.com/uploads/tu/20150700/v45jx3rpefz.jpg");
        images.add("http://t2.hddhhn.com/uploads/tu/20150700/z3wvldjvb2y.jpg");


        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        banner.setBannerAnimation(Transformer.Tablet);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        banner.setImages(images).setImageLoader(new GlideImageLoader()).start();


        banner2.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner2.setImages(images);
        banner2.setBannerAnimation(Transformer.DepthPage);
        //banner设置方法全部调用完毕时最后调用
        banner2.start();
        banner2.setImages(images).setImageLoader(new GlideImageLoader()).start();
    }

    private class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             注意：
             1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
             2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
             传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
             切记不要胡乱强转！
             */

            ImageUtils.imageLoad(context, path, imageView, 0, false, 0);

        }

        //提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
        @Override
        public ImageView createImageView(Context context) {
            return null;
        }
    }

    private void initTabhost() {
        // 绑定fragment
        tabhost.setup(this, getSupportFragmentManager(), R.id.layout_content);
        // tab背景
        tabhost.getTabWidget().setBackgroundResource(R.color.colorWhite);
        // 去除分割线
        tabhost.getTabWidget().setDividerDrawable(null);
        tabhost.addTab(tabhost.newTabSpec("testF01").setIndicator(getTabItemView(R.drawable.tab_test)),
                TestFragment.class, null);

        tabhost.addTab(tabhost.newTabSpec("testF02").setIndicator(getTabItemView(R.drawable.tab_test)),
                TestFragment2.class, null);
    }

    /**
     * 给每个Tab按钮设置图标和文字
     */
    private View getTabItemView(int pic) {
        LayoutInflater mLayoutInflater = LayoutInflater.from(this);
        View view = mLayoutInflater.inflate(R.layout.item_tab, null);
        ImageView imageView = view.findViewById(R.id.iv);
        imageView.setImageResource(pic);
        return view;
    }

    //如果你需要考虑更好的体验，可以这么操作
    @Override
    protected void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }


}
