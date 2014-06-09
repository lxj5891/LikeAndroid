package com.starwall.like.api;

import com.starwall.like.AppContext;
import com.starwall.like.AppException;
import com.starwall.like.bean.DeskList;
import com.starwall.like.bean.MenuList;
import com.starwall.like.bean.URLs;
import org.json.JSONException;

import java.io.IOException;

/**
 * Created by Antony on 14-6-6.
 */
public class MenuModule extends ApiClient{

    /*
     * 获取菜单 信息
     *
     */
    public static MenuList getMenuList(AppContext appContext) throws AppException {

        try {

            return MenuList.parse(http_get(appContext, URLs.API_MENU_LIST));
        } catch (IOException e) {

            e.printStackTrace();
            throw AppException.network(e);
        }
    }
}
