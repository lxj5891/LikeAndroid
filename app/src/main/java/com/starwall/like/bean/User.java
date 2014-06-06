package com.starwall.like.bean;

import com.starwall.like.AppException;
import com.starwall.like.common.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Antony on 14-6-3.
 */
public class User {

    private String _id;
    private String email;
    private String first;
    private String userName;

    public static User parse(InputStream inputStream) throws IOException, AppException, JSONException {

        String json = StringUtils.toConvertString(inputStream);
        JSONObject jsonObject = new JSONObject(json).getJSONObject("data");
        User user = new User();
        user.set_id(jsonObject.getString("_id"));
        user.setEmail(jsonObject.getString("email"));
        user.setFirst(jsonObject.getString("first"));
        user.setUserName(jsonObject.getString("userName"));
        return user;
    }

    @Override
    public String toString() {
        return "（Object User） ＝ _id : \"" + this._id + "\" email : \"" + this.email + "\" first : \"" + this.first + "\" userName : \"" + this.userName + "\"";
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
