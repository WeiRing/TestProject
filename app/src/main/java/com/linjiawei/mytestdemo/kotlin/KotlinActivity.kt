package com.linjiawei.mytestdemo.kotlin



import android.os.Bundle
import com.blankj.utilcode.util.ToastUtils
import com.linjiawei.mytestdemo.R
import com.linjiawei.mytestdemo.base.ToolbarBaseActivity
import kotlinx.android.synthetic.main.activity_kotlin.*//kotlin方式，导入完成后即可调用在xml文件中以视图控件命名属性的对应扩展
class KotlinActivity : ToolbarBaseActivity() {

    var setText = "Will：不用初始化控件，直接通过kotlin语法进行附值" //声明变量
    var price : Double  = 12.1;   //指定类型的 变量
    val price2 : Double = 12.2;   //指定类型的 常量


    override fun initData(savedInstanceState: Bundle?) {
        setTitle(intent.extras.getString(ToolbarBaseActivity.ACTIVITY_TITLE_NAME))
        price = 123.1;
     //   price2 = 123.2;
        kTextViewId.setText(setText)
        kTextViewId.setOnClickListener { ToastUtils.showLong(setText) }

    }

    override fun getContentView(): Int {
        return R.layout.activity_kotlin;
    }

}
