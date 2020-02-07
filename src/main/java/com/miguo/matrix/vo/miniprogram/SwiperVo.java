package com.miguo.matrix.vo.miniprogram;

import com.miguo.matrix.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Table;

/**
 * 功能描述：小程序轮播图
 *
 * @author Hocassian
 * @date 2019-11-28 09:15
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
@Accessors(chain = true)
@ApiModel("小程序轮播图Vo实体")
public class SwiperVo extends BaseEntity {

    @ApiModelProperty("轮播图名称")
    private String swiperName;

    @ApiModelProperty("轮播图地址")
    private String swiperPic;
}
