package cn.yueying0083.pediyforum.manager;

import android.content.Context;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.greenrobot.eventbus.EventBus;

import cn.yueying0083.pediyforum.R;
import cn.yueying0083.pediyforum.http.HttpClientFactory;
import cn.yueying0083.pediyforum.model.response.ForumPostsList;
import cn.yueying0083.pediyforum.utils.Constant;
import cn.yueying0083.pediyforum.utils.SPHelper;
import cz.msebera.android.httpclient.Header;

/**
 * 获取版块列表
 * <p>
 * Created by luoj@huoli.com on 2016/11/23.
 */

public class ForumPostsManager implements Constant.Sp.HotTopic {

    private static ForumPostsManager mInstance;

    private ForumPostsManager() {

    }

    public static synchronized ForumPostsManager getInstance() {
        if (mInstance == null) {
            mInstance = new ForumPostsManager();
        }
        return mInstance;
    }


    public void getFirst(final Context context, String id) {
        AsyncHttpClient client = HttpClientFactory.getClient(context);

        String url = String.format("http://bbs.pediy.com/forumdisplay.php?styleid=12&f=%s&page=1", id);
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        ForumPostsList list = new Gson().fromJson(new String(responseBody), ForumPostsList.class);
                        list.setCode(1);
                        SPHelper.setSerializableObj(context, FILE_NAME, FIELD_FIRST_PAGE_DISPLAY, list);
                        EventBus.getDefault().post(list);
                    } catch (Exception e) {
                        e.printStackTrace();
                        ForumPostsList list = new ForumPostsList();
                        list.setCode(-2);
                        list.setMessage(context.getString(R.string.parse_error));
                        EventBus.getDefault().post(list);
                        // TODO umeng log upload
                    }
                } else {
                    ForumPostsList list = new ForumPostsList();
                    list.setCode(-1);
                    list.setMessage(context.getString(R.string.network_down));
                    EventBus.getDefault().post(list);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                ForumPostsList list = new ForumPostsList();
                list.setCode(-1);
                list.setMessage(context.getString(R.string.network_down));
                EventBus.getDefault().post(list);
            }
        });
    }


}
