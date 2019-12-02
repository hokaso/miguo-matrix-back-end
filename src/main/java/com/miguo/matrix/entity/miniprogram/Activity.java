package com.miguo.matrix.entity.miniprogram;

import com.miguo.matrix.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 功能描述：
 *
 * @author Hocassian
 * @date 2019-11-28 10:13
 */
@Slf4j
@Entity
@Accessors(chain = true)
@ApiModel("小程序活动实体")
@Table(name = "vote_activities")
public class Activity extends BaseEntity {

    @Column(name = "activity_name")
    @ApiModelProperty("活动名称")
    private String activityName;

    @Column(name = "activity_profile")
    @ApiModelProperty("活动简介")
    private String activityProfile;
}
