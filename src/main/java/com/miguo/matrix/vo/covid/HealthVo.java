package com.miguo.matrix.vo.covid;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * 功能描述：
 *
 * @author Hocassian
 * @date 2019-11-28 11:41
 */
@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel("小程序投票对象Vo实体")
public class HealthVo {

    private String id;

    private String createBy;

    private String updateBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateAt;

    private Boolean isDel = Boolean.FALSE;

    @ApiModelProperty("员工表id")
    private String eastId;

    @ApiModelProperty("体温")
    private String staffTemp;

    @ApiModelProperty("状况")
    private String staffStatus;

    @ApiModelProperty("工作地")
    private String staffPlace;

    @ApiModelProperty("员工名称")
    private String eastName;

}
