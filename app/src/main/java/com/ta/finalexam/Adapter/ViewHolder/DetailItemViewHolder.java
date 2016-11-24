package com.ta.finalexam.Adapter.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ta.finalexam.Bean.DetailBean.CommentListData;
import com.ta.finalexam.R;
import com.ta.finalexam.Ulities.RoundedCornersTransformation;

import butterknife.BindView;
import vn.app.base.adapter.viewholder.OnClickViewHolder;
import vn.app.base.util.StringUtil;

/**
 * Created by Veteran Commander on 10/26/2016.
 */

public class DetailItemViewHolder extends OnClickViewHolder {
    public static final int LAYOUT_ID = R.layout.detail_screen_item;
    @BindView(R.id.iv_detail_ava_cm)
    ImageView ivAvatar;
    @BindView(R.id.tv_detail_cm)
    TextView tvComment;

    public static int sCorner = 15;
    public static int sMargin = 2;

    public DetailItemViewHolder(View itemView) {
        super(itemView);
    }

    public void bind(CommentListData commentListData){
        if (commentListData.user.avatar.equals("")){
            ivAvatar.setImageResource(R.drawable.dummy_avatar);
            StringUtil.displayText(commentListData.dummycomment, tvComment);
        } else {
            Glide.with(itemView.getContext()).load(commentListData.user.avatar)
                    .bitmapTransform(new RoundedCornersTransformation(itemView.getContext(), sCorner, sMargin)).into(ivAvatar);
            StringUtil.displayText(commentListData.comment, tvComment);
        }

    }

}
