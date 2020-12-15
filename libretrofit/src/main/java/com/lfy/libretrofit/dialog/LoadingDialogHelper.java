package com.lfy.libretrofit.dialog;

import android.text.TextUtils;

/**
 * Created by FengYi.Lee<fengyi.li@hotmail.com> on 2020/12/15.
 */
public class LoadingDialogHelper {

    private int count = 0;
    private static LoadingDialogHelper loadDialogManager;
    private OnShowDialogListener listener;

    public static LoadingDialogHelper getInstance() {
        if (loadDialogManager == null) {
            loadDialogManager = new LoadingDialogHelper();
        }
        return loadDialogManager;
    }

    public void up() {
        up("");
    }

    public void down() {
        count--;
        if (count < 0)
            count = 0;
        if (listener != null) {
            System.out.println(count);
            listener.onShow(isShow());
        }
    }

    public void up(String text) {
        count++;
        if (listener != null) {
            if (TextUtils.isEmpty(text))
                listener.onShow(isShow());
            else
                listener.onShow(isShow(), text);
        }
    }

    public void showDialog() {
        up();
    }

    public void showDialog(String text) {
        up(text);
    }

    public void cancelDialog() {
        down();
    }

    public void rest() {
        count = 0;
        if (listener != null) {
            listener.onShow(isShow());
        }
    }

    private boolean isShow() {
        return count != 0;
    }

    public void setOnShowDialogListener(OnShowDialogListener listener) {
        this.listener = listener;
    }

    public interface OnShowDialogListener {
        void onShow(boolean isShow);

        void onShow(boolean isShow, String text);
    }

}
