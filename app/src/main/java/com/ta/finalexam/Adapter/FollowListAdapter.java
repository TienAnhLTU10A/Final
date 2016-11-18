package com.ta.finalexam.Adapter;

import android.view.ViewGroup;

import com.ta.finalexam.Adapter.ViewHolder.FollowListViewHolder;
import com.ta.finalexam.Bean.MemberBean;

import java.util.List;

import vn.app.base.adapter.AdapterWithItemClick;
import vn.app.base.util.UiUtil;

/**
 * Created by Veteran Commander on 10/14/2016.
 */

public class FollowListAdapter extends AdapterWithItemClick<FollowListViewHolder> {
    public List<MemberBean> memberList;

    public FollowListAdapter(List<MemberBean> members) {
        this.memberList = members;
    }

    @Override
    public FollowListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FollowListViewHolder(UiUtil.inflate(parent, FollowListViewHolder.LAYOUT_ID, false));
    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }

    @Override
    public void onBindViewHolder(FollowListViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.bind(memberList.get(position));
    }
}
