package org.csu.csumall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;

@Data
public class Shipping {
    //主键自增
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private String receiverName;

    private String receiverPhone;

    private String receiverMobile;

    private String receiverProvince;

    private String receiverCity;

    private String receiverDistrict;

    private String receiverAddress;

    private String receiverZip;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
