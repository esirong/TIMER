package com.esirong.timer.util;

import android.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

/**
 * 对话框帮助类
 * @author lanzheng
 * @date 2014年6月26日
 * @project COM.TOOLS
 * @package com.esirong.tools
 * @package DialogUtil.java
 * @version [版本号]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DialogUtil extends Dialog {
	
	 public DialogUtil(Context context) {
	        super(context);
	    } 
    /**
     * 显示一个对话框
     * 
     * @param cxt
     * @param titleid
     * @param msgid
     */
    public static void showDialog(Context cxt, int titleid, int msgid) {
        AlertDialog.Builder builder = new AlertDialog.Builder(cxt);
        builder.setTitle(titleid);
        builder.setMessage(msgid);
        builder.setPositiveButton(android.R.string.ok, null);
        builder.setCancelable(true);
        builder.show();
    }
    
    /**
     * 显示一个对话框
     * 
     * @param cxt
     * @param titleid
     * @param msg
     */
    public static void showDialog(Context cxt, int titleid, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(cxt);
        builder.setTitle(titleid);
        builder.setMessage(msg);
        builder.setPositiveButton(android.R.string.ok, null);
        builder.setCancelable(true);
        builder.show();
    }
    public static void showDialog(Context cxt, String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(cxt);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton(android.R.string.ok, null);
        builder.setCancelable(true);
        builder.show();
    }
    /**
     * 显示一个确认的对话框
     * 
     * @param cxt
     * @param msgid
     * @param oklistener
     */
    public static void showConfirmationDialog(Context cxt, int msgid, OnClickListener oklistener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(cxt);
        // builder.setTitle(cxt.getPackageName());
        builder.setMessage(msgid);
        builder.setPositiveButton(android.R.string.ok, oklistener);
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.setCancelable(true);
        builder.show();
    }
    
    /**
     * 显示一个确认的对话框
     * 
     * @param cxt
     * @param msgid
     * @param oklistener
     * @param titleid
     */
    public static void showConfirmationDialog(Context cxt, int msgid, OnClickListener oklistener, int titleid) {
        AlertDialog.Builder builder = new AlertDialog.Builder(cxt);
        //         builder.setTitle(cxt.getPackageName());
        builder.setTitle(titleid);
        builder.setMessage(msgid);
        builder.setPositiveButton(android.R.string.ok, oklistener);
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.setCancelable(true);
        builder.show();
    }
    
    //多选对话框监听
    public interface OnMultiChoiceDialogListener {
        void onClick(boolean[] selected);
    }
    
    //创建一个多选的对话框
    public static Dialog createMultiChoiceDialog(Context cxt, CharSequence[] keys, boolean[] values, Integer titleId,
        Integer iconId, final OnMultiChoiceDialogListener listener) {
        final boolean[] res;
        
        if (values == null) {
            res = new boolean[keys.length];
        } else {
            res = values;
        }
        
        AlertDialog.Builder builder = new AlertDialog.Builder(cxt);
        
        if (iconId != null) {
            builder.setIcon(iconId);
        }
        
        if (titleId != null) {
            builder.setTitle(titleId);
        }
        
        builder.setMultiChoiceItems(keys, values, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton, boolean isChecked) {
                res[whichButton] = isChecked;
            }
        });
        
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                listener.onClick(res);
            }
        });
        
        builder.setNegativeButton(R.string.cancel, null);
        
        return builder.create();
    }
}
