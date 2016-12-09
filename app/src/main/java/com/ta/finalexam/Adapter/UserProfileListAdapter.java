package com.ta.finalexam.Adapter;


import android.view.ViewGroup;

import com.ta.finalexam.Adapter.ViewHolder.UserProfileHeaderViewHolder;
import com.ta.finalexam.Adapter.ViewHolder.UserProfileListViewHolder;
import com.ta.finalexam.Bean.ImageListBean;
import com.ta.finalexam.Bean.ProfileBean;
import com.ta.finalexam.callback.OnUserEdit;

import vn.app.base.adapter.HeaderAdapterWithItemClick;
import vn.app.base.adapter.viewholder.OnClickViewHolder;
import vn.app.base.util.UiUtil;

/**
 * Created by kooryy2 on 11/21/2016.
 */

public class UserProfileListAdapter extends HeaderAdapterWithItemClick<OnClickViewHolder, ProfileBean, ImageListBean, String> {
    OnUserEdit onUserEdit;

    public void setOnUserEdit(OnUserEdit onUserEdit) {
        this.onUserEdit = onUserEdit;
    }

    @Override
    protected OnClickViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {
        return new UserProfileHeaderViewHolder(UiUtil.inflate(parent, UserProfileHeaderViewHolder.LAYOUT_ID, false));
    }

    @Override
    protected OnClickViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new UserProfileListViewHolder(UiUtil.inflate(parent, UserProfileListViewHolder.LAYOUT_ID, false));
    }

    @Override
    protected void onBindHeaderViewHolder(OnClickViewHolder holder, int position) {
        super.onBindHeaderViewHolder(holder, position);
        ((UserProfileHeaderViewHolder) holder).bind(getHeader());
        ((UserProfileHeaderViewHolder)holder).setOnUserEdit(onUserEdit);
    }

    @Override
    protected void onBindItemViewHolder(OnClickViewHolder holder, int position) {
        super.onBindItemViewHolder(holder, position);
        ImageListBean imageListBean = getItem(position);
        ((UserProfileListViewHolder)holder).bind(imageListBean);
    }
}
