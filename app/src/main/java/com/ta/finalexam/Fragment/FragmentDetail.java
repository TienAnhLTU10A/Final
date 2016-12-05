package com.ta.finalexam.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.maps.model.LatLng;
import com.ta.finalexam.Activity.MainActivity;
import com.ta.finalexam.Adapter.ImageDetailListAdapter;
import com.ta.finalexam.Bean.DetailBean.CommentListData;
import com.ta.finalexam.Bean.HomeBean.HomeBean;
import com.ta.finalexam.Constant.ApiConstance;
import com.ta.finalexam.Constant.HeaderOption;
import com.ta.finalexam.R;
import com.ta.finalexam.Ulities.manager.UserManager;
import com.ta.finalexam.api.CommentListResponse;
import com.ta.finalexam.api.Request.CommentListRequest;
import com.ta.finalexam.api.Request.CommentRequest;
import com.ta.finalexam.api.Request.DeleteRequest;
import com.ta.finalexam.api.Request.FavouritesRequest;
import com.ta.finalexam.api.Request.FollowRequest;
import com.ta.finalexam.callback.OnDetailClicked;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import vn.app.base.adapter.DividerItemDecoration;
import vn.app.base.api.response.BaseResponse;
import vn.app.base.api.volley.callback.ApiObjectCallBack;
import vn.app.base.util.DebugLog;
import vn.app.base.util.FragmentUtil;
import vn.app.base.util.IntentUtil;

/**
 * Created by Veteran Commander on 10/19/2016.
 * last fix : 5/12/2016 by TA
 */

public class FragmentDetail extends BaseHeaderListFragment {

    public static final String IMAGE = "image";

    FollowRequest followRequest;

    FavouritesRequest favouritesRequest;

    LatLng pictureLocation;

    @BindView(R.id.edt_send_cm)
    EditText edtSendCm;

    @BindView(R.id.img_send)
    ImageView imgSend;

    @OnClick(R.id.img_send)
    public void onSendClicked() {
        if (edtSendCm.getText().toString() !=""){
            CommentRequest commentRequest = new CommentRequest(selectHomeBean.image.id,edtSendCm.getText().toString());
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
            commentRequest.execute();
        }

    }

