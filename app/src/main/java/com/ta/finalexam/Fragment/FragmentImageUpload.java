package com.ta.finalexam.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.ta.finalexam.Constant.ApiConstance;
import com.ta.finalexam.Constant.HeaderOption;
import com.ta.finalexam.R;
import com.ta.finalexam.api.Request.ImageUploadRequest;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import vn.app.base.api.volley.callback.SimpleRequestCallBack;
import vn.app.base.util.BitmapUtil;
import vn.app.base.util.DebugLog;
import vn.app.base.util.FragmentUtil;
import vn.app.base.util.ImagePickerUtil;

import static com.ta.finalexam.Fragment.RegisterFragment.REGISTER_PHOTO;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentImageUpload extends HeaderFragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    @BindView(R.id.ivPhotoHeader)
    ImageView ivPhotoPreview;

    @BindView(R.id.fabUpload)
    FloatingActionButton fabUpload;

    @BindView(R.id.etHashTag)
    EditText etHashTag;

    @BindView(R.id.etCaption)
    EditText etCaption;

    @BindView(R.id.btnCancel)
    Button btnCancel;
    @BindView(R.id.btnPost)
    Button btnPost;

    @BindView(R.id.switchCompat)
    SwitchCompat mSwitch;

    double mlong, lat;
    String location;
    private Uri fileUri;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    File imageAvatar;

    public FragmentImageUpload() {
    }

    public static FragmentImageUpload newInstance() {
        FragmentImageUpload fragmentImageUpload = new FragmentImageUpload();
        return fragmentImageUpload;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_image_upload;
    }

    @Override
    public String getScreenTitle() {
        return "Post Image";
    }

    @Override
    protected int getLeftIcon() {
        return HeaderOption.LEFT_BACK;
    }

    @Override
    protected void getArgument(Bundle bundle) {
    }

    @Override
    protected void initView(View root) {
        super.initView(root);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

        }
    }

    @OnClick(R.id.fabUpload)
    public void onUpload() {
        onLauchCamera();
    }

    @OnClick(R.id.btnCancel)
    public void onCancel() {
        FragmentUtil.popBackStack(getActivity());
    }

    @OnClick(R.id.btnPost)
    public void onPost() {
        uploadImage(etCaption.getText().toString(), String.valueOf(mlong), String.valueOf(lat), location, etHashTag.getText().toString(), imageAvatar);
    }

    @OnCheckedChanged(R.id.switchCompat)
    public void onCheck() {
        if (mSwitch.isChecked()) {
            mGoogleApiClient.connect();
        } else {
            mGoogleApiClient.disconnect();
        }
    }

    private void onLauchCamera() {
        ImagePickerUtil imagePickerUtil = new ImagePickerUtil();
        Intent getCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = Uri.fromFile(imagePickerUtil.createFileUri(getActivity()));
        getCamera.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(getCamera, ApiConstance.REQUEST_CODE_TAKEPHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ApiConstance.REQUEST_CODE_TAKEPHOTO && resultCode == Activity.RESULT_OK) {
            //Start cropImage Activity
            CropImage.activity(fileUri).setAspectRatio(16, 9).start(getContext(), this);
        }
        //Get result from cropImage Activity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == Activity.RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    //lay bitmap tu uri result
                    Bitmap bitmap = BitmapUtil.decodeFromFile(resultUri.getPath(), 900, 900);
                    creatFilefromBitmap(bitmap);
                    ivPhotoPreview.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
    //Tao file tu Bitmap
    private File creatFilefromBitmap(Bitmap bitmap) throws IOException {
        File imageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/InstagramFaker");
        imageDir.mkdir();
        imageAvatar = new File(imageDir, "avatarCropped.jpg");
        OutputStream fOut = new FileOutputStream(imageAvatar);
        Bitmap getBitmap = bitmap;
        getBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
        fOut.flush();
        fOut.close();
        return imageAvatar;
    }

    public void uploadImage(String caption, String mlong, String lat, String location,
                            String hashtag, File image) {
        showCoverNetworkLoading();
        Log.e("X _ initData", "caption :" + caption);
        Log.e("X _ initData", "mlong :" + mlong);
        Log.e("X _ initData", "lat :" + lat);
        Log.e("X _ initData", "location :" + location);
        Log.e("X _ initData", "caption :" + hashtag);
        new ImageUploadRequest(caption, mlong, lat, location, hashtag, image, new SimpleRequestCallBack() {
            @Override
            public void onResponse(boolean success, String message) {
                hideCoverNetworkLoading();
            }
        }).execute();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            lat = mLastLocation.getLatitude();
            mlong = mLastLocation.getLongitude();
            getCompleteAddressString(lat, mlong);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        location = "";
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("");
                }
                location = strReturnedAddress.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }
}

