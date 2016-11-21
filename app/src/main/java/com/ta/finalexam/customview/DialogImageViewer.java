package com.ta.finalexam.customview;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.ta.finalexam.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.app.base.imageloader.ImageLoader;
import vn.app.base.util.StringUtil;

/**
 *
 */
public class DialogImageViewer extends Dialog {

    @BindView(R.id.img_viewer)
    ImageView ivViewer;

    String url;

    public DialogImageViewer(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDialogView();
        ButterKnife.bind(this);
    }

    private void initDialogView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_dialog_image_viewer);
        final Window window = getWindow();
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT);
            window.setBackgroundDrawable(new ColorDrawable(0));
        }
        setCanceledOnTouchOutside(false);
    }

    @Override
    public void show() {
        super.show();
        if (StringUtil.checkStringValid(url)) {
            ImageLoader.loadImage(getContext(), R.drawable.dummy_avatar, url, ivViewer);
        }
    }

    @OnClick(R.id.btnClose)
    public void close() {
        dismiss();
    }
}
