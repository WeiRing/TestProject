package com.linjiawei.mytestdemo.kotlin.demo

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.linjiawei.mytestdemo.R
import com.linjiawei.mytestdemo.base.ToolbarBaseActivity
import kotlinx.android.synthetic.main.activity_kotlin_list_demo.*
import org.jetbrains.anko.toast


class KotlinListDemoActivity : ToolbarBaseActivity() {
    override fun getContentView(): Int {
        return R.layout.activity_kotlin_list_demo

    }

    override fun initData(savedInstanceState: Bundle?) {
        kotlinDemoRv.layoutManager = LinearLayoutManager(this)
        return

        //System.out.print("Hello Kotlin")
        print("Hello Kotlin")

        //String name = "aaa";
        var name: String = "kotlin"

        //final String : sex = "男"
        val sex: String = "男"

        //String otherName;
        var otherName: String?

        //otherName = null
        otherName = null;


//        if (name != null) {
//            int length = name.length();
//        }
        name?.let {
            var length = name.length
        }
        //或者
        var length = name?.length

        //String myName = "My name is :" + name;
        var myName = "My name is : $name"


        //String message = "First Line\n" +"Second Line\n" +"Third Line";
        var message: String = """First Line
                                |Second Line
                                |Third Line""".trimMargin()


        //String  result = x>5 ? "x>5" : "x<=5"
        var x = 3
        var result: String = if (x > 5) "x>5" else "x<=5"


        /*String mesNull = null;
        toast(mesNull != null ? mesNull : "")*/
        var mesNull: String? = null
        toast(mesNull ?: "")


        /*final int andResult  = a & b;
        final int orResult   = a | b;
        final int xorResult  = a ^ b;
        final int rightShift = a >> 2;
        final int leftShift  = a << 2;*/
        val a: Int = 0;
        val b: Int = 1;
        val andResult = a and b
        val orResult = a or b
        val xorResult = a xor b
        val rightShift = a shr 2
        val leftShift = a shl 2


        //判断对象是否属于某个类的子类...
        /*if (object instanceof Car) {
        }
        Car car = (Car) object;*/

//        if (object is Car) {
//        }
//        var car = object as Car


        /*int count = 100;
          if (count >= 0 && count <= 100) { }*/
        var count = 100;
        if (count in 0..100) {
        }


//        int score = 10;
//        switch(score){
//            case 10:
//            case 9:
//                grade = "Excellent";
//                break;
//            case 8:
//            case 7:
//            case 6:
//                grade = "Good";
//                break;
//            case 5:
//            case 4:
//                grade = "OK";
//                break;
//            case 3:
//            case 2:
//            case 1:
//                grade = "Fail";
//                break;
//            default:
//                grade = "Fail";
//        }

        var score = 0;
        var grade : String = when(score) {
            9,10 -> "Excellent"
            in 6..8 -> "Good"
            4,5 -> "Ok"
            in 1..3 -> "Fail"
            else -> "Fail"
        }


//        for (int i = 1; i <= 10 ; i++) { }
        for (i in 1..10){}
//        for (int i = 1; i < 10 ; i++) { }
        for (i in 1 until 10){}
//        for (int i = 10; i >= 0 ; i--) { }
        for (i in 10 downTo 0){}
//        for (int i = 1; i <= 10 ; i+=2) { }
        for (i in 1..10 step 2){}
//        for (int i = 10; i >= 0 ; i-=2) { }
        for (i in 10 downTo 1 step 2){}
//        for (String item : collection) { }
        var collection = arrayListOf<String>("list0","list1",";list2")
        for (item in collection){}
//        for (Map.Entry<String, String> entry: map.entrySet()) { }
        var map = mapOf<Int,String>();

        for ((key,value) in map){}

//        final List<Integer> list = Arrays.asList(1, 2, 3, 4);
//        final Map<Integer, String> map = new HashMap<Integer,String>();
//        map.put(1, "Amit");
//        map.put(2, "Ali");
//        map.put(3,"Mindorks");

        val list = arrayListOf<Int>(1, 2, 3, 4)
        val keyValueMap = mapOf<Int, String>(1 to "Amit", 2 to "Ali", 3 to "Mindorks")


        val array = arrayListOf<Int>(1,2,3,4)
//        for (int item : array){
//            toast(item)
//        }
//        for (int item : array) {
//            if (item > 100) {
//                toast(item+"");
//            }
//        }
        array.forEach { toast(it) }
        array.filter { it >100 }.forEach { toast(it) }

//        String[] splits = "param=car".split("=");
//        String param = splits[0];
//        String value = splits[1];

        var (param,value) = "param=char".split("=")
        toast(param)
        toast(value)


//        void doSomething(){ }
        fun doSomething(){ }


//        void doSomething(int... numbers) {  }
        fun doSomething(vararg numbers : Int){ }

//        int getScore() {  return score*2; }
        //kotlin的3种写法
        fun getScore(): Int { return score*2 }
        fun getScore1(): Int = score*2;
        fun getScore2() = score*2;

    }

        /*public class Utils {
           private Utils() { }
           public static int getScore(int value) {
               return 2 * value;
           }
       }*/
        class Utils private constructor(){
            companion object{
                fun getScore(value: Int) : Int {
                    return 2*value
                }
            }
        }
    
    //创建一个实体类对象
    data class Developer(var name: String, var age: Int)


    /**
     * 自定义类，模拟自定义访问类
     */
    class Rectangle(var height: Int, var width: Int){
        val isSquare : Boolean get() = height == width  //自定义访问器
    }

    fun  isSquare (height: Int ,width: Int) : Boolean{
        var rectangle = Rectangle(height, width)
        return rectangle.isSquare
    }



    fun listCollectionTest(){
        var items = listOf<String>("零", "一", "二", "三", "四", "五")  //只可读list
        var rwList = mutableListOf<Int>(0, 1, 2, 3, 4, 5, 6)          //可变list

        var isAny : Boolean = items.any { it.equals("一") }
        var isAll : Boolean = items.all { it.equals("零")}

    }

    enum class Color{
        RE,ORANGE,YELLOW,GREEN,BLUE,INDIGO,VIOLET,WIHTE
    }

    fun getEnumColorName(color : Color): String {
        var isExact = when(color){
            Color.ORANGE -> "橙色"
            Color.YELLOW -> "黄色"
            Color.GREEN  -> "绿色"
            Color.BLUE   -> "蓝色"
            Color.WIHTE  -> "白色"
            else -> "其他颜色"
        }
        return isExact
    }


    val set = hashSetOf<String>("1", "2", "3")
    val list = arrayListOf<Int>(1, 2, 3)
    val map = hashMapOf<Int,String>(
            1 to "One",
            2 to "Two",
            3 to "Three"
    )




}
