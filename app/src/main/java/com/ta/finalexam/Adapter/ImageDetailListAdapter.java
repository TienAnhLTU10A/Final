package com.ta.finalexam.Adapter;

import android.view.ViewGroup;

import com.ta.finalexam.Adapter.ViewHolder.DetailHeaderViewHolder;
import com.ta.finalexam.Adapter.ViewHolder.DetailItemViewHolder;
import com.ta.finalexam.Bean.DetailBean.ImageDetailBean;
import com.ta.finalexam.Bean.DetailBean.CommentListData;
import com.ta.finalexam.Bean.HomeBean.HomeBean;

import vn.app.base.adapter.HeaderAdapterWithItemClick;
import vn.app.base.adapter.viewholder.OnClickViewHolder;
import vn.app.base.util.UiUtil;

/**
 * Created by Veteran Commander on 10/26/2016.
 */

public class ImageDetailListAdapter extends HeaderAdapterWithItemClick<OnClickViewHolder, HomeBean, CommentListData, String> {


    @Override
    protected OnClickViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {
        return new DetailHeaderViewHolder(UiUtil.inflate(parent, DetailHeaderViewHolder.LAYOUT_ID,false));
    }

    @Override
    protected OnClickViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new DetailItemViewHolder(UiUtil.inflate(parent, DetailItemViewHolder.LAYOUT_ID,false));
    }

    @Override
    protected void onBindHeaderViewHolder(OnClickViewHolder holder, int position) {
        super.onBindHeaderViewHolder(holder, position);
//        DummyDetail dummyDetail = getItem(0);
        ((DetailHeaderViewHolder)holder).bind(getHeader());
    }

    @Override
    protected void onBindItemViewHolder(OnClickViewHolder holder, int position) {
        super.onBindItemViewHolder(holder, position);
        CommentListData commentListData = getItem(position);
        ((DetailItemViewHolder) holder).bind(commentListData);
    }
}
