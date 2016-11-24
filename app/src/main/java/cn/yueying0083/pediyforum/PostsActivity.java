package cn.yueying0083.pediyforum;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.yueying0083.pediyforum.http.HttpClientFactory;
import cn.yueying0083.pediyforum.http.URLImageGetter;
import cn.yueying0083.pediyforum.model.ForumPosts;
import cn.yueying0083.pediyforum.model.response.ForumPostsDetail;
import cn.yueying0083.pediyforum.utils.Constant;
import cn.yueying0083.pediyforum.utils.ImageDisplay;
import cn.yueying0083.pediyforum.views.CircleImageView;
import cz.msebera.android.httpclient.Header;

/**
 * Created by luoj@huoli.com on 2016/11/18.
 */

public class PostsActivity extends BaseActivity {

    public static final String INTENT_SER_POSTS = "PostsActivity.INTENT_SER_POSTS";

    @BindView(R.id.ll_content)
    View mContentView;
    @BindView(R.id.iv_user_head)
    CircleImageView mUserHeadImageView;
    @BindView(R.id.tv_author_name)
    TextView mUsernameTextView;
    @BindView(R.id.tv_posts_time)
    TextView mPostsTimeTextView;

    @BindView(R.id.tv_view_counts)
    TextView mViewCountsTextView;
    @BindView(R.id.tv_reply_counts)
    TextView mReplyCountsTextView;
    @BindView(R.id.tv_goodness)
    TextView mGoodnessTextView;

    @BindView(R.id.tv_posts_title)
    TextView mPostsTitleTextView;
    @BindView(R.id.tv_posts_content)
    TextView mPostsContentTextView;

    private ForumPosts mForumPosts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_posts);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        Object obj = getIntent().getSerializableExtra(INTENT_SER_POSTS);
        if (obj == null || !(obj instanceof ForumPosts)) {
            return;
        }

        mForumPosts = (ForumPosts) obj;
        invalidate();
    }

    private void invalidate() {
        if (mForumPosts.getAuthorAvatar() == 1) {
            ImageDisplay.display(mUserHeadImageView, String.format(Constant.Url.USER_HEAD_URL, mForumPosts.getAuthorId(), mForumPosts.getAuthorAvatarLine()));
        }
        mPostsTitleTextView.setText(mForumPosts.getTitle());

        mUsernameTextView.setText(mForumPosts.getAuthorName());
        mPostsTimeTextView.setText(mForumPosts.getLastPostDate());
        mViewCountsTextView.setText(getString(R.string.view_counts, mForumPosts.getViewCounts()));
        mReplyCountsTextView.setText(getString(R.string.reply_counts, mForumPosts.getReplyCounts()));

        if (mForumPosts.getGoodness() >= 10) {
            mGoodnessTextView.setVisibility(View.VISIBLE);
            mGoodnessTextView.setText(mForumPosts.getGoodnessText(String.valueOf(mForumPosts.getGoodness())));
        } else {
            mGoodnessTextView.setVisibility(View.GONE);
        }

        getDetailList();
    }

    public void getDetailList() {
        AsyncHttpClient client = HttpClientFactory.getClient(getSelfContext());
        String url = String.format("http://bbs.pediy.com/showthread.php?t=%s?styleid=12", mForumPosts.getId());
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        String res = new String(responseBody);
                        if (res.startsWith("{")) {
                            res = res.substring(res.indexOf("{"));
                        }
                        ForumPostsDetail detail = new Gson().fromJson(res, ForumPostsDetail.class);
                        List<ForumPostsDetail.Detail> list = detail.getDetailList();
                        if (list == null || list.isEmpty()) {
                            return;
                        }
                        String content = list.get(0).getPostsContent();
                        if (content.endsWith("...")) {
                            getContentDetail(String.valueOf(list.get(0).getPostsId()));
                        } else {
                            mPostsContentTextView.setText(Html.fromHtml(content, new URLImageGetter(mPostsContentTextView), null));
                        }
                    } catch (Exception e) {
                        Snackbar.make(mContentView, R.string.parse_error, Snackbar.LENGTH_SHORT).show();
                        // TODO umeng log upload
                    }
                } else {
                    Snackbar.make(mContentView, R.string.network_down, Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Snackbar.make(mContentView, R.string.network_down, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void getContentDetail(String id) {
        AsyncHttpClient client = HttpClientFactory.getClient(getSelfContext());

        String url = String.format("http://bbs.pediy.com/showpost.php?styleid=12&p=%s", id);
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                mPostsContentTextView.setText(Html.fromHtml(new String(responseBody), new URLImageGetter(mPostsContentTextView), null));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            }
        });

    }
}
