package cn.yueying0083.pediyforum.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.yueying0083.pediyforum.R;
import cn.yueying0083.pediyforum.model.ForumPosts;
import cn.yueying0083.pediyforum.utils.Constant;
import cn.yueying0083.pediyforum.utils.ImageDisplay;

/**
 * Created by luoj@huoli.com on 2016/11/23.
 */

public class ForumPostsAdapter extends BaseAdapter {

    private Context mContext;
    private List<ForumPosts> mList;

    public ForumPostsAdapter(Context context, List<ForumPosts> list) {
        mContext = context;
        mList = list;
    }

    public void setSource(List<ForumPosts> source) {
        mList = source;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public ForumPosts getItem(int position) {
        return position > -1 && position < getCount() ? mList.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        ViewHolder vh;
        if (v == null || v.getTag() == null || !(v.getTag() instanceof ViewHolder)) {
            v = LayoutInflater.from(mContext).inflate(R.layout.content_forum_post, parent, false);
            vh = new ViewHolder();
            vh.userHead = (ImageView) v.findViewById(R.id.iv_user_head);
            vh.username = (TextView) v.findViewById(R.id.tv_username);

            vh.title = (TextView) v.findViewById(R.id.tv_posts_title);

            vh.globalSticky = v.findViewById(R.id.tv_global_sticky);
            vh.sticky = v.findViewById(R.id.tv_sticky);
            vh.goodness = (TextView) v.findViewById(R.id.tv_goodness);
            vh.viewCounts = (TextView) v.findViewById(R.id.tv_view_counts);
            vh.replyCounts = (TextView) v.findViewById(R.id.tv_reply_counts);
        } else {
            vh = (ViewHolder) v.getTag();
        }

        ForumPosts posts = getItem(position);

        if (posts.getAuthorAvatar() > 0) {
            ImageDisplay.display(vh.userHead, String.format(Constant.Url.USER_HEAD_URL, posts.getAuthorId(), posts.getAuthorAvatarLine()));
        } else {
            vh.userHead.setImageDrawable(mContext.getResources().getDrawable(android.R.drawable.sym_def_app_icon));
        }
        vh.username.setText(posts.getAuthorName());
        vh.title.setText(Html.fromHtml(posts.getTitle()));
        vh.globalSticky.setVisibility(posts.getGlobalSticky() == -1 ? View.VISIBLE : View.GONE);
        vh.sticky.setVisibility(posts.getSticky() == 1 ? View.VISIBLE : View.GONE);
        if (posts.getGoodness() >= 10) {
            vh.goodness.setVisibility(View.VISIBLE);
            vh.goodness.setText(posts.getGoodnessText(String.valueOf(posts.getGoodness())));
        } else {
            vh.goodness.setVisibility(View.GONE);
        }

        vh.viewCounts.setText(mContext.getString(R.string.view_counts, posts.getViewCounts()));
        vh.replyCounts.setText(mContext.getString(R.string.reply_counts, posts.getReplyCounts()));

        return v;
    }

    private static class ViewHolder {
        ImageView userHead;
        TextView username;

        TextView title;

        View globalSticky;
        View sticky;
        TextView goodness;
        TextView viewCounts;
        TextView replyCounts;

    }
}
