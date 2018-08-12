package com.example.p2pmoney12.fragment;

//import android.app.Fragment;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.p2pmoney12.R;
import com.example.p2pmoney12.bean.Image;
import com.example.p2pmoney12.bean.Index;
import com.example.p2pmoney12.bean.Product;
import com.example.p2pmoney12.common.AppNetConfig;
import com.example.p2pmoney12.common.BaseFragment;
import com.example.p2pmoney12.ui.MyScrollview;
import com.example.p2pmoney12.ui.RoundProgress;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends BaseFragment {


    @BindView(R.id.title_left)
    ImageView titleLeft;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right)
    ImageView titleRight;
    @BindView(R.id.vp_barner)
    ViewPager vpBarner;
    @BindView(R.id.circle_barner)
    CirclePageIndicator circleBarner;
    @BindView(R.id.textView1)
    TextView textView1;
    @BindView(R.id.p_progresss)
    RoundProgress pProgresss;
    @BindView(R.id.p_yearlv)
    TextView pYearlv;
    @BindView(R.id.button1)
    Button button1;
    @BindView(R.id.myscrollview)
    MyScrollview myscrollview;
    Unbinder unbinder;

    private Index index;

    @Override
    protected RequestParams getParams() {
        return new RequestParams();
    }

    @Override
    protected String getUrl() {
        return AppNetConfig.INDEX;
    }

    @Override
    protected void initData(String content) {
        if (!TextUtils.isEmpty(content)){
            index = new Index();

            Log.e("Content","content-----"+content);
            JSONObject jsonObject = JSON.parseObject(content);
            String proInfo = jsonObject.getString("proInfo");
            Product product = JSON.parseObject(proInfo, Product.class);
            String imageArr = jsonObject.getString("imageArr");
            List<Image> imageList = JSON.parseArray(imageArr, Image.class);
            index.product = product;
            index.imageList = imageList;
            //适配一下数据
            vpBarner.setAdapter(new MyAdapter());
            //viewpager 交给指示器，关联一下
            circleBarner.setViewPager(vpBarner);

            totalProgress = Integer.parseInt(index.product.progress);
            new Thread(runnable).start();

        }

    }

    @Override
    protected void initTitle() {
        titleLeft.setVisibility(View.INVISIBLE);
        titleRight.setVisibility(View.INVISIBLE);

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    private int totalProgress; //获取总进度

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int tempProgress = 0;
            try {
                while(tempProgress <= totalProgress){
                    pProgresss.setProgess(tempProgress);
                    tempProgress++;
                    Thread.sleep(70);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };



    private class MyAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return index.imageList == null ? 0: index.imageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            String imageUrl = index.imageList.get(position).IMAURL;
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Picasso.with(getActivity()).load(imageUrl).into(imageView);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
           container.removeView((View) object);
        }
    }


//    AsyncHttpClient client = new AsyncHttpClient();
//
//    @BindView(R.id.title_left)
//    ImageView titleLeft;
//    @BindView(R.id.title_tv)
//    TextView titleTv;
//    @BindView(R.id.title_right)
//    ImageView titleRight;
//
//    @BindView(R.id.vp_barner)
//    ViewPager vpBarner;
//    @BindView(R.id.circle_barner)
//    CirclePageIndicator circleBarner;
//    @BindView(R.id.textView1)
//    TextView textView1;
//
//    @BindView(R.id.p_yearlv)
//    TextView pYearlv;
//    @BindView(R.id.button1)
//    Button button1;
//    @BindView(R.id.myscrollview)
//    MyScrollview myscrollview;
//
//    @BindView(R.id.p_progresss)
//    RoundProgress pProgresss;
//
//    private Index index;
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.fragment_home;
//    }
//
//    //定义一个方法，初始化头布局,左右端隐藏
//    @Override
//    public void initTitle() {
//        titleLeft.setVisibility(View.INVISIBLE);
//        titleRight.setVisibility(View.INVISIBLE);
//    }
//
//    //定义一个方法,初始化数据
//    @Override
//    public void initData() {
//        index = new Index();
//        client.post(AppNetConfig.INDEX,new AsyncHttpResponseHandler(){
//            @Override
//            public void onSuccess(String content) {
//                Log.e("Content","content-----"+content);
//                JSONObject jsonObject = JSON.parseObject(content);
//                String proInfo = jsonObject.getString("proInfo");
//                Product product = JSON.parseObject(proInfo, Product.class);
//                String imageArr = jsonObject.getString("imageArr");
//                List<Image> imageList = JSON.parseArray(imageArr, Image.class);
//                index.product = product;
//                index.imageList = imageList;
//                //适配一下数据
//                vpBarner.setAdapter(new MyAdapter());
//                //viewpager 交给指示器，关联一下
//                circleBarner.setViewPager(vpBarner);
//
//                totalProgress = Integer.parseInt(index.product.progress);
//                new Thread(runnable).start();
//
//            }
//
//            @Override
//            public void onFailure(Throwable error, String content) {
//
//                Toast.makeText(getActivity(),"请求服务器异常----"+error.getMessage(),Toast.LENGTH_SHORT).show();
////                Log.e("Error","error---"+error.getMessage());
//            }
//        });
//    }
//
//    private int totalProgress; //获取总进度
//
//    private Runnable runnable = new Runnable() {
//        @Override
//        public void run() {
//            int tempProgress = 0;
//            try {
//                while(tempProgress <= totalProgress){
//                    pProgresss.setProgess(tempProgress);
//                    tempProgress++;
//                    Thread.sleep(70);
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    };
//
//
//
//    private class MyAdapter extends PagerAdapter {
//        @Override
//        public int getCount() {
//            return index.imageList == null ? 0: index.imageList.size();
//        }
//
//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return view == object;
//        }
//
//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//            String imageUrl = index.imageList.get(position).IMAURL;
//            ImageView imageView = new ImageView(getActivity());
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            Picasso.with(getActivity()).load(imageUrl).into(imageView);
//            container.addView(imageView);
//            return imageView;
//        }
//
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//           container.removeView((View) object);
//        }
//    }
}
