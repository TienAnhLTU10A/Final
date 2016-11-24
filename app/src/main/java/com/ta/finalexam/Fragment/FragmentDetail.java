package com.ta.finalexam.Fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ta.finalexam.Activity.MainActivity;
import com.ta.finalexam.Adapter.ImageDetailListAdapter;
import com.ta.finalexam.Bean.DetailBean.CommentListData;
import com.ta.finalexam.Bean.HomeBean.HomeBean;
import com.ta.finalexam.Constant.HeaderOption;
import com.ta.finalexam.R;
import com.ta.finalexam.Ulities.manager.UserManager;
import com.ta.finalexam.api.CommentListResponse;
import com.ta.finalexam.api.Request.CommentListRequest;
import com.ta.finalexam.api.Request.CommentRequest;
import com.ta.finalexam.api.Request.DeleteRequest;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import vn.app.base.adapter.DividerItemDecoration;
import vn.app.base.api.response.BaseResponse;
import vn.app.base.api.volley.callback.ApiObjectCallBack;
import vn.app.base.util.DebugLog;
import vn.app.base.util.FragmentUtil;

/**
 * Created by Veteran Commander on 10/19/2016.
 */

public class FragmentDetail extends BaseHeaderListFragment {

    public static final String IMAGE = "image";

    @BindView(R.id.edt_send_cm)
    EditText edtSendCm;

    @BindView(R.id.img_send)
    ImageView imgSend;

    @OnClick(R.id.img_send)
    public void onSendClicked() {
        if (edtSendCm.getText().toString() !=""){
            CommentRequest commentRequest = new CommentRequest(homeBean.image.id,edtSendCm.getText().toString());
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

    HomeBean homeBean;

    ImageDetailListAdapter imageDetailListAdapter;

    List<CommentListData> commentList;

    List<CommentListData> commentListDummy;

    public static FragmentDetail newInstance(HomeBean homeBean) {
        FragmentDetail newFragment = new FragmentDetail();
        Bundle getHomeBean = new Bundle();
        getHomeBean.putParcelable(IMAGE,homeBean);
        newFragment.setArguments(getHomeBean);
        return newFragment;

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_image_detail;
    }

    @Override
    protected void onRefreshData() {

    }

    @Override
    protected void getArgument(Bundle bundle) {
        homeBean = bundle.getParcelable(IMAGE);
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
        TextView tvDelete = ((MainActivity)getActivity()).getTvDelete();
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Delete",Toast.LENGTH_SHORT);
                if (homeBean.user.id.equals(UserManager.getCurrentUser().id)){
                    DeleteRequest deleteRequest = new DeleteRequest(homeBean.image.id);
                    deleteRequest.setRequestCallBack(new ApiObjectCallBack<BaseResponse>() {
                        @Override
                        public void onSuccess(BaseResponse data) {
                            if (data.status == 1){
                                FragmentUtil.pushFragment(getActivity(),FragmentHome.newInstance(),null);
                            }
                        }

                        @Override
                        public void onFail(int failCode, String message) {

                        }
                    });
                }

            }
        });

        getCommentList();
        imageDetailListAdapter = new ImageDetailListAdapter();
        imageDetailListAdapter.setHeader(homeBean);
        imageDetailListAdapter.setItems(commentListDummy);
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
        commentListDummy = new ArrayList<>();
        commentListDummy.add(new CommentListData("TienAnh","ACACACACAC"));
        commentListDummy.add(new CommentListData("TienAnh","ACACACACAC"));
        commentListDummy.add(new CommentListData("TienAnh","ACACACACAC"));
        CommentListRequest commentListRequest = new CommentListRequest(homeBean.image.id);
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
