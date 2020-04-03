package com.example.baidu.retrofit.Bean.home;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author
 * @date 2020/4/3.
 * GitHub：
 * email：
 * description：
 */
public class BannerBean implements Parcelable {

    private String desc;
    private int id;
    private String imagePath;
    private int isvisible;
    private int order;
    private String title;
    private int type;
    private String url;


    public BannerBean() {
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagepath() {
        return imagePath;
    }

    public void setImagepath(String imagepath) {
        this.imagePath = imagepath;
    }

    public int getIsvisible() {
        return isvisible;
    }

    public void setIsvisible(int isvisible) {
        this.isvisible = isvisible;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static Creator<BannerBean> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.desc);
        dest.writeInt(this.id);
        dest.writeString(this.imagePath);
        dest.writeInt(this.isvisible);
        dest.writeInt(this.order);
        dest.writeString(this.title);
        dest.writeInt(this.type);
        dest.writeString(this.url);
    }

    protected BannerBean(Parcel in) {
        this.desc = in.readString();
        this.id = in.readInt();
        this.imagePath = in.readString();
        this.isvisible = in.readInt();
        this.order = in.readInt();
        this.title = in.readString();
        this.type = in.readInt();
        this.url = in.readString();
    }

    public static final Creator<BannerBean> CREATOR = new Creator<BannerBean>() {
        @Override
        public BannerBean createFromParcel(Parcel source) {
            return new BannerBean(source);
        }

        @Override
        public BannerBean[] newArray(int size) {
            return new BannerBean[size];
        }
    };
}
