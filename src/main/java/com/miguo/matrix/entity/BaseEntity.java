package com.miguo.matrix.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Hocassian
 */
@Slf4j
@Data
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Id
    @Column(name = "id")
    private String id;

    @ApiModelProperty("创建人员")
    @Column(name = "create_by")
    private String createBy;

    @ApiModelProperty("更新人员")
    @Column(name = "update_by")
    private String updateBy;

    @ApiModelProperty("创建时间")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "create_at")
    private Date createAt;

    @ApiModelProperty("更新时间")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "update_at")
    private Date updateAt;

    @ApiModelProperty("是否删除")
    @Column(name = "is_del")
    private Boolean isDel = Boolean.FALSE;

}
