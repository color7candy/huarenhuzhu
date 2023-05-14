package com.huzhu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author user
 * @since 2023-05-07
 */
public class Resort implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userid;

    private String title;

    private String resortName;

    private String resortType;

    private LocalDateTime resortTime;

    private String description;

    private String address;

    private String contact;

    private String imagepath;

    private String status;

    private String contentcount;

    private String star1count;

    private String star2count;

    private String star3count;

    private String isstar;

    private String imagearray;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getResortName() {
        return resortName;
    }

    public void setResortName(String resortName) {
        this.resortName = resortName;
    }
    public String getResortType() {
        return resortType;
    }

    public void setResortType(String resortType) {
        this.resortType = resortType;
    }
    public LocalDateTime getResortTime() {
        return resortTime;
    }

    public void setResortTime(LocalDateTime resortTime) {
        this.resortTime = resortTime;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getContentcount() {
        return contentcount;
    }

    public void setContentcount(String contentcount) {
        this.contentcount = contentcount;
    }
    public String getStar1count() {
        return star1count;
    }

    public void setStar1count(String star1count) {
        this.star1count = star1count;
    }
    public String getStar2count() {
        return star2count;
    }

    public void setStar2count(String star2count) {
        this.star2count = star2count;
    }
    public String getStar3count() {
        return star3count;
    }

    public void setStar3count(String star3count) {
        this.star3count = star3count;
    }
    public String getIsstar() {
        return isstar;
    }

    public void setIsstar(String isstar) {
        this.isstar = isstar;
    }
    public String getImagearray() {
        return imagearray;
    }

    public void setImagearray(String imagearray) {
        this.imagearray = imagearray;
    }

    @Override
    public String toString() {
        return "Resort{" +
            "id=" + id +
            ", userid=" + userid +
            ", title=" + title +
            ", resortName=" + resortName +
            ", resortType=" + resortType +
            ", resortTime=" + resortTime +
            ", description=" + description +
            ", address=" + address +
            ", contact=" + contact +
            ", imagepath=" + imagepath +
            ", status=" + status +
            ", contentcount=" + contentcount +
            ", star1count=" + star1count +
            ", star2count=" + star2count +
            ", star3count=" + star3count +
            ", isstar=" + isstar +
            ", imagearray=" + imagearray +
        "}";
    }
}
