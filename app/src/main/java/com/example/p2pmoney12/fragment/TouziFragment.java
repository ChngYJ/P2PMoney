package com.example.p2pmoney12.fragment;

//import android.app.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.p2pmoney12.R;
import com.example.p2pmoney12.common.BaseFragment;
import com.example.p2pmoney12.util.UIUtils;
import com.loopj.android.http.RequestParams;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TouziFragment extends BaseFragment {

    @BindView(R.id.title_left)
    ImageView titleLeft;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right)
    ImageView titleRight;
    @BindView(R.id.tab_indictor)
    TabPageIndicator tabIndictor;
    @BindView(R.id.pager)
    ViewPager pager;
    Unbinder unbinder;

    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected RequestParams getParams() {
        return new RequestParams();
    }

    @Override
    protected String getUrl() {
        return "";
    }

    @Override
    protected void initData(String content) {
        initFragment();
        pager.setAdapter(new Myadapter(getFragmentManager()));
        //交给指示器，关联一下
        tabIndictor.setViewPager(pager);

    }

    private void initFragment() {
        ProductListFragment productListFragment = new ProductListFragment();
        ProductRecommendFragment productRecommendFragment = new ProductRecommendFragment();
        ProductHotFragment productHotFragment = new ProductHotFragment();

        fragmentList.add(productListFragment);
        fragmentList.add(productRecommendFragment);
        fragmentList.add(productHotFragment);

    }

    @Override
    protected void initTitle() {
        titleTv.setText("我要投资");
        titleLeft.setVisibility(View.INVISIBLE);
        titleRight.setVisibility(View.INVISIBLE);

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_touzi;
    }


    private class Myadapter extends FragmentPagerAdapter {

        public Myadapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList == null ? 0 :fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return UIUtils.getStringArr(R.array.touzi_tab)[position];
        }
    }
}
