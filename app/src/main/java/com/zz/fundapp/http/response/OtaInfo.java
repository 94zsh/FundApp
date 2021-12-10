package com.zz.fundapp.http.response;


public class OtaInfo {
    String upgradeName;
    String upgradeUrl;
    String explains;
    String explainsEN;
    String version;
    String deviceName;
    int deviceId;
    String md5;

    public OtaInfo() {
    }

    public OtaInfo(String upgradeName, String upgradeUrl, String explains, String explainsEN, String version, String deviceName, int deviceId, String md5) {
        this.upgradeName = upgradeName;
        this.upgradeUrl = upgradeUrl;
        this.explains = explains;
        this.explainsEN = explainsEN;
        this.version = version;
        this.deviceName = deviceName;
        this.deviceId = deviceId;
        this.md5 = md5;
    }

    public String getUpgradeName() {
        return upgradeName;
    }

    public void setUpgradeName(String upgradeName) {
        this.upgradeName = upgradeName;
    }

    public String getUpgradeUrl() {
        return upgradeUrl;
    }

    public void setUpgradeUrl(String upgradeUrl) {
        this.upgradeUrl = upgradeUrl;
    }

    public String getExplains() {
        return explains;
    }

    public void setExplains(String explains) {
        this.explains = explains;
    }

    public String getExplainsEN() {
        return explainsEN;
    }

    public void setExplainsEN(String explainsEN) {
        this.explainsEN = explainsEN;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    @Override
    public String toString() {
        return "OtaInfo{" +
                "upgradeName='" + upgradeName + '\'' +
                ", upgradeUrl='" + upgradeUrl + '\'' +
                ", explains='" + explains + '\'' +
                ", explainsEN='" + explainsEN + '\'' +
                ", version='" + version + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", deviceId=" + deviceId +
                ", md5='" + md5 + '\'' +
                '}';
    }
}
