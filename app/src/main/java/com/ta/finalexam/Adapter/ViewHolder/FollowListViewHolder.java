package com.ta.finalexam.Adapter.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ta.finalexam.Bean.MemberBean;
import com.ta.finalexam.R;
import com.ta.finalexam.Ulities.RoundedCornersTransformation;

import butterknife.BindView;
import butterknife.OnClick;
import vn.app.base.adapter.viewholder.OnClickViewHolder;
import vn.app.base.imageloader.ImageLoader;
import vn.app.base.util.StringUtil;

import static com.ta.finalexam.Adapter.ViewHolder.HomeViewHolder.sCorner;
import static com.ta.finalexam.Adapter.ViewHolder.HomeViewHolder.sMargin;

/**
 * Created by Veteran Commander on 10/14/2016.
 * last fix : 5/12/2016 by TA
 */

public class FollowListViewHolder extends OnClickViewHolder {

    public static final int LAYOUT_ID = R.layout.item_list_follow;

    @BindView(R.id.avatar_follow)
    public ImageView ivAvatar;

    @BindView(R.id.tv_name_follow)
    public TextView tvFollowName;

    @BindView(R.id.btn_follow)
    public Button btnFollow;

    @OnClick(R.id.btn_follow)
    public void clickFollow(Button btnFollow){
        btnFollow.setText("UnFollow");
    }

    public FollowListViewHolder(View itemView) {
        super(itemView);
    }

    public void bind(MemberBean member){
        if (member.isFollowing == null){
            return;
        }
        Glide.with(itemView.getContext()).load(member.avatar).placeholder(R.drawable.placeholer_avatar)
                .bitmapTransform(new RoundedCornersTransformation(itemView.getContext(), sCorner, sMargin)).into(ivAvatar);
        StringUtil.displayText(member.username,tvFollowName);
    }
}
