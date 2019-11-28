package com.miguo.matrix.entity.miniprogram;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 功能描述：
 *
 * @author Hocassian
 * @date 2019-11-28 10:35
 */
@Slf4j
@Data
@Entity
@Accessors(chain = true)
@ApiModel("小程序公告栏实体")
@Table(name = "vote_records")
public class Record {
}
