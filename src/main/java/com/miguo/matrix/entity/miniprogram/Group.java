package com.miguo.matrix.entity.miniprogram;

import com.miguo.matrix.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import lombok.Data;

import javax.persistence.*;

/**
 * 功能描述：
 *
 * @author Hocassian
 * @date 2019-11-28 11:41
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
@Entity
@Accessors(chain = true)
@ApiModel("小程序投票对象实体")
@Table(name = "vote_groups")
public class Group extends BaseEntity {

    @Column(name = "activity_id")
    @ApiModelProperty("活动id")
    private String activityId;

    @Column(name = "group_name")
    @ApiModelProperty("组名")
    private String groupName;

    @Column(name = "group_profile")
    @ApiModelProperty("简介")
    private String groupProfile;

    @Column(name = "group_votes")
    @ApiModelProperty("获票数")
    private Integer groupVotes;

    @Column(name = "group_pic")
    @ApiModelProperty("缩略图")
    private String groupPic;

    @Column(name = "group_pic_hd")
    @ApiModelProperty("高清图")
    private String groupPicHd;
}
