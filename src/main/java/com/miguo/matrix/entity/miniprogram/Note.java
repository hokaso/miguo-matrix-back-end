package com.miguo.matrix.entity.miniprogram;

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
@Entity
@Accessors(chain = true)
@ApiModel("小程序公告栏实体")
@Table(name = "vote_notes")
public class Note extends BaseEntity {

    @Column(name = "activity_id")
    @ApiModelProperty("活动id")
    private String activityId;

    @Column(name = "note_name")
    @ApiModelProperty("公告项目名称")
    private String noteName;

    @Column(name = "note_display_time")
    @ApiModelProperty("公告展示时间")
    //    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date noteDisplayTime;

    @Column(name = "note_details")
    @ApiModelProperty("公告正文")
    private String noteDetails;
}
