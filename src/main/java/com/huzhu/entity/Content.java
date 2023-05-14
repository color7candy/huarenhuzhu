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
public class Content implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer contentid;

    private String content;

    private LocalDateTime contentTime;

    private String contentType;

    private String contentImage;

    private Integer userid;

    private String contentImagearray;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getContentid() {
        return contentid;
    }

    public void setContentid(Integer contentid) {
        this.contentid = contentid;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public LocalDateTime getContentTime() {
        return contentTime;
    }

    public void setContentTime(LocalDateTime contentTime) {
        this.contentTime = contentTime;
    }
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    public String getContentImage() {
        return contentImage;
    }

    public void setContentImage(String contentImage) {
        this.contentImage = contentImage;
    }
    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
    public String getContentImagearray() {
        return contentImagearray;
    }

    public void setContentImagearray(String contentImagearray) {
        this.contentImagearray = contentImagearray;
    }

    @Override
    public String toString() {
        return "Content{" +
            "id=" + id +
            ", contentid=" + contentid +
            ", content=" + content +
            ", contentTime=" + contentTime +
            ", contentType=" + contentType +
            ", contentImage=" + contentImage +
            ", userid=" + userid +
            ", contentImagearray=" + contentImagearray +
        "}";
    }
}
