package com.ta.finalexam.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mikelau.croperino.Croperino;
import com.mikelau.croperino.CroperinoConfig;
import com.mikelau.croperino.CroperinoFileUtil;
import com.ta.finalexam.Activity.MainActivity;
import com.ta.finalexam.Constant.ApiConstance;
import com.ta.finalexam.R;
import com.ta.finalexam.Ulities.StringEncryption;
import com.ta.finalexam.api.Request.RegisterRequest;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import vn.app.base.api.volley.callback.SimpleRequestCallBack;
import vn.app.base.util.BitmapUtil;
import vn.app.base.util.DebugLog;
import vn.app.base.util.DialogUtil;
import vn.app.base.util.NetworkUtils;
import vn.app.base.util.StringUtil;

import static android.app.Activity.RESULT_OK;
import static vn.app.base.util.BitmapUtil.decodeFromFile;


/**
 * Created by 3543 on 10/16/2016.
 */

public class RegisterFragment extends NoHeaderFragment {
    public static final String REGISTER_PHOTO = "REGISTER_PHOTO";
    private static final String APP_TAG = RegisterFragment.class.getSimpleName();

    String userId;
    String email;
    String pass;
    String confirmPass;
    String encodePass;

    File mediaStoreDir;
    Uri fileUri;
    Uri uri;

    public static final int REQUEST_GALLERY = 1;


    @BindView(R.id.ivAvatar)
    ImageView ivAvatar;

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


    public static RegisterFragment newInstance() {
        RegisterFragment registerFragment = new RegisterFragment();
        return registerFragment;
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

        RegisterRequest registerRequest = new RegisterRequest(userId, pass, email, new SimpleRequestCallBack() {
            @Override
            public void onResponse(boolean success, String message) {

            }
        });


    }

//    private void registerUser(final String name, final String email, final String pass) {
//        String url = ApiConstance.REGISTER;
//        StringRequest registerRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
//                DebugLog.i(response);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("username", name);
//                params.put("email", email);
//                params.put("password", pass);
//                return params;
//            }
//        };
//        NetworkUtils.getInstance(getActivity()).addToRequestQueue(registerRequest);
//    }


    @OnClick(R.id.ivAvatar)
    public void picture() {
        takePhoto();
//        choosephoto();
    }

    private void choosephoto() {
        Intent getGallery = new Intent();
        getGallery.setType("image/*");
        getGallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(getGallery, "Chon anh"), REQUEST_GALLERY);
    }

    private void takePhoto() {
        Intent getCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        uri = Uri.fromFile(creatFileUri(getActivity()));
        getCamera.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFileUri(ApiConstance.AVATAR_PHOTO));
        getCamera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        if (getCamera.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(getCamera, ApiConstance.REQUEST_CODE_TAKEPHOTO);
        }
    }

    public Uri getPhotoFileUri(String fileName) {
        if (isExternalStorageAvailable()) {
            mediaStoreDir = new File
                    (getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Picture");
            if (!mediaStoreDir.exists() && !mediaStoreDir.mkdir()) {
                Log.d(APP_TAG, "Failed to creat folder");
            }
            return Uri.fromFile(new File(mediaStoreDir.getPath() + File.separator + fileName + File.separator));
        }
        return null;
    }

    public File creatFileUri(Context context) {
        File[] externalFile = ContextCompat.getExternalFilesDirs(context, null);
        if (externalFile == null) {
            externalFile = new File[]{context.getExternalFilesDir(null)};
        }
        final File root = new File(externalFile[0] + File.separator + "You" + File.separator);
        root.mkdir();
        final String fname = "NewImg.jpg";
        final File sdImageMainDirectory = new File(root, fname);
        if (sdImageMainDirectory.exists()) {
            sdImageMainDirectory.delete();
        }
        return sdImageMainDirectory;
    }

    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK) {
            Bitmap bitmap2 = null;
            if (data != null) {
                try {
                    bitmap2 = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bitmap2.getWidth() > bitmap2.getHeight()) {
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                bitmap2 = Bitmap.createBitmap(bitmap2, 0, 0, bitmap2.getWidth(), bitmap2.getHeight(), matrix, true);
            }
            ivAvatar.setImageBitmap(bitmap2);
//        }else if (requestCode == ApiConstance.REQUEST_CODE_TAKEPHOTO && resultCode == RESULT_OK) {
//                Bitmap bitmap = decodeFromFile(uri.getPath(),100,100);
//                if (bitmap != null){
//                    ivAvatar.setImageBitmap(bitmap);
//                }
//            }
        } else if (requestCode == ApiConstance.REQUEST_CODE_TAKEPHOTO && resultCode == RESULT_OK) {
            fileUri = getPhotoFileUri(ApiConstance.AVATAR_PHOTO);
            Bitmap bitmap = BitmapUtil.decodeFromFile(fileUri.getPath(), 100, 100);
            DebugLog.i("file" + fileUri.getPath());
            if (bitmap.getWidth() > bitmap.getHeight()) {
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            }
            ivAvatar.setImageBitmap(bitmap);
        }
    }
}



