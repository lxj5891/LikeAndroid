package com.starwall.like.api;

import com.starwall.like.AppContext;
import com.starwall.like.AppException;
import com.starwall.like.bean.URLs;
import com.starwall.like.bean.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Antony on 14-6-5.
 */
public class UserModule extends ApiClient {


    /*
     *  用户登录
     *
     */
    public static User login(AppContext appContext, final String name, final String password) throws AppException {


        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", name);
        params.put("password", password);

        try {

            User user = User.parse(_post(appContext, URLs.LOGIN_VALIDATE_HTTP, params));

            return user;

        } catch (Exception e) {

            if (e instanceof AppException)
                throw (AppException) e;

            throw AppException.network(e);
        }
    }
}
