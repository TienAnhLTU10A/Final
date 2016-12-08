package com.ta.finalexam.Fragment;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Api;
import com.ta.finalexam.Constant.ApiConstance;
import com.ta.finalexam.R;
import com.ta.finalexam.Ulities.StringEncryption;
import com.ta.finalexam.Ulities.manager.UserManager;
import com.ta.finalexam.api.LoginResponse;
import com.ta.finalexam.api.Request.LoginRequest;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.OnClick;
import vn.app.base.api.volley.callback.ApiObjectCallBack;
import vn.app.base.util.DebugLog;
import vn.app.base.util.DialogUtil;
import vn.app.base.util.FragmentUtil;
import vn.app.base.util.KeyboardUtil;
import vn.app.base.util.SharedPrefUtils;
import vn.app.base.util.StringUtil;

/**
 * Created by 3543 on 10/14/2016.
 */

public class FragmentLogin extends NoHeaderFragment {

    String user;
    String pass;

    @BindView(R.id.etLogin)
    EditText etLogin;

    @BindView(R.id.etPassword)
    EditText etPass;

    @BindView(R.id.btnLogin)
    Button btnLogin;

    @BindView(R.id.btnCreateAccount)
    Button btnCreate;

    public static FragmentLogin newInstance() {
        FragmentLogin fragmentLogin = new FragmentLogin();
        return fragmentLogin;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initView(View root) {
        super.initView(root);
        etPass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    login();
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    protected void getArgument(Bundle bundle) {

    }

    @Override
    protected void initData() {
    }

    @OnClick(R.id.btnCreateAccount)
    public void goToRegisterFragment() {
        FragmentUtil.pushFragment(getActivity(), FragmentRegister.newInstance(), null);
    }

    @OnClick(R.id.btnLogin)
    public void login() {
        user = etLogin.getText().toString().trim();
        try {
            pass = StringEncryption.SHA1(etPass.getText().toString().trim());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        pass = etPass.getText().toString().trim();
        String encodePass = null;
        try {
            encodePass = SHA1(pass);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (!StringUtil.checkStringValid(user) || !StringUtil.checkStringValid(pass)) {
            DialogUtil.showOkBtnDialog(getActivity(), getString(R.string.missing_input_title), getString(R.string.missing_input_message)).setCancelable(true);
            return;
        }
        final LoginRequest loginRequest = new LoginRequest(user, encodePass);
        loginRequest.setRequestCallBack(new ApiObjectCallBack<LoginResponse>() {
            @Override
            public void onSuccess(LoginResponse data) {
                if (data.status == 1) {
                    DebugLog.i("Token lay dc " + data.data.token);
                    SharedPrefUtils.saveAccessToken(data.data.token);
                    DebugLog.i("Token la" + SharedPrefUtils.getAccessToken());
                    UserManager.saveCurrentUser(data.data);

                    if(SharedPrefUtils.getBoolean(ApiConstance.ISLOGINYET,false) == false){
                        FragmentUtil.pushFragmentWithAnimation(getActivity(), new FragmentTutorial(), null);
                        SharedPrefUtils.putBoolean(ApiConstance.ISLOGINYET,true);
                    } else FragmentUtil.pushFragmentWithAnimation(getActivity(), new FragmentHome(), null);

                } else Toast.makeText(getActivity(), "Fail roi", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFail(int failCode, String message) {
                hideCoverNetworkLoading();
            }
        });
        loginRequest.execute();
        KeyboardUtil.hideKeyboard(getActivity());
        showCoverNetworkLoading();
    }

    //Ham ma hoa
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
    //Ma hoa SHA1
    public static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        byte[] sha1hash = md.digest();
        return convertToHex(sha1hash);
    }

}
