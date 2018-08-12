package com.example.p2pmoney12.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.p2pmoney12.R;
import com.example.p2pmoney12.bean.Product;
import com.example.p2pmoney12.common.AppNetConfig;
import com.example.p2pmoney12.common.BaseHolder;
import com.example.p2pmoney12.common.MyBaseAdapter2;
import com.example.p2pmoney12.common.MySimpleBaseAdapter;
import com.example.p2pmoney12.ui.RoundProgress;
import com.example.p2pmoney12.util.UIUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ProductListFragment extends Fragment {
    @BindView(R.id.lv)
    ListView lv;
    Unbinder unbinder;

    private AsyncHttpClient client = new AsyncHttpClient();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = UIUtils.getXmlView(R.layout.fragment_product_list);
        unbinder = ButterKnife.bind(this, view);
        initTitle();
        initData();
        return view;
    }

    private void initData() {
        client.post(AppNetConfig.PRODUCT, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String content) {
                JSONObject jsonObject = JSON.parseObject(content);
                if (jsonObject.getBoolean("success")) {
                    String data = jsonObject.getString("data");
                    List<Product> products = JSON.parseArray(data, Product.class);
                    lv.setAdapter(new MyAdapter3(products));
                }


            }

            @Override
            public void onFailure(Throwable error, String content) {
                super.onFailure(error, content);
            }
        });

    }

    private void initTitle() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

//    private class MyAdapter extends BaseAdapter {
//
//        private final List<Product> products;
//
//        public MyAdapter(List<Product> products) {
//            this.products = products;
//        }
//
//        @Override
//        public int getCount() {
//            return products == null ? 0 : products.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return position;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            Product product = products.get(position);
//            ViewHolder viewHolder = null;
//            if (convertView == null) {
//                convertView = View.inflate(parent.getContext(), R.layout.item_product_list, null);
//                viewHolder = new ViewHolder(convertView);
//                convertView.setTag(viewHolder);
//            } else {
//                viewHolder = (ViewHolder) convertView.getTag();
//            }
//            //设置数据
//            viewHolder.pMinzouzi.setText(product.minTouMoney);
//            viewHolder.pMoney.setText(product.money);
//            viewHolder.pName.setText(product.name);
//            viewHolder.pSuodingdays.setText(product.suodingDay);
//            viewHolder.pYearlv.setText(product.yearLv);
//            viewHolder.pProgresss.setProgess(Integer.parseInt(product.progress));
//
//            return convertView;
//        }
//
//    }


//    private class MyAdapter2 extends MySimpleBaseAdapter<Product> {
//
//        public MyAdapter2(List<Product> list) {
//            super(list);
//        }
//
//        @Override
//        public View getYourView(int position, View convertView, ViewGroup parent) {
//            Product product = list.get(position);
//            ViewHolder viewHolder = null;
//            if (convertView == null) {
//                convertView = View.inflate(parent.getContext(), R.layout.item_product_list, null);
//                viewHolder = new ViewHolder(convertView);
//                convertView.setTag(viewHolder);
//            } else {
//                viewHolder = (ViewHolder) convertView.getTag();
//            }
//            //设置数据
//            viewHolder.pMinzouzi.setText(product.minTouMoney);
//            viewHolder.pMoney.setText(product.money);
//            viewHolder.pName.setText(product.name);
//            viewHolder.pSuodingdays.setText(product.suodingDay);
//            viewHolder.pYearlv.setText(product.yearLv);
//            viewHolder.pProgresss.setProgess(Integer.parseInt(product.progress));
//
//            return convertView;
//        }
//
//    }


//    private class MyAdapter3 extends MyBaseAdapter1<Product> {
//
//        public MyAdapter3(List<Product> list) {
//            super(list);
//        }
//
//        @Override
//        protected void setData(View convertView, Product product) {
//            Log.e("setData","setData-----");
//            ((TextView) convertView.findViewById(R.id.p_name)).setText(product.name);
//            ((TextView) convertView.findViewById(R.id.p_money)).setText(product.money);
//
//        }
//
//        @Override
//        protected View initView() {
//            return View.inflate(getActivity(),R.layout.item_product_list,null);
//        }
//    }

    private class MyAdapter3 extends MyBaseAdapter2<Product> {

        public MyAdapter3(List<Product> list) {
            super(list);
        }

        @Override
        protected BaseHolder getHolder() {
            return new MyViewHolder();
        }
    }

    class MyViewHolder extends BaseHolder<Product> {

        @BindView(R.id.p_name)
        TextView pName;
        @BindView(R.id.p_money)
        TextView pMoney;
        @BindView(R.id.p_yearlv)
        TextView pYearlv;
        @BindView(R.id.p_suodingdays)
        TextView pSuodingdays;
        @BindView(R.id.p_minzouzi)
        TextView pMinzouzi;
        @BindView(R.id.p_progresss)
        RoundProgress pProgresss;

        @Override
        public View initView() {
            View inflate = View.inflate(getActivity(), R.layout.item_product_list, null);

            return inflate;

        }

        @Override
        protected void refreshView() {
            Product product = getData();
            //设置数据
            pMinzouzi.setText(product.minTouMoney);
            pMoney.setText(product.money);
            pName.setText(product.name);
            pSuodingdays.setText(product.suodingDay);
            pYearlv.setText(product.yearLv);
            pProgresss.setProgess(Integer.parseInt(product.progress));

        }
    }

//    static class ViewHolder {
//        @BindView(R.id.p_name)
//        TextView pName;
//        @BindView(R.id.p_money)
//        TextView pMoney;
//        @BindView(R.id.p_yearlv)
//        TextView pYearlv;
//        @BindView(R.id.p_suodingdays)
//        TextView pSuodingdays;
//        @BindView(R.id.p_minzouzi)
//        TextView pMinzouzi;
//        @BindView(R.id.p_progresss)
//        RoundProgress pProgresss;
//
//        ViewHolder(View view) {
//            ButterKnife.bind(this, view);
//        }
//    }

}
