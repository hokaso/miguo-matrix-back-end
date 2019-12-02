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
 * @date 2019-11-28 10:35
 */
@EqualsAndHashCode(callSuper = true)
@Slf4j
@Data
@Entity
@Accessors(chain = true)
@ApiModel("小程序投票记录实体")
@Table(name = "vote_records")
public class Record extends BaseEntity {

    @Column(name = "activity_id")
    @ApiModelProperty("活动id")
    private String activityId;

    @Column(name = "record_nickname")
    @ApiModelProperty("用户昵称")
    private String recordNickname;

    @Column(name = "record_openid")
    @ApiModelProperty("用户openid")
    private String recordOpenid;

    @Column(name = "record_vote")
    @ApiModelProperty("投票对象")
    private String recordVote;

    @Column(name = "record_date")
    @ApiModelProperty("投票时间")
    //    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date recordDate;
}
