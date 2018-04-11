package com.linjiawei.mytestdemo.tools;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import com.blankj.utilcode.util.ToastUtils;

import java.util.List;

/**
 * Created by LinJiawei on 2018/1/19. 10:34
 * mail:911926881@qq.com
 */

public class AppTools {

    /**
     * 还有一种通过URL方式唤醒APP：要在目标的APP的清单文件中指定的Activity添加intent-filter 过滤器
     * 详细配置可查看：https://www.cnblogs.com/zhang-cb/p/7093769.html
     */
//     <intent-filter>
//     　　　　<!--下面这几个必须要设置-->
//     <action android:name="android.intent.action.VIEW"/>
//     <category android:name="android.intent.category.DEFAULT"/>
//     <category android:name="android.intent.category.BROWSABLE"/>
//     　　　　<!--协议部分-->
//     <data android:scheme="scan" android:host="escan" android:path="/news" android:port="8088"/>
//     </intent-filter>


    /**
     * 方法一：通过包名启动另外的APP
     * @param context
     * @param appPackageName  包名
     */
    public static void startOtherApp(Context context, String appPackageName){
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(appPackageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo != null) {
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(appPackageName);
            context.startActivity(intent);
        }else {
            ToastUtils.showShort("没有安装");
        }
    }

    /**
     * 检查包是否存在（检查app是否存在）
     *
     * @param packname
     * @return
     */
    public static boolean checkPackInfo(Context context, String packname) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packname, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo != null;
    }


    /**
     * 方法二：也是通过包名启动APP
     * @param context
     * @param packagename
     */
    public static void doStartApplicationWithPackageName(Context context, String packagename) {
        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
        PackageInfo packageinfo = null;
        try {
            packageinfo = context.getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageinfo == null) {
            return;
        }

        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageinfo.packageName);

        // 通过getPackageManager()的queryIntentActivities方法遍历
        List<ResolveInfo> resolveinfoList = context.getPackageManager().queryIntentActivities(resolveIntent, 0);

        ResolveInfo resolveinfo = resolveinfoList.iterator().next();
        if (resolveinfo != null) {
            // packagename = 参数packname
            String packageName = resolveinfo.activityInfo.packageName;
            // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
            String className = resolveinfo.activityInfo.name;
            // LAUNCHER Intent
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//这种启动模式如果不添加你会发现有时候返回顺序是混乱的：
            // 设置ComponentName参数1:packagename参数2:MainActivity路径
            ComponentName cn = new ComponentName(packageName, className);

            intent.setComponent(cn);
            context.startActivity(intent);
        }
    }


    /**
     *
     * @param context
     * @param packageName  com.clio.selfservice2
     * @param activityName  com.clio.selfservice2.MainActivity
     */
    public static void openAppAndActivity(Context context, String packageName, String activityName){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ComponentName cn = new ComponentName(packageName, activityName);
        intent.setComponent(cn);
        context.startActivity(intent);
    }


    /**
     * 判断是否安装了应用
     * @param context
     * @param schemeUrlPath  在被启动的app中，所配置scheme,如：sino://escan:8088/news?
     * @return
     */
    public static boolean hasInstallApp(Context context,String schemeUrlPath) {
        PackageManager manager = context.getPackageManager();
        Intent action = new Intent(Intent.ACTION_VIEW);
        action.setData(Uri.parse(schemeUrlPath));
        List list = manager.queryIntentActivities(action, PackageManager.GET_RESOLVED_FILTER);
        return list != null && list.size() > 0;
    }


}
