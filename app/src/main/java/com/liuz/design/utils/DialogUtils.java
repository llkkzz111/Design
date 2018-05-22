package com.liuz.design.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import com.vise.utils.view.DialogUtil;

/**
 * date: 2018/5/22 15:38
 * author liuzhao
 */
public class DialogUtils extends DialogUtil {

    public static Dialog showTips(Context context, String title, String des, String left, DialogInterface.OnClickListener leftListener, String right, DialogInterface.OnClickListener rightListener) {
        AlertDialog.Builder builder = dialogBuilder(context, title, des);
        builder.setCancelable(true);
        builder.setPositiveButton(right, rightListener);
        builder.setNegativeButton(left, leftListener);
        Dialog dialog = builder.show();
        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }


    public static Dialog showTips(Context context, int title, int des, int left, DialogInterface.OnClickListener leftListener, int right, DialogInterface.OnClickListener rightListener) {
        return showTips(context, context.getString(title), context.getString(des), context.getString(left), leftListener, context.getString(right), rightListener);
    }

}
