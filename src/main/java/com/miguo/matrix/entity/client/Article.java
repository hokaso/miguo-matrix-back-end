package com.miguo.matrix.entity.client;

import com.miguo.matrix.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 功能描述：
 *
 * @author Hocassian
 * @date 2019-11-28 11:47
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
@Entity
@Accessors(chain = true)
@ApiModel("文章实体")
@Table(name = "client_article")
public class Article extends BaseEntity {

    @Column(name = "article")
    @ApiModelProperty("正文")
    private String article;

    @Column(name = "title")
    @ApiModelProperty("标题")
    private String title;

    @Column(name = "author")
    @ApiModelProperty("作者")
    private String author;

    @Column(name = "reviewer")
    @ApiModelProperty("审核人")
    private String reviewer;

    @Column(name = "readings")
    @ApiModelProperty("阅读量")
    private Integer readings;

    @Column(name = "status")
    @ApiModelProperty("状态")
    private String status;

    @Column(name = "pic")
    @ApiModelProperty("文章头图")
    private String pic;
}
