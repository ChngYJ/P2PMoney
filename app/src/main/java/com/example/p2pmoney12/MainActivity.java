package com.example.p2pmoney12;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.p2pmoney12.common.AppManager;
import com.example.p2pmoney12.fragment.HomeFragment;
import com.example.p2pmoney12.fragment.MeFragment;
import com.example.p2pmoney12.fragment.MoreFragment;
import com.example.p2pmoney12.fragment.TouziFragment;
import com.example.p2pmoney12.util.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends FragmentActivity {

    @BindView(R.id.content)
    FrameLayout content;

    @BindView(R.id.ll_home)
    LinearLayout llHome;

    @BindView(R.id.ll_touzi)
    LinearLayout llTouzi;

    @BindView(R.id.ll_me)
    LinearLayout llMe;

    @BindView(R.id.ll_more)
    LinearLayout llMore;
    @BindView(R.id.iv_home)
    ImageView ivHome;
    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.iv_touzi)
    ImageView ivTouzi;
    @BindView(R.id.tv_touzi)
    TextView tvTouzi;
    @BindView(R.id.iv_me)
    ImageView ivMe;
    @BindView(R.id.tv_me)
    TextView tvMe;
    @BindView(R.id.iv_more)
    ImageView ivMore;
    @BindView(R.id.tv_more)
    TextView tvMore;

    private HomeFragment homeFragment;
    private TouziFragment touziFragment;
    private MeFragment meFragment;
    private MoreFragment moreFragment;
    private FragmentTransaction ft;

//    private ImageView ivHome;
//    private TextView tvHome;
//    private ImageView ivTouzi;
//    private ImageView ivMe;
//    private ImageView ivMore;
//    private TextView tvTouzi;
//    private TextView tvMe;
//    private TextView tvMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ivHome = findViewById(R.id.iv_home);
//        ivTouzi = findViewById(R.id.iv_touzi);
//        ivMe = findViewById(R.id.iv_me);
//        ivMore = findViewById(R.id.iv_more);
//
//        tvHome = findViewById(R.id.tv_home);
//        tvTouzi = findViewById(R.id.tv_touzi);
//        tvMe = findViewById(R.id.tv_me);
//        tvMore = findViewById(R.id.tv_more);

        //插件，快速findById
        ButterKnife.bind(this);

        AppManager.getInstance().addActivity(this);
        //定义一个方法  默认点开是首页
        initData();

    }

    //定义一个方法  默认点开是首页
    private void initData() {
        setSelect(0);
    }

    //点击方法，底部导航栏四个Linearlayout
    @OnClick({R.id.ll_home, R.id.ll_touzi, R.id.ll_me, R.id.ll_more})

    public void changeTab(View view) {
        switch (view.getId()) {
            case R.id.ll_home:
                //定义一个方法 设置返回信息的内容
                setSelect(0);
                break;
            case R.id.ll_touzi:
                setSelect(1);
                break;
            case R.id.ll_me:
                setSelect(2);
                break;
            case R.id.ll_more:
                setSelect(3);
                break;
        }

    }

    public void setSelect(int i) {
        FragmentManager fm = getSupportFragmentManager();
        //android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        //都设置   android.support.v4.app 的包
        ft = fm.beginTransaction();
        //定义一个隐藏方法，避免fragment重叠
        hideFragment();
        //定义一个方法  重设定选项
        reSetTab();

        switch (i) {
            case 0:
                //首页
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    //添加各个布局
                    ft.add(R.id.content, homeFragment);
                }

                ivHome.setImageResource(R.drawable.bid01);
                tvHome.setTextColor(UIUtils.getColor(R.color.home_back_selected));

                ft.show(homeFragment);
                break;
            case 1:
                //我要投资
                if (touziFragment == null) {
                    touziFragment = new TouziFragment();
                    ft.add(R.id.content, touziFragment);
                }

                ivTouzi.setImageResource(R.drawable.bid03);
                tvTouzi.setTextColor(UIUtils.getColor(R.color.home_back_selected));

                ft.show(touziFragment);
                break;
            case 2:
                //我的资产
                if (meFragment == null) {
                    meFragment = new MeFragment();
                    ft.add(R.id.content, meFragment);
                }

                ivMe.setImageResource(R.drawable.bid05);
                tvMe.setTextColor(UIUtils.getColor(R.color.home_back_selected));

                ft.show(meFragment);
                break;
            case 3:
                //更多
                if (moreFragment == null) {
                    moreFragment = new MoreFragment();
                    ft.add(R.id.content, moreFragment);
                }

                ivMore.setImageResource(R.drawable.bid07);
                tvMore.setTextColor(UIUtils.getColor(R.color.home_back_selected));

                ft.show(moreFragment);
                break;
        }
        //最后提交
        ft.commit();
    }

    //定义一个方法  重设定选项
    private void reSetTab() {
        //图片
        ivHome.setImageResource(R.drawable.bid02);
        ivTouzi.setImageResource(R.drawable.bid04);
        ivMe.setImageResource(R.drawable.bid06);
        ivMore.setImageResource(R.drawable.bid08);
        //文字
        tvHome.setTextColor(UIUtils.getColor(R.color.home_back_unselected));
        tvTouzi.setTextColor(UIUtils.getColor(R.color.home_back_unselected));
        tvMe.setTextColor(UIUtils.getColor(R.color.home_back_unselected));
        tvMore.setTextColor(UIUtils.getColor(R.color.home_back_unselected));
    }

    //定义一个隐藏方法，避免fragment重叠
    public void hideFragment() {
        if (homeFragment != null) {
            ft.hide(homeFragment);
        }
        if (touziFragment != null) {
            ft.hide(touziFragment);
        }
        if (meFragment != null) {
            ft.hide(meFragment);
        }
        if (moreFragment != null) {
            ft.hide(moreFragment);
        }
    }
}
