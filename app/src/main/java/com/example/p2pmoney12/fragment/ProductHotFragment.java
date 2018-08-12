package com.example.p2pmoney12.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.p2pmoney12.R;
import com.example.p2pmoney12.util.UIUtils;

public class ProductHotFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = UIUtils.getXmlView(R.layout.fragment_product_hot);
        return view;
    }
}
