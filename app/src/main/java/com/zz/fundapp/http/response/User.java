package com.zz.fundapp.http.response;


//登陆用户
public class User {
    private Long id;
    private String userName = "";//账号
    private String token = "";
    private String userId = "";
    private String localPortrait = "";//本地头像 图片路径
    private String servicePortrait = "";//服务器头像 网址链接
    private String fullName = "";
    private String nickName = "";
    private String email = "";
    private String birthday = "631123200";//时间戳 秒

    public User() {
    }

    public User(String userName, String token, String userId,
                String localPortrait, String servicePortrait, String fullName,
                String nickName, String email, String birthday) {
        this.userName = userName;
        this.token = token;
        this.userId = userId;
        this.localPortrait = localPortrait;
        this.servicePortrait = servicePortrait;
        this.fullName = fullName;
        this.nickName = nickName;
        this.email = email;
        this.birthday = birthday;
    }

    public User(Long id, String userName, String token, String userId,
                String localPortrait, String servicePortrait, String fullName,
                String nickName, String email, String birthday) {
        this.id = id;
        this.userName = userName;
        this.token = token;
        this.userId = userId;
        this.localPortrait = localPortrait;
        this.servicePortrait = servicePortrait;
        this.fullName = fullName;
        this.nickName = nickName;
        this.email = email;
        this.birthday = birthday;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLocalPortrait() {
        return this.localPortrait;
    }

    public void setLocalPortrait(String localPortrait) {
        this.localPortrait = localPortrait;
    }

    public String getServicePortrait() {
        return this.servicePortrait;
    }

    public void setServicePortrait(String servicePortrait) {
        this.servicePortrait = servicePortrait;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", token='" + token + '\'' +
                ", userId='" + userId + '\'' +
                ", localPortrait='" + localPortrait + '\'' +
                ", servicePortrait='" + servicePortrait + '\'' +
                ", fullName='" + fullName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", email='" + email + '\'' +
                ", birthday='" + birthday + '\'' +
                '}';
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
