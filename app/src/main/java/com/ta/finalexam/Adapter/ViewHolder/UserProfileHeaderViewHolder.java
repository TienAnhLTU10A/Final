package com.ta.finalexam.Adapter.ViewHolder;

import android.view.View;
import android.widget.TextView;

import com.ta.finalexam.Bean.ProfileBean;
import com.ta.finalexam.R;
import com.ta.finalexam.Ulities.manager.UserManager;
import com.ta.finalexam.callback.OnUserEdit;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import vn.app.base.adapter.viewholder.OnClickViewHolder;
import vn.app.base.imageloader.ImageLoader;
import vn.app.base.util.StringUtil;

/**
 * Created by kooryy2 on 11/20/2016.
 */

public class UserProfileHeaderViewHolder extends OnClickViewHolder {

    public static final int LAYOUT_ID = R.layout.item_profile_user_header;

    private boolean isFollow = false;

    @BindView(R.id.user_profile_avatar_edit)
    CircleImageView circleEditUser;

    @BindView(R.id.user_profile_avatar)
    CircleImageView user_profile_avatar;

    @BindView(R.id.user_profile_username)
    TextView user_profile_username;

    @BindView(R.id.tvFollowerSum)
    TextView tvFollowerSum;

    @BindView(R.id.tvFollowSum)
    TextView tvFollowSum;

    @BindView(R.id.tvPostSum)
    TextView tvPostSum;

    ProfileBean profileBean;

    OnUserEdit onUserEdit;

    public void setOnUserEdit(OnUserEdit onUserEdit) {
        this.onUserEdit = onUserEdit;
    }

    public UserProfileHeaderViewHolder(View itemView) {
        super(itemView);
    }

    public void bind(ProfileBean profileBean) {
        this.profileBean = profileBean;
        ImageLoader.loadImage(itemView.getContext(), R.drawable.loading_avatar_290, profileBean.avatar, user_profile_avatar);
        StringUtil.displayText(profileBean.username, user_profile_username);
        StringUtil.displayText(String.valueOf(profileBean.follow), tvFollowSum);
        StringUtil.displayText(String.valueOf(profileBean.follower), tvFollowerSum);
        StringUtil.displayText(String.valueOf(profileBean.post), tvPostSum);
        if (profileBean.id.equals(UserManager.getCurrentUser().id)) {
            circleEditUser.setVisibility(View.VISIBLE);
        } else {
            circleEditUser.setVisibility(View.GONE);
        }

    }

    @OnClick(R.id.user_profile_avatar_edit)
    public void pickPhoto() {
        if (onUserEdit != null) {
            onUserEdit.OnChangePhoto(getAdapterPosition());
        }
    }
}
