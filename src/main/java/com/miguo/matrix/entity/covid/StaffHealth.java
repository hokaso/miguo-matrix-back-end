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
@ApiModel("东城员工身体状况")
@Table(name = "east_staff_health")
public class StaffHealth extends BaseEntity {

    @Column(name = "east_id")
    @ApiModelProperty("员工表id")
    private String eastId;

    @Column(name = "staff_temp")
    @ApiModelProperty("体温")
    private String staffTemp;

    @Column(name = "staff_status")
    @ApiModelProperty("状况")
    private String staffStatus;

    @Column(name = "staff_place")
    @ApiModelProperty("工作地")
    private String staffPlace;

}
