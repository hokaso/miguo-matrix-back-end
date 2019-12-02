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
 * @date 2019-11-28 10:21
 */
@Slf4j
@Entity
@Accessors(chain = true)
@ApiModel("小程序赞助商实体")
@Table(name = "vote_merchants")
public class Merchant extends BaseEntity {

    @Column(name = "activity_id")
    @ApiModelProperty("活动id")
    private String activityId;

    @Column(name = "merchant_name")
    @ApiModelProperty("赞助商名称")
    private String merchantName;

    @Column(name = "merchant_info")
    @ApiModelProperty("赞助商信息")
    private String merchantInfo;

    @Column(name = "merchant_logo")
    @ApiModelProperty("赞助商图标（地址）")
    private String merchantLogo;
}
