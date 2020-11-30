package com.example.lindroidcode.greendaoatoz.beans;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class AudioBean {

    @Id(autoincrement = true)
    private Long _id;
    private String localPath;

    @Convert(columnType = String.class, converter = LongConverter.class)
    private List<Long> marks;

    @Generated(hash = 501469822)
    public AudioBean(Long _id, String localPath, List<Long> marks) {
        this._id = _id;
        this.localPath = localPath;
        this.marks = marks;
    }

    @Generated(hash = 1628963493)
    public AudioBean() {
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getLocalPath() {
        return this.localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public List<Long> getMarks() {
        return this.marks;
    }

    public void setMarks(List<Long> marks) {
        this.marks = marks;
    }

    @Override
    public String toString() {
        return "AudioBean{" +
                "_id=" + _id +
                ", localPath='" + localPath + '\'' +
                ", marks=" + marks +
                '}';
    }
}
