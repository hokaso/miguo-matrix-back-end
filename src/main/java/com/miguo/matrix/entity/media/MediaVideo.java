package com.miguo.matrix.entity.media;

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
@Table(name = "media_video")
public class MediaVideo extends BaseEntity {

    @Column(name = "video_url")
    @ApiModelProperty("视频链接")
    private String videoUrl;

    @Column(name = "video_cid")
    @ApiModelProperty("视频cid")
    private String videoCid;

    @Column(name = "video_title")
    @ApiModelProperty("视频标题")
    private String videoTitle;

    @Column(name = "video_profile")
    @ApiModelProperty("视频简介")
    private String videoProfile;

    @Column(name = "video_pic")
    @ApiModelProperty("视频封面")
    private String videoPic;

    @Column(name = "video_author")
    @ApiModelProperty("创作团队")
    private String videoAuthor;

    @Column(name = "video_status")
    @ApiModelProperty("状态")
    private String videoStatus;

    @Column(name = "video_class")
    @ApiModelProperty("视频类别")
    private String videoClass;

    @Column(name = "video_tag")
    @ApiModelProperty("视频标签")
    private String videoTag;

    @Column(name = "video_reviewer")
    @ApiModelProperty("视频审核人")
    private String videoReviewer;

    @Column(name = "video_path")
    @ApiModelProperty("视频本地路径")
    private String videoPath;
}
