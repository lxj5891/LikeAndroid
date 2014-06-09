package com.starwall.like.bean;

import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.starwall.like.AppException;
import com.starwall.like.common.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antony on 14-6-6.
 */
public class MenuList {

    private List<Menu> items;
    private int totalItems;

    public static MenuList parse(InputStream inputStream) throws IOException, AppException {

        String json = StringUtils.toConvertString(inputStream);
        JSONObject jsonObject = JSON.parseObject(json);
        JSONObject data = jsonObject.getJSONObject("data");

        MenuList menuList =  new MenuList();
        menuList = JSON.parseObject(data.toJSONString(),MenuList.class);

        Log.i("MenuList ", data.toJSONString());

        return menuList;
    }

    public List<Menu> getItems() {
        return items;
    }

    public void setItems(List<Menu> items) {
        this.items = items;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }
}
