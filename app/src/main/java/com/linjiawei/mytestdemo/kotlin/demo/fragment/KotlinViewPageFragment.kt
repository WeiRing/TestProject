package com.linjiawei.mytestdemo.kotlin.demo.fragment


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.linjiawei.mytestdemo.R
import com.linjiawei.mytestdemo.rxandroid.fragment.RxLoopFragment
import com.trello.rxlifecycle.components.support.RxFragment
import kotlinx.android.synthetic.main.fragment_kotlin_view_page.*

class KotlinViewPageFragment : RxFragment() {
    var tabTitle: ArrayList<String> = arrayListOf("段子","鸡汤")

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_kotlin_view_page, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ktViewPage.offscreenPageLimit = tabTitle.size
        ktViewPage.adapter = TextPageAdapter()
        ktTabLayout.tabMode = TabLayout.MODE_FIXED
        ktTabLayout.tabGravity = TabLayout.GRAVITY_FILL
        ktTabLayout.setupWithViewPager(ktViewPage)
    }

    companion object {
        fun newInstance(): KotlinViewPageFragment {
            val fragment = KotlinViewPageFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    /**
     * ViewPage适配器
     */
    private inner class TextPageAdapter() : FragmentStatePagerAdapter(fragmentManager){

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> { KotlinViewPageFragmentChildOne.newInstance() }
                else -> { RxLoopFragment.newInstance("","") }
            }
        }

        override fun getCount(): Int {
            return tabTitle.size
        }

        override fun getPageTitle(position: Int): CharSequence {
            return tabTitle[position]
        }
    }

}
