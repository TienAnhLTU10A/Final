package com.ta.finalexam.api;


import com.ta.finalexam.Bean.LoginBean.DataBean;

import vn.app.base.api.response.BaseResponse;

/**
 * Created by 3543 on 10/21/2016.
 */

public class LoginResponse extends BaseResponse {
    public String token;
    public DataBean dataBean;
}
