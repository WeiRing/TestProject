package com.linjiawei.mytestdemo.kotlin.demo

import android.os.Bundle
import com.blankj.utilcode.util.FragmentUtils
import com.linjiawei.mytestdemo.R
import com.linjiawei.mytestdemo.base.ToolbarBaseActivity
import com.linjiawei.mytestdemo.interfacebase.OnFragmentInteractionListener
import com.linjiawei.mytestdemo.kotlin.demo.fragment.KotlinViewPageFragment
import org.jetbrains.anko.toast

class KotlinDemoActivity :  ToolbarBaseActivity() , OnFragmentInteractionListener{

    var showKtType : Int ?= null;

    override fun getContentView(): Int {
        return R.layout.activity_kotlin_demo
    }

    override fun initData(savedInstanceState: Bundle?) {
        setTitle(intent.extras.getString(ACTIVITY_TITLE_NAME))
        showKtType = intent.extras.getInt("showKtType")
        if (showKtType != null) {
            addFragmentToContentLayout(showKtType!!)
        }
    }

    //加载不同意的fragment到布局容器中
    fun addFragmentToContentLayout(fragmentType : Int){
        when(fragmentType){
            0 ->  FragmentUtils.addFragment(supportFragmentManager, KotlinViewPageFragment.newInstance(),R.id.kotlinFragmentContent)
            else -> {
                toast("此功能暂未开发，敬请期待...")
            }
        }
    }


    //实现接口时的方法
    override fun onFragmentInteraction(type: Int) {

    }
}
