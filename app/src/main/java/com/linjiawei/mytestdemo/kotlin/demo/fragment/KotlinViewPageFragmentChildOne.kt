package com.linjiawei.mytestdemo.kotlin.demo.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.linjiawei.mytestdemo.R
import com.linjiawei.mytestdemo.base.RxFragmentV4
import kotlinx.android.synthetic.main.fragment_kotlin_view_page_fragment_child_one.*

class KotlinViewPageFragmentChildOne : RxFragmentV4() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_kotlin_view_page_fragment_child_one, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initEvent()
    }

    private fun initEvent() {

    }

    private fun initView() {
        ktSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary)
        ktChildRecyclerView.setHasFixedSize(true)
        ktChildRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    companion object {
        fun newInstance(): KotlinViewPageFragmentChildOne {
            val fragment = KotlinViewPageFragmentChildOne()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
