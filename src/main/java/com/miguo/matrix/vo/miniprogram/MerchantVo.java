package com.miguo.matrix.vo.miniprogram;

import com.miguo.matrix.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

/**
 * 功能描述：
 *
 * @author Hocassian
 * @date 2019-11-28 10:21
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
@Accessors(chain = true)
@ApiModel("小程序赞助商Vo实体")
public class MerchantVo extends BaseVo {

    @ApiModelProperty("活动id")
    private String activityId;

    @ApiModelProperty("赞助商名称")
    private String merchantName;

    @ApiModelProperty("赞助商信息")
    private String merchantInfo;

    @ApiModelProperty("赞助商图标（链接地址）")
    private String merchantLogo;
}
