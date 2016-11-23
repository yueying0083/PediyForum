package cn.yueying0083.pediyforum.manager;

import android.content.Context;

import cn.yueying0083.pediyforum.model.response.BaseResponseModel;

/**
 * Created by luoj@huoli.com on 2016/11/23.
 */

public abstract class AbsManager<T extends BaseResponseModel> {


    AbsManager() {

    }

    /**
     * 更新数据
     */
    protected abstract void update(Context context);


}
