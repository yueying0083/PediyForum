package cn.yueying0083.pediyforum.http;

import android.content.Context;
import android.support.annotation.NonNull;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;

import cn.yueying0083.pediyforum.utils.Constant;
import cz.msebera.android.httpclient.client.protocol.ClientContext;
import cz.msebera.android.httpclient.protocol.HttpContext;

/**
 * Created by luoj@huoli.com on 2016/10/11.
 */
public class HttpClientFactory {

    private static AsyncHttpClient mCurrentHttpClient;

    public synchronized static AsyncHttpClient getClient(@NonNull Context context) {
        return getClient(context, false);
    }

    public synchronized static AsyncHttpClient getClient(@NonNull Context context, boolean clearCookieStore) {
        if (mCurrentHttpClient == null) {
            mCurrentHttpClient = new AsyncHttpClient(true, 80, 443);
            if (Constant.App.DEBUG) mCurrentHttpClient.setProxy("192.168.100.159", 8888);
            PersistentCookieStore cookieStore = new PersistentCookieStore(context);
            mCurrentHttpClient.setCookieStore(cookieStore);
        }

        if (clearCookieStore) {
            clearCookies(mCurrentHttpClient);
            mCurrentHttpClient.removeAllHeaders();
        }
        return mCurrentHttpClient;
    }

    private static void clearCookies(@NonNull AsyncHttpClient client) {
        HttpContext hc = client.getHttpContext();
        if (hc != null) {
            Object obj = hc.getAttribute(ClientContext.COOKIE_STORE);
            if (obj != null && obj instanceof PersistentCookieStore) {
                PersistentCookieStore pcs = (PersistentCookieStore) obj;
                pcs.clear();
            }
        }
    }


}
