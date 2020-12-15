package com.lfy.libretrofit.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.lfy.libretrofit.R;
import com.lfy.libretrofit.databinding.ViewDialogLoadingBinding;

/**
 * Created by FengYi.Lee<fengyi.li@hotmail.com> on 2020/12/15.
 */
public class LoadingDialog extends BaseDialog<ViewDialogLoadingBinding> {

    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.LoadingDialog);
    }

    @Override
    public int getLayoutId() {
        return R.layout.view_dialog_loading;
    }

    @Override
    public void initData() {
        getWindow().setGravity(Gravity.CENTER);
        getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    public void show(String text) {
        getBinding().tvTips.setText(TextUtils.isEmpty(text) ? "加载中..." : text);
        getBinding().tvTips.setVisibility(View.VISIBLE);
        super.show();
    }

    public void show() {
        this.show("加载中...");
    }
}
