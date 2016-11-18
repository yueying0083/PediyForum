package cn.yueying0083.pediyforum.utils;

import android.content.Context;
import com.afollestad.materialdialogs.MaterialDialog;


/**
 * Created by luoj@huoli.com on 2016/10/14.
 */

public class DialogUtils {

    public static void showMessageDialog(Context context, int titleId, int messageId, int btnTextId) {
        new MaterialDialog.Builder(context)
                .title(titleId)
                .content(messageId)
                .positiveText(btnTextId)
                .show();
    }

    public static void showMessageDialog(Context context, String title, String message, String btn) {
        new MaterialDialog.Builder(context)
                .title(title)
                .content(message)
                .positiveText(btn)
                .show();
    }

    public static MaterialDialog buildLoadingDialog(Context context, int titleId, int messageId) {
        return new MaterialDialog.Builder(context)
                .title(titleId)
                .content(messageId)
                .progress(true, 0)
                .build();
    }

    public static MaterialDialog buildLoadingDialog(Context context, String title, String message) {
        return new MaterialDialog.Builder(context)
                .title(title)
                .content(message)
                .progress(true, 0)
                .build();
    }
}
