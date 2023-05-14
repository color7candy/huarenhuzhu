package com.huzhu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author user
 * @since 2023-05-07
 */
public class Liveorfraud implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String liveorfraud;

    private String type;

    private String title;

    private String description;

    private String precaution;

    private String imagepath;

    private String status;

    private Integer userid;

    private String imagearray;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getLiveorfraud() {
        return liveorfraud;
    }

    public void setLiveorfraud(String liveorfraud) {
        this.liveorfraud = liveorfraud;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getPrecaution() {
        return precaution;
    }

    public void setPrecaution(String precaution) {
        this.precaution = precaution;
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
    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
    public String getImagearray() {
        return imagearray;
    }

    public void setImagearray(String imagearray) {
        this.imagearray = imagearray;
    }

    @Override
    public String toString() {
        return "Liveorfraud{" +
            "id=" + id +
            ", liveorfraud=" + liveorfraud +
            ", type=" + type +
            ", title=" + title +
            ", description=" + description +
            ", precaution=" + precaution +
            ", imagepath=" + imagepath +
            ", status=" + status +
            ", userid=" + userid +
            ", imagearray=" + imagearray +
        "}";
    }
}
