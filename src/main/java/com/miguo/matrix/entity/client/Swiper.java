package com.miguo.matrix.entity.client;

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
 * @date 2019-11-28 11:50
 */
@Slf4j
@Entity
@Accessors(chain = true)
@ApiModel("客户端轮播图实体")
@Table(name = "client_swiper")
public class Swiper extends BaseEntity {

    @Column(name = "swiper_pic")
    @ApiModelProperty("轮播图地址")
    private String swiperPic;

    @Column(name = "swiper_name")
    @ApiModelProperty("轮播图名称")
    private String swiperName;
}
