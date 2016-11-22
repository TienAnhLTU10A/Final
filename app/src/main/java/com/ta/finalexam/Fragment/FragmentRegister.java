package com.ta.finalexam.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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
import com.ta.finalexam.Ulities.StringEncryption;
import com.ta.finalexam.api.Request.RegisterRequest;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import vn.app.base.api.volley.callback.SimpleRequestCallBack;
import vn.app.base.util.DialogUtil;
import vn.app.base.util.StringUtil;

import static android.app.Activity.RESULT_OK;


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

    String imagepath;

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
        if ((confirmPass == pass) && (!StringUtil.checkStringValid(userId) || !StringUtil.checkStringValid(email) || !StringUtil.checkStringValid(pass)
                || !StringUtil.checkStringValid(confirmPass))) {
            try {
                encodePass = StringEncryption.SHA1(pass);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            DialogUtil.showOkBtnDialog(getActivity(), getString(R.string.missing_input_title), getString(R.string.missing_input_message)).setCancelable(true);
            return;
        }
        if (imageAvatar == null) {
            registerRequest = new RegisterRequest(userId, pass, email, new SimpleRequestCallBack() {
                @Override
                public void onResponse(boolean success, String message) {

                }
            });

        } else {
            registerRequest = new RegisterRequest(userId, pass, email, imageAvatar, new SimpleRequestCallBack() {
                @Override
                public void onResponse(boolean success, String message) {

                }
            });
        }

        registerRequest.execute();


    }


    @OnClick(R.id.ivAvatar)
    public void picture() {
        Intent getCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = Uri.fromFile(creatFileUri(getActivity()));
        getCamera.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(getCamera, ApiConstance.REQUEST_CODE_TAKEPHOTO);
    }


    public File creatFileUri(Context context) {
        File[] externalFile = ContextCompat.getExternalFilesDirs(context, null);
        if (externalFile == null) {
            externalFile = new File[]{context.getExternalFilesDir(null)};
        }
        final File root = new File(externalFile[0] + File.separator + "InstagramFake" + File.separator);
        root.mkdir();
        imagepath = root.getAbsolutePath();
        final String fname = REGISTER_PHOTO;
        final File sdImageMainDirectory = new File(root, fname);
        if (sdImageMainDirectory.exists()) {
            sdImageMainDirectory.delete();
        }
        return sdImageMainDirectory;
    }

    private File creatFilefromBitmap(Bitmap bitmap) throws IOException {
        OutputStream fOut = null;
        imageAvatar = new File(imagepath, "avatarCropped.jpg");
        fOut = new FileOutputStream(imageAvatar);
        Bitmap getBitmap = bitmap;
        getBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
        fOut.flush();
        fOut.close();
        MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), imageAvatar.getAbsolutePath(), imageAvatar.getName(), imageAvatar.getName());
        return imageAvatar;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ApiConstance.REQUEST_CODE_TAKEPHOTO && resultCode == RESULT_OK) {
            //Start cropImage Activity
            CropImage.activity(fileUri).start(getContext(), this);

        }
        //Get result from cropImage Activity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    //lay bitmap tu uri result
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), resultUri);
                    creatFilefromBitmap(bitmap);
                    ivAvatar.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }


    }
}




