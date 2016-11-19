package cn.yueying0083.pediyforum.utils;

/**
 * Created by luoj@huoli.com on 2016/11/19.
 */

public class NumberUtils {

    public static String timeSec(){
        long time = System.currentTimeMillis();
        return String.valueOf(time).substring(0, 10);
    }
}
