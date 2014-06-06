package com.starwall.like.api;

import com.starwall.like.AppContext;
import com.starwall.like.AppException;
import com.starwall.like.bean.DeskList;
import com.starwall.like.bean.URLs;
import org.json.JSONException;

import java.io.IOException;

/**
 * Created by Antony on 14-6-5.
 */
public class DeskModule  extends ApiClient{


    /*
     * 获取桌台 信息
     *
     */
    public static DeskList getDeskList(AppContext appContext) throws AppException {

        try {

            return DeskList.parse(http_get(appContext, URLs.API_DESK_LIST));
        } catch (IOException e) {

            e.printStackTrace();
            throw AppException.network(e);
        } catch (JSONException e) {

            e.printStackTrace();
            throw AppException.network(e);
        }
    }
}
