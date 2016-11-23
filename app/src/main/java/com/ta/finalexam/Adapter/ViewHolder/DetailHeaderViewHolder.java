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

import butterknife.BindView;
import vn.app.base.adapter.viewholder.OnClickViewHolder;
import vn.app.base.imageloader.ImageLoader;
import vn.app.base.util.StringUtil;

import static com.ta.finalexam.R.id.btnFollow;

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
    Button btn_follow_detail_header;
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
   

    boolean mFollow, mFavourites;
    int hashtash;

    public DetailHeaderViewHolder(View itemView) {
        super(itemView);
    }


    public void bind(HomeBean homeBean){
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
            btn_follow_detail_header.setSelected(true);
            btn_follow_detail_header.setBackgroundResource(R.drawable.btn_following);
            btn_follow_detail_header.setText("Following");
        } else {
            btn_follow_detail_header.setSelected(false);
            btn_follow_detail_header.setBackgroundResource(R.drawable.btn_un_following);
            btn_follow_detail_header.setText("Follow");
        }
        mFollow = homeBean.user.isFollowing;

    }
}
