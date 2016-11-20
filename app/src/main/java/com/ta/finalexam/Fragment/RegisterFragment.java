package com.ta.finalexam.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import com.ta.finalexam.Constant.ApiConstance;
import com.ta.finalexam.R;
import com.ta.finalexam.Ulities.StringEncryption;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import vn.app.base.util.BitmapUtil;
import vn.app.base.util.DebugLog;
import vn.app.base.util.DialogUtil;
import vn.app.base.util.NetworkUtils;
import vn.app.base.util.StringUtil;


/**
 * Created by 3543 on 10/16/2016.
 */

public class RegisterFragment extends NoHeaderFragment {
    public static final String REGISTER_PHOTO = "REGISTER_PHOTO";

    String userId;
    String email;
    String pass;
    String confirmPass;
    String encodePass;


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

        registerUser(userId, email, pass);

    }

    private void registerUser(final String name, final String email, final String pass) {


        String url = ApiConstance.REGISTER;
        StringRequest registerRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                DebugLog.i(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", name);
                params.put("email", email);
                params.put("password", pass);
                return params;
            }
        };
        NetworkUtils.getInstance(getActivity()).addToRequestQueue(registerRequest);
    }


    @OnClick(R.id.ivAvatar)
    public void picture() {
        takePhoto();
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,getPhotoFileUri(ApiConstance.AVATAR_PHOTO));
        if (intent.resolveActivity(getActivity().getPackageManager())!=null){
            startActivityForResult(intent,ApiConstance.REQUEST_CODE_TAKEPHOTO);
        }
    }


    @Override
    public void onFragmentDataHandle(Bundle bundle) {
        super.onFragmentDataHandle(bundle);
    }

    public Uri getPhotoFileUri(String fileName){
        if (isExternalStorageAvailable()){
            File mediaStorageAvailable = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES),"manh ho");
            if (!mediaStorageAvailable.exists()&& mediaStorageAvailable.mkdir()){

            }
            return Uri.fromFile(new File(mediaStorageAvailable.getPath() + File.separator + fileName +File.separator));
        }

        return null;
    }

    private boolean isExternalStorageAvailable(){
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ApiConstance.REQUEST_CODE_PICKPHOTO && resultCode == Activity.RESULT_OK){
            Uri fileUri = getPhotoFileUri(ApiConstance.AVATAR_PHOTO);
            Bitmap bitmap = BitmapUtil.decodeFromFile(fileUri.getPath(),100,100);
            DebugLog.i("file"+fileUri.getPath());
            if (bitmap.getWidth()>bitmap.getHeight()){
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                bitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
            }
        ivAvatar.setImageBitmap(bitmap);
        }
    }
}

