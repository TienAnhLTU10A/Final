package com.ta.finalexam.Fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.ta.finalexam.Adapter.ImageDetailListAdapter;
import com.ta.finalexam.Bean.DetailBean.DetailData;
import com.ta.finalexam.Bean.HomeBean.HomeBean;
import com.ta.finalexam.Constant.HeaderOption;
import com.ta.finalexam.R;
import com.ta.finalexam.api.CommentListResponse;
import com.ta.finalexam.api.Request.CommentListRequest;
import com.ta.finalexam.api.Request.CommentRequest;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import vn.app.base.adapter.DividerItemDecoration;
import vn.app.base.api.response.BaseResponse;
import vn.app.base.api.volley.callback.ApiObjectCallBack;
import vn.app.base.util.DebugLog;

/**
 * Created by Veteran Commander on 10/19/2016.
 */

public class FragmentDetail extends BaseHeaderListFragment {

    @BindView(R.id.edt_send_cm)
    EditText edtSendCm;

    @BindView(R.id.img_send)
    ImageView imgSend;

    @OnClick(R.id.img_send)
    public void onSendClicked() {
        if (edtSendCm.getText().toString() !=""){
            CommentRequest commentRequest = new CommentRequest(slImage.image.id,edtSendCm.getText().toString());
            commentRequest.setRequestCallBack(new ApiObjectCallBack<BaseResponse>() {
                @Override
                public void onSuccess(BaseResponse data) {
                    DebugLog.i(data.message);
                    getCommentList();

                }

                @Override
                public void onFail(int failCode, String message) {
                    DebugLog.e(message);
                }
            });
        }

    }

    HomeBean slImage;

    ImageDetailListAdapter imageDetailListAdapter;

    List<DetailData> commentList;

    public static FragmentDetail newInstance(HomeBean homeBean) {
        FragmentDetail newFragment = new FragmentDetail();
        newFragment.slImage = homeBean;
        return newFragment;

    }

    @Override
    protected void onRefreshData() {

    }

    @Override
    protected void getArgument(Bundle bundle) {

    }

    @Override
    protected void initView(View root) {
        super.initView(root);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvList.addItemDecoration(new DividerItemDecoration(getActivity(), null));
        setCanRefresh(false);
    }

    @Override
    protected void initData() {
        getCommentList();
        imageDetailListAdapter = new ImageDetailListAdapter();
        imageDetailListAdapter.setHeader(slImage);
        imageDetailListAdapter.setItems(commentList);
        rvList.setAdapter(imageDetailListAdapter);


    }

    @Override
    protected int getLeftIcon() {
        return HeaderOption.LEFT_BACK;
    }

    @Override
    protected int getRightIcon() {
        return HeaderOption.RIGHT_DELETE;
    }

    @Override
    public String getScreenTitle() {
        return "Detail";
    }

    private void getCommentList(){
        CommentListRequest commentListRequest = new CommentListRequest(slImage.image.id);
        commentListRequest.setRequestCallBack(new ApiObjectCallBack<CommentListResponse>() {
            @Override
            public void onSuccess(CommentListResponse data) {
                initialResponseHandled();
                commentList = data.data;
            }

            @Override
            public void onFail(int failCode, String message) {
                DebugLog.e(message);
            }
        });
    }


}
