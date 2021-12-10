package com.zz.fundapp.http.request;

import android.content.Context;

import java.util.Calendar;
import java.util.Locale;


/**
 * Created by 方奕峰 on 14-6-17.
 */
public class RequestEntity {
    public static Sysdata sysdata;
    private Object rqdata;

    public Sysdata getSysdata() {
        return sysdata;
    }

    public void setSysdata(Sysdata sysdata) {
        this.sysdata = sysdata;
    }

    public Object getRqdata() {
        return rqdata;
    }

    public void setRqdata(Object rqdata) {
        this.rqdata = rqdata;
    }

    public static class Sysdata {
        private String protocolType;
        private String appVer;
        private String deviceVer;
        private String blueVer;
        private String deviceId;
        private String mobileType;
        private String systemVer;
        private String systemLang;
        private String sendDate;
        private String timeZone;
        public static String manufCode = "001";

        public String getProtocolType() {
            return protocolType;
        }

        public void setProtocolType(String protocolType) {
            this.protocolType = protocolType;
        }

        public String getAppVer() {
            return appVer;
        }

        public void setAppVer(String appVer) {
            this.appVer = appVer;
        }

        public String getDeviceVer() {
            return deviceVer;
        }

        public void setDeviceVer(String deviceVer) {
            this.deviceVer = deviceVer;
        }

        public String getBlueVer() {
            return blueVer;
        }

        public void setBlueVer(String blueVer) {
            this.blueVer = blueVer;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getMobileType() {
            return mobileType;
        }

        public void setMobileType(String mobileType) {
            this.mobileType = mobileType;
        }

        public String getSystemVer() {
            return systemVer;
        }

        public void setSystemVer(String systemVer) {
            this.systemVer = systemVer;
        }

        public String getSystemLang() {
            return systemLang;
        }

        public void setSystemLang(String systemLang) {
            this.systemLang = systemLang;
        }

        public String getSendDate() {
            return sendDate;
        }

        public void setSendDate(String sendDate) {
            this.sendDate = sendDate;
        }

        public String getManufCode() {
            return manufCode;
        }

        public void setManufCode(String manufCode) {
            this.manufCode = manufCode;
        }

        public String getTimeZone() {
            return timeZone;
        }

        public void setTimeZone(String timeZone) {
            this.timeZone = timeZone;
        }
    }

    public static final int PROTOCOL_TYPE_UMIDIGI = 3000;
    public static final int PROTOCOL_TYPE_LENOVO_LIFE = 2000;
    public static final int PROTOCOL_TYPE_LENOVO_HEALTHY = 1000;
    public static Sysdata generateSysData(Context context) {
        sysdata = new Sysdata();
        sysdata.setProtocolType(""+PROTOCOL_TYPE_UMIDIGI);//此字段用于标记
//        sysdata.setAppVer(AppUtil.getVerName(context));
//        BindDevice mBindDevice = null;
//        UserInfo mUserInfo = HyUserUtil.loginUser;
//        if (mUserInfo != null) {
//            mBindDevice = mUserInfo.getBindDevice();
//        }
//        if (mBindDevice != null) {
//            sysdata.setDeviceVer(mBindDevice.getDeviceVersion().getUpdateCode() + "");
//            sysdata.setBlueVer(mBindDevice.getDeviceVersion().getViewVersions());
//        }
//        TelephonyManager tm = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
//        String deviceID = "";
//        try {
//            deviceID = tm.getDeviceId();
//        } catch (Exception e) {
//            HyLog.e("tm.getDeviceId() error = " + e);
//        }
        sysdata.setDeviceId("");
        sysdata.setMobileType(android.os.Build.MODEL);
        sysdata.setSystemVer(android.os.Build.VERSION.RELEASE);
        sysdata.setTimeZone(Calendar.getInstance().getTimeZone().getID());
        String language = Locale.getDefault().getLanguage();
        sysdata.setSystemLang(language);
//        sysdata.setSendDate(SystemContant.timeFormat1E.format(new Date()));
        return sysdata;
    }

    public static int getBtDevMarksCode(Context context) {
        return 0;
    }
}
