package com.example.p2pmoney12.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.p2pmoney12.ui.LoadingPage;
import com.example.p2pmoney12.util.UIUtils;
import com.loopj.android.http.RequestParams;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    private LoadingPage loadingPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        loadingPage = new LoadingPage(container.getContext()){


            @Override
            public int LayoutId() {
                return getLayoutId();
            }

            @Override
            protected void OnSuccess(ResultState resultState,View successView) {
                ButterKnife.bind(BaseFragment.this,successView);
                initTitle();
                initData(resultState.getContent());
            }

            @Override
            protected RequestParams params() {
                return getParams();
            }

            @Override
            protected String url() {
                return getUrl();
            }
        };

//        View view = UIUtils.getXmlView(getLayoutId());
//        unbinder = ButterKnife.bind(this, view);
//        initTitle();
//        initData();

        return loadingPage;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        UIUtils.gethandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                show();
            }
        },800);

    }

    public void show() {
        loadingPage.show();
    }

    protected abstract RequestParams getParams();

    protected abstract String getUrl();

    protected abstract void initData(String content);

    protected abstract void initTitle();

    public abstract int getLayoutId() ;

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        ButterKnife.unbind(this);
//    }
  Unbinder unbinder;
@Override
public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
}
}
