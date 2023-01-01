package com.mike.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: 23236
 * @createTime: 2022/12/28   17:19
 * @description:
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RoleInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private String id;

    private String roleName;

    private String roleCode;

    private String roleRemark;

    private Integer activeStatus;

    private LocalDateTime createTime;


}