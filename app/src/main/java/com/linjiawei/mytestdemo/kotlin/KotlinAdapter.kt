package com.linjiawei.mytestdemo.kotlin

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.linjiawei.mytestdemo.R
import java.util.*

/**
 * Created by LinJiawei on 2017/10/30. 10:36
 * mail:911926881@qq.com
 */

class KotlinAdapter(val mContext: Context, val datas: ArrayList<String>) : RecyclerView.Adapter<KotlinAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflate = LayoutInflater.from(mContext).inflate(R.layout.list_data_item, parent, false)
        val holder = MyViewHolder(inflate)
        return holder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.mTextView.text = datas[position]
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mTextView: TextView
        init {
        //    mTextView = view.findViewById(R.id.list_item_text) as TextView  过时写法
            mTextView = view.findViewById<TextView>(R.id.list_item_text)
        }
    }
}
