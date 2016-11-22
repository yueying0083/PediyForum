package cn.yueying0083.pediyforum.manager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.loopj.android.http.PersistentCookieStore;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.yueying0083.pediyforum.http.HttpClientFactory;
import cn.yueying0083.pediyforum.model.UserModel;
import cn.yueying0083.pediyforum.utils.Constant;
import cn.yueying0083.pediyforum.utils.SPHelper;
import cz.msebera.android.httpclient.client.protocol.HttpClientContext;
import cz.msebera.android.httpclient.cookie.Cookie;

/**
 * Created by luoj@huoli.com on 2016/11/22.
 */

public class UserManager {

    private static UserManager instance = new UserManager();

    private UserManager() {

    }

    public static synchronized UserManager getInstance() {
        return instance;
    }

    public UserModel getCurrentUser(Context context) {
        Object obj = SPHelper.getSerializableObj(context, Constant.Sp.UserInfo.FILE_NAME, Constant.Sp.UserInfo.FIELD_USER_MODEL);
        if (obj != null && obj instanceof UserModel) {
            return (UserModel) obj;
        }
        return null;
    }

    public void loginSucc(Context context, UserModel um) {
        if (um != null) {
            SPHelper.setSerializableObj(context, Constant.Sp.UserInfo.FILE_NAME, Constant.Sp.UserInfo.FIELD_USER_MODEL, um);
        } else {
            SPHelper.clearFile(context, Constant.Sp.UserInfo.FILE_NAME);
        }
        EventBus.getDefault().post(um);
    }

    public boolean isUserLogin(Context context) {
        UserModel um = getCurrentUser(context);
        if (um != null) {
            try {
                Object obj = HttpClientFactory.getClient(context).getHttpContext().getAttribute(HttpClientContext.COOKIE_STORE);
                if (obj != null && obj instanceof PersistentCookieStore) {
                    PersistentCookieStore pcs = (PersistentCookieStore) obj;
                    List<Cookie> list = pcs.getCookies();
                    if (list != null && !list.isEmpty()) {
                        for (Cookie c : list) {
                            if (c != null && TextUtils.equals(c.getDomain(), "bbs.pediy.com") && TextUtils.equals(c.getName(), "bbuserid")) {
                                if (TextUtils.equals(c.getValue(), um.getId())) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }


}
