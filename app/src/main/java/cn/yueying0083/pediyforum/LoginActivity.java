package cn.yueying0083.pediyforum;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.greenrobot.eventbus.EventBus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.yueying0083.pediyforum.http.HttpClientFactory;
import cn.yueying0083.pediyforum.manager.UserManager;
import cn.yueying0083.pediyforum.model.UserModel;
import cn.yueying0083.pediyforum.model.response.LoginResponse;
import cn.yueying0083.pediyforum.utils.Constant;
import cn.yueying0083.pediyforum.utils.DialogUtils;
import cn.yueying0083.pediyforum.utils.EncryptUtils;
import cn.yueying0083.pediyforum.utils.NumberUtils;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.protocol.HttpClientContext;
import cz.msebera.android.httpclient.cookie.Cookie;

public class LoginActivity extends BaseActivity {


    private MaterialDialog mLoginDialog;

    @BindView(R.id.email)
    AutoCompleteTextView mUsernameTextView;
    @BindView(R.id.password)
    EditText mPasswordTextView;

    private boolean mLoginStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mPasswordTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    if (!mLoginStarted) {
                        attemptLogin();
                    }
                    return true;
                }
                return false;
            }
        });

    }

    @OnClick(R.id.email_sign_in_button)
    void attemptLogin() {
        mLoginStarted = true;
        mUsernameTextView.setError(null);
        mPasswordTextView.setError(null);

        String username = mUsernameTextView.getText().toString();
        String password = mPasswordTextView.getText().toString();

        if (TextUtils.isEmpty(username)) {
            mUsernameTextView.setError(getString(R.string.username_cannot_be_null));
            mUsernameTextView.requestFocus();
            mLoginStarted = false;
            return;
        }

        if (TextUtils.isEmpty(password)) {
            mPasswordTextView.setError(getString(R.string.password_cannot_be_null));
            mPasswordTextView.requestFocus();
            mLoginStarted = false;
            return;
        }

        String passwordMD5 = EncryptUtils.md5(password);
        showProgress(true);

        AsyncHttpClient client = HttpClientFactory.getClient(getSelfContext());
        // start login, clear cookie
        HttpClientFactory.clearCookies(client);
        RequestParams params = new RequestParams();
        params.put("account", username);
        params.put("password", passwordMD5.toLowerCase());
        client.addHeader("Host", "passport.kanxue.com");
        client.addHeader("User-Agent", Constant.App.UA);
        client.addHeader("X-Requested-With", "XMLHttpRequest");
        client.post("http://passport.kanxue.com/user-login.htm", params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    Thread.sleep(TimeUnit.SECONDS.toMillis(1));
                } catch (InterruptedException e) {
                }
                String rtn = new String(responseBody);
                LoginResponse response = null;
                try {
                    response = new Gson().fromJson(rtn, LoginResponse.class);
                } catch (Exception e) {
                    doLoginFailed(0x01);
                    return;
                }

                if (response == null) {
                    showProgress(false);
                    mPasswordTextView.setError(getString(R.string.password_cannot_be_null));
                    mPasswordTextView.requestFocus();
                    return;
                }

                if (response.getCode() != 0) {
                    showProgress(false);
                    DialogUtils.showMessageDialog(getSelfContext(), getString(R.string.notice), response.getMessage(), getString(R.string.confirm));
                    return;
                }

                doLogin(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                showProgress(false);
                DialogUtils.showMessageDialog(getSelfContext(), R.string.notice, R.string.network_down, R.string.confirm);
            }
        });
    }

    /**
     * jump from kanxue.com, continue to login pediy.com
     * <p>
     * 获取用户信息
     *
     * @param response
     */
    private void doLogin(LoginResponse response) {
        final AsyncHttpClient client = HttpClientFactory.getClient(getSelfContext());

        client.addHeader("Host", "bbs.pediy.com");
        client.addHeader("Referer", "http://passport.kanxue.com/user-login.htm");
        String url = String.format("http://bbs.pediy.com/ucenter/user.php?action=synlogin&token=%s&time=", response.getMessage(), NumberUtils.timeSec());
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Object obj = client.getHttpContext().getAttribute(HttpClientContext.COOKIE_STORE);
                if (obj != null && obj instanceof PersistentCookieStore) {
                    PersistentCookieStore pcs = (PersistentCookieStore) obj;

                    List<Cookie> list = pcs.getCookies();
                    if (list != null && !list.isEmpty()) {
                        for (Cookie c : list) {
                            if (c != null && TextUtils.equals(c.getDomain(), "bbs.pediy.com") && TextUtils.equals(c.getName(), "bbuserid")) {
                                // login succ
                                doGetUserInfo(client, c.getValue());
                                return;
                            }
                        }
                    }

                }

                doLoginFailed(0x02);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                doLoginFailed(0x03);
            }
        });
    }

    private void doGetUserInfo(AsyncHttpClient client, final String userId) {
        String url = String.format("http://bbs.pediy.com/member.php?u=%s&styleid=12", userId);
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    doLoginSucc(new String(responseBody));
                } else {
                    doLoginFailed(0x04);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                doLoginFailed(0x05);
            }
        });

    }

    private void doLoginSucc(String html) {
        try {
            Type t = new TypeToken<List<UserModel>>() {
            }.getType();

            List<UserModel> list = new Gson().fromJson(html, t);
            if (list == null || list.isEmpty()) {
                doLoginFailed(0x06);
                return;
            }
            UserManager.getInstance().loginSucc(getSelfContext(), list.get(0));
            showProgress(false);
            finish();
        } catch (Exception e) {
            doLoginFailed(0x07);
        }
    }

    private void doLoginFailed(int type) {
        showProgress(false);
        DialogUtils.showMessageDialog(getSelfContext(), R.string.notice, R.string.login_error, R.string.confirm);
        // TODO statistic, upload to umeng
    }

    private void showProgress(final boolean show) {
        if (mLoginDialog == null) {
            mLoginDialog = DialogUtils.buildLoadingDialog(getSelfContext(), R.string.action_sign_in, R.string.now_sing_in);
        }

        if (show) {
            mLoginDialog.show();
        } else {
            mLoginStarted = false;
            mLoginDialog.dismiss();
        }
    }
}

