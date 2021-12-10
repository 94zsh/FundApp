package com.zz.fundapp.http.response;

public class UserinfoService {
    String userid;
    String nickname;
    String sexCode;
    String birthday;
    int height;
    String weight;
    String address;
    String portraitUrl;
    String token;
    String country;
    String industries;
    String job;
    long registrationTime;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSexCode() {
        return sexCode;
    }

    public void setSexCode(String sexCode) {
        this.sexCode = sexCode;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPortraitUrl() {
        return portraitUrl;
    }

    public void setPortraitUrl(String portraitUrl) {
        this.portraitUrl = portraitUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIndustries() {
        return industries;
    }

    public void setIndustries(String industries) {
        this.industries = industries;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public long getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(long registrationTime) {
        this.registrationTime = registrationTime;
    }

    @Override
    public String toString() {
        return "UserinfoService{" +
                "userid='" + userid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", sexCode='" + sexCode + '\'' +
                ", birthday='" + birthday + '\'' +
                ", height=" + height +
                ", weight='" + weight + '\'' +
                ", address='" + address + '\'' +
                ", portraitUrl='" + portraitUrl + '\'' +
                ", token='" + token + '\'' +
                ", country='" + country + '\'' +
                ", industries='" + industries + '\'' +
                ", job='" + job + '\'' +
                ", registrationTime='" + registrationTime + '\'' +
                '}';
    }
}
