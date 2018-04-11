package com.linjiawei.mytestdemo.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.linjiawei.mytestdemo.R;


/** 自动实现两个父类的抽象方法、删掉子类的 onCreate() 方法，并通过 getContentView() 方法返回布局 activity_main 的 id **/

public abstract class ToolbarBaseActivity extends AppCompatActivity {
    FrameLayout frameContentLayout;
    Toolbar toolbar;
    TextView titleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar_base);
        frameContentLayout = (FrameLayout) findViewById(R.id.baseContentView);
        titleView = (TextView) findViewById(R.id.toolbarTitleText);
        //初始化相应的toolbar
        toolbar = (Toolbar) findViewById(R.id.baseToolbar);
        setSupportActionBar(toolbar);
        //设置不显示自带的title
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //将子类的布局加载到本页面的frameLayout容器中...
        LayoutInflater.from(this).inflate(getContentView(),frameContentLayout);
        initData(savedInstanceState);
    }

    /**
     * 默认显示标题的同时显示返回键
     * @param titleName
     */
    public void setTitle(String titleName){
        if (!TextUtils.isEmpty(titleName)) {
            titleView.setText(titleName);
        }
        if (!isHideBackIcon()) {
            //默认给继承本类的activity都显返回按钮
            setToolbarLeftBthListener(R.mipmap.ic_reply_white, new OnClickToolbarBtnListener() {
                @Override
                public void click() {
                    finish();
                }
            });
        }
    }

    protected abstract int getContentView();

    protected abstract void initData(Bundle savedInstanceState);

    /**
     * 默认此子类的activity显示返回按钮，可以重写此方法。决定是否隐藏...
     * @return
     */
    public boolean isHideBackIcon(){
        return false;
    }


    /************封装toolbar左边按钮，仅支持icon  ************/
    OnClickToolbarBtnListener clickToolbarLeftBtnListener,clickToolbarRightBtnListener;
    public interface OnClickToolbarBtnListener{
        void click();
    }

    public void setToolbarLeftBthListener(int resId, OnClickToolbarBtnListener clickToolbarLeftBtnListener){
        if (resId != 0) {
            toolbar.setNavigationIcon(resId);
        }
        this.clickToolbarLeftBtnListener = clickToolbarLeftBtnListener;
    }
    /************封装toolbar左边按钮，仅支持icon  ************/


    /************封装toolbar右边按钮，支持显示text或icon  ************/
    private int menuResId;
    private String menuStr;

    /**
     *  重写此方法可以自定右边按钮icon及事件
     * @param menuStr
     * @param clickToolbarRightBtnListener
     */
    public void setToolbarRightBthListener(String menuStr,OnClickToolbarBtnListener clickToolbarRightBtnListener){
        this.menuStr = menuStr;
        this.clickToolbarRightBtnListener = clickToolbarRightBtnListener;
    }

    /**
     * 重写此方法可以自定返回按钮icon及事件
     * @param menuResId
     * @param clickToolbarRightBtnListener
     */
    public void setToolbarRightBthListener(int menuResId,OnClickToolbarBtnListener clickToolbarRightBtnListener){
        this.menuResId = menuResId;
        this.clickToolbarRightBtnListener = clickToolbarRightBtnListener;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (menuResId != 0 || !TextUtils.isEmpty(menuStr)) {
            getMenuInflater().inflate(R.menu.menu_activity_toolbar_base,menu);
        }
        return true;//告诉系统自己处理点击事件
//        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (menuResId != 0) {
            menu.findItem(R.id.menu_0).setIcon(menuResId);
        }
        if (!TextUtils.isEmpty(menuStr)) {
            menu.findItem(R.id.menu_0).setTitle(menuStr);
        }
        return super.onPrepareOptionsMenu(menu);
    }
    /************封装toolbar右边按钮，支持显示text或icon  ************/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //android.R.id.home是特有的返回键id
            clickToolbarLeftBtnListener.click();
        } else if (item.getItemId() == R.id.menu_0) {
            //R.id.munu_0是自定义声明在menu文本中的
            clickToolbarRightBtnListener.click();
        }
        return true;//告诉系统自己处理点击事件
        //return super.onOptionsItemSelected(item);
    }



    public static String ACTIVITY_TITLE_NAME = "ActivityTitleName";
    /**
     * 启动activity，顺带标题过来
     * @param activityTitleName
     * @param startClass
     * @param desClass
     */
    public void openNewActivity(String activityTitleName, AppCompatActivity startClass, Class<? extends AppCompatActivity> desClass){
        Bundle bundle = new Bundle();
        bundle.putString(ACTIVITY_TITLE_NAME, activityTitleName);
        ActivityUtils.startActivity(bundle, startClass, desClass);
    }

    /**
     * 启动activity，顺带标题过来
     * @param startClass
     * @param desClass
     */
    public void openNewActivity(Bundle bundle, AppCompatActivity startClass, Class<? extends AppCompatActivity> desClass){
        ActivityUtils.startActivity(bundle, startClass, desClass);
    }

}
