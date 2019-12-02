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
 * 功能描述：小程序轮播图
 *
 * @author Hocassian
 * @date 2019-11-28 09:15
 */
@Slf4j
@Entity
@Accessors(chain = true)
@ApiModel("小程序轮播图实体")
@Table(name = "vote_swipers")
public class Swiper extends BaseEntity {

    @Column(name = "swiper_name")
    @ApiModelProperty("轮播图名称")
    private String swiperName;

    @Column(name = "swiper_pic")
    @ApiModelProperty("轮播图地址")
    private String swiperPic;
}
