package com.miguo.matrix.vo.miniprogram;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.miguo.matrix.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * 功能描述：
 *
 * @author Hocassian
 * @date 2019-11-28 10:25
 */
@EqualsAndHashCode(callSuper = true)
@Slf4j
@Data
@Accessors(chain = true)
@ApiModel("小程序公告栏Vo实体")
public class NoteVo extends BaseVo {

    @ApiModelProperty("活动id")
    private String activityId;

    @ApiModelProperty("公告项目名称")
    private String noteName;

    @ApiModelProperty("公告展示时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date noteDisplayTime;

    @ApiModelProperty("公告正文")
    private String noteDetails;
}
