package com.huzhu.vo;


import com.huzhu.entity.Resort;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ResortVo extends Resort {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Integer page = 1;
    private Integer limit = 10;
}
