package com.ta.finalexam.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ta.finalexam.Constant.ApiConstance;
import com.ta.finalexam.R;
import com.ta.finalexam.Ulities.FileForUploadUtils;
import com.ta.finalexam.Ulities.StringEncryption;
import com.ta.finalexam.Ulities.manager.UserManager;
import com.ta.finalexam.api.RegisterResponse;
import com.ta.finalexam.api.Request.RegisterRequest;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import vn.app.base.api.volley.callback.SimpleRequestCallBack;
import vn.app.base.util.BitmapUtil;
import vn.app.base.util.DebugLog;
import vn.app.base.util.DialogUtil;
import vn.app.base.util.FragmentUtil;
import vn.app.base.util.ImagePickerUtil;
import vn.app.base.util.SharedPrefUtils;
import vn.app.base.util.StringUtil;

import static android.app.Activity.RESULT_OK;
import static com.ta.finalexam.Ulities.FileForUploadUtils.creatFilefromBitmap;


/**
 * Created by 3543 on 10/16/2016.
 */

public class FragmentRegister extends NoHeaderFragment {
    public static final String REGISTER_PHOTO = "REGISTER_PHOTO";
    private static final String APP_TAG = FragmentRegister.class.getSimpleName();

    String userId;
    String email;
    String pass;
    String confirmPass;
    String encodePass;

    File imageAvatar;

    Uri fileUri;

    RegisterRequest registerRequest;

//    @BindView(R.id.cropImageView)
//    CropImageView cropImageView;


    @BindView(R.id.ivAvatar)
    CircleImageView ivAvatar;

    @BindView(R.id.etUser)
    EditText etUser;

    @BindView(R.id.etEmail)
    EditText etEmail;

    @BindView(R.id.etPass)
    EditText etPass;

    @BindView(R.id.etConfirm)
    EditText etConfirm;

    @BindView(R.id.btnSignUp)
    Button btnSignUp;


    public static FragmentRegister newInstance() {
        FragmentRegister fragmentRegister = new FragmentRegister();
        return fragmentRegister;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    protected void initView(View root) {

    }

    @Override
    protected void getArgument(Bundle bundle) {

    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.btnSignUp)
    public void register() {
        userId = etUser.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        confirmPass = etConfirm.getText().toString().trim();
        pass = etPass.getText().toString().trim();
        try {
            encodePass = SHA1(pass);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if ((confirmPass.equals(pass)) && (StringUtil.checkStringValid(userId)
                && StringUtil.checkStringValid(email) && StringUtil.checkStringValid(pass)
                && StringUtil.checkStringValid(confirmPass))) {
            if (imageAvatar == null) {
                try {
                    creatFilefromDrawable(R.drawable.placeholer_avatar);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            registerRequest = new RegisterRequest(userId, pass, email, imageAvatar, getActivity());
            registerRequest.execute();
            showCoverNetworkLoading();
        } else DialogUtil.createCloseBtnDialog(getActivity(),"Error","Hay dien du va dong thong tin yeu cau");

    }

    @OnClick(R.id.ivAvatar)
    //Goi intent chup anh
    public void picture() {
        ImagePickerUtil imagePickerUtil = new ImagePickerUtil();
        Intent getCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = Uri.fromFile(imagePickerUtil.createFileUri(getActivity()));
        getCamera.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(getCamera, ApiConstance.REQUEST_CODE_TAKEPHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ApiConstance.REQUEST_CODE_TAKEPHOTO && resultCode == RESULT_OK) {
            //Start cropImage Activity
            CropImage.activity(fileUri).setAspectRatio(1, 1).start(getContext(), this);
        }
        //Get result from cropImage Activity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    //lay bitmap tu uri result
                    Bitmap bitmap = BitmapUtil.decodeFromFile(resultUri.getPath(), 800, 800);
                    imageAvatar = FileForUploadUtils.creatFilefromBitmap(bitmap);
                    ivAvatar.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }


    //Ma hoa
    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        byte[] sha1hash = md.digest();
        return convertToHex(sha1hash);
    }

    private File creatFilefromDrawable(int drawableID) throws IOException {
        Drawable drawable = getResources().getDrawable(drawableID);
        File imageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/InstagramFaker");
        imageDir.mkdir();
        imageAvatar = new File(imageDir, "avatarDefault.jpg");
        DebugLog.i("Duong dan" + imageDir);
        OutputStream fOut = new FileOutputStream(imageAvatar);
        Bitmap getBitmap = ((BitmapDrawable) drawable).getBitmap();
        getBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
        fOut.flush();
        fOut.close();
        return imageAvatar;
    }


}




