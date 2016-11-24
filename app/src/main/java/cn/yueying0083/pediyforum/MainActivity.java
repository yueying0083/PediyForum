package cn.yueying0083.pediyforum;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.yueying0083.pediyforum.adapter.ForumPostsAdapter;
import cn.yueying0083.pediyforum.manager.ForumPostsManager;
import cn.yueying0083.pediyforum.manager.UserManager;
import cn.yueying0083.pediyforum.model.UserModel;
import cn.yueying0083.pediyforum.model.response.ForumPostsList;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.cl_root)
    View mContentView;
    @BindView(R.id.lv_posts)
    ListView mPostsListView;

    private TextView mUsernameTextView;
    private TextView mRankTextView;

    private UserManager mUserManager;
    private ForumPostsManager mForumPostsManager;

    private ForumPostsAdapter mForumPostsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        mUserManager = UserManager.getInstance();
        mForumPostsManager = ForumPostsManager.getInstance();

        initDrawer();
        initNav();
        initData();

        EventBus.getDefault().register(this);
    }

    private void initDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void initNav() {
        mNavigationView.setNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        View headView = mNavigationView.getHeaderView(0);
        mUsernameTextView = (TextView) headView.findViewById(R.id.tv_username);
        mRankTextView = (TextView) headView.findViewById(R.id.tv_rank);

        onEvent(mUserManager.getCurrentUser(getSelfContext()));

        headView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUserManager.isUserLogin(getSelfContext())) {
                    startActivity(new Intent(getSelfContext(), UserCenterActivity.class));
                } else {
                    Intent intent = new Intent(getSelfContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void initData() {
        mForumPostsAdapter = new ForumPostsAdapter(getSelfContext(), null);
        mPostsListView.setAdapter(mForumPostsAdapter);
        mForumPostsManager.getFirst(getSelfContext(), "161");
        mPostsListView.setOnItemClickListener(mOnItemClickListener);
    }

    @Subscribe
    public void onEvent(UserModel userModel) {
        if (userModel != null) {
            mUsernameTextView.setText(userModel.getUsername());
            mRankTextView.setText(userModel.getRank());
        } else {
            mUsernameTextView.setText(R.string.click_login);
            mRankTextView.setText("");
        }
    }

    @Subscribe
    public void onEvent(ForumPostsList list) {
        if (list.getCode() == 1) {
            mForumPostsAdapter.setSource(list.getPostsList());
        } else {
            Snackbar.make(mContentView, list.getMessage(), Snackbar.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


        }
    };

    private AbsListView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getSelfContext(), PostsActivity.class);
            intent.putExtra(PostsActivity.INTENT_SER_POSTS, mForumPostsAdapter.getItem(position));
            startActivity(intent);
        }
    };

    private NavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {

        @SuppressWarnings("StatementWithEmptyBody")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // Handle navigation view item clicks here.
            switch (item.getItemId()) {
                case R.id.nav_about:
                    break;
            }

            if (mDrawerLayout != null) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
            return true;
        }
    };


}
