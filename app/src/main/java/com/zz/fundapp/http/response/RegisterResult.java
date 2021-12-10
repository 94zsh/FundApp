package com.zz.fundapp.http.response;

public class RegisterResult {
    private String userid;
    private String token;
    private String portraitUrl;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPortraitUrl() {
        return portraitUrl;
    }

    public void setPortraitUrl(String portraitUrl) {
        this.portraitUrl = portraitUrl;
    }

    @Override
    public String toString() {
        return "RegisterResult{" +
                "userid='" + userid + '\'' +
                ", token='" + token + '\'' +
                ", portraitUrl='" + portraitUrl + '\'' +
                '}';
    }
}
