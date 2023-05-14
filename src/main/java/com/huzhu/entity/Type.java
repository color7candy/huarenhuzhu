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
 * @since 2023-04-09
 */
public class Type implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String type;

    private String resortorfraud;

    private String available;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getResortorfraud() {
        return resortorfraud;
    }

    public void setResortorfraud(String resortorfraud) {
        this.resortorfraud = resortorfraud;
    }
    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Type{" +
            "id=" + id +
            ", type=" + type +
            ", resortorfraud=" + resortorfraud +
            ", available=" + available +
        "}";
    }
}
