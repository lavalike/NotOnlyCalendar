package com.notonly.calendar.domain;

import java.io.Serializable;

/**
 * 蒲公英更新检测
 * Created by wangzhen on 2016/11/27.
 */

public class UpdateBean implements Serializable {
    public int code;
    public String message;
    public DataBean data;

    public static class DataBean implements Serializable {
        /**
         * lastBuild : 1
         * downloadURL : http://qiniu-app-cdn.pgyer.com/5badb228734725b50c27a4cecad56bc6.apk?e=1480213060&attname=app-release.apk&token=6fYeQ7_TVB5L0QSzosNFfw2HU8eJhAirMF5VxV9G:pf0hY-qiMqTGobSGvUl3wYw5qvw=
         * versionCode : 104
         * versionName : 1.0.4
         * appUrl : http://www.pgyer.com/IQQV
         * build : 1
         * releaseNote :
         */

        public String lastBuild;
        public String downloadURL;
        public String versionCode;
        public String versionName;
        public String appUrl;
        public String build;
        public String releaseNote;
    }
}
