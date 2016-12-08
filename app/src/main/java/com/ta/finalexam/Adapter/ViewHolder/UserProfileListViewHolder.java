package com.ta.finalexam.Adapter.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ta.finalexam.Bean.ImageListBean;
import com.ta.finalexam.R;

import butterknife.BindView;
import vn.app.base.adapter.viewholder.OnClickViewHolder;
import vn.app.base.imageloader.ImageLoader;
import vn.app.base.util.StringUtil;

/**
 * Created by kooryy2 on 11/21/2016.
 */

public class UserProfileListViewHolder extends OnClickViewHolder {
    public static final int LAYOUT_ID = R.layout.item_list_profile_user;

    @BindView(R.id.ivAvatar_profile_user)
    ImageView ivAvatar_profile_user;

    @BindView(R.id.tvName_profile_user)
    TextView tvName_profile_user;

    @BindView(R.id.ivPhoto_cover_profile_user)
    ImageView ivPhoto_cover_profile_user;

    @BindView(R.id.tvLocation_profile_user)
    TextView tvLocation_profile_user;

    @BindView(R.id.tvCaption_profile_user)
    TextView tvCaption_profile_user;

    @BindView(R.id.tvHashTag_profile_user)
    TextView tvHashTag_profile_user;

    public UserProfileListViewHolder(View itemView) {
        super(itemView);
    }

    public void bind(ImageListBean imageListBean) {
        ImageLoader.loadImage(itemView.getContext(), imageListBean.user.avatar, ivAvatar_profile_user);
        ImageLoader.loadImage(itemView.getContext(), R.drawable.placeholer_image_800, imageListBean.image.url, ivPhoto_cover_profile_user);

        StringUtil.displayText(imageListBean.user.username , tvName_profile_user);
        StringUtil.displayText(imageListBean.image.caption , tvCaption_profile_user);
        StringUtil.displayText(String.valueOf(imageListBean.image.hashtag), tvHashTag_profile_user);
        StringUtil.displayText(imageListBean.image.location , tvLocation_profile_user);
    }
}
