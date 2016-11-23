package cn.yueying0083.pediyforum.utils;

/**
 * Created by luoj@huoli.com on 2016/11/18.
 */

public class Constant {

    public interface App {
        public static final boolean DEBUG = true;

        public static final String UA = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:48.0) Gecko/20100101 Firefox/48.0";
    }

    public interface Url{
        public static final String USER_HEAD_URL = "http://bbs.pediy.com/image.php?u=%s&dateline=%s";
    }

    public interface Sp {

        public interface UserInfo {

            public static final String FILE_NAME = "user_info";

            public static final String FIELD_USER_MODEL = "user_model";
        }

        public interface HotTopic {
            public static final String FILE_NAME = "hot_topics";

            public static final String FIELD_FIRST_PAGE_DISPLAY = "first_page_display";
        }
    }

}
