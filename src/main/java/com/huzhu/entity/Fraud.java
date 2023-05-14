package com.huzhu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author user
 * @since 2023-05-07
 */
public class Fraud implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userid;

    private String fraudType;

    private String title;

    private LocalDateTime fraudTime;

    private String fraudMethod;

    private String fraudName;

    private String address;

    private String contact;

    private BigDecimal amount;

    private String imagepath;

    private String description;

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
    public String getFraudType() {
        return fraudType;
    }

    public void setFraudType(String fraudType) {
        this.fraudType = fraudType;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public LocalDateTime getFraudTime() {
        return fraudTime;
    }

    public void setFraudTime(LocalDateTime fraudTime) {
        this.fraudTime = fraudTime;
    }
    public String getFraudMethod() {
        return fraudMethod;
    }

    public void setFraudMethod(String fraudMethod) {
        this.fraudMethod = fraudMethod;
    }
    public String getFraudName() {
        return fraudName;
    }

    public void setFraudName(String fraudName) {
        this.fraudName = fraudName;
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
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        return "Fraud{" +
            "id=" + id +
            ", userid=" + userid +
            ", fraudType=" + fraudType +
            ", title=" + title +
            ", fraudTime=" + fraudTime +
            ", fraudMethod=" + fraudMethod +
            ", fraudName=" + fraudName +
            ", address=" + address +
            ", contact=" + contact +
            ", amount=" + amount +
            ", imagepath=" + imagepath +
            ", description=" + description +
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
