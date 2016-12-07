package com.ta.finalexam.Adapter.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ta.finalexam.Bean.HomeBean.HomeBean;
import com.ta.finalexam.Constant.ApiConstance;
import com.ta.finalexam.R;
import com.ta.finalexam.Ulities.RoundedCornersTransformation;
import com.ta.finalexam.callback.OnClickRecycleView;
import com.ta.finalexam.callback.OnMapClick;

import butterknife.BindView;
import butterknife.OnClick;
import vn.app.base.adapter.viewholder.OnClickViewHolder;
import vn.app.base.util.StringUtil;

/**
 * Created by kooryy2 on 10/30/2016.
 */

public class HomeViewHolder extends OnClickViewHolder {
    public static int sCorner = 15;
    public static int sMargin = 2;
    OnMapClick onMapCallBack;
    OnClickRecycleView onClickCallBack;
    private HomeBean homeBean;

    @BindView(R.id.ivAvatar)
    ImageView ivUserPhoto;

    @BindView(R.id.btnFollow_Home)
    Button btnFollow_Home;

    @BindView(R.id.tvNameHome)
    TextView tvName;

    @BindView(R.id.ivPhotoPreview)
    ImageView ivPhotoCover;

    @BindView(R.id.tvCaption)
    TextView tvLabel;

    @BindView(R.id.tvHashTag)
    TextView tvHashTag;

    @BindView(R.id.tvLocation)
    TextView tvLocation;

    @BindView(R.id.ivLike_Home)
    ImageView ivFavourite_Home;

    int hashtash;
    boolean isFollow = false, isFavourite = false;

    public HomeViewHolder(View itemView) {
        super(itemView);
    }

    public void bind(HomeBean homeBean, OnMapClick onMapClick, OnClickRecycleView onClickRecycleView) {
        this.onMapCallBack = onMapClick;
        this.onClickCallBack = onClickRecycleView;
        this.homeBean = homeBean;

        Glide.with(itemView.getContext()).load(homeBean.user.avatar).placeholder(R.drawable.placeholer_avatar)
                .bitmapTransform(new RoundedCornersTransformation(itemView.getContext(), sCorner, sMargin)).into(ivUserPhoto);
        Glide.with(itemView.getContext()).load(homeBean.image.url).crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(ivPhotoCover);

        hashtash = homeBean.image.hashtag.size();
        for (int i = 0; i < hashtash; i++) {
            StringUtil.displayText(homeBean.image.hashtag.get(i), tvHashTag);
        }

        String location = homeBean.image.location;
        location.replace("\n", "");
        StringUtil.displayText(location, tvLocation);
        StringUtil.displayText(homeBean.user.username, tvName);
        StringUtil.displayText(homeBean.image.caption, tvLabel);

        if (homeBean.user.isFollowing == true) {
            btnFollow_Home.setBackgroundResource(R.color.color_btn_follow_bg);
            btnFollow_Home.setText("Following");
        } else {
            btnFollow_Home.setBackgroundResource(R.color.txt_gray);
            btnFollow_Home.setText("Follow");
        }
        isFollow = homeBean.user.isFollowing;

        if (homeBean.image.isFavourite) {
            ivFavourite_Home.setImageResource(R.drawable.icon_favourite);
        } else {
            ivFavourite_Home.setImageResource(R.drawable.icon_no_favourite);
        }
        isFavourite = homeBean.image.isFavourite;
    }

    @OnClick(R.id.btnFollow_Home)
    public void FollowUser() {
        if (onClickCallBack != null) {
            if (!isFollow) {
                onClickCallBack.onFollowResponse(homeBean.user.id, ApiConstance.FOLLOW);
            } else {
                onClickCallBack.onFollowResponse(homeBean.user.id, ApiConstance.UN_FOLLOW);
            }
        }
    }

    @OnClick(R.id.tvLocation)
    public void openMap() {
        if (onMapCallBack != null) {
            onMapCallBack.onMapClick(homeBean);
        }
    }

    @OnClick(R.id.ivAvatar)
    public void openUser() {
        //TODO Chuyển Màn User
    }

    @OnClick(R.id.ivPhotoPreview)
    public void openDetail() {
        if (onClickCallBack != null){
            onClickCallBack.onGoToDetail(homeBean);
        }
    }


    @OnClick(R.id.ivLike_Home)
    public void FavouriteImage() {
        if (onClickCallBack != null) {
            if (!isFavourite)
                onClickCallBack.onFavouriteResponse(homeBean.image.id, 1);
        } else {
            onClickCallBack.onFavouriteResponse(homeBean.image.id, 0);
        }
    }

}
