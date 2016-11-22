package com.ta.finalexam.Adapter.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ta.finalexam.Bean.HomeBean.HomeBean;
import com.ta.finalexam.R;
import com.ta.finalexam.Ulities.RoundedCornersTransformation;
import com.ta.finalexam.callback.GoToDetail;
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
    GoToDetail imageClicked;
    private HomeBean homeBean;

    @BindView(R.id.ivAvatar)
    ImageView ivUserPhoto;

    @BindView(R.id.btnFollow)
    Button btn_follow;

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

    @BindView(R.id.ivLike)
    ImageView ivFavourite;

    boolean mFollow, mFavourites;
    int hashtash;

    public HomeViewHolder(View itemView) {
        super(itemView);
    }

    public void bind(HomeBean homeBean , OnMapClick onMapClick, GoToDetail goToDetail) {
        this.onMapCallBack = onMapClick;
        this.imageClicked = goToDetail;
        this.homeBean = homeBean;
        Glide.with(itemView.getContext()).load(homeBean.user.avatar)
                .bitmapTransform(new RoundedCornersTransformation(itemView.getContext(),sCorner,sMargin)).into(ivUserPhoto);
        Glide.with(itemView.getContext()).load(homeBean.image.url).crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(ivPhotoCover);

        hashtash = homeBean.image.hashtag.size();
        for (int i = 0; i < hashtash; i++) {
            StringUtil.displayText(homeBean.image.hashtag.get(i), tvHashTag);
        }

        StringUtil.displayText(homeBean.image.location, tvLocation);
        StringUtil.displayText(homeBean.user.username, tvName);
        StringUtil.displayText(homeBean.image.caption, tvLabel);

        if (homeBean.image.isFavourite) {
            ivFavourite.setImageResource(R.drawable.icon_favourite);
        } else {
            ivFavourite.setImageResource(R.drawable.icon_no_favourite);
        }
        mFavourites = homeBean.image.isFavourite;


        if (homeBean.user.isFollowing) {
            btn_follow.setSelected(true);
            btn_follow.setBackgroundResource(R.drawable.btn_following);
            btn_follow.setText("Following");
        } else {
            btn_follow.setSelected(false);
            btn_follow.setBackgroundResource(R.drawable.btn_un_following);
            btn_follow.setText("Follow");
        }
        mFollow = homeBean.user.isFollowing;
    }

    @OnClick(R.id.tvLocation)
    public void openMap() {
        if (onMapCallBack != null){
            onMapCallBack.onMapClick(homeBean);
        }
    }

    @OnClick(R.id.ivPhotoPreview)
    public void gotoDetailScreen() {
        if (imageClicked != null){
            imageClicked.onImageClicked(homeBean);
        }

    }

}
