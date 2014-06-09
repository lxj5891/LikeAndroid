package com.starwall.like.bean;

import java.io.Serializable;

/**
 * 接口URL实体类
 *
 * @author Antony
 * @version 1.0
 * @created 2012-3-21
 */
public class URLs implements Serializable {

    public final static String HOST = "10.2.3.246:3000";

    public final static String HTTP = "http://";

    private final static String URL_SPLITTER = "/";

    private final static String URL_API_HOST = HTTP + HOST + URL_SPLITTER;

    public final static String LOGIN_VALIDATE_HTTP = HTTP + HOST + URL_SPLITTER + "login/user.json";
    public final static String API_DESK_LIST = HTTP + HOST + URL_SPLITTER + "desk/list.json";
    public final static String API_MENU_LIST = HTTP + HOST + URL_SPLITTER + "menu/list.json";

}
