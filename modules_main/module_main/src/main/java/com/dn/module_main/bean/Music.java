package com.dn.module_main.bean;

public class Music {

    String musicName;
    String singerName;
    String url;
    String img;

    /**
     * 必须提供无参构造方法，
     * 否则没法动态创建对象设置属性值
     */
    public Music() {
    }

    public Music(String musicName, String singerName) {
        this.musicName = musicName;
        this.singerName = singerName;
    }

    public Music(String musicName, String singerName, String img) {
        this.musicName = musicName;
        this.singerName = singerName;
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Music{" +
                "musicName='" + musicName + '\'' +
                ", singerName='" + singerName + '\'' +
                ", url='" + url + '\'' +
                ", img='" + img + '\'' +
                '}';
    }

}