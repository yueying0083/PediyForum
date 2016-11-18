package cn.yueying0083.pediyforum;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by luoj@huoli.com on 2016/11/18.
 * <p>
 * 所有activity的公共父类，处理公共事件
 */

public class BaseActivity extends AppCompatActivity {


    public AppCompatActivity getSelfContext() {
        return this;
    }

}
