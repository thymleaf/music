package com.thymleaf.example.test;

import android.os.Parcel;
import android.os.Parcelable;

public class News implements Parcelable
{


    /**
     * newId : 2
     * title : 核心价值观
     * content : <p>我们贵州人都是好同志，积极培养社会主义核心价值观并践行。</p><p><img src="https://zhsf.obs.cn-east-3.myhuaweicloud.com:443/20200312%2F0d1ce0c2c1e04cc28e9d2542cc10c24f.png" title="1583987533220027575.png" alt="核心价值观banner_.png" width="535" height="181"/></p>
     * img : https://zhsf.obs.cn-east-3.myhuaweicloud.com:443/20200312%2Fc09bbe3388dd490ab6f1c9c0cac98e4b.png
     * newType : 2
     * newStatus : 1
     * orderNum : 0
     * comment :
     * createTime : 2020-03-12 12:33:26
     * onlineId : 1
     */

    private int newId;
    private String title;
    private String content;
    private String img;
    private int newType;
    private int newStatus;
    private int orderNum;
    private String comment;
    private String createTime;
    private int onlineId;

    public int getNewId() { return newId;}

    public void setNewId(int newId) { this.newId = newId;}

    public String getTitle() { return title;}

    public void setTitle(String title) { this.title = title;}

    public String getContent() { return content;}

    public void setContent(String content) { this.content = content;}

    public String getImg() { return img;}

    public void setImg(String img) { this.img = img;}

    public int getNewType() { return newType;}

    public void setNewType(int newType) { this.newType = newType;}

    public int getNewStatus() { return newStatus;}

    public void setNewStatus(int newStatus) { this.newStatus = newStatus;}

    public int getOrderNum() { return orderNum;}

    public void setOrderNum(int orderNum) { this.orderNum = orderNum;}

    public String getComment() { return comment;}

    public void setComment(String comment) { this.comment = comment;}

    public String getCreateTime() { return createTime;}

    public void setCreateTime(String createTime) { this.createTime = createTime;}

    public int getOnlineId() { return onlineId;}

    public void setOnlineId(int onlineId) { this.onlineId = onlineId;}

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(this.newId);
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeString(this.img);
        dest.writeInt(this.newType);
        dest.writeInt(this.newStatus);
        dest.writeInt(this.orderNum);
        dest.writeString(this.comment);
        dest.writeString(this.createTime);
        dest.writeInt(this.onlineId);
    }

    public News() {}

    protected News(Parcel in)
    {
        this.newId = in.readInt();
        this.title = in.readString();
        this.content = in.readString();
        this.img = in.readString();
        this.newType = in.readInt();
        this.newStatus = in.readInt();
        this.orderNum = in.readInt();
        this.comment = in.readString();
        this.createTime = in.readString();
        this.onlineId = in.readInt();
    }

    public static final Creator<News> CREATOR = new Creator<News>()
    {
        @Override
        public News createFromParcel(Parcel source) {return new News(source);}

        @Override
        public News[] newArray(int size) {return new News[size];}
    };
}
