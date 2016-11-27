package com.notonly.calendar.domain;

import java.io.Serializable;

/**
 * 蒲公英更新检测
 * Created by wangzhen on 2016/11/27.
 */

public class UpdateBean implements Serializable {

    /**
     * code : 0
     * message :
     * data : {"lastBuild":"1","downloadURL":"http://qiniu-app-cdn.pgyer.com/5badb228734725b50c27a4cecad56bc6.apk?e=1480213060&attname=app-release.apk&token=6fYeQ7_TVB5L0QSzosNFfw2HU8eJhAirMF5VxV9G:pf0hY-qiMqTGobSGvUl3wYw5qvw=","versionCode":"104","versionName":"1.0.4","appUrl":"http://www.pgyer.com/IQQV","build":"1","releaseNote":""}
     */

    private int code;
    private String message;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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

        private String lastBuild;
        private String downloadURL;
        private String versionCode;
        private String versionName;
        private String appUrl;
        private String build;
        private String releaseNote;

        public String getLastBuild() {
            return lastBuild;
        }

        public void setLastBuild(String lastBuild) {
            this.lastBuild = lastBuild;
        }

        public String getDownloadURL() {
            return downloadURL;
        }

        public void setDownloadURL(String downloadURL) {
            this.downloadURL = downloadURL;
        }

        public String getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(String versionCode) {
            this.versionCode = versionCode;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public String getAppUrl() {
            return appUrl;
        }

        public void setAppUrl(String appUrl) {
            this.appUrl = appUrl;
        }

        public String getBuild() {
            return build;
        }

        public void setBuild(String build) {
            this.build = build;
        }

        public String getReleaseNote() {
            return releaseNote;
        }

        public void setReleaseNote(String releaseNote) {
            this.releaseNote = releaseNote;
        }
    }
}
