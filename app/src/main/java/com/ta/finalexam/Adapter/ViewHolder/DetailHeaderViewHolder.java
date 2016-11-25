package com.ta.finalexam.Adapter.ViewHolder;

import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ta.finalexam.Bean.DetailBean.ImageDetailBean;
import com.ta.finalexam.Bean.HomeBean.HomeBean;
import com.ta.finalexam.R;
import com.ta.finalexam.Ulities.RoundedCornersTransformation;
import com.ta.finalexam.callback.OnClickRecycleView;
import com.ta.finalexam.callback.OnDetailClicked;

import butterknife.BindView;
import butterknife.OnClick;
import vn.app.base.adapter.viewholder.OnClickViewHolder;
import vn.app.base.imageloader.ImageLoader;
import vn.app.base.util.StringUtil;

import static com.ta.finalexam.R.id.btnFollow;
import static com.ta.finalexam.R.id.btn_follow_detail_header;

/**
 * Created by Veteran Commander on 10/26/2016.
 */

public class DetailHeaderViewHolder extends OnClickViewHolder {

    public static final int LAYOUT_ID = R.layout.detail_screen_header;

    public static int sCorner = 15;
    public static int sMargin = 2;

    OnDetailClicked onDetailClicked;

    HomeBean selectedHomeBean;

    @BindView(R.id.ivProfile_detail_header)
    ImageView ivAvatar;
    @BindView(R.id.tvName_detail_header)
    TextView tvUserName;
    @BindView(R.id.btn_follow_detail_header)
    Button btnFollowDetail;
    @BindView(R.id.ivPhotoCover_detail_header)
    ImageView ivContent;
    @BindView(R.id.tvLabel_detail_header)
    TextView tvLabel;
    @BindView(R.id.tvHashTag_detail_header)
    TextView tvHashtag;
    @BindView(R.id.tvLocation_detail_screen)
    TextView tvLocation;
    @BindView(R.id.btn_like_detail_header)
    FloatingActionButton fabFavorite;
   

    boolean mFollow, mFavourites;
    int hashtash;

    public DetailHeaderViewHolder(View itemView) {
        super(itemView);
    }


    public void bind(HomeBean homeBean,OnDetailClicked onDetailClicked){
        selectedHomeBean = homeBean;
        this.onDetailClicked = onDetailClicked;
        ImageLoader.loadImage(itemView.getContext(),homeBean.image.url,ivContent);
        Glide.with(itemView.getContext()).load(homeBean.user.avatar)
                .bitmapTransform(new RoundedCornersTransformation(itemView.getContext(),sCorner,sMargin)).into(ivAvatar);
        hashtash = homeBean.image.hashtag.size();
        for (int i = 0; i < hashtash; i++) {
            StringUtil.displayText(homeBean.image.hashtag.get(i), tvHashtag);
        }

        StringUtil.displayText(homeBean.image.location, tvLocation);
        StringUtil.displayText(homeBean.user.username, tvUserName);
        StringUtil.displayText(homeBean.image.caption, tvLabel);

        if (homeBean.image.isFavourite) {
            fabFavorite.setImageResource(R.drawable.icon_favourite);
        } else {
            fabFavorite.setImageResource(R.drawable.icon_no_favourite);
        }
        mFavourites = homeBean.image.isFavourite;

        if (homeBean.user.isFollowing) {
            btnFollowDetail.setBackgroundResource(R.drawable.btn_following);
            btnFollowDetail.setText("Following");
        } else {
            btnFollowDetail.setBackgroundResource(R.drawable.btn_un_following);
            btnFollowDetail.setText("Follow");
        }
        mFollow = homeBean.user.isFollowing;

    }

    @OnClick(R.id.btn_follow_detail_header)
    public void onFollowClick(){
        if (onDetailClicked != null){
            onDetailClicked.onFollowDetailClick(selectedHomeBean);
        }
    }
    @OnClick(R.id.btn_like_detail_header)
    public void onFavouriteClick(){
        if (onDetailClicked != null){
            onDetailClicked.onFavouriteDetailClick(selectedHomeBean);
        }
    }
}
