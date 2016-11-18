package com.ta.finalexam.Adapter.ViewHolder;

import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ta.finalexam.Bean.DetailBean.ImageDetailBean;
import com.ta.finalexam.R;
import com.ta.finalexam.Ulities.RoundedCornersTransformation;

import butterknife.BindView;
import vn.app.base.adapter.viewholder.OnClickViewHolder;
import vn.app.base.imageloader.ImageLoader;
import vn.app.base.util.StringUtil;

/**
 * Created by Veteran Commander on 10/26/2016.
 */

public class DetailHeaderViewHolder extends OnClickViewHolder {

    public static final int LAYOUT_ID = R.layout.detail_screen_header;

    public static int sCorner = 15;
    public static int sMargin = 2;

    @BindView(R.id.ivProfile_detail_header)
    ImageView ivAvatar;
    @BindView(R.id.tvName_detail_header)
    TextView tvUserName;
    @BindView(R.id.btn_follow_detail_header)
    Button btnFollow;
    @BindView(R.id.ivPhotoCover_detail_header)
    ImageView ivContent;
    @BindView(R.id.tvLabel_detail_header)
    TextView tvLabel;
    @BindView(R.id.tvHashTag_detail_header)
    TextView tvHashtag;
    @BindView(R.id.tvLocation_detail_screen)
    TextView tvLocation;
    @BindView(R.id.imageView_like_detail_header)
    FloatingActionButton fabFavorite;
    @BindView(R.id.btn_follow_detail_header)
    Button btn_follow;

    boolean mFollow, mFavourites;
    int hashtash;

    public DetailHeaderViewHolder(View itemView) {
        super(itemView);
    }


    public void bind(ImageDetailBean imageDetailBean){
        ImageLoader.loadImage(itemView.getContext(),imageDetailBean.image.url,ivContent);
        Glide.with(itemView.getContext()).load(imageDetailBean.user.avatar)
                .bitmapTransform(new RoundedCornersTransformation(itemView.getContext(),sCorner,sMargin)).into(ivAvatar);
        hashtash = imageDetailBean.image.hashtag.size();
        for (int i = 0; i < hashtash; i++) {
            StringUtil.displayText(imageDetailBean.image.hashtag.get(i), tvHashtag);
        }

        StringUtil.displayText(imageDetailBean.image.location, tvLocation);
        StringUtil.displayText(imageDetailBean.user.username, tvUserName);
        StringUtil.displayText(imageDetailBean.image.caption, tvLabel);

        if (imageDetailBean.image.isFavourite) {
            fabFavorite.setImageResource(R.drawable.icon_favourite);
        } else {
            fabFavorite.setImageResource(R.drawable.icon_no_favourite);
        }
        mFavourites = imageDetailBean.image.isFavourite;


        if (imageDetailBean.user.isFollowing) {
            btn_follow.setSelected(true);
            btn_follow.setBackgroundResource(R.drawable.btn_following);
            btn_follow.setText("Following");
        } else {
            btn_follow.setSelected(false);
            btn_follow.setBackgroundResource(R.drawable.btn_un_following);
            btn_follow.setText("Follow");
        }
        mFollow = imageDetailBean.user.isFollowing;

    }
}
