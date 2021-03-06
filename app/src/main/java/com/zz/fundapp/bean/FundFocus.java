package com.zz.fundapp.bean;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

//@Entity
@Entity(tableName = "f_focus")
public class FundFocus {
//    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @PrimaryKey(autoGenerate = true)//主键是否自动增长，默认为false
    @NonNull
    private Long id;
    String account;
    String code;
    String name;
    String jzrq;
    String dwjz;
    String gsz;
    String gszzl;//估算涨跌值
    String gztime;
    long timestamp;
    int dayChange = 0;//变化日期
    Float dayChangeValue = 0f;//变化日期幅度

    @Ignore
    public FundFocus() {
    }

    @Ignore
    public FundFocus(Long id, String account, String code, String name, String jzrq, String dwjz, String gsz, String gszzl, String gztime, long timestamp) {
        this.id = id;
        this.account = account;
        this.code = code;
        this.name = name;
        this.jzrq = jzrq;
        this.dwjz = dwjz;
        this.gsz = gsz;
        this.gszzl = gszzl;
        this.gztime = gztime;
        this.timestamp = timestamp;
    }

    public FundFocus(String account, String code, String name, String jzrq, String dwjz, String gsz, String gszzl, String gztime, long timestamp, int dayChange, Float dayChangeValue) {
        this.account = account;
        this.code = code;
        this.name = name;
        this.jzrq = jzrq;
        this.dwjz = dwjz;
        this.gsz = gsz;
        this.gszzl = gszzl;
        this.gztime = gztime;
        this.timestamp = timestamp;
        this.dayChange = dayChange;
        this.dayChangeValue = dayChangeValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJzrq() {
        return jzrq;
    }

    public void setJzrq(String jzrq) {
        this.jzrq = jzrq;
    }

    public String getDwjz() {
        return dwjz;
    }

    public void setDwjz(String dwjz) {
        this.dwjz = dwjz;
    }

    public String getGsz() {
        return gsz;
    }

    public void setGsz(String gsz) {
        this.gsz = gsz;
    }

    public String getGszzl() {
        return gszzl;
    }

    public void setGszzl(String gszzl) {
        this.gszzl = gszzl;
    }

    public String getGztime() {
        return gztime;
    }

    public void setGztime(String gztime) {
        this.gztime = gztime;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getDayChange() {
        return dayChange;
    }

    public void setDayChange(int dayChange) {
        this.dayChange = dayChange;
    }

    public Float getDayChangeValue() {
        return dayChangeValue;
    }

    public void setDayChangeValue(Float dayChangeValue) {
        this.dayChangeValue = dayChangeValue;
    }

    @Override
    public String toString() {
        return "FundFocus{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", jzrq='" + jzrq + '\'' +
                ", dwjz='" + dwjz + '\'' +
                ", gsz='" + gsz + '\'' +
                ", gszzl='" + gszzl + '\'' +
                ", gztime='" + gztime + '\'' +
                ", timestamp=" + timestamp +
                ", dayChange=" + dayChange +
                ", dayChangeValue=" + dayChangeValue +
                '}';
    }
}