    HomeBean selectHomeBean;

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
        getCommentList();
    }

    @Override
    protected void getArgument(Bundle bundle) {
        selectHomeBean = bundle.getParcelable(IMAGE);

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
        commentList = new ArrayList<>();
        getCommentList();
        TextView tvDelete = ((MainActivity)getActivity()).getTvDelete();
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Delete",Toast.LENGTH_SHORT).show();
                if (selectHomeBean.user.id.equals(UserManager.getCurrentUser().id)){
                    DeleteRequest deleteRequest = new DeleteRequest(selectHomeBean.image.id);
                    deleteRequest.setRequestCallBack(new ApiObjectCallBack<BaseResponse>() {
                        @Override
                        public void onSuccess(BaseResponse data) {
                            if (data.status == 1){
                                FragmentUtil.pushFragment(getActivity(),FragmentHome.newInstance(), null);
                            }
                        }

                        @Override
                        public void onFail(int failCode, String message) {

                        }
                    });
                }

            }
        });




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
        showCoverNetworkLoading();
        commentList.clear();
        CommentListRequest commentListRequest = new CommentListRequest(selectHomeBean.image.id);
        commentListRequest.setRequestCallBack(new ApiObjectCallBack<CommentListResponse>() {
            @Override
            public void onSuccess(CommentListResponse data) {
//                CommentListData commentListData = new CommentListData();
//                for (int i = 0 ; i <data.data.size(); i ++ ){
//                    commentListData = data.data.get(i);
//                    commentList.add(commentListData);
//                }
                commentList = data.data;
                hideCoverNetworkLoading();
                setRecyclerView(commentList);
            }
            @Override
            public void onFail(int failCode, String message) {
                DebugLog.e(message);
            }
        });
        commentListRequest.execute();
    }

    private void setRecyclerView(List<CommentListData> dataList){
        imageDetailListAdapter = new ImageDetailListAdapter();
        imageDetailListAdapter.setHeader(selectHomeBean);
        imageDetailListAdapter.setItems(dataList);
        imageDetailListAdapter.setOnDetailClicked(new OnDetailClicked() {
            @Override
            public void onFollowDetailClick(HomeBean homeBean) {
                if (homeBean.user.isFollowing){
                    //Goi unfollow
                    followRequest = new FollowRequest(homeBean.user.id,0);
                    followRequest.setRequestCallBack(new ApiObjectCallBack<BaseResponse>() {
                        @Override
                        public void onSuccess(BaseResponse data) {
                            if (data.status == 1){
                                selectHomeBean.user.isFollowing = false;
                                imageDetailListAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onFail(int failCode, String message) {

                        }
                    });
                } else {
                    //Goi follow
                    followRequest = new FollowRequest(homeBean.user.id,1);
                    followRequest.setRequestCallBack(new ApiObjectCallBack<BaseResponse>() {
                        @Override
                        public void onSuccess(BaseResponse data) {
                            if (data.status == 1){
                                selectHomeBean.user.isFollowing = true;
                                imageDetailListAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onFail(int failCode, String message) {

                        }
                    });
                }
                followRequest.execute();
            }

            @Override
            public void onFavouriteDetailClick(HomeBean homeBean) {
                //TODO: get favourite request
                if (homeBean.image.isFavourite){
                    //Goi unfavorite
                    favouritesRequest = new FavouritesRequest(homeBean.image.id,0);
                    favouritesRequest.setRequestCallBack(new ApiObjectCallBack<BaseResponse>() {
                        @Override
                        public void onSuccess(BaseResponse data) {
                            if (data.status == 1){
                                selectHomeBean.image.isFavourite = false;
                                imageDetailListAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onFail(int failCode, String message) {

                        }
                    });

                } else {
                    //Goi favourite
                    favouritesRequest = new FavouritesRequest(homeBean.image.id,1);
                    favouritesRequest.setRequestCallBack(new ApiObjectCallBack<BaseResponse>() {
                        @Override
                        public void onSuccess(BaseResponse data) {
                            if (data.status == 1){
                                selectHomeBean.image.isFavourite = true;
                                imageDetailListAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onFail(int failCode, String message) {

                        }
                    });
                }
                favouritesRequest.execute();
            }

            @Override
            public void onMapClick(HomeBean homeBean) {
                goToMapAddress(homeBean);
            }

            @Override
            public void onAvatarClicked(HomeBean homeBean) {
                //TODO: go to profile

            }
        });
        rvList.setAdapter(imageDetailListAdapter);
    }

    private void goToMapAddress(HomeBean homeBean) {
        Uri mapUri;
        if (pictureLocation != null) {
            mapUri = Uri.parse("geo:" + pictureLocation.latitude + "," + pictureLocation.longitude + "?q=" + +pictureLocation.latitude + "," + pictureLocation.longitude + "&z=15");
        } else {
            mapUri = Uri.parse("geo:0,0?z=15&q=" + getDecodeAddress(homeBean.image.location));
        }
        IntentUtil.openMap(getActivity(), mapUri);
    }

    private String getDecodeAddress(String location) {
        try {
            return URLDecoder.decode(location, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return location.replace(" ", "+");
        }
    }

//    private void getDummyData(){
//        commentListDummy = new ArrayList<>();
//        CommentListData dummyData = new CommentListData();
//        for (int i = 0; i < 5; i++) {
//            dummyData.comment = "AHAHAHAHAHAHA";
//            commentListDummy.add(dummyData);
//        }
//        imageDetailListAdapter = new ImageDetailListAdapter();
//        imageDetailListAdapter.setHeader(selectHomeBean);
//        imageDetailListAdapter.setItems(commentListDummy);
//        imageDetailListAdapter.setOnDetailClicked(new OnDetailClicked() {
//            @Override
//            public void onFollowDetailClick(final HomeBean homeBean) {
//                Toast.makeText(getActivity(),"Clecl",Toast.LENGTH_SHORT).show();
//                if (homeBean.user.isFollowing){
//                    //Goi unfollow
//                    followRequest = new FollowRequest(homeBean.user.id,0);
//                    followRequest.setRequestCallBack(new ApiObjectCallBack<BaseResponse>() {
//                        @Override
//                        public void onSuccess(BaseResponse data) {
//                            if (data.status == 1){
//                                selectHomeBean.user.isFollowing = false;
//                                imageDetailListAdapter.notifyDataSetChanged();
//                            }
//                        }
//
//                        @Override
//                        public void onFail(int failCode, String message) {
//
//                        }
//                    });
//                } else {
//                    //Goi follow
//                    followRequest = new FollowRequest(homeBean.user.id,1);
//                    followRequest.setRequestCallBack(new ApiObjectCallBack<BaseResponse>() {
//                        @Override
//                        public void onSuccess(BaseResponse data) {
//                            if (data.status == 1){
//                                selectHomeBean.user.isFollowing = true;
//                                imageDetailListAdapter.notifyDataSetChanged();
//                            }
//                        }
//
//                        @Override
//                        public void onFail(int failCode, String message) {
//
//                        }
//                    });
//                }
//                followRequest.execute();
//            }
//
//
//            @Override
//            public void onFavouriteDetailClick(HomeBean homeBean) {
//
//            }
//        });
//        rvList.setAdapter(imageDetailListAdapter);
//
//    }


}
