package com.example.p2pmoney12.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.p2pmoney12.R;
import com.example.p2pmoney12.util.UIUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public abstract class LoadingPage  extends FrameLayout{

    AsyncHttpClient client = new AsyncHttpClient();

    private static final int PAGE_LOADING_STATE = 1;  //正在加载的情况

    private static final int PAGE_ERROR_STATE = 2;   //网络出错了的情况

    private static final int PAGE_EMPTY_STATE = 3;   //请求成功了，没有数据返回的情况

    private static final int PAGE_SUCCESS_STATE = 4;  //正常成功的情况

    private int PAGE_CURRENT_STATE = 1; //当前状态, 应该由网络请求的结果决定

    private View loadingView;

    private  View errorView;

    private View emptyView;

    private View successView;

    private LayoutParams lp;

    private ResultState resultState = null;

    private Context mConext;


    public LoadingPage(@NonNull Context context) {
       this(context,null);
    }

    public LoadingPage(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadingPage(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mConext = context;
        //定义一个方法
        init();
    }
    //定义一个方法
    private void init() {

        lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        if (loadingView == null){
            loadingView = UIUtils.getXmlView(R.layout.page_loading);
            addView(loadingView,lp);
        }
        if (errorView == null){
            errorView = UIUtils.getXmlView(R.layout.page_error);
            addView(errorView,lp);
        }
        if (emptyView == null){
            emptyView = UIUtils.getXmlView(R.layout.page_empty);
            addView(emptyView,lp);
        }
        //定义一个方法，安全展示界面
       showSafePage();
    }
    //定义一个方法，安全展示界面
    private void showSafePage() {
        UIUtils.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                showPage();
            }
        });
    }
    //定义一个方法
    private void showPage(){
        loadingView.setVisibility(PAGE_CURRENT_STATE == PAGE_LOADING_STATE ? View.VISIBLE : View.GONE);
        errorView.setVisibility(PAGE_CURRENT_STATE == PAGE_ERROR_STATE ? View.VISIBLE : View.GONE);
        emptyView.setVisibility(PAGE_CURRENT_STATE == PAGE_EMPTY_STATE ? View.VISIBLE : View.GONE);
        if (successView == null){
//            successView = UIUtils.getXmlView(LayoutId());
            successView = View.inflate(mConext,LayoutId(),null);
            addView(successView,lp);
        }
        successView.setVisibility(PAGE_CURRENT_STATE == PAGE_SUCCESS_STATE ? View.VISIBLE : View.GONE);

    }
    //定义一个方法
    public abstract int LayoutId() ;
    
    
    public void show(){
        //状态归位一下
        if (PAGE_CURRENT_STATE != PAGE_LOADING_STATE){
            PAGE_CURRENT_STATE = PAGE_LOADING_STATE;
        }
        //特殊情况处理一下，不需要发送请求来决定界面的情况
        String url = url();
        if (TextUtils.isEmpty(url)){
            resultState = ResultState.SUCCESS;
            resultState.setContent("");
            loadPage();
        }else {
            client.get(url,params(),new AsyncHttpResponseHandler(){
                @Override
                public void onSuccess(String content) {
                    if (TextUtils.isEmpty(content)){
                        resultState = ResultState.EMPTY;
                        resultState.setContent("");
                    }else {
                        resultState = ResultState.SUCCESS;
                        resultState.setContent(content);
                    }
                    //定义一个方法
                    loadPage();
                }

                @Override
                public void onFailure(Throwable error, String content) {
                    resultState = ResultState .ERROR;
                    resultState.setContent("");
                    loadPage();
                }
            });

        }


    }
    //定义一个方法
    private void loadPage() {
        switch (resultState){
            case ERROR:
                //当前状态设置为2，显示错误界面
                PAGE_CURRENT_STATE = 2;
                break;
            case EMPTY:
                //当前状态设置为3，显示空界面
                PAGE_CURRENT_STATE = 3;
                break;
            case SUCCESS:
                //当前状态设置为4，显示成功界面
                PAGE_CURRENT_STATE = 4;
                break;
        }
        showSafePage();
        if (PAGE_CURRENT_STATE == 4){
            OnSuccess(resultState,successView);
        }
    }

    protected abstract void OnSuccess(ResultState resultState,View successView);

    protected abstract RequestParams params();

    //定义一个方法，网络请求地址
    protected abstract String url();
    //枚举
    public enum ResultState{
        ERROR(2),EMPTY(3),SUCCESS(4);

        private int state;

        private String content;

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        ResultState(int state) {
            this.state = state;
        }
    }


}
