package com.miguo.matrix.entity.client;

import com.fasterxml.jackson.annotation.JsonFormat;
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
 * @date 2019-11-28 11:53
 */
@EqualsAndHashCode(callSuper = true)
@Slf4j
@Data
@Entity
@Accessors(chain = true)
@ApiModel("视频实体")
@Table(name = "client_video")
public class Video extends BaseEntity {

    @Column(name = "video_url")
    @ApiModelProperty("视频链接")
    private String videoUrl;

    @Column(name = "video_title")
    @ApiModelProperty("视频标题")
    private String videoTitle;

    @Column(name = "video_profile")
    @ApiModelProperty("视频简介")
    private String videoProfile;

    @Column(name = "video_pic")
    @ApiModelProperty("视频封面")
    private String videoPic;

    @Column(name = "video_date")
    @ApiModelProperty("创作日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date videoDate;
}
