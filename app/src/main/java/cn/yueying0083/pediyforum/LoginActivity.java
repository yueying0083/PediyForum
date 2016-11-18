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
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.yueying0083.pediyforum.http.HttpClientFactory;
import cn.yueying0083.pediyforum.model.response.LoginResponse;
import cn.yueying0083.pediyforum.utils.DialogUtils;
import cn.yueying0083.pediyforum.utils.EncryptUtils;
import cz.msebera.android.httpclient.Header;

public class LoginActivity extends BaseActivity {


    private MaterialDialog mLoginDialog;

    @BindView(R.id.email)
    AutoCompleteTextView mUsernameTextView;
    @BindView(R.id.password)
    EditText mPasswordTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mPasswordTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

    }

    @OnClick(R.id.email_sign_in_button)
    void attemptLogin() {
        mUsernameTextView.setError(null);
        mPasswordTextView.setError(null);

        String username = mUsernameTextView.getText().toString();
        String password = mPasswordTextView.getText().toString();

        if (TextUtils.isEmpty(username)) {
            mUsernameTextView.setError(getString(R.string.username_cannot_be_null));
            mUsernameTextView.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            mPasswordTextView.setError(getString(R.string.password_cannot_be_null));
            mPasswordTextView.requestFocus();
            return;
        }

        String passwordMD5 = EncryptUtils.md5(password);
        showProgress(true);

        AsyncHttpClient client = HttpClientFactory.getClient(getSelfContext());
        RequestParams params = new RequestParams();
        params.put("account", username);
        params.put("password", passwordMD5.toLowerCase());
        client.addHeader("Host", "passport.kanxue.com");
        client.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:48.0) Gecko/20100101 Firefox/48.0");
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
                    showProgress(false);
                    DialogUtils.showMessageDialog(getSelfContext(), R.string.notice, R.string.login_error, R.string.confirm);
                    // TODO statistic, upload to umeng
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

                getUserInfo(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                showProgress(false);
                DialogUtils.showMessageDialog(getSelfContext(), R.string.notice, R.string.network_down, R.string.confirm);
            }
        });
    }

    /**
     * 获取用户信息
     *
     * @param response
     */
    private void getUserInfo(LoginResponse response) {
        // TODO login succ, try get user info
    }

    private void showProgress(final boolean show) {
        if (mLoginDialog == null) {
            mLoginDialog = DialogUtils.buildLoadingDialog(getSelfContext(), R.string.action_sign_in, R.string.now_sing_in);
        }

        if (show) {
            mLoginDialog.show();
        } else {
            mLoginDialog.dismiss();
        }
    }
}

