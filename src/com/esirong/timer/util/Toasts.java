package com.esirong.timer.util;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

/**
 * 提示操作类
 * @author lanzheng
 * @date 2014年6月26日
 * @project COM.TOOLS
 * @package com.esirong.tools
 * @package Toasts.java
 * @version [版本号]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class Toasts {
    //吐司显示
    public static void showToastLong(Context cxt, int resid) {
        Toast.makeText(cxt, resid, Toast.LENGTH_LONG).show();
    }
    
    public static void showToastShort(Context cxt, int resid) {
        Toast.makeText(cxt, resid, Toast.LENGTH_SHORT).show();
    }
    
    public static void showToastLong(Context cxt, String msg) {
        Toast.makeText(cxt, msg, Toast.LENGTH_LONG).show();
    }
    
    public static void showToastShort(Context cxt, String msg) {
        Toast.makeText(cxt, msg, Toast.LENGTH_SHORT).show();
    }
    
    /**
     * 自定义
     */
    public static void showToast(Context cxt, View view) {
        Toast mToast = new Toast(cxt);
        mToast.setView(view);
        mToast.show();
    }
}
