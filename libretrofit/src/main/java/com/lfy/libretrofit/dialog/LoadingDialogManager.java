package com.lfy.libretrofit.dialog;

import android.app.Dialog;

/**
 * Created by FengYi.Lee<fengyi.li@hotmail.com> on 2020/12/15.
 */
public class LoadingDialogManager {
    private static LoadingDialogManager loadDialogManager;
    private Dialog dialog;

    public static LoadingDialogManager getInstance() {
        if (loadDialogManager == null) {
            loadDialogManager = new LoadingDialogManager();
        }
        return loadDialogManager;
    }

    private LoadingDialogManager() {
        LoadingDialogHelper.getInstance().setOnShowDialogListener(new LoadingDialogHelper.OnShowDialogListener() {
            @Override
            public void onShow(boolean isShow, String text) {
                if (dialog != null) {
                    if (isShow) {
                        if (dialog instanceof LoadingDialog)
                            ((LoadingDialog) dialog).show(text);
                        else
                            dialog.show();
                    } else {
                        dialog.dismiss();
                    }
                }
            }

            @Override
            public void onShow(boolean isShow) {
                if (dialog != null) {
                    if (isShow) {
                        dialog.show();
                    } else {
                        dialog.dismiss();
                    }
                }
            }
        });
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public void show() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
