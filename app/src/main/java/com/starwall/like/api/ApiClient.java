package com.starwall.like.api;

import com.starwall.like.AppContext;
import com.starwall.like.AppException;
import com.starwall.like.bean.URLs;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ApiClient {

    public static final String UTF_8 = "UTF-8";

    private final static int TIMEOUT_SOCKET = 20000;
    private final static int RETRY_TIME = 3;

    private final static int HTTP_OK = 200;

    private static String appCookie;
    private static String appUserAgent;

    private static String getCookie(AppContext appContext) {

        if (appCookie == null || appCookie.length() == 0) {
            appCookie = appContext.getProperty("cookie");
        }
        return appCookie;
    }

    private static String getUserAgent(AppContext appContext) {

        if (appUserAgent == null || appUserAgent.length() == 0) {
            StringBuilder ua = new StringBuilder("starwall.org");
            ua.append('#' + appContext.getPackageInfo().versionName + '_' + appContext.getPackageInfo().versionCode);//App版本
            ua.append("#Android");//手机系统平台
            ua.append("#" + android.os.Build.VERSION.RELEASE);//手机系统版本
            ua.append("#" + android.os.Build.MODEL); //手机型号
            ua.append("#" + appContext.getAppId());//客户端唯一标识
            appUserAgent = ua.toString();
        }
        return appUserAgent.toString();
    }

    public static void cleanCookie(AppContext appContext) {

        appCookie = "";
        appContext.setProperty("cookie","");
    }

    private static DefaultHttpClient getHttpClient() {

        return new DefaultHttpClient();
    }


    private static HttpGet getHttpGet(String url, String cookie, String userAgent) {

        HttpGet httpGet = new HttpGet(url);

        // 设置 请求超时时间
        httpGet.getParams().setIntParameter("http.socket.timeout", TIMEOUT_SOCKET);
        httpGet.setHeader("Host", URLs.HOST);
        httpGet.setHeader("Connection", "Keep-Alive");
        httpGet.setHeader("Cookie", cookie);
        httpGet.setHeader("User-Agent", userAgent);
        return httpGet;
    }

    private static HttpPost getHttpPost(String url, String cookie, String userAgent) {

        HttpPost httpPost = new HttpPost(url);

        // 设置 请求超时时间
        httpPost.getParams().setIntParameter("http.socket.timeout", TIMEOUT_SOCKET);
        httpPost.setHeader("Host", URLs.HOST);
        httpPost.setHeader("Connection", "Keep-Alive");
        httpPost.setHeader("Cookie", cookie);
        httpPost.setHeader("User-Agent", userAgent);
        return httpPost;
    }

    private static String _MakeURL(String p_url, Map<String, Object> params) {

        StringBuilder url = new StringBuilder(p_url);
        if (url.indexOf("?") < 0)
            url.append('?');

        for (String name : params.keySet()) {
            url.append('&');
            url.append(name);
            url.append('=');
            url.append(String.valueOf(params.get(name)));
        }

        return url.toString().replace("?&", "?");
    }

    /**
     * get请求URL
     *
     * @param url
     * @throws AppException
     */
    protected static InputStream http_get(AppContext appContext, String url) throws AppException {

        String cookie = getCookie(appContext);
        String userAgent = getUserAgent(appContext);

        DefaultHttpClient httpClient = null;
        HttpGet httpGet = null;

        InputStream inputStream = null;
        int statusCode = 0;
        int time = 0;

        do {
            try {
                httpClient = getHttpClient();
                httpGet = getHttpGet(url, cookie, userAgent);

                HttpResponse httpResponse = httpClient.execute(httpGet);
                statusCode = httpResponse.getStatusLine().getStatusCode();

                if (statusCode != HTTP_OK) {

                    throw AppException.http(statusCode);
                }

                List<org.apache.http.cookie.Cookie> cookies = httpClient.getCookieStore().getCookies();
                String tmpcookies = "";

                for (org.apache.http.cookie.Cookie ck : cookies) {

                    tmpcookies += ck.getName() + "=" + ck.getValue() + ";";
                    tmpcookies += "smartcore.sid=" + ck.getValue() + ";";
                }

                //保存cookie
                if (appContext != null && tmpcookies != "") {

                    appContext.setProperty("cookie", tmpcookies);
                    appCookie = tmpcookies;
                }


                inputStream = httpResponse.getEntity().getContent();
                break;

            } catch (ClientProtocolException e) {

                time++;

                if (time < RETRY_TIME) {

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                    }
                    continue;
                }
                // 发生致命的异常，可能是协议不对或者返回的内容有问题
                e.printStackTrace();
                throw AppException.http(e);
            } catch (IOException e) {

                time++;
                if (time < RETRY_TIME) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                    }
                    continue;
                }
                // 发生网络异常
                e.printStackTrace();
                throw AppException.network(e);
            } finally {
                // 释放连接
                httpGet = null;
                httpClient = null;
            }
        } while (time < RETRY_TIME);

        return inputStream;
    }


    /**
     * 公用post方法
     *
     * @param url
     * @param params
     * @throws AppException
     */
    protected static InputStream _post(AppContext appContext, String url, Map<String, Object> params) throws AppException {

        String cookie = getCookie(appContext);
        String userAgent = getUserAgent(appContext);

        DefaultHttpClient httpClient = null;
        HttpPost httpPost = null;
        InputStream inputStream = null;

        List<org.apache.http.NameValuePair> list = new ArrayList<org.apache.http.NameValuePair>();

        if (params != null) {

            for (String name : params.keySet()) {
                org.apache.http.NameValuePair pair = new BasicNameValuePair(name, String.valueOf(params.get(name)));
                list.add(pair);
            }
        }

        int time = 0;
        do {
            try {

                httpClient = getHttpClient();
                httpPost = getHttpPost(url, cookie, userAgent);

                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, UTF_8);
                httpPost.setEntity(entity);

                HttpResponse response = httpClient.execute(httpPost);

                int statusCode = response.getStatusLine().getStatusCode();

                if (statusCode != HTTP_OK) {

                    throw AppException.http(statusCode);
                } else if (statusCode == HTTP_OK) {

                    List<org.apache.http.cookie.Cookie> cookies = httpClient.getCookieStore().getCookies();
                    String tmpcookies = "";

                    for (org.apache.http.cookie.Cookie ck : cookies) {

                        tmpcookies += ck.getName() + "=" + ck.getValue() + ";";
                        tmpcookies += "smartcore.sid=" + ck.getValue() + ";";
                    }

                    //保存cookie
                    if (appContext != null && tmpcookies != "") {

                        appContext.setProperty("cookie", tmpcookies);
                        appCookie = tmpcookies;
                    }
                }

                inputStream = response.getEntity().getContent();
                break;

            } catch (ClientProtocolException e) {
                time++;
                if (time < RETRY_TIME) {

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                    }
                    continue;
                }
                // 发生致命的异常，可能是协议不对或者返回的内容有问题
                e.printStackTrace();
                throw AppException.http(e);
            } catch (IOException e) {

                time++;
                if (time < RETRY_TIME) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                    }
                    continue;
                }
                // 发生网络异常
                e.printStackTrace();
                throw AppException.network(e);
            } finally {
                // 释放连接

                httpPost = null;
                httpClient = null;
            }
        } while (time < RETRY_TIME);

        return inputStream;
    }
}
