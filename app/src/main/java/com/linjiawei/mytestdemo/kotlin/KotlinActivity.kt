package com.linjiawei.mytestdemo.kotlin



import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.blankj.utilcode.util.ToastUtils
import com.linjiawei.mytestdemo.OnRecycleItemClickListener
import com.linjiawei.mytestdemo.R
import com.linjiawei.mytestdemo.base.ToolbarBaseActivity
import com.linjiawei.mytestdemo.kotlin.demo.KotlinDemoActivity
import kotlinx.android.synthetic.main.activity_kotlin.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast

class KotlinActivity : ToolbarBaseActivity() {

    var setText = "Will：不用初始化控件，直接通过kotlin语法进行附值" //声明变量
    var price : Double  = 12.1;   //指定类型的 变量
    val price2 : Double = 12.2;   //指定类型的 常量

    var datas = arrayListOf<String>()
    var int_array : IntArray = intArrayOf(1, 2, 3)
    var double_array : DoubleArray = doubleArrayOf(0.1,0.2,32.0,20.00)

    var string_array : Array<String> = arrayOf("a", "b", "c")
    var string_list : List<String> = listOf("1a","2b","3c");
    var string_arrayList: ArrayList<String> = arrayListOf("aa","bb","cc","dd")


    val origin : String = "sd,f,sl,jd,f,l,sk,jd,f,kjs,lkd,j,f"

    override fun initData(savedInstanceState: Bundle?) {
        setTitle(intent.extras.getString(ToolbarBaseActivity.ACTIVITY_TITLE_NAME))
        price = 123.1;
        //   price2 = 123.2;
        //kTextViewId.setText(setText)
        kTextViewId.text = setText
        kotlinBtn.setOnClickListener {
            toast("点击事件：${kotlinBtn.text}");
        //    ToastUtils.showShort("点击事件：${kotlinBtn.text}")
        }
        kotlinBtn.setOnLongClickListener {
            longToast("长按事件：" + kotlinBtn.text); true
            //ToastUtils.showShort("点击事件："+kotlinBtn.text) ; true
        }
        kotlinRecyclerView.layoutManager = LinearLayoutManager(this)
        kotlinRecyclerView.adapter = KotlinAdapter(this, datas)
        addData()//Kotlin写法：加载完数据后都不用去手动刷新适配器，会动态更新...
        kotlinRecyclerView.addOnItemTouchListener(object : OnRecycleItemClickListener(kotlinRecyclerView) {
            override fun OnItemClick(holder: RecyclerView.ViewHolder) {
                toast(datas.get(holder.adapterPosition))
                val bundle = Bundle()
                bundle.putString(ToolbarBaseActivity.ACTIVITY_TITLE_NAME, datas.get(holder.adapterPosition))
                bundle.putInt("showKtType", holder.adapterPosition)
                openNewActivity(bundle, this@KotlinActivity, KotlinDemoActivity::class.java)
            }
        })

        var str: String? = null
        var int_length:Int?
        int_length = str?.length; //int_length可以存在null情况
        ToastUtils.showShort(""+ (int_length ?: -1))
        ToastUtils.showShort("$int_length")

    }

    override fun getContentView(): Int {
        return R.layout.activity_kotlin;
    }

    fun addData(){
        datas.clear()
        datas.add(0, "Kotlin Fragment-演示")
        for (i in 1 until 30) {
            datas.add(i,"KotlinItem 写法 -"+i)
        }

    }

}
