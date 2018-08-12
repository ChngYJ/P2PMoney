package com.example.p2pmoney12.common;

import android.view.View;

import com.example.p2pmoney12.fragment.ProductListFragment;

import butterknife.ButterKnife;

/**
 * getView的实现全部交给BaseHolder类去做
 */
public abstract class BaseHolder<T> {

    private View rootView;

    private T mData ;

    public T getData(){
        return mData;
    }

    public BaseHolder() {
        this.rootView = initView();
        this.rootView.setTag(this);
        ButterKnife.bind(BaseHolder.this, rootView);


    }

    public View getRootView(){
        return rootView;
    }

    public void setData(T t){
        this.mData = t;
        refreshView();
    }


    public abstract View initView();

    protected abstract void refreshView();
}
