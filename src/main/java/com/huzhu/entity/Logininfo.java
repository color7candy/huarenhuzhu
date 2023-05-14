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
 * @since 2023-04-05
 */
public class Logininfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userid;

    private String nickname;

    private String loginip;

    private LocalDateTime logintime;

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
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getLoginip() {
        return loginip;
    }

    public void setLoginip(String loginip) {
        this.loginip = loginip;
    }
    public LocalDateTime getLogintime() {
        return logintime;
    }

    public void setLogintime(LocalDateTime logintime) {
        this.logintime = logintime;
    }

    @Override
    public String toString() {
        return "Logininfo{" +
            "id=" + id +
            ", userid=" + userid +
            ", nickname=" + nickname +
            ", loginip=" + loginip +
            ", logintime=" + logintime +
        "}";
    }
}
