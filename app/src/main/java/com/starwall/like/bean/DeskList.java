package com.starwall.like.bean;


import android.util.Log;
import com.starwall.like.AppException;
import com.starwall.like.common.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Antony on 14-6-3.
 */
public class DeskList {

    private ArrayList<Desk> items;
    private int totalItems;

    public static DeskList parse(InputStream inputStream) throws IOException, AppException, JSONException {

        String json = StringUtils.toConvertString(inputStream);

        Log.i("DeskList",json);
        JSONObject jsonObject = new JSONObject(json).getJSONObject("data");
        JSONArray itemsArray = jsonObject.getJSONArray("items");

        ArrayList<Desk> tmpItems = new ArrayList<Desk>();

        for (int i = 0; i < itemsArray.length(); i++) {

            JSONObject object = (JSONObject) itemsArray.get(i);
            Desk desk = Desk.parseJson(object);
            tmpItems.add(desk);
        }

        DeskList deskList = new DeskList();
        deskList.setItems(tmpItems);
        deskList.setTotalItems(jsonObject.getInt("totalItems"));

        return deskList;
    }

    public ArrayList<Desk> getItems() {
        return items;
    }

    public void setItems(ArrayList<Desk> items) {
        this.items = items;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }
}
