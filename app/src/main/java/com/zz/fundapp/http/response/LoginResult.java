package com.zz.fundapp.http.response;

public class LoginResult {
    //{
    //	"stateCode":"",
    //	"msg":"",
    //	"data":
    //	{
    //		"info":
    //		{
    //			"nickname":"",
    //			"sexCode":"",
    //			"birthday":"",
    //			"height":"",
    //			"weight":"",
    //			"address":"",
    //			"portraitUrl":"",
    //			"country": "",
    //			"industries": "",
    //			"job": "",
    //			"fullName": ""
    //		},
    //		"userid":"402860815dcfc52d015dcfd525720001",
    //		"token":"eyJhbGciOiJIUzI1NiJ9.eyJ1c2Vybm..."
    //	}
    //}
    private String userid;
    private String token;
    private info info;

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

    public LoginResult.info getInfo() {
        return info;
    }

    public void setInfo(LoginResult.info info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "LoginResult{" +
                "userid='" + userid + '\'' +
                ", token='" + token + '\'' +
                ", info=" + info +
                '}';
    }

    public class info{
        private String nickname;
        private String sexCode;
        private String birthday;
        private int height;
        private String weight;
        private String address;
        private String portraitUrl;
        private String country;
        private String industries;
        private String job;
        private String fullName;

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

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        @Override
        public String toString() {
            return "info{" +
                    "nickname='" + nickname + '\'' +
                    ", sexCode='" + sexCode + '\'' +
                    ", birthday='" + birthday + '\'' +
                    ", height=" + height +
                    ", weight='" + weight + '\'' +
                    ", address='" + address + '\'' +
                    ", portraitUrl='" + portraitUrl + '\'' +
                    ", country='" + country + '\'' +
                    ", industries='" + industries + '\'' +
                    ", job='" + job + '\'' +
                    ", fullName='" + fullName + '\'' +
                    '}';
        }
    }

}
