package com.ta.finalexam.Fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.ta.finalexam.Adapter.FollowListAdapter;
import com.ta.finalexam.Bean.MemberBean;
import com.ta.finalexam.Constant.HeaderOption;
import com.ta.finalexam.R;
import com.ta.finalexam.api.FollowlistResponse;
import com.ta.finalexam.api.Request.FollowlistRequest;

import java.util.List;

import vn.app.base.api.volley.callback.ApiObjectCallBack;
import vn.app.base.callback.OnRecyclerViewItemClick;
import vn.app.base.util.DebugLog;

/**
 * Created by Veteran Commander on 10/14/2016.
 */

public class FragmentFollowlist extends BaseHeaderListFragment {
//    List<Member> memberFollowList;
    FollowListAdapter followListAdapter;

    public static FragmentFollowlist newInstance(){
        FragmentFollowlist newFragment = new FragmentFollowlist();
        return newFragment;
    }



    @Override
    protected void onRefreshData() {

    }

    @Override
    protected void getArgument(Bundle bundle) {

    }

    @Override
    protected void initData() {
        getFollowlistdata();
    }

    @Override
    protected boolean isSkipGenerateBaseLayout() {
        return true;
    }

    @Override
    protected void initView(View root) {
        super.initView(root);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_follow_screen;
    }

    @Override
    protected int getLeftIcon() {
        return HeaderOption.LEFT_MENU;
    }

    @Override
    public String getScreenTitle() {
        return "Follow";
    }

    @Override
    protected boolean isStartWithLoading() {
        return true;
    }

    private void getFollowlistdata(){
        FollowlistRequest followlistRequest = new FollowlistRequest();
        followlistRequest.setRequestCallBack(new ApiObjectCallBack<FollowlistResponse>() {
            @Override
            public void onSuccess(FollowlistResponse data) {
                initialResponseHandled();
                DebugLog.i("" + data.status);
                DebugLog.i(data.message);
//                handleFollowlistdata(data.data);

            }

            @Override
            public void onFail(int failCode, String message) {
                DebugLog.e("Error" + failCode + message);
            }
        });
    }

    private void handleFollowlistdata(List<MemberBean> dataFollowList){
        followListAdapter = new FollowListAdapter(dataFollowList);
        followListAdapter.setOnRecyclerViewItemClick(new OnRecyclerViewItemClick() {
            @Override
            public void onItemClick(View view, int position) {
                int realpos = position + 1;
                Toast.makeText(getActivity(),realpos + "" ,Toast.LENGTH_SHORT).show();
            }
        });
        rvList.setAdapter(followListAdapter);

    }




}
