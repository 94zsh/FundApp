package com.zz.fundapp.bean;

/**
 * Created by dean on 2017/8/31.
 */

public class ScoreEntity {

    public String name;
    public String code;
    public int mathematics;
    public int language;
    public int english;
    public int physics;
    public int biology;
    public int chemistry;

    public ScoreEntity(String name, String code, int mathematics, int language, int english, int physics, int biology, int chemistry) {
        this.name = name;
        this.code = code;
        this.mathematics = mathematics;
        this.language = language;
        this.english = english;
        this.physics = physics;
        this.biology = biology;
        this.chemistry = chemistry;
    }
}
