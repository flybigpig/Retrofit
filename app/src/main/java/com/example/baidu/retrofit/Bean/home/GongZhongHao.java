package com.example.baidu.retrofit.Bean.home;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @date 2020/4/3.
 * GitHub：
 * email：
 * description：
 */
public class GongZhongHao implements Parcelable {

    private List<String> children = new ArrayList<>();
    private int courseid;
    private int id;
    private String name;
    private int order;
    private int parentchapterid;
    private boolean usercontrolsettop;
    private int visible;


    public void setChildren(List<String> children) {
        this.children = children;
    }

    public List<String> getChildren() {
        return children;
    }


    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }

    public int getCourseid() {
        return courseid;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public void setOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }


    public void setParentchapterid(int parentchapterid) {
        this.parentchapterid = parentchapterid;
    }

    public int getParentchapterid() {
        return parentchapterid;
    }


    public void setUsercontrolsettop(boolean usercontrolsettop) {
        this.usercontrolsettop = usercontrolsettop;
    }

    public boolean getUsercontrolsettop() {
        return usercontrolsettop;
    }


    public void setVisible(int visible) {
        this.visible = visible;
    }

    public int getVisible() {
        return visible;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(this.children);
        dest.writeInt(this.courseid);
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.order);
        dest.writeInt(this.parentchapterid);
        dest.writeByte(this.usercontrolsettop ? (byte) 1 : (byte) 0);
        dest.writeInt(this.visible);
    }

    public GongZhongHao() {
    }

    protected GongZhongHao(Parcel in) {
        this.children = in.createStringArrayList();
        this.courseid = in.readInt();
        this.id = in.readInt();
        this.name = in.readString();
        this.order = in.readInt();
        this.parentchapterid = in.readInt();
        this.usercontrolsettop = in.readByte() != 0;
        this.visible = in.readInt();
    }

    public static final Parcelable.Creator<GongZhongHao> CREATOR = new Parcelable.Creator<GongZhongHao>() {
        @Override
        public GongZhongHao createFromParcel(Parcel source) {
            return new GongZhongHao(source);
        }

        @Override
        public GongZhongHao[] newArray(int size) {
            return new GongZhongHao[size];
        }
    };
}
