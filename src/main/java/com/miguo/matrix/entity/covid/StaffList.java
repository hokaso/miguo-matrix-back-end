package com.miguo.matrix.entity.covid;

import com.miguo.matrix.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 功能描述：
 *
 * @author Hocassian
 * @date 2020-02-14 11:28
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
@Entity
@Accessors(chain = true)
@ApiModel("东城员工列表")
@Table(name = "east_staff_list")
public class StaffList extends BaseEntity {
    @Column(name = "east_name")
    @ApiModelProperty("姓名")
    private String eastName;

    @Column(name = "east_live")
    @ApiModelProperty("住哪")
    private String eastLive;

    @Column(name = "east_phone")
    @ApiModelProperty("手机号")
    private String eastPhone;

    @Column(name = "east_card")
    @ApiModelProperty("身份证号")
    private String eastCard;

    @Column(name = "east_vehicle")
    @ApiModelProperty("身份证号")
    private String eastVehicle;
}
