package com.huzhu.vo;


import com.huzhu.entity.Liveorfraud;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class LiveorfraudVo extends Liveorfraud {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Integer page = 1;
    private Integer limit = 10;
}
