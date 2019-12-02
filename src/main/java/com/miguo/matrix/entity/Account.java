package com.miguo.matrix.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 功能描述：员工账户信息
 *
 * @author Hocassian
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
@Entity
@Accessors(chain = true)
@ApiModel("员工实体")
@Table(name = "com_staff")
public class Account extends BaseEntity{

    @Column(name = "password")
    @ApiModelProperty("密码")
    private String password;

    @Column(name = "name")
    @ApiModelProperty("姓名")
    private String name;

    @Column(name = "nickname")
    @ApiModelProperty("昵称")
    private String nickname;

    @Column(name = "level")
    @ApiModelProperty("权限等级")
    private String level;
}
