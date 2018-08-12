package com.example.p2pmoney12.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.p2pmoney12.R;
import com.example.p2pmoney12.ui.randomLayout.StellarMap;
import com.example.p2pmoney12.util.UIUtils;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ProductRecommendFragment extends Fragment {

    @BindView(R.id.stellarMap)
    StellarMap stellarMap;
    Unbinder unbinder;

    private String[] datas = new String[]{"超级新手计划", "乐享活系列90天计划", "钱包计划", "30天理财计划(加息2%)", "90天理财计划(加息5%)", "180天理财计划(加息10%)",
            "林业局投资商业经营", "中学老师购买车辆", "屌丝下海经商计划", "新西游影视拍摄投资", "Java培训老师自己周转", "养猪场扩大经营",
            "旅游公司扩大规模", "阿里巴巴洗钱计划", "铁路局回款计划", "高级白领赢取白富美投资计划"
    };

    private Random random;

    private String[] one = new String[8];
    private String[] two = new String[8];

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = UIUtils.getXmlView(R.layout.fragment_product_recommend);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }
    private void initData(){

        for (int i = 0; i < 8; i++) {
            one[i] = datas[i];
        }
        for(int i = 0; i < 8; i++){
            two[i] = datas[i + 8];
        }

        random = new Random();
        int padding = UIUtils.dp2px(5);
        stellarMap.setInnerPadding(padding,padding,padding,padding);
        stellarMap.setAdapter(new MyAdapter());
        //每一组出现数据组的搭配规则
        stellarMap.setRegularity(7,9); //因为数组datas是16个
        //设置从哪一组开始做动画操作
        stellarMap.setGroup(0,true);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private class MyAdapter implements StellarMap.Adapter {


        @Override
        public int getGroupCount() {
            return 2;
        }

        @Override
        public int getCount(int group) {
            return 8;
        }

        @Override
        public View getView(int group, int position, View convertView) {

            TextView tv = new TextView(getActivity());
            tv.setText(datas[position]);
            int r = random.nextInt(210);
            int g = random.nextInt(210);
            int b = random.nextInt(210);
            tv.setTextColor(Color.rgb(r,g,b));
            tv.setTextSize(UIUtils.dp2px(8)+random.nextInt(8));

            if (group == 0){
                tv.setText(one[position]);
            }else if (group == 1){
                tv.setText(two[position]);
            }
            return tv;
        }

        @Override
        public int getNextGroupOnPan(int group, float degree) {
            return 0;
        }

        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            return 1;
        }
    }
}
