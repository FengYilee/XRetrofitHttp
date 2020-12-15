package com.lfy.libretrofit.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * Created by FengYi.Lee<fengyi.li@hotmail.com> on 2020/12/15.
 */
public abstract class BaseDialog<V extends ViewDataBinding> extends Dialog {

    private V binding;

    public BaseDialog(@NonNull Context context) {
        this(context,0);
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        if (getLayoutId() > 0){
            View view = LayoutInflater.from(context).inflate(getLayoutId(), null);
            setContentView(view);
            binding = DataBindingUtil.bind(view);
            initData();
        } else {
            throw new NullPointerException("未设置layout");
        }
    }

    public V getBinding() {
        return binding;
    }

    public abstract int getLayoutId();

    public abstract void initData();



}
